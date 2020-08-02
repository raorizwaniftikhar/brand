package com.java.brand.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.java.brand.domain.Brand;

/**
 * Spring Data MongoDB repository for the Brand entity.
 */
@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {
	/**
	 * Get the "name" brand.
	 *
	 * @param name the name of the entity.
	 * @return the entity.
	 */
	Optional<Brand> findByName(String name);

	/**
	 * Get the "slug" brand.
	 *
	 * @param slug the slug of the entity.
	 * @return the entity.
	 */
	List<Brand> findBySlugContainingIgnoreCase(String slug);

	/**
	 * Get the "type" brand.
	 *
	 * @param type the type of the entity.
	 * @return the entity.
	 */
	List<Brand> findByStatus(String string);
}
