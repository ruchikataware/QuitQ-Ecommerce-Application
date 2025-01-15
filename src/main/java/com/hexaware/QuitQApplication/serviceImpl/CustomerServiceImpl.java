package com.hexaware.QuitQApplication.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.exception.ValidationErrorException;
import com.hexaware.QuitQApplication.model.Category;
import com.hexaware.QuitQApplication.model.Customer;
import com.hexaware.QuitQApplication.repository.CategoryRepository;
import com.hexaware.QuitQApplication.repository.CustomerRepository;
import com.hexaware.QuitQApplication.service.ICustomerService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServiceImpl implements ICustomerService {

	private final CustomerRepository customerRepo;
	private final CategoryRepository categoryRepo;

	public CustomerServiceImpl(CustomerRepository customerRepo, CategoryRepository categoryRepo) {
		this.customerRepo = customerRepo;
		this.categoryRepo = categoryRepo;
	}

	@Override
	public Customer saveCustomerDetails(Customer customer) {
		if (customer == null) {
			throw new ValidationErrorException("Customer cannot be null", Map.of("customer", "Null value provided"));
		}
		return this.customerRepo.save(customer);
	}

	@Override
	public List<Category> allCategories() {
		List<Category> categories = this.categoryRepo.findAll();
		if (categories.isEmpty()) {
			throw new RuntimeException("There is no category listed yet!");
		}
		return categories;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerRepo.findAll();
		if (customers.isEmpty()) {
			throw new RuntimeException("There is no customer registered yet!");
		}
		return customers;
	}

	@Override
	public void deleteCustomerById(Long customerId) {
		if (!customerRepo.existsById(customerId)) {
			throw new RuntimeException("Customer not found with id: " + customerId);
		}
		customerRepo.deleteById(customerId);
	}

	@Override
	public Customer findByCustomerId(Long customerId) {
		return customerRepo.findByCustomerId(customerId);
	}

}
