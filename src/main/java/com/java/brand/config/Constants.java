package com.java.brand.config;

/**
 * Application constants.
 */
public final class Constants {

	// Regex for acceptable logins
	public static final String LOGIN_REGEX = "^(?>[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*)|(?>[_.@A-Za-z0-9-]+)$";

	public static final String SYSTEM_ACCOUNT = "system";
	public static final String DEFAULT_LANGUAGE = "en";
	public static final String ANONYMOUS_USER = "anonymoususer";

	// Response and Swagger Implementation
	public static final String NO_CONTENT = "NO CONTENT";
	public static final String RECORD_NOT_FOUND = " No Record Found";
	public static final String X_BRAND_APP_ALERT = "X-Brand App-alert";
	public static final String X_BRAND_APP_PARAM = "X-Brand App-params";
	public static final String MESSAGE_200 = "Successful retrieval";
	public static final String MESSAGE_401 = "Full authentication is required to access this resource";
	public static final String MESSAGE_403 = "Access is denied";
	public static final String MESSAGE_404 = "URL not found";
	public static final String VARIABLE_LIST = "List";
	public static final String MESSAGE_201 = "Record Created";
	public static final String MESSAGE_400 = "Validation Error";
	public static final String RECORD_DELETE = "Record Successfully Delete";

	private Constants() {
	}
}
