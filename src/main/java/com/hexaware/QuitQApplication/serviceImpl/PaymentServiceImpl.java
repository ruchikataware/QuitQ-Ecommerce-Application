package com.hexaware.QuitQApplication.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.dto.OrderItemResponse;
import com.hexaware.QuitQApplication.dto.PaymentResponseDTO;
import com.hexaware.QuitQApplication.exception.BadRequestException;
import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.model.Customer;
import com.hexaware.QuitQApplication.model.Orders;
import com.hexaware.QuitQApplication.model.Orders.OrderStatus;
import com.hexaware.QuitQApplication.model.Payment;
import com.hexaware.QuitQApplication.model.Seller;
import com.hexaware.QuitQApplication.repository.OrderRepository;
import com.hexaware.QuitQApplication.repository.PaymentRepository;
import com.hexaware.QuitQApplication.service.IPaymentService;

@Service
public class PaymentServiceImpl implements IPaymentService {
	private PaymentRepository paymentRepository;
	private OrderRepository orderRepository;

	public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
		super();
		this.paymentRepository = paymentRepository;
		this.orderRepository = orderRepository;
	}

	// Process payment for a specific order
	@Override
	public PaymentResponseDTO processPayment(Long orderId, String paymentMethod, String transactionId)
			throws ResourceNotFoundException {
		// Fetch the order by ID
		Orders order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "ID", orderId.toString()));

		// Validate the order status
		if (!order.getStatus().equals(OrderStatus.PENDING)) {
			throw new BadRequestException(HttpStatus.BAD_REQUEST, "Order is not in a valid state for payment.");
		}

		// Create a new payment record
		Payment payment = new Payment();
		payment.setAmount(order.getTotalAmount());
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentStatus("COMPLETED");
		payment.setPaymentMethod(paymentMethod);
		payment.setTransactionId(transactionId);
		payment.setOrder(order);
		payment.setCustomer(order.getCustomer());

		// Save the payment record
		Payment savedPayment = paymentRepository.save(payment);

		// Update the order status to SHIPPED
		order.setStatus(OrderStatus.SHIPPED);
		orderRepository.save(order);

		// Prepare the payment response
		PaymentResponseDTO response = new PaymentResponseDTO();

		// Set customer details
		Customer customer = order.getCustomer();
		response.setCustomerId(customer.getCustomerId());
		response.setCustomerUsername(customer.getUsername());

		// Set order details
		response.setOrderId(order.getOrderId());
		response.setOrderDate(order.getOrderDate());
		response.setOrderStatus(order.getStatus().name()); // Convert to String
		response.setTotalAmount(order.getTotalAmount());
		response.setShippingAddress(order.getShippingAddress());

		// Map order items to OrderItemResponse
		List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream().map(orderItem -> {
			OrderItemResponse itemResponse = new OrderItemResponse();
			itemResponse.setOrderItemId(orderItem.getOrderItemId());
			itemResponse.setQuantity(orderItem.getQuantity());
			itemResponse.setPrice(orderItem.getPrice());

			// Set seller details
			Seller seller = orderItem.getProduct().getSeller();
			itemResponse.setSellerId(seller.getSellerId());
			itemResponse.setSellerUsername(seller.getUsername());

			return itemResponse;
		}).collect(Collectors.toList());
		response.setOrderItems(orderItemResponses);

		// Set payment details
		response.setAmount(savedPayment.getAmount());
		response.setPaymentDate(savedPayment.getPaymentDate());
		response.setPaymentStatus(savedPayment.getPaymentStatus());
		response.setPaymentMethod(savedPayment.getPaymentMethod());
		response.setTransactionId(savedPayment.getTransactionId());

		return response;
	}

	// Get all payments for a specific customer and return them as PaymentResponse
	// objects
	@Override
	public List<PaymentResponseDTO> getPaymentsByCustomerId(Long customerId) {
		// Fetch payments from the database for the given customer ID
		List<Payment> payments = paymentRepository.findByCustomer_CustomerId(customerId);
		if (payments.isEmpty()) {
			throw new RuntimeException("No order is placed yet for customer with customer_id" + customerId);
		}
		// Convert the list of Payment entities to PaymentResponse objects
		return payments.stream().map(payment -> {
			PaymentResponseDTO response = new PaymentResponseDTO();

			// Set customer details
			Customer customer = payment.getCustomer();
			response.setCustomerId(customer.getCustomerId());
			response.setCustomerUsername(customer.getUsername());

			// Set order details
			Orders order = payment.getOrder();
			response.setOrderId(order.getOrderId());
			response.setOrderDate(order.getOrderDate());
			response.setOrderStatus(order.getStatus().name()); // Convert enum to string
			response.setTotalAmount(order.getTotalAmount());
			response.setShippingAddress(order.getShippingAddress());

			// Map order items to OrderItemResponse
			List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream().map(orderItem -> {
				OrderItemResponse itemResponse = new OrderItemResponse();
				itemResponse.setOrderItemId(orderItem.getOrderItemId());
				itemResponse.setQuantity(orderItem.getQuantity());
				itemResponse.setPrice(orderItem.getPrice());

				// Set seller details
				Seller seller = orderItem.getProduct().getSeller();
				itemResponse.setSellerId(seller.getSellerId());
				itemResponse.setSellerUsername(seller.getUsername());

				return itemResponse;
			}).collect(Collectors.toList());
			response.setOrderItems(orderItemResponses);

			// Set payment details
			response.setAmount(payment.getAmount());
			response.setPaymentDate(payment.getPaymentDate());
			response.setPaymentStatus(payment.getPaymentStatus());
			response.setPaymentMethod(payment.getPaymentMethod());
			response.setTransactionId(payment.getTransactionId());

			return response;
		}).collect(Collectors.toList());
	}

	// Get all payments (admin access)
	@Override
	public List<PaymentResponseDTO> getAllPayments() {
		List<Payment> payments = paymentRepository.findAll();
		if (payments.isEmpty()) {
			throw new RuntimeException("No order is placed yet");
		}
		return payments.stream().map(payment -> {
			PaymentResponseDTO response = new PaymentResponseDTO();

			// Set customer details
			Customer customer = payment.getCustomer();
			response.setCustomerId(customer.getCustomerId());
			response.setCustomerUsername(customer.getUsername());

			// Set order details
			Orders order = payment.getOrder();
			response.setOrderId(order.getOrderId());
			response.setOrderDate(order.getOrderDate());

			// Convert OrderStatus to String
			response.setOrderStatus(order.getStatus().name()); // Convert to String using .name()

			response.setTotalAmount(order.getTotalAmount());
			response.setShippingAddress(order.getShippingAddress());

			// Map order items to OrderItemResponse
			List<OrderItemResponse> orderItemResponses = order.getOrderItems().stream().map(orderItem -> {
				OrderItemResponse itemResponse = new OrderItemResponse();
				itemResponse.setOrderItemId(orderItem.getOrderItemId());
				itemResponse.setQuantity(orderItem.getQuantity());
				itemResponse.setPrice(orderItem.getPrice());

				// Set seller details
				Seller seller = orderItem.getProduct().getSeller();
				itemResponse.setSellerId(seller.getSellerId());
				itemResponse.setSellerUsername(seller.getUsername());

				return itemResponse;
			}).collect(Collectors.toList());
			response.setOrderItems(orderItemResponses);

			// Set payment details
			response.setAmount(payment.getAmount());
			response.setPaymentDate(payment.getPaymentDate());
			response.setPaymentStatus(payment.getPaymentStatus());
			response.setPaymentMethod(payment.getPaymentMethod());
			response.setTransactionId(payment.getTransactionId());

			return response;
		}).collect(Collectors.toList());
	}
}
