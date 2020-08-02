package com.java.brand.domain;

import org.springframework.data.mongodb.core.mapping.Field;

public class Meta {
	@Field("timestamps")
	private TimeStamp timestamps = new TimeStamp();

	public TimeStamp getTimestamps() {
		return timestamps;
	}

	public void setTimestamps(TimeStamp timestamps) {
		this.timestamps = timestamps;
	}
}
