package com.hexaware.QuitQApplication.exception;

import java.util.Map;

public class ValidationErrorException extends RuntimeException {

	private final Map<String, String> validationErrors;

	public ValidationErrorException(String message, Map<String, String> validationErrors) {
		super(message);
		this.validationErrors = validationErrors;
	}

	public Map<String, String> getValidationErrors() {
		return validationErrors;
	}
}
