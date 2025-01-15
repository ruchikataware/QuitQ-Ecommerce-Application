package com.hexaware.QuitQApplication.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class OrderItemResponse {

	@NotNull(message = "Order Item ID cannot be null.")
	private Long orderItemId;

	@Positive(message = "Quantity must be a positive number.")
	private int quantity;

	@Positive(message = "Price must be a positive value.")
	private double price;

	@NotNull(message = "Seller ID cannot be null.")
	private Long sellerId;

	@NotNull(message = "Seller username cannot be null.")
	@Size(min = 3, max = 50, message = "Seller username must be between 3 and 50 characters.")
	private String sellerUsername;

	// Getters and Setters
	public Long getOrderItemId() {
		return orderItemId;
	}

	public void setOrderItemId(Long orderItemId) {
		this.orderItemId = orderItemId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public String getSellerUsername() {
		return sellerUsername;
	}

	public void setSellerUsername(String sellerUsername) {
		this.sellerUsername = sellerUsername;
	}
}
