package com.hexaware.QuitQApplication.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserDTO {
	@NotNull(message = "Username cannot be null.")
	@NotEmpty(message = "Username cannot be empty.")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
	private String username;

	@NotNull(message = "Email cannot be null.")
	@NotEmpty(message = "Email cannot be empty.")
	@Email(message = "Invalid email format.")
	private String email;

	@NotNull(message = "Role cannot be null.")
	@NotEmpty(message = "Role cannot be empty.")
	@Size(min = 3, max = 20, message = "Role must be between 3 and 20 characters.")
	private String role;

	@NotNull(message = "Id cannot be null.")
	@NotEmpty(message = "Id cannot be empty.")
	private Long id;

	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserDTO(
			@NotNull(message = "Username cannot be null.") @NotEmpty(message = "Username cannot be empty.") @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.") String username,
			@NotNull(message = "Email cannot be null.") @NotEmpty(message = "Email cannot be empty.") @Email(message = "Invalid email format.") String email,
			@NotNull(message = "Role cannot be null.") @NotEmpty(message = "Role cannot be empty.") @Size(min = 3, max = 20, message = "Role must be between 3 and 20 characters.") String role,
			@NotNull(message = "Id cannot be null.") @NotEmpty(message = "Id cannot be empty.") Long id) {
		super();
		this.username = username;
		this.email = email;
		this.role = role;
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "UserDTO [username=" + username + ", email=" + email + ", role=" + role + ", id=" + id + "]";
	}
}
