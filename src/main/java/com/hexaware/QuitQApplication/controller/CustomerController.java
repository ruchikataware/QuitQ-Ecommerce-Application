package com.hexaware.QuitQApplication.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.hexaware.QuitQApplication.model.Cart;
import com.hexaware.QuitQApplication.model.Orders;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.repository.OrderRepository;
import com.hexaware.QuitQApplication.service.ICartService;
import com.hexaware.QuitQApplication.service.IOrderService;
import com.hexaware.QuitQApplication.service.IPaymentService;
import com.hexaware.QuitQApplication.service.IProductService;

/* 
 * 0. Should be able to order single product also
 * 1. Filter based search or browse product(s)
 * 2. Add product to cart
 * 3. Update product into the cart
 * 4. Remove product(s) from the cart
 * 5. Get/view product(s) into the cart
 * 6. Place order
 * 7. Process payment 
 * 8. Order history to track, cancel & delete order(s)
 */

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	private IProductService productService;
	private ICartService cartService;
	private IOrderService orderService;
	private IPaymentService paymentService;
	@Autowired
	private OrderRepository orderRepo;

	public CustomerController(IProductService productService, ICartService cartService, IOrderService orderService,
			IPaymentService paymentService) {
		super();
		this.productService = productService;
		this.cartService = cartService;
		this.orderService = orderService;
		this.paymentService = paymentService;
	}

	@GetMapping("/browse-products")
	public List<Product> browseProducts(@RequestParam(required = false) String name,
			@RequestParam(required = false) String categoryName, @RequestParam(required = false) Double minPrice,
			@RequestParam(required = false) Double maxPrice, @RequestParam(required = false) String description) {
		return productService.browseProducts(name, categoryName, minPrice, maxPrice, description);
	}

	@GetMapping("/cart/{customerId}")
	public ResponseEntity<Cart> getCart(@PathVariable Long customerId) throws ResourceNotFoundException {
		Cart cart = cartService.getCartByCustomerId(customerId);
		if (cart == null) {
			throw new ResourceNotFoundException("Cart", "customerId", customerId.toString());
		}
		return ResponseEntity.ok(cart);
	}
	
	@GetMapping("/orders/{customerId}")
	public ResponseEntity<List<Orders>> getOrders(@PathVariable Long customerId) throws ResourceNotFoundException {
		List<Orders> orders = orderRepo.getOrderByCustomerId(customerId);
		return ResponseEntity.ok(orders);
	}

	@PostMapping("/cart/{customerId}/{productId}/{quantity}/add")
	public ResponseEntity<Cart> addProductToCart(@PathVariable Long customerId, @PathVariable Long productId,
			@PathVariable int quantity) throws ResourceNotFoundException {
		if (quantity <= 0) {
			throw new ValidationErrorException("Invalid quantity",
					Map.of("quantity", "Quantity must be greater than 0"));
		}
		return ResponseEntity.ok(cartService.addProductToCart(customerId, productId, quantity));
	}

	@PutMapping("/cart/{customerId}/{productId}/{quantity}/update")
	public ResponseEntity<Cart> updateCartItem(@PathVariable Long customerId, @PathVariable Long productId,
			@PathVariable int quantity) throws ResourceNotFoundException {
		if (quantity < 0) {
			throw new ValidationErrorException("Invalid quantity", Map.of("quantity", "Quantity must not be negative"));
		}
		return ResponseEntity.ok(cartService.updateCartItem(customerId, productId, quantity));
	}

	@DeleteMapping("/cart/{customerId}/{productId}/remove")
	public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long customerId, @PathVariable Long productId)
			throws ResourceNotFoundException {
		Cart updatedCart = cartService.removeItemFromCart(customerId, productId);
		return ResponseEntity.ok(updatedCart);
	}

	@PostMapping("/orders/place-order/{customerId}")
	public Orders placeOrder(@PathVariable Long customerId, @RequestBody String shippingAddress) {
		if (shippingAddress == null || shippingAddress.trim().isEmpty()) {
			throw new ValidationErrorException("Invalid shipping address",
					Map.of("shippingAddress", "Cannot be empty"));
		}
		return orderService.placeOrder(customerId, shippingAddress);
	}

	@PostMapping("/payment-process/{orderId}")
	public PaymentResponseDTO processPayment(@PathVariable Long orderId, @RequestParam String paymentMethod,
			@RequestParam String transactionId) throws ResourceNotFoundException {
		if (paymentMethod == null || transactionId == null) {
			throw new ValidationErrorException("Invalid payment details",
					Map.of("paymentMethod", "Cannot be null", "transactionId", "Cannot be null"));
		}
		return paymentService.processPayment(orderId, paymentMethod, transactionId);
	}

	@GetMapping("/payment-receipts/{customerId}")
	public List<PaymentResponseDTO> getCustomerPayments(@PathVariable Long customerId)
			throws ResourceNotFoundException {
		List<PaymentResponseDTO> payments = paymentService.getPaymentsByCustomerId(customerId);
		if (payments.isEmpty()) {
			throw new ResourceNotFoundException("Payments", "customerId", customerId.toString());
		}
		return payments;
	}

}
