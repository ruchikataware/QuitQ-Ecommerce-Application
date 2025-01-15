package com.hexaware.QuitQApplication.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

/*
 * A cart can have multiple cart-items.
 * If cart gets deleted then all its items will also be removed. 
 */

@Entity
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cartId;

	@OneToOne
	@JsonIgnore
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;
	
	
	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
	private List<CartItem> cartItems;

	@Column(nullable = false)
	@CreationTimestamp
	private LocalDateTime createdAt;

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Cart(Long cartId, Customer customer, List<CartItem> cartItems, LocalDateTime createdAt) {
		super();
		this.cartId = cartId;
		this.customer = customer;
		this.cartItems = cartItems;
		this.createdAt = createdAt;
	}

	public Long getCartId() {
		return cartId;
	}

	public void setCartId(Long cartId) {
		this.cartId = cartId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", customer=" + customer + ", cartItems=" + cartItems + ", createdAt="
				+ createdAt + "]";
	}

}
