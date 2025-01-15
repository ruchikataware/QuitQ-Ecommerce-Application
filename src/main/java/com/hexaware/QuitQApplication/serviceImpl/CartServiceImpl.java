package com.hexaware.QuitQApplication.serviceImpl;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.exception.ValidationErrorException;
import com.hexaware.QuitQApplication.model.Cart;
import com.hexaware.QuitQApplication.model.CartItem;
import com.hexaware.QuitQApplication.model.Customer;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.repository.CartItemRepository;
import com.hexaware.QuitQApplication.repository.CartRepository;
import com.hexaware.QuitQApplication.repository.CustomerRepository;
import com.hexaware.QuitQApplication.repository.ProductRepository;
import com.hexaware.QuitQApplication.service.ICartService;

@Service
public class CartServiceImpl implements ICartService {
	private CartRepository cartRepository;
	private CartItemRepository cartItemRepository;
	private ProductRepository productRepository;
	private CustomerRepository customerRepository;

	public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository,
			ProductRepository productRepository, CustomerRepository customerRepository) {
		super();
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.productRepository = productRepository;
		this.customerRepository = customerRepository;
	}

	@Override
	public Cart getCartByCustomerId(Long customerId) throws ResourceNotFoundException {
		return cartRepository.findByCustomerCustomerId(customerId).orElseGet(() -> {
			Customer customer = null;
			try {
				customer = customerRepository.findById(customerId)
						.orElseThrow(() -> new ResourceNotFoundException("Customer", "ID", customerId.toString()));
			} catch (ResourceNotFoundException e) {
				e.printStackTrace();
			}
			Cart newCart = new Cart();
			newCart.setCustomer(customer);
			return cartRepository.save(newCart);
		});
	}

	@Override
	public Cart addProductToCart(Long customerId, Long productId, int quantity) throws ResourceNotFoundException {
		if (quantity <= 0) {
			throw new ValidationErrorException("Invalid quantity", Map.of("quantity", "Must be greater than 0"));
		}

		Cart cart = getCartByCustomerId(customerId);
		if (cart.getCartItems() == null) {
			cart.setCartItems(new ArrayList<>());
		}
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId.toString()));

		Optional<CartItem> existingItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)).findFirst();

		if (existingItem.isPresent()) {
			CartItem cartItem = existingItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + quantity);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(quantity);
			cart.getCartItems().add(cartItem);
		}

		return cartRepository.save(cart);
	}

	@Override
	public Cart updateCartItem(Long customerId, Long productId, int quantity) throws ResourceNotFoundException {
		Cart cart = getCartByCustomerId(customerId);
		CartItem cartItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product in Cart", "productId", productId.toString()));

		if (quantity <= 0) {
			cart.getCartItems().remove(cartItem);
			cartItemRepository.delete(cartItem);
		} else {
			cartItem.setQuantity(quantity);
		}
		return cartRepository.save(cart);
	}

	@Override
	public Cart removeItemFromCart(Long customerId, Long productId) throws ResourceNotFoundException {
		Cart cart = getCartByCustomerId(customerId);
		CartItem cartItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getProductId().equals(productId)).findFirst()
				.orElseThrow(() -> new ResourceNotFoundException("Product in Cart", "productId", productId.toString()));

		cart.getCartItems().remove(cartItem);
		cartItemRepository.delete(cartItem);

		return cartRepository.save(cart);
	}

}
