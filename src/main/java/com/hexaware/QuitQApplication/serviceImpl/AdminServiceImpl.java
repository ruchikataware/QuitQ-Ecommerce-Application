package com.hexaware.QuitQApplication.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.QuitQApplication.exception.ValidationErrorException;
import com.hexaware.QuitQApplication.model.Category;
import com.hexaware.QuitQApplication.repository.CategoryRepository;
import com.hexaware.QuitQApplication.service.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private CategoryRepository categoryRepo;

	@Override
	@Transactional
	public Category addCategories(Category categoryList) {
		// Save all categories and return the saved list
		return this.categoryRepo.save(categoryList);
	}

	@Override
	@Transactional
	public Category updateCategory(Long categoryId, Category categoryDetails) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
		category.setName(categoryDetails.getName());
		category.setDescription((categoryDetails.getDescription()));
		category.setImage(categoryDetails.getImage());
		return categoryRepo.save(category);
	}

	@Override
	@Transactional
	public void deleteCategory(Long categoryId) {
		if (!categoryRepo.existsById(categoryId)) {
			throw new RuntimeException("Category not found with id: " + categoryId);
		}
		categoryRepo.deleteById(categoryId);
	}

}
