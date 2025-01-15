package com.hexaware.QuitQApplication.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.dto.OrderItemDTO;
import com.hexaware.QuitQApplication.model.Cart;
import com.hexaware.QuitQApplication.model.CartItem;
import com.hexaware.QuitQApplication.model.Customer;
import com.hexaware.QuitQApplication.model.OrderItem;
import com.hexaware.QuitQApplication.model.Orders;
import com.hexaware.QuitQApplication.model.Orders.OrderStatus;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.repository.CartItemRepository;
import com.hexaware.QuitQApplication.repository.CartRepository;
import com.hexaware.QuitQApplication.repository.CustomerRepository;
import com.hexaware.QuitQApplication.repository.OrderRepository;
import com.hexaware.QuitQApplication.repository.ProductRepository;
import com.hexaware.QuitQApplication.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
	private CustomerRepository customerRepository;
	private CartRepository cartRepository;
	private OrderRepository orderRepository;
	private ProductRepository productRepository;
	@Autowired
	private CartItemRepository cartItemRepository;

	public OrderServiceImpl(CustomerRepository customerRepository, CartRepository cartRepository,
			OrderRepository orderRepository, ProductRepository productRepository) {
		super();
		this.customerRepository = customerRepository;
		this.cartRepository = cartRepository;
		this.orderRepository = orderRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Orders placeOrder(Long customerId, String shippingAddress) {
		// Fetch customer by ID
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new RuntimeException("Customer not found"));

		// Get the customer's cart
		Cart cart = customer.getCart();
		List<CartItem> cartItems = cart.getCartItems();
		if (cartItems.isEmpty()) {
			throw new RuntimeException("Cart is empty. Cannot place an order.");
		}

		double totalAmount = calculateTotalAmount(cartItems);

		// Create a new order
		Orders order = new Orders();
		order.setCustomer(customer);
		order.setShippingAddress(shippingAddress);
		order.setTotalAmount(totalAmount);
		order.setStatus(OrderStatus.PENDING);

		// Create OrderItems and update stock
		List<OrderItem> orderItems = new ArrayList<>();
		for (CartItem cartItem : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setOrder(order);
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setQuantity(cartItem.getQuantity());
			orderItem.setPrice(cartItem.getProduct().getPrice());
			orderItems.add(orderItem);

			// Reduce product stock
			Product product = cartItem.getProduct();
			product.setStockQuantity(product.getStockQuantity() - cartItem.getQuantity());
			productRepository.save(product);
		}

		order.setOrderItems(orderItems);

		cartItemRepository.deleteItems(cart.getCartId());

		// Save the order
		return orderRepository.save(order);
	}

	@Override
	public List<OrderItemDTO> getOrderItemsBySeller(Long sellerId) {
		List<Map<String, Object>> rawResults = orderRepository.findOrderItemsBySeller(sellerId);
		return rawResults.stream().map(row -> {
			OrderItemDTO dto = new OrderItemDTO();
			dto.setOrderItemId(((Long) row.get("orderItemId")).longValue());
			dto.setOrderId(((Long) row.get("orderId")).longValue());
			dto.setProductId(((Long) row.get("productId")).longValue());
			dto.setProductName((String) row.get("productName"));
			dto.setQuantity((Integer) row.get("quantity"));
			dto.setPrice((Double) row.get("price"));
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	public double calculateTotalAmount(List<CartItem> cartItems) {
		return cartItems.stream().mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity()).sum();
	}
}
