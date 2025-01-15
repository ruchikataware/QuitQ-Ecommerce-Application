package com.hexaware.QuitQApplication.service;

import java.util.List;

import com.hexaware.QuitQApplication.model.Category;

public interface IAdminService {
	public Category addCategories(Category categoryList);

	Category updateCategory(Long categoryId, Category categoryDetails);

	void deleteCategory(Long categoryId);
}
