package com.java.brand.service.dto;

import java.util.Optional;

import com.java.brand.domain.Brand;

/**
 * A DTO representing a user, with his authorities.
 */
public class BrandDTO {
	private Optional<Brand> data;

	public Optional<Brand> getData() {
		return data;
	}

	public void setData(Optional<Brand> data) {
		this.data = data;
	}

}
