package com.hexaware.QuitQApplication.serviceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.exception.ValidationErrorException;
import com.hexaware.QuitQApplication.model.Orders;
import com.hexaware.QuitQApplication.model.Orders.OrderStatus;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.model.Seller;
import com.hexaware.QuitQApplication.repository.OrderRepository;
import com.hexaware.QuitQApplication.repository.ProductRepository;
import com.hexaware.QuitQApplication.repository.SellerRepository;
import com.hexaware.QuitQApplication.service.ISellerService;

import jakarta.transaction.Transactional;

@Service
public class SellerServiceImpl implements ISellerService {
	public SellerRepository sellerRepo;
	private OrderRepository orderRepository;
	private ProductRepository productRepository;

	public SellerServiceImpl(SellerRepository sellerRepo, OrderRepository orderRepository,
			ProductRepository productRepository) {
		super();
		this.sellerRepo = sellerRepo;
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Seller saveSellerDetails(Seller seller) {
		if (seller == null || seller.getUsername() == null) {
			throw new ValidationErrorException("Invalid Seller Data", Map.of("username", "Username is required"));
		}
		return this.sellerRepo.save(seller);
	}

	@Override
	public List<Seller> getAllSellers() {
		List<Seller> sellers = sellerRepo.findAll();
		if (sellers.isEmpty()) {
			throw new RuntimeException("No seller is registered yet!");
		}
		return sellers;
	}

	@Override
	public Orders updateOrderStatus(Long orderId, OrderStatus status) throws ResourceNotFoundException {
		Orders order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", "ID", orderId.toString()));

		if (status == null) {
			throw new ValidationErrorException("Invalid Status", Map.of("status", "Order status cannot be null"));
		}

		order.setStatus(status);
		return orderRepository.save(order);
	}

	@Override
	public Map<String, List<Product>> getProductsBySellerAndCategory(Long sellerId) throws ResourceNotFoundException {
		List<Product> products = productRepository.findAllBySellerIdOrderByCategory(sellerId);
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Products", "seller ID", sellerId.toString());
		}
		return products.stream().collect(Collectors.groupingBy(product -> product.getCategory().getName()));
	}

	@Override
	public void deleteSellerById(Long sellerId) {
		if (!sellerRepo.existsById(sellerId)) {
			throw new RuntimeException("Seller not found with id: " + sellerId);
		}
		sellerRepo.deleteById(sellerId);
	}

	@Override
	public Seller findBySellerId(Long sellerId) {
		return sellerRepo.findBySellerId(sellerId);
	}
}
