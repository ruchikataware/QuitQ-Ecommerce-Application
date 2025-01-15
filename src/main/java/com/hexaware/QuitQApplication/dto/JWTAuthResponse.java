package com.hexaware.QuitQApplication.dto;

import com.hexaware.QuitQApplication.validation.ValidUserDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class JWTAuthResponse {
	@NotNull(message = "Access token cannot be null.")
    @Size(min = 1, message = "Access token cannot be empty.")
    private String accessToken;

    @NotNull(message = "Token type cannot be null.")
    @Size(min = 1, message = "Token type cannot be empty.")
    private String tokenType = "Bearer"; // Default value

    @NotNull(message = "User details cannot be null.")
    @ValidUserDTO(message = "Invalid user data.")
    private UserDTO userDto;

	public JWTAuthResponse() {
	}

	public JWTAuthResponse(String accessToken, UserDTO userDto) {
		super();
		this.accessToken = accessToken;
		this.userDto = userDto;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getTokenType() {
		return tokenType;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public UserDTO getUserDto() {
		return userDto;
	}

	public void setUserDto(UserDTO userDto) {
		this.userDto = userDto;
	}
}
