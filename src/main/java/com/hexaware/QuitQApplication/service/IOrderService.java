package com.hexaware.QuitQApplication.service;

import java.util.List;

import com.hexaware.QuitQApplication.dto.OrderItemDTO;
import com.hexaware.QuitQApplication.model.CartItem;
import com.hexaware.QuitQApplication.model.Orders;

public interface IOrderService {

	Orders placeOrder(Long customerId, String shippingAddress);

	double calculateTotalAmount(List<CartItem> cartItems);

	List<OrderItemDTO> getOrderItemsBySeller(Long sellerId);

}
