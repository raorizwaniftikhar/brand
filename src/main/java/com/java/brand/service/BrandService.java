package com.java.brand.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.java.brand.domain.Brand;

/**
 * Service Interface for managing {@link Brand}.
 */
public interface BrandService {

	/**
	 * Save a brand.
	 *
	 * @param brand the entity to save.
	 * @return the persisted entity.
	 */
	Brand save(Brand brand);

	/**
	 * Get all the brands.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	Page<Brand> findAll(Pageable pageable);

	/**
	 * Get the "id" brand.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	Optional<Brand> findOne(String id);

	/**
	 * Delete the "id" brand.
	 *
	 * @param id the id of the entity.
	 */

	void delete(String id);

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
	List<Brand> findByStatus(String status);
	
	/**
	 * Get the "total" brand.
	 *
	 * @return the entity.
	 */
	Long count();

}
