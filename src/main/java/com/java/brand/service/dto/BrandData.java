package com.java.brand.service.dto;

import com.java.brand.domain.Brand;

public class BrandData {

	private Brand data;

	public BrandData data(Brand data) {
		this.data = data;
		return this;
	}

	public Brand getData() {
		return data;
	}

	public void setData(Brand data) {
		this.data = data;
	}
}
