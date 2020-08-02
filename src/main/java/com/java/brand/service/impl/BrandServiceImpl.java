package com.java.brand.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.java.brand.domain.Brand;
import com.java.brand.repository.BrandRepository;
import com.java.brand.service.BrandService;

/**
 * Service Implementation for managing {@link Brand}.
 */
@Service
public class BrandServiceImpl implements BrandService {

	private final Logger log = LoggerFactory.getLogger(BrandServiceImpl.class);

	private final BrandRepository brandRepository;

	public BrandServiceImpl(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}

	/**
	 * Save a brand.
	 *
	 * @param brand the entity to save.
	 * @return the persisted entity.
	 */
	@Override
	public Brand save(Brand brand) {
		log.debug("Request to save Brand : {}", brand);
		return brandRepository.save(brand);
	}

	/**
	 * Get all the brands.
	 *
	 * @param pageable the pagination information.
	 * @return the list of entities.
	 */
	@Override
	public Page<Brand> findAll(Pageable pageable) {
		log.debug("Request to get all Brands");
		return brandRepository.findAll(pageable);
	}

	/**
	 * Get one brand by id.
	 *
	 * @param id the id of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<Brand> findOne(String id) {
		log.debug("Request to get Brand : {}", id);
		return brandRepository.findById(id);
	}

	/**
	 * Delete the brand by id.
	 *
	 * @param id the id of the entity.
	 */
	@Override
	public void delete(String id) {
		log.debug("Request to delete Brand : {}", id);
		brandRepository.deleteById(id);
	}

	/**
	 * Get the "name" brand.
	 *
	 * @param name the name of the entity.
	 * @return the entity.
	 */
	@Override
	public Optional<Brand> findByName(String name) {
		log.debug("Request to get Brand by name : {}", name);
		return brandRepository.findByName(name);
	}

	/**
	 * Get the "slug" brand.
	 *
	 * @param slug the slug of the entity.
	 * @return the entity.
	 */
	@Override
	public List<Brand> findBySlugContainingIgnoreCase(String slug) {
		log.debug("Request to get Brands by slug : {}", slug);
		return brandRepository.findBySlugContainingIgnoreCase(slug);
	}

	/**
	 * Get the "type" brand.
	 *
	 * @param type the type of the entity.
	 * @return the entity.
	 */
	@Override
	public List<Brand> findByStatus(String status) {
		log.debug("Request to get Brand by type : {}", status);
		return brandRepository.findByStatus(status);
	}

	@Override
	public Long count() {
		log.debug("Request to get total Brand ");
		return brandRepository.count();
	}
}
