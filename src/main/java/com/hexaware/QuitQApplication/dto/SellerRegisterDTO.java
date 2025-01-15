package com.hexaware.QuitQApplication.dto;

import com.hexaware.QuitQApplication.model.User.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class SellerRegisterDTO {
	@NotEmpty(message = "Username cannot be empty.")
	@Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters.")
	private String username;

	@NotEmpty(message = "Password cannot be empty.")
	@Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters.")
	private String password;

	@NotEmpty(message = "Email cannot be empty.")
	@Email(message = "Invalid email format.")
	private String email;

	@NotEmpty(message = "Store name cannot be empty.")
	@Size(min = 1, max = 100, message = "Store name must be between 1 and 100 characters.")
	private String storeName;

	@NotEmpty(message = "Business address cannot be empty.")
	@Size(min = 5, max = 255, message = "Business address must be between 5 and 255 characters.")
	private String businessAddress;

	@NotEmpty(message = "Business registration number cannot be empty.")
	@Pattern(regexp = "^[A-Za-z0-9]{5,20}$", message = "Business registration number must be alphanumeric and between 5 to 20 characters.")
	private String businessRegistrationNumber;

	@NotEmpty(message = "Contact person name cannot be empty.")
	@Size(min = 1, max = 100, message = "Contact person name must be between 1 and 100 characters.")
	private String contactPersonName;

	@Pattern(regexp = "^[0-9]{10}$", message = "Contact person phone must be a 10-digit number.")
	private String contactPersonPhone;

	private final Role role = Role.SELLER;

	public Role getRole() {
		return role;
	}

	public SellerRegisterDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SellerRegisterDTO(String username, String password, String email, String storeName, String businessAddress,
			String businessRegistrationNumber, String contactPersonName, String contactPersonPhone) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.storeName = storeName;
		this.businessAddress = businessAddress;
		this.businessRegistrationNumber = businessRegistrationNumber;
		this.contactPersonName = contactPersonName;
		this.contactPersonPhone = contactPersonPhone;
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

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getBusinessAddress() {
		return businessAddress;
	}

	public void setBusinessAddress(String businessAddress) {
		this.businessAddress = businessAddress;
	}

	public String getBusinessRegistrationNumber() {
		return businessRegistrationNumber;
	}

	public void setBusinessRegistrationNumber(String businessRegistrationNumber) {
		this.businessRegistrationNumber = businessRegistrationNumber;
	}

	public String getContactPersonName() {
		return contactPersonName;
	}

	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
	}

	public String getContactPersonPhone() {
		return contactPersonPhone;
	}

	public void setContactPersonPhone(String contactPersonPhone) {
		this.contactPersonPhone = contactPersonPhone;
	}

	@Override
	public String toString() {
		return "SellerRegisterDTO [username=" + username + ", password=" + password + ", email=" + email
				+ ", storeName=" + storeName + ", businessAddress=" + businessAddress + ", businessRegistrationNumber="
				+ businessRegistrationNumber + ", contactPersonName=" + contactPersonName + ", contactPersonPhone="
				+ contactPersonPhone + "]";
	}

}
