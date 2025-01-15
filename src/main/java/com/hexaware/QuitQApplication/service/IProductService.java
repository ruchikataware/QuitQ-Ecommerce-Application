package com.hexaware.QuitQApplication.service;

import java.util.List;

import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.model.Product;

public interface IProductService {

	List<Product> getAllProducts();

	List<Product> browseProducts(String name, String categoryName, Double minPrice, Double maxPrice,
			String description);

	Product addProduct(Product product) throws ResourceNotFoundException;

	Product updateProduct(Long productId, Product productDetails);
	
	void deleteProductById(Long productId);

}
