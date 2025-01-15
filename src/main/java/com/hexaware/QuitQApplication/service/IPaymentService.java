package com.hexaware.QuitQApplication.service;

import java.util.List;

import com.hexaware.QuitQApplication.dto.PaymentResponseDTO;
import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;

public interface IPaymentService {

	List<PaymentResponseDTO> getAllPayments();

	List<PaymentResponseDTO> getPaymentsByCustomerId(Long customerId);

	PaymentResponseDTO processPayment(Long orderId, String paymentMethod, String transactionId) throws ResourceNotFoundException;

}
