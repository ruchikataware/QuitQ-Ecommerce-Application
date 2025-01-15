package com.hexaware.QuitQApplication.repository;

import com.hexaware.QuitQApplication.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
	Seller findByUsername(String username);

	Seller findBySellerId(Long sellerId);
}
