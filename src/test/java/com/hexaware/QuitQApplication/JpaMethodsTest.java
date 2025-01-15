package com.hexaware.QuitQApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.QuitQApplication.dto.CustomerRegisterDTO;
import com.hexaware.QuitQApplication.dto.SellerRegisterDTO;
import com.hexaware.QuitQApplication.exception.ResourceNotFoundException;
import com.hexaware.QuitQApplication.model.Cart;
import com.hexaware.QuitQApplication.model.CartItem;
import com.hexaware.QuitQApplication.model.Category;
import com.hexaware.QuitQApplication.model.Customer;
import com.hexaware.QuitQApplication.model.Customer.Gender;
import com.hexaware.QuitQApplication.model.OrderItem;
import com.hexaware.QuitQApplication.model.Product;
import com.hexaware.QuitQApplication.model.Seller;
import com.hexaware.QuitQApplication.model.User;
import com.hexaware.QuitQApplication.repository.CartItemRepository;
import com.hexaware.QuitQApplication.repository.CartRepository;
import com.hexaware.QuitQApplication.repository.CategoryRepository;
import com.hexaware.QuitQApplication.repository.CustomerRepository;
import com.hexaware.QuitQApplication.repository.ProductRepository;
import com.hexaware.QuitQApplication.repository.SellerRepository;
import com.hexaware.QuitQApplication.repository.UserRepository;
import com.hexaware.QuitQApplication.serviceImpl.CartServiceImpl;

@SpringBootTest
@TestMethodOrder(MethodOrderer.MethodName.class)
public class JpaMethodsTest {
	private CartItemRepository cartItemRepository;
	private CartRepository cartRepository;
	private CategoryRepository categoryRepository;
	private CustomerRepository customerRepository;
	private ProductRepository productRepository;
	private SellerRepository sellerRepository;
	private UserRepository userRepository;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private CartServiceImpl cartService;

	@Autowired
	public JpaMethodsTest(CartItemRepository cartItemRepository, CartRepository cartRepository,
			CategoryRepository categoryRepository, CustomerRepository customerRepository,
			ProductRepository productRepository, SellerRepository sellerRepository, UserRepository userRepository) {
		super();
		this.cartItemRepository = cartItemRepository;
		this.cartRepository = cartRepository;
		this.categoryRepository = categoryRepository;
		this.customerRepository = customerRepository;
		this.productRepository = productRepository;
		this.sellerRepository = sellerRepository;
		this.userRepository = userRepository;
	}

	@Test
	void a_registerCustomer() {
		CustomerRegisterDTO dto = new CustomerRegisterDTO("raj", "rajamadar", "7219581156", "raufjamadar113@gmail.com",
				"Rauf", "Jamadar", "Nagar", Gender.MALE);
		User user = mapper.map(dto, User.class);
		user.setPassword(dto.getPassword());
		userRepository.save(user);
		Customer customer = mapper.map(dto, Customer.class);
		customerRepository.save(customer);
	}

	@Test
	void b_registerSeller() {
		SellerRegisterDTO dto = new SellerRegisterDTO("rucha", "ruchataware", "rucha@hexaware.com", "store", "nagar",
				"BRN12345", "rucha taware", "9876543210");
		User user = mapper.map(dto, User.class);
		user.setPassword(dto.getPassword());
		userRepository.save(user);
		Seller seller = mapper.map(dto, Seller.class);
		sellerRepository.save(seller);
	}

	@Test
	void c_addCategories() {
		Category category = new Category("test1_category");
		categoryRepository.save(category);
	}

	@Test
	void d0_addProduct() {
		Seller seller = sellerRepository.findBySellerId(1L);
		Category category = categoryRepository.findByCategoryId(1L);
		Product product = new Product(1L, seller, category, "temp_prod", "temp_brand", "temp_desc", 1000.0, 100, null,
				new ArrayList<OrderItem>(), new ArrayList<CartItem>());
		productRepository.save(product);
	}

	@Test
	void d1_updateProduct() {
		Seller seller = sellerRepository.findBySellerId(1L);
		Category category = categoryRepository.findByCategoryId(1L);
		Product productDetails = new Product(1L, seller, category, "temp_prod", "temp_brand", "temp_desc", 1000.0, 100,
				null, new ArrayList<OrderItem>(), new ArrayList<CartItem>());
		Product existingProduct = productRepository.findById(1L).get();
		existingProduct.setPrice(productDetails.getPrice());
		existingProduct.setStockQuantity(productDetails.getStockQuantity());
		productRepository.save(existingProduct);
	}

	@Test
	void e_getAllSellers() {
		List<Seller> sellers = sellerRepository.findAll();
	}

	@Test
	void f_getAllCustomers() {
		List<Customer> customers = customerRepository.findAll();
	}

	@Test
	void g_getAllCategories() {
		List<Category> categories = categoryRepository.findAll();
	}

	@Test
	void h_browseProducts() {
		productRepository.findProducts("Snickers", "Footwear", 1000.0, 3000.0, "Black Sneakers");
	}

	@Test
	void i_getCartByCustomerId() {
		cartRepository.findByCustomerCustomerId(1L);
	}

	@Test
	void j_addProductToCart() {
		Cart cart = null;
		try {
			cart = cartService.getCartByCustomerId(1L);
		} catch (ResourceNotFoundException e) {
			e.printStackTrace();
		}
		Product product = productRepository.findById(1L).get();
		if (cart.getCartItems() == null) {
			cart.setCartItems(new ArrayList<>());
		}
		Optional<CartItem> existingItem = cart.getCartItems().stream()
				.filter(item -> item.getProduct().getProductId().equals(1L)).findFirst();

		if (existingItem.isPresent()) {
			CartItem cartItem = existingItem.get();
			cartItem.setQuantity(cartItem.getQuantity() + 10);
		} else {
			CartItem cartItem = new CartItem();
			cartItem.setCart(cart);
			cartItem.setProduct(product);
			cartItem.setQuantity(10);
			cart.getCartItems().add(cartItem);
		}
		cartRepository.save(cart);
	}

	@Test
	void m_getProductsBySellerAndCategory() {
		List<Product> products = productRepository.findAllBySellerIdOrderByCategory(1L);
		products.stream().collect(Collectors.groupingBy(product -> product.getCategory().getName()));
	}

}
