package com.hexaware.QuitQApplication.repository;

import com.hexaware.QuitQApplication.model.Payment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	List<Payment> findByCustomer_CustomerId(Long customerId);
}
