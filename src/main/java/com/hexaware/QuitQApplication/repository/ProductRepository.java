package com.hexaware.QuitQApplication.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.QuitQApplication.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	// Product browsing for customer query
	@Query("SELECT p FROM Product p WHERE " + "(:name IS NULL OR p.name = :name) AND "
			+ "(:category IS NULL OR p.category.name = :category) AND "
			+ "(:minPrice IS NULL OR p.price >= :minPrice) AND " + "(:maxPrice IS NULL OR p.price <= :maxPrice) AND "
			+ "(:description IS NULL OR p.description LIKE %:description%)")
	List<Product> findProducts(@Param("name") String name, @Param("category") String category,
			@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
			@Param("description") String description);

	Product findByName(String name);
	// Optional<Product> findById(Long productId);

	@Query("SELECT p FROM Product p WHERE p.seller.sellerId = :sellerId ORDER BY p.category.name")
	List<Product> findAllBySellerIdOrderByCategory(Long sellerId);

	@Query("SELECT p FROM Product p WHERE p.seller.sellerId = :sellerId AND p.category.categoryId = :categoryId")
	List<Product> getProductsBySellerIdAndCategoryId(Long sellerId, Long categoryId);

	@Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
	List<Product> getProductsByCategoryId(Long categoryId);
}
