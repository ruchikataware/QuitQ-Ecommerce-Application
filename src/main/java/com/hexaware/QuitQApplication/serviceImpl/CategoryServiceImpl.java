package com.hexaware.QuitQApplication.serviceImpl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.model.Category;
import com.hexaware.QuitQApplication.repository.CategoryRepository;
import com.hexaware.QuitQApplication.service.ICategoryService;

@Service
public class CategoryServiceImpl implements ICategoryService {
	private CategoryRepository categoryRepository;

	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		super();
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<Category> getAllCategories() {
		 List<Category> categories = categoryRepository.findAll();
	        if (categories.isEmpty()) {
	            throw new RuntimeException("No category is listed yet!");
	        }
	        return categories;
	}
}
