package com.hexaware.QuitQApplication.service;

import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.model.Cart;

public interface ICartService {

	Cart removeItemFromCart(Long customerId, Long productId) throws ResourceNotFoundException;

	Cart updateCartItem(Long customerId, Long productId, int quantity) throws ResourceNotFoundException;

	Cart addProductToCart(Long customerId, Long productId, int quantity) throws ResourceNotFoundException;

	Cart getCartByCustomerId(Long customerId) throws ResourceNotFoundException;

}
