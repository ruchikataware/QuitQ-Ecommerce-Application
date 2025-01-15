package com.hexaware.QuitQApplication.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.repository.CategoryRepository;
import com.hexaware.QuitQApplication.repository.ProductRepository;
import com.hexaware.QuitQApplication.service.IProductService;

@Service
public class ProductServiceImpl implements IProductService {
	private ProductRepository productRepository;
	private CategoryRepository categoryRepository;

	public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
		super();
		this.productRepository = productRepository;
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public List<Product> browseProducts(String name, String categoryName, Double minPrice, Double maxPrice,
			String description) {
		return productRepository.findProducts(name, categoryName, minPrice, maxPrice, description);
	}

	@Override
	public Product addProduct(Product product) throws ResourceNotFoundException {
		if (!categoryRepository.existsById(product.getCategory().getCategoryId())) {
			throw new ResourceNotFoundException("Category", "ID", product.getCategory().getCategoryId().toString());
		}
		return productRepository.save(product);
	}

	@Override
	public Product updateProduct(Long productId, Product productDetails){
		return productRepository.save(productDetails);
	}

	@Override
	public void deleteProductById(Long productId) {
		if (!productRepository.existsById(productId)) {
			throw new RuntimeException("Product not found with id: " + productId);
		}
		productRepository.deleteById(productId);

	}

}
