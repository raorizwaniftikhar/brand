package com.java.brand.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.java.brand.BrandApp;
import com.java.brand.domain.Brand;
import com.java.brand.repository.BrandRepository;
import com.java.brand.service.BrandService;
import com.java.brand.service.dto.BrandDTO;
import com.java.brand.service.dto.BrandData;
import com.java.brand.service.dto.BrandMapper;

/**
 * Integration tests for the {@link BrandResource} REST controller.
 */
@SpringBootTest(classes = BrandApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class BrandResourceIT {

	private static final String DEFAULT_TYPE = "AAAAAAAAAA";
	private static final String UPDATED_TYPE = "BBBBBBBBBB";

	private static final String DEFAULT_NAME = "AAAAAAAAAA";
	private static final String UPDATED_NAME = "BBBBBBBBBB";

	private static final String DEFAULT_SLUG = "AAAAAAAAAA";
	private static final String UPDATED_SLUG = "BBBBBBBBBB";

	private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
	private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

	private static final String DEFAULT_STATUS = "AAAAAAAAAA";
	private static final String UPDATED_STATUS = "BBBBBBBBBB";

	private static final String URL = "/api/v2/brands";

	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private BrandService brandService;

	@Autowired
	private MockMvc restBrandMockMvc;

	private Brand brand;
	BrandDTO brandDTO;
	BrandData brandData;
	BrandMapper brandMapper;

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Brand createEntity() {
		Brand brand = new Brand().type(DEFAULT_TYPE).name(DEFAULT_NAME).slug(DEFAULT_SLUG).description(DEFAULT_DESCRIPTION).status(DEFAULT_STATUS);
		return brand;
	}

	/**
	 * Create an updated entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static Brand createUpdatedEntity() {
		Brand brand = new Brand().type(UPDATED_TYPE).name(UPDATED_NAME).slug(UPDATED_SLUG).description(UPDATED_DESCRIPTION).status(UPDATED_STATUS);
		return brand;
	}

	@BeforeEach
	public void initTest() {
		brandRepository.deleteAll();
		brand = createEntity();
	}

	@Test
	public void createBrand() throws Exception {
		int databaseSizeBeforeCreate = brandRepository.findAll().size();
		// Create the Brand
		brandData = new BrandData().data(brand);
		restBrandMockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brandData))).andExpect(status().isCreated());

		// Validate the Brand in the database
		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeCreate + 1);
		Brand testBrand = brandList.get(brandList.size() - 1);
		assertThat(testBrand.getType()).isEqualTo(DEFAULT_TYPE);
		assertThat(testBrand.getName()).isEqualTo(DEFAULT_NAME);
		assertThat(testBrand.getSlug()).isEqualTo(DEFAULT_SLUG);
		assertThat(testBrand.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
		assertThat(testBrand.getStatus()).isEqualTo(DEFAULT_STATUS);
	}

	@Test
	public void createBrandWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = brandRepository.findAll().size();

		// Create the Brand with an existing ID
		brand.setId("existing_id");
		brandData = new BrandData().data(brand);
		// An entity with an existing ID cannot be created, so this API call must fail
		restBrandMockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brandData))).andExpect(status().isBadRequest());

		// Validate the Brand in the database
		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	public void checkTypeIsRequired() throws Exception {
		int databaseSizeBeforeTest = brandRepository.findAll().size();
		// set the field null
		brand.setType(null);

		// Create the Brand, which fails.
		brandData = new BrandData().data(brand);
		restBrandMockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brandData))).andExpect(status().isBadRequest());

		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void checkNameIsRequired() throws Exception {
		int databaseSizeBeforeTest = brandRepository.findAll().size();
		// set the field null
		brand.setName(null);

		// Create the Brand, which fails.
		brandData = new BrandData().data(brand);
		restBrandMockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brandData))).andExpect(status().isBadRequest());

		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void checkSlugIsRequired() throws Exception {
		int databaseSizeBeforeTest = brandRepository.findAll().size();
		// set the field null
		brand.setSlug(null);

		// Create the Brand, which fails.
		brandData = new BrandData().data(brand);
		restBrandMockMvc.perform(post(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brandData))).andExpect(status().isBadRequest());

		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeTest);
	}

	@Test
	public void getAllBrands() throws Exception {
		// Initialize the database
		brandRepository.save(brand);

		// Get all the brandList
		restBrandMockMvc.perform(get(URL + "?sort=id,desc")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.data.[*].id").value(hasItem(brand.getId()))).andExpect(jsonPath("$.data.[*].type").value(hasItem(DEFAULT_TYPE)))
				.andExpect(jsonPath("$.data.[*].name").value(hasItem(DEFAULT_NAME))).andExpect(jsonPath("$.data.[*].slug").value(hasItem(DEFAULT_SLUG)))
				.andExpect(jsonPath("$.data.[*].description").value(hasItem(DEFAULT_DESCRIPTION))).andExpect(jsonPath("$.data.[*].status").value(hasItem(DEFAULT_STATUS)));
	}

	@Test
	public void getBrand() throws Exception {
		// Initialize the database
		brandRepository.save(brand);

		// Get the brand
		restBrandMockMvc.perform(get(URL + "/{id}", brand.getId())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.data.id").value(brand.getId())).andExpect(jsonPath("$.data.type").value(DEFAULT_TYPE)).andExpect(jsonPath("$.data.name").value(DEFAULT_NAME))
				.andExpect(jsonPath("$.data.slug").value(DEFAULT_SLUG)).andExpect(jsonPath("$.data.description").value(DEFAULT_DESCRIPTION)).andExpect(jsonPath("$.data.status").value(DEFAULT_STATUS));
	}

	@Test
	public void getNonExistingBrand() throws Exception {
		// Get the brand
		restBrandMockMvc.perform(get(URL + "/{id}", Long.MAX_VALUE)).andExpect(status().isNoContent());
	}

	@Test
	public void updateBrand() throws Exception {
		// Initialize the database
		brandService.save(brand);

		int databaseSizeBeforeUpdate = brandRepository.findAll().size();

		// Update the brand
		Brand updatedBrand = brandRepository.findById(brand.getId()).get();
		updatedBrand.type(UPDATED_TYPE).name(UPDATED_NAME).slug(UPDATED_SLUG).description(UPDATED_DESCRIPTION).status(UPDATED_STATUS);
		brandData = new BrandData().data(updatedBrand);
		restBrandMockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brandData))).andExpect(status().isOk());

		// Validate the Brand in the database
		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
		Brand testBrand = brandList.get(brandList.size() - 1);
		assertThat(testBrand.getType()).isEqualTo(UPDATED_TYPE);
		assertThat(testBrand.getName()).isEqualTo(UPDATED_NAME);
		assertThat(testBrand.getSlug()).isEqualTo(UPDATED_SLUG);
		assertThat(testBrand.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
		assertThat(testBrand.getStatus()).isEqualTo(UPDATED_STATUS);
	}

	@Test
	public void updateNonExistingBrand() throws Exception {
		int databaseSizeBeforeUpdate = brandRepository.findAll().size();
		brandData = new BrandData().data(brand);
		// If the entity doesn't have an ID, it will throw BadRequestAlertException
		restBrandMockMvc.perform(put(URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(brandData))).andExpect(status().isBadRequest());

		// Validate the Brand in the database
		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeUpdate);
	}

	@Test
	public void deleteBrand() throws Exception {
		// Initialize the database
		brandService.save(brand);

		int databaseSizeBeforeDelete = brandRepository.findAll().size();

		// Delete the brand
		restBrandMockMvc.perform(delete(URL + "/{id}", brand.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

		// Validate the database contains one less item
		List<Brand> brandList = brandRepository.findAll();
		assertThat(brandList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	public void getBrandByName() throws Exception {
		// Initialize the database
		brandRepository.save(brand);

		// Get the brand
		restBrandMockMvc.perform(get(URL + "/name/{name}", brand.getName())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.data.id").value(brand.getId())).andExpect(jsonPath("$.data.type").value(DEFAULT_TYPE)).andExpect(jsonPath("$.data.name").value(DEFAULT_NAME))
				.andExpect(jsonPath("$.data.slug").value(DEFAULT_SLUG)).andExpect(jsonPath("$.data.description").value(DEFAULT_DESCRIPTION)).andExpect(jsonPath("$.data.status").value(DEFAULT_STATUS));
	}

	@Test
	public void getAllBrandsBySlug() throws Exception {
		// Initialize the database
		brandRepository.save(brand);

		// Get all the brandList by Slug
		restBrandMockMvc.perform(get(URL + "/slug/{slug}", brand.getSlug())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.data.[*].id").value(hasItem(brand.getId()))).andExpect(jsonPath("$.data.[*].type").value(hasItem(DEFAULT_TYPE)))
				.andExpect(jsonPath("$.data.[*].name").value(hasItem(DEFAULT_NAME))).andExpect(jsonPath("$.data.[*].slug").value(hasItem(DEFAULT_SLUG)))
				.andExpect(jsonPath("$.data.[*].description").value(hasItem(DEFAULT_DESCRIPTION))).andExpect(jsonPath("$.data.[*].status").value(hasItem(DEFAULT_STATUS)));
	}

	@Test
	public void getAllBrandsByType() throws Exception {
		// Initialize the database
		brandRepository.save(brand);

		// Get all the brandList by Type
		restBrandMockMvc.perform(get(URL + "/status/{status}", brand.getType())).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.data.[*].id").value(hasItem(brand.getId()))).andExpect(jsonPath("$.data.[*].type").value(hasItem(DEFAULT_TYPE)))
				.andExpect(jsonPath("$.data.[*].name").value(hasItem(DEFAULT_NAME))).andExpect(jsonPath("$.data.[*].slug").value(hasItem(DEFAULT_SLUG)))
				.andExpect(jsonPath("$.data.[*].description").value(hasItem(DEFAULT_DESCRIPTION))).andExpect(jsonPath("$.data.[*].status").value(hasItem(DEFAULT_STATUS)));
	}
}
