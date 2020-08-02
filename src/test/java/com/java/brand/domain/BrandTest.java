package com.java.brand.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.java.brand.web.rest.TestUtil;

public class BrandTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brand.class);
        Brand brand1 = new Brand();
        brand1.setId("id1");
        Brand brand2 = new Brand();
        brand2.setId(brand1.getId());
        assertThat(brand1).isEqualTo(brand2);
        brand2.setId("id2");
        assertThat(brand1).isNotEqualTo(brand2);
        brand1.setId(null);
        assertThat(brand1).isNotEqualTo(brand2);
    }
}
