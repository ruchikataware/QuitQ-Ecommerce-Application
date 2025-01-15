package com.hexaware.QuitQApplication.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hexaware.QuitQApplication.model.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

	@Query(value = "SELECT * FROM orders WHERE customer_id = :customerId", nativeQuery = true)
	List<Orders> getOrderByCustomerId(Long customerId);

	@Query(value = "SELECT * FROM orders", nativeQuery = true)
	List<Orders> getAll();

	@Query(value = """
			    SELECT oi.order_item_id AS orderItemId,
			           oi.order_id AS orderId,
			           oi.product_id AS productId,
			           p.name AS productName,
			           oi.quantity AS quantity,
			           oi.price AS price
			    FROM order_item oi
			    JOIN product p ON oi.product_id = p.product_id
			    JOIN seller s ON p.seller_id = s.seller_id
			    WHERE s.seller_id = :sellerId
			""", nativeQuery = true)
	List<Map<String, Object>> findOrderItemsBySeller(@Param("sellerId") Long sellerId);
}
