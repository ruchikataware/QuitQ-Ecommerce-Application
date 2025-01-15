package com.hexaware.QuitQApplication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.QuitQApplication.dto.OrderItemDTO;
import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.exception.ValidationErrorException;
import com.hexaware.QuitQApplication.model.Orders;
import com.hexaware.QuitQApplication.model.Orders.OrderStatus;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.repository.OrderRepository;
import com.hexaware.QuitQApplication.repository.ProductRepository;
import com.hexaware.QuitQApplication.service.IOrderService;
import com.hexaware.QuitQApplication.service.IProductService;
import com.hexaware.QuitQApplication.service.ISellerService;

/*
 * 0. Add, update and delete specific product
 * 1. Delete multiple product(s) 
 * 2. Filter based search his own product(s)
 * 3. Updating order status
 * 4. Sales dashboard 
 */

@RestController
@RequestMapping("/api/seller")
public class SellerController {
	private IProductService productService;
	private ISellerService sellerService;
	@Autowired
	private ProductRepository productRepo;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private IOrderService orderService;

	public SellerController(IProductService productService, ISellerService sellerService) {
		super();
		this.productService = productService;
		this.sellerService = sellerService;
	}

	@PostMapping("/add-product")
	public Product addProduct(@RequestBody Product product) throws ResourceNotFoundException {
		if (product.getCategory() == null || product.getCategory().getCategoryId() == null) {
			throw new ValidationErrorException("Invalid Product Data",
					Map.of("category", "Category details are required"));
		}
		return productService.addProduct(product);
	}

	// Update Product (Only available to seller)
	@PutMapping("/update-product/{productId}")
	public Product updateProduct(@PathVariable Long productId, @RequestBody Product productDetails)
			throws ResourceNotFoundException {
		Product updatedProduct = productService.updateProduct(productId, productDetails);
		return updatedProduct;
	}

	@DeleteMapping("/delete-product/{productId}")
	@PreAuthorize("hasRole('SELLER')")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
		productRepo.deleteById(productId);
		return ResponseEntity.ok("Product deleted successfully.");
	}

	@GetMapping("/{id}/show-products")
	public List<Product> showProducts(@PathVariable Long id) {
		return productRepo.findAllBySellerIdOrderByCategory(id);
	}

	@PutMapping("/{orderId}/status")
	public Orders updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status)
			throws ResourceNotFoundException {
		if (status == null) {
			throw new ValidationErrorException("Invalid Order Status", Map.of("status", "Cannot be null"));
		}
		return sellerService.updateOrderStatus(orderId, status);
	}

	@GetMapping("/{sellerId}/{categoryId}/products-by-category")
	public List<Product> getProductsByCategory(@PathVariable Long sellerId, @PathVariable Long categoryId) {
		return productRepo.getProductsBySellerIdAndCategoryId(sellerId, categoryId);
	}

	@GetMapping("/{sellerId}/show-orders")
	public ResponseEntity<List<Orders>> getOrders(@PathVariable Long sellerId) throws ResourceNotFoundException {
		List<Orders> orders = orderRepo.getAll();
		return ResponseEntity.ok(orders);
	}

	@GetMapping("/show-order-items/{sellerId}")
	public ResponseEntity<List<OrderItemDTO>> findOrderItemsBySeller(@PathVariable Long sellerId) {
		List<OrderItemDTO> orderItems = orderService.getOrderItemsBySeller(sellerId);
		return ResponseEntity.ok(orderItems);
	}

}
