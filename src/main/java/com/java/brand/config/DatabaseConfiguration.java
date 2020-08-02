package com.java.brand.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.event.ValidatingMongoEventListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import io.github.jhipster.domain.util.JSR310DateConverters.DateToZonedDateTimeConverter;
import io.github.jhipster.domain.util.JSR310DateConverters.ZonedDateTimeToDateConverter;

@Configuration
@EnableMongoRepositories("com.java.brand.repository")
@Import(value = MongoAutoConfiguration.class)
public class DatabaseConfiguration {

	private final Logger log = LoggerFactory.getLogger(DatabaseConfiguration.class);

	@Bean
	public ValidatingMongoEventListener validatingMongoEventListener() {
		return new ValidatingMongoEventListener(validator());
	}

	@Bean
	public LocalValidatorFactoryBean validator() {
		return new LocalValidatorFactoryBean();
	}

	@Bean
	public MongoCustomConversions customConversions() {
		log.debug("Date and Time Conversions");
		List<Converter<?, ?>> converters = new ArrayList<>();
		converters.add(DateToZonedDateTimeConverter.INSTANCE);
		converters.add(ZonedDateTimeToDateConverter.INSTANCE);
		return new MongoCustomConversions(converters);
	}

}
