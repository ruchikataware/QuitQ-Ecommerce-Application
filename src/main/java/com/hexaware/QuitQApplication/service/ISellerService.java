package com.hexaware.QuitQApplication.service;

import java.util.List;
import java.util.Map;

import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.model.Orders;
import com.hexaware.QuitQApplication.model.Orders.OrderStatus;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.model.Seller;

public interface ISellerService {
	public Seller saveSellerDetails(Seller seller);

	Map<String, List<Product>> getProductsBySellerAndCategory(Long sellerId) throws ResourceNotFoundException;

	Orders updateOrderStatus(Long orderId, OrderStatus status) throws ResourceNotFoundException;

	List<Seller> getAllSellers();

	void deleteSellerById(Long sellerId);
	
	Seller findBySellerId(Long sellerId);

}
