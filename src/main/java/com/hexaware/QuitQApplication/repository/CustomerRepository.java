package com.hexaware.QuitQApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.QuitQApplication.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByCustomerId(Long customerId);

	Customer findByUsername(String username);
}
