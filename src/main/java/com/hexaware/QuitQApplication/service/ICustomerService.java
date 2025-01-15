package com.hexaware.QuitQApplication.service;

import java.util.List;

import com.hexaware.QuitQApplication.model.Category;
import com.hexaware.QuitQApplication.model.Customer;

public interface ICustomerService {
	public Customer saveCustomerDetails(Customer customer);

	public List<Category> allCategories();

	List<Customer> getAllCustomers();

	void deleteCustomerById(Long customerId);
	
	Customer findByCustomerId(Long customerId);

}
