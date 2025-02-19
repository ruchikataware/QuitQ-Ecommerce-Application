package com.hexaware.QuitQApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQApplication.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByCategoryId(long l);

}