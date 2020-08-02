package com.java.brand.domain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Brand.
 */
@Document(collection = "brand")
public class Brand implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@NotNull
	@Field("type")
	@Indexed
	private String type;

	@NotNull
	@Field("name")
	@Indexed
	private String name;

	@NotNull
	@Field("slug")
	@Indexed
	private String slug;

	@Field("description")
	private String description;

	@Field("status")
	private String status;

	@Field("meta")
	private Meta meta = new Meta();

	public String getId() {
		return id;
	}
	public Brand id(String id) {
		this.id = id;
		return this;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public Brand type(String type) {
		this.type = type;
		return this;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public Brand name(String name) {
		this.name = name;
		return this;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public Brand slug(String slug) {
		this.slug = slug;
		return this;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public String getDescription() {
		return description;
	}

	public Brand description(String description) {
		this.description = description;
		return this;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public Brand status(String status) {
		this.status = status;
		return this;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// setters here

	public Meta getMeta() {
		return meta;
	}

	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Brand)) {
			return false;
		}
		return id != null && id.equals(((Brand) o).id);
	}

	@Override
	public int hashCode() {
		return 31;
	}

	// prettier-ignore
	@Override
	public String toString() {
		return "Brand{" + "id=" + getId() + ", type='" + getType() + "'" + ", name='" + getName() + "'" + ", slug='" + getSlug() + "'" + ", description='" + getDescription() + "'" + ", status='"
				+ getStatus() + "'" + "}";
	}
}
