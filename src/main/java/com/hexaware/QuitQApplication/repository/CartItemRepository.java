package com.hexaware.QuitQApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.QuitQApplication.model.CartItem;

import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM cart_item WHERE cart_id = :cartId", nativeQuery = true)
	void deleteItems(@Param("cartId") Long cartId);

}
