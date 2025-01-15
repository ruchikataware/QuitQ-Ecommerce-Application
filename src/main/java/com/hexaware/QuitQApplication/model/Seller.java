package com.hexaware.QuitQApplication.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

/*
 * A seller can have multiple products.
 * If seller gets removed then its all products will be removed.
 */

@Entity
public class Seller {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sellerId;

	@Column(nullable = false, unique = true, length = 50)
	private String username;

	@Column(nullable = false, length = 100)
	private String storeName;

	private String businessAddress;

	@Column(nullable = false, unique = true, length = 50)
	private String businessRegistrationNumber;

	private String contactPersonName;

	@Column(length = 15)
	private String contactPersonPhone;

	private LocalDateTime establishedDate;

	private String profilePic;

	@OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Product> products;

	public Seller() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Seller(Long sellerId, String username, String storeName, String businessAddress, String businessRegistrationNumber,
			String contactPersonName, String contactPersonPhone, LocalDateTime establishedDate,
			String profilePic, List<Product> products) {
		super();
		this.sellerId = sellerId;
		this.username = username;
		this.storeName = storeName;
		this.businessAddress = businessAddress;
		this.businessRegistrationNumber = businessRegistrationNumber;
		this.contactPersonName = contactPersonName;
		this.contactPersonPhone = contactPersonPhone;
		this.establishedDate = establishedDate;
		this.profilePic = profilePic;
		this.products = products;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public LocalDateTime getEstablishedDate() {
		return establishedDate;
	}

	public void setEstablishedDate(LocalDateTime establishedDate) {
		this.establishedDate = establishedDate;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	@Override
	public String toString() {
		return "Seller [sellerId=" + sellerId + ", username=" + username + ", storeName=" + storeName
				+ ", businessAddress=" + businessAddress + ", businessRegistrationNumber=" + businessRegistrationNumber
				+ ", contactPersonName=" + contactPersonName + ", contactPersonPhone=" + contactPersonPhone
				+ ", establishedDate=" + establishedDate + ", profilePic=" + profilePic + ", products=" + products
				+ "]";
	}

}
