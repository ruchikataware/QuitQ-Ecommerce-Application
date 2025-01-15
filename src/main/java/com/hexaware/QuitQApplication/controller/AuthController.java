package com.hexaware.QuitQApplication.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.QuitQApplication.dto.CustomerRegisterDTO;
import com.hexaware.QuitQApplication.dto.JWTAuthResponse;
import com.hexaware.QuitQApplication.dto.LoginDTO;
import com.hexaware.QuitQApplication.dto.SellerRegisterDTO;
import com.hexaware.QuitQApplication.exception.BadRequestException;
import com.hexaware.QuitQApplication.service.IAuthService;

import jakarta.validation.Valid;

/*
 * 0. Different register and login API routes for different users (ADMIN, CUSTOMER, SELLER) 
 */

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

	private final IAuthService authService;

	public AuthController(IAuthService authService) {
		this.authService = authService;
	}

	@PostMapping("/register-customer")
	public ResponseEntity<String> registerCustomer(@Valid @RequestBody CustomerRegisterDTO dto) {
		try {
			String value = authService.registerCustomer(dto);
			return new ResponseEntity<>(value, HttpStatus.CREATED);
		} catch (BadRequestException e) {
			throw new BadRequestException(e.getStatus(), e.getMessage());
		}
	}

	@PostMapping("/register-seller")
	public ResponseEntity<String> registerSeller(@Valid @RequestBody SellerRegisterDTO dto) {
		try {
			String value = authService.registerSeller(dto);
			return new ResponseEntity<>(value, HttpStatus.CREATED);
		} catch (BadRequestException e) {
			throw new BadRequestException(e.getStatus(), e.getMessage());
		}
	}

	@PostMapping(value = { "/login-admin", "/login-customer", "login-seller" })
	public ResponseEntity<JWTAuthResponse> login(@Valid @RequestBody LoginDTO dto) {
		try {
			JWTAuthResponse token = authService.login(dto);
			return ResponseEntity.ok(token);
		} catch (BadRequestException e) {
			throw new BadRequestException(e.getStatus(), e.getMessage());
		}
	}

}