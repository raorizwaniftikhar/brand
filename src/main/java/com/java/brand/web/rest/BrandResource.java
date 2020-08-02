package com.java.brand.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.java.brand.config.Constants;
import com.java.brand.domain.Brand;
import com.java.brand.service.BrandService;
import com.java.brand.service.dto.BrandDTO;
import com.java.brand.service.dto.BrandData;
import com.java.brand.service.dto.BrandMapper;
import com.java.brand.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

/**
 * REST controller for managing {@link com.java.brand.domain.Brand}.
 */
@RestController
@RequestMapping(value = "/api/v2", produces = MediaType.APPLICATION_JSON_VALUE)

public class BrandResource {

	private final Logger log = LoggerFactory.getLogger(BrandResource.class);

	private static final String ENTITY_NAME = "brand";

	@Value("${jhipster.clientApp.name}")
	private String applicationName;

	private final BrandService brandService;

	public BrandResource(BrandService brandService) {
		this.brandService = brandService;
	}

	/**
	 * {@code POST  /brands} : Create a new brand.
	 *
	 * @param brand the brand to create.
	 * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with
	 *         body the new brand, or with status {@code 400 (Bad Request)} if the
	 *         brand has already an ID.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 201, message = Constants.MESSAGE_201, response = Brand.class, responseContainer = ENTITY_NAME) })
	@PostMapping("/brands")
	public ResponseEntity<BrandData> createBrand(@Valid @RequestBody BrandData brandData) throws URISyntaxException {
		log.debug("REST request to save Brand : {}", brandData);
		if (brandData.getData().getId() != null) {
			throw new BadRequestAlertException("A new brand cannot already have an ID", ENTITY_NAME, "idexists");
		}
		Brand brand = new Brand().type(brandData.getData().getType()).name(brandData.getData().getName())
				.slug(brandData.getData().getSlug()).description(brandData.getData().getDescription())
				.status(brandData.getData().getStatus());
		Brand result = brandService.save(brand);
		brandData.getData().setId(result.getId());
		return ResponseEntity.created(new URI("/api/brands/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
				.body(brandData);
	}

	/**
	 * {@code PUT  /brands} : Updates an existing brand.
	 *
	 * @param brand the brand to update.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the updated brand, or with status {@code 400 (Bad Request)} if the
	 *         brand is not valid, or with status
	 *         {@code 500 (Internal Server Error)} if the brand couldn't be updated.
	 * @throws URISyntaxException if the Location URI syntax is incorrect.
	 */
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 201, message = Constants.MESSAGE_201, response = Brand.class, responseContainer = ENTITY_NAME) })
	@PutMapping("/brands")
	public ResponseEntity<BrandData> updateBrand(@Valid @RequestBody BrandData brandData) throws URISyntaxException {
		log.debug("REST request to update Brand : {}", brandData);
		if (brandData.getData().getId() == null) {
			throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
		}
		Brand brand = new Brand().id(brandData.getData().getId()).type(brandData.getData().getType())
				.name(brandData.getData().getName()).slug(brandData.getData().getSlug())
				.description(brandData.getData().getDescription()).status(brandData.getData().getStatus());
		Brand result = brandService.save(brand);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId()))
				.body(brandData);
	}

	/**
	 * {@code GET  /brands} : get all the brands.
	 *
	 * @param pageable the pagination information.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list
	 *         of brands in body.
	 */
	@GetMapping("/brands")
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Brand.class, responseContainer = Constants.VARIABLE_LIST) })
	public ResponseEntity<BrandMapper> getAllBrands(Pageable pageable) {
		log.debug("REST request to get a page of Brands");
		Page<Brand> page = brandService.findAll(pageable);

		if (page == null || page.isEmpty()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, null)).build();
		}
		BrandMapper brandMapper = new BrandMapper();
		brandMapper.setData(page.getContent());
		HttpHeaders headers = PaginationUtil
				.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

		return ResponseEntity.ok().headers(headers).body(brandMapper);
	}

	/**
	 * {@code GET  /brands/:id} : get the "id" brand.
	 *
	 * @param id the id of the brand to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the brand, or with status {@code 404 (Not Found)}.
	 */

	@ApiResponses(value = { @ApiResponse(code = 204, message = Constants.NO_CONTENT, responseHeaders = {
			@ResponseHeader(name = Constants.X_BRAND_APP_ALERT, response = String.class, description = Constants.RECORD_NOT_FOUND),
			@ResponseHeader(name = Constants.X_BRAND_APP_PARAM, response = String.class, description = "BRAND ID") }),
			@ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Brand.class, responseContainer = ENTITY_NAME) })
	@GetMapping("/brands/{id}")
	public ResponseEntity<BrandDTO> getBrand(@PathVariable String id) {
		log.debug("REST request to get Brand : {}", id);
		Optional<Brand> brand = brandService.findOne(id);

		if (brand.isEmpty()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, id)).build();
		}
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setData(brand);
		return ResponseEntity.ok().body(brandDTO);
	}

	/**
	 * {@code DELETE  /brands/:id} : delete the "id" brand.
	 *
	 * @param id the id of the brand to delete.
	 * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
	 */
	@ApiResponses(value = { @ApiResponse(code = 200, message = Constants.NO_CONTENT, responseHeaders = {
			@ResponseHeader(name = Constants.X_BRAND_APP_ALERT, response = String.class, description = Constants.RECORD_DELETE),
			@ResponseHeader(name = Constants.X_BRAND_APP_PARAM, response = String.class, description = "BRAND ID") }),
			@ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403) })
	@DeleteMapping("/brands/{id}")
	public ResponseEntity<Void> deleteBrand(@PathVariable String id) {
		log.debug("REST request to delete Brand : {}", id);
		brandService.delete(id);
		return ResponseEntity.noContent()
				.headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
	}

	/**
	 * {@code GET  /brands/name/:name} : get the "name" brand.
	 *
	 * @param name the name of the brand to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the brand, or with status {@code 404 (Not Found)}.
	 */
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Brand.class, responseContainer = ENTITY_NAME) })
	@GetMapping("/brands/name/{name}")
	public ResponseEntity<BrandDTO> getBrandByName(@PathVariable String name) {
		log.debug("REST request to get Brand by name : {}", name);
		Optional<Brand> brand = brandService.findByName(name);
		if (brand.isEmpty()) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, name)).build();
		}
		BrandDTO brandDTO = new BrandDTO();
		brandDTO.setData(brand);
		return ResponseEntity.ok().body(brandDTO);
	}

	/**
	 * {@code GET  /brands/slug/:slug} : get the "slug" brand.
	 *
	 * @param slug the slug of the brand to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the brand, or with status {@code 404 (Not Found)}.
	 */
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Brand.class, responseContainer = Constants.VARIABLE_LIST) })
	@GetMapping("/brands/slug/{slug}")
	public ResponseEntity<BrandMapper> getBrandBySlug(@PathVariable String slug) {
		log.debug("REST request to get Brand by Slug : {}", slug);
		List<Brand> brands = brandService.findBySlugContainingIgnoreCase(slug);
		if (brands.size() < 1) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, slug)).build();
		}
		BrandMapper brandMapper = new BrandMapper();
		brandMapper.setData(brands);
		return ResponseEntity.ok().body(brandMapper);
	}

	/**
	 * {@code GET  /brands/status/:status} : get the "type" brand.
	 *
	 * @param type the type of the brand to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the brand, or with status {@code 404 (Not Found)}.
	 */
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Brand.class, responseContainer = Constants.VARIABLE_LIST) })
	@GetMapping("/brands/status/{status}")
	public ResponseEntity<BrandMapper> getBrandByStatus(@PathVariable String status) {
		log.debug("REST request to get Brand by Type : {}", status);
		List<Brand> brands = brandService.findByStatus(status);
		if (brands.size() < 1) {
			return ResponseEntity.noContent()
					.headers(HeaderUtil.createAlert(applicationName, Constants.RECORD_NOT_FOUND, status)).build();
		}
		BrandMapper brandMapper = new BrandMapper();
		brandMapper.setData(brands);
		return ResponseEntity.ok().body(brandMapper);
	}

	/**
	 * {@code GET  /brands/count} : get the "total brands number" brand.
	 *
	 * @param type the type of the brand to retrieve.
	 * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body
	 *         the brand, or with status {@code 404 (Not Found)}.
	 */
	@ApiResponses(value = { @ApiResponse(code = 404, message = Constants.MESSAGE_404),
			@ApiResponse(code = 401, message = Constants.MESSAGE_401),
			@ApiResponse(code = 403, message = Constants.MESSAGE_403),
			@ApiResponse(code = 400, message = Constants.MESSAGE_400),
			@ApiResponse(code = 200, message = Constants.MESSAGE_200, response = Brand.class, responseContainer = Constants.VARIABLE_LIST) })
	@GetMapping("/brands/count")
	public ResponseEntity<Long> count() {
		log.debug("REST request to get total count Brand ");
		return ResponseEntity.ok().body(brandService.count());
	}
}
