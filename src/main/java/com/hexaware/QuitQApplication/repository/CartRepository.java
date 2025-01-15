package com.hexaware.QuitQApplication.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQApplication.model.Cart;
import com.hexaware.QuitQApplication.model.Product;

public interface CartRepository extends JpaRepository<Cart, Long> {
	Optional<Cart> findByCustomerCustomerId(Long customerId);
}
