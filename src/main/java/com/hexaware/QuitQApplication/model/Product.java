package com.hexaware.QuitQApplication.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

/*
 * 
 */

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;

	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "seller_id", nullable = false)
	private Seller seller;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@Column(nullable = false, length = 100)
	private String name;
	
	@Column(nullable = false, length = 100)
	private String brandName;

	private String description;

	@Column(nullable = false)
	private double price;

	private int stockQuantity;

	private String image;

	@OneToMany(mappedBy = "product")
	@JsonIgnore
	private List<OrderItem> orderItems;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<CartItem> cartItems;

	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(Long productId, Seller seller, Category category, String name, String brandName, String description, double price,
			int stockQuantity, String image, List<OrderItem> orderItems, List<CartItem> cartItems) {
		super();
		this.productId = productId;
		this.seller = seller;
		this.category = category;
		this.name = name;
		this.brandName = brandName;
		this.description = description;
		this.price = price;
		this.stockQuantity = stockQuantity;
		this.image = image;
		this.orderItems = orderItems;
		this.cartItems = cartItems;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", seller=" + seller + ", category=" + category + ", name=" + name
				+ ", brandName=" + brandName + ", description=" + description + ", price=" + price + ", stockQuantity="
				+ stockQuantity + ", image=" + image + ", orderItems=" + orderItems + ", cartItems=" + cartItems + "]";
	}

}
