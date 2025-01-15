package com.hexaware.QuitQApplication.dto;

import com.hexaware.QuitQApplication.model.Customer.Gender;
import com.hexaware.QuitQApplication.model.User.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerRegisterDTO {
	@NotEmpty(message = "Username cannot be empty.")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
	private String username;

	@NotEmpty(message = "Password cannot be empty.")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
	private String password;

	@NotEmpty(message = "Email cannot be empty.")
	@Email(message = "Invalid email format.")
	private String email;

	@NotEmpty(message = "First name cannot be empty.")
	@Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters.")
	private String firstName;

	@Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters.")
	private String lastName;

	@NotEmpty(message = "Address cannot be empty.")
	@Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters.")
	private String address;

	@Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits long.")
	private String phoneNumber;

	@NotNull(message = "Gender cannot be null.")
	private Gender gender;

	private final Role role = Role.CUSTOMER;

	public Role getRole() {
		return role;
	}

	public CustomerRegisterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomerRegisterDTO(String username, String password, String email, String firstName, String lastName,
			String address, String phoneNumber, Gender gender) {
		super();
		this.username = username;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "CustomerRegisterDTO [username=" + username + ", password=" + password + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", phoneNumber="
				+ phoneNumber + ", gender=" + gender + "]";
	}

}
