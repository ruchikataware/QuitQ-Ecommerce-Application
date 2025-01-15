package com.hexaware.QuitQApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.QuitQApplication.model.Category;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.repository.ProductRepository;
import com.hexaware.QuitQApplication.service.ICategoryService;
import com.hexaware.QuitQApplication.service.IProductService;

/*
 * 0. API end-points that are accessible w/o login 
 */

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiController {
	private ICategoryService categoryService;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private IProductService productService;

	public ApiController(ICategoryService categoryService) {
		super();
		this.categoryService = categoryService;
	}

	@GetMapping("/show-categories")
	public List<Category> showCategories() {
		List<Category> categories = categoryService.getAllCategories();
		if (categories.isEmpty()) {
			throw new RuntimeException("No category is listed yet!");
		}
		return categories;
	}

	@GetMapping("/view-products")
	public List<Product> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		if (products.isEmpty()) {
			throw new RuntimeException("No products available.");
		}
		return products;
	}

	@GetMapping("/{categoryId}/products-by-category")
	public List<Product> getProductsByCategory(@PathVariable Long categoryId) {
		return productRepo.getProductsByCategoryId(categoryId);
	}

}
