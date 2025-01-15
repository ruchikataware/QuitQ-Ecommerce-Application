package com.hexaware.QuitQApplication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.QuitQApplication.dto.PaymentResponseDTO;
import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.exception.ValidationErrorException;
import com.hexaware.QuitQApplication.model.Category;
import com.hexaware.QuitQApplication.model.Customer;
import com.hexaware.QuitQApplication.model.Orders;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.model.Seller;
import com.hexaware.QuitQApplication.model.Orders.OrderStatus;
import com.hexaware.QuitQApplication.repository.OrderRepository;
import com.hexaware.QuitQApplication.service.IAdminService;
import com.hexaware.QuitQApplication.service.ICustomerService;
import com.hexaware.QuitQApplication.service.IPaymentService;
import com.hexaware.QuitQApplication.service.IProductService;
import com.hexaware.QuitQApplication.service.ISellerService;
import com.hexaware.QuitQApplication.service.IUserService;

/* 
 * 1. Register (AdminRegisterDTO) and login (LoginDTO).
 * 2. Add & update category.
 * 3. Get all or specific customer(s)/seller(s)/category(s)/product(s).
 * 4. Delete specific customer/seller/category/product.
 * 5. Delete multiple categories.
 */

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

	private final IAdminService adminService;
	private final ISellerService sellerService;
	private final ICustomerService customerService;
	private final IPaymentService paymentService;
	private final IProductService productService;
	private final IUserService userService;
	@Autowired
	private OrderRepository orderRepo;

	public AdminController(IAdminService adminService, ISellerService sellerService, ICustomerService customerService,
			IPaymentService paymentService, IProductService productService, IUserService userService) {
		this.adminService = adminService;
		this.sellerService = sellerService;
		this.customerService = customerService;
		this.paymentService = paymentService;
		this.productService = productService;
		this.userService = userService;
	}

	@PostMapping("/add-categories")
	@PreAuthorize("hasRole('ADMIN')")
	public Category addCategories(@RequestBody Category categoryList) {
//		if (categoryList.isEmpty()) {
//			throw new ValidationErrorException("Category list cannot be empty",
//					Map.of("categories", "Empty list provided"));
//		}
		return adminService.addCategories(categoryList);
	}

	@PutMapping("/update-category/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public Category updateCategory(@PathVariable Long categoryId, @RequestBody Category categoryDetails) {
		if (categoryDetails.getName() == null || categoryDetails.getName().isEmpty()) {
			throw new ValidationErrorException("Category name cannot be null or empty",
					Map.of("name", "Invalid category name provided"));
		}
		return adminService.updateCategory(categoryId, categoryDetails);
	}

	@DeleteMapping("/delete-category/{categoryId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
		adminService.deleteCategory(categoryId);
		return ResponseEntity.ok("Category deleted successfully.");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get-all-sellers")
	public List<Seller> getAllSellers() {
		List<Seller> sellers = sellerService.getAllSellers();
		if (sellers.isEmpty()) {
			throw new RuntimeException("There is no seller registered!");
		}
		return sellers;
	}

	@DeleteMapping("/delete-seller/{sellerId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteSeller(@PathVariable Long sellerId) {
		userService.deleteByUsername(sellerService.findBySellerId(sellerId).getUsername());
		sellerService.deleteSellerById(sellerId);
		return ResponseEntity.ok("Seller deleted successfully.");
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/get-all-customers")
	public List<Customer> getAllCustomers() {
		List<Customer> customers = customerService.getAllCustomers();
		if (customers.isEmpty()) {
			throw new RuntimeException("There is no customer registered!");
		}
		return customers;
	}

	@DeleteMapping("/delete-customer/{customerId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
		userService.deleteByUsername(customerService.findByCustomerId(customerId).getUsername());
		customerService.deleteCustomerById(customerId);
		return ResponseEntity.ok("Customer deleted successfully.");
	}

	@GetMapping("/orders")
	public ResponseEntity<List<Orders>> getOrders() throws ResourceNotFoundException {
		List<Orders> orders = orderRepo.getAll();
		return ResponseEntity.ok(orders);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/all-order-receipts")
	public List<PaymentResponseDTO> getAllPayments() {
		List<PaymentResponseDTO> payments = paymentService.getAllPayments();
		if (payments.isEmpty()) {
			throw new RuntimeException("No order is placed yet!");
		}
		return payments;
	}

	@PutMapping("/update-order-status/{orderId}/{status}")
	public Orders updateOrderStatus(@PathVariable Long orderId, @PathVariable OrderStatus status)
			throws ResourceNotFoundException {
		if (status == null) {
			throw new ValidationErrorException("Invalid Order Status", Map.of("status", "Cannot be null"));
		}
		return sellerService.updateOrderStatus(orderId, status);
	}

	@GetMapping("/view-products")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Product> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		if (products.isEmpty()) {
			throw new RuntimeException("No products available.");
		}
		return products;
	}

	@DeleteMapping("/delete-product/{productId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
		productService.deleteProductById(productId);
		return ResponseEntity.ok("Product deleted successfully.");
	}

}
