package com.java.brand.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Brand.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {
}
