package com.hexaware.QuitQApplication.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends RuntimeException {
	private HttpStatus status;
	private String message;

	public BadRequestException(HttpStatus status, String message) {
		this.status = status;
		this.message = message;
	}

	public BadRequestException(HttpStatus status, String message, String message2) {
		super(message);
		this.status = status;
		this.message = message2;
	}

	public BadRequestException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus sataus) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
