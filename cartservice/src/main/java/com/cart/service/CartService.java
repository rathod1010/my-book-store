package com.cart.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cart.entity.Cart;
import com.cart.entity.CartItem;
import com.cart.entity.User;
import com.cart.exception.EmptyCartException;
import com.cart.repository.CartRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private RestTemplate restTemplate;

	private Logger logger = LoggerFactory.getLogger(CartService.class);

	public Cart saveCart(Cart cart) {

		ResponseEntity<User> forEntity = restTemplate.getForEntity("http://USER-SERVICE/user/get/" + cart.getUserId(),
				User.class);
		User user = forEntity.getBody();
		cart.setCartId(user.getUserId());

		CartItem[] items = restTemplate.getForObject("http://CARTITEM-SERVICE/cartitem/get/" + cart.getUserId(),
				CartItem[].class);
		List<CartItem> cartItems = Arrays.stream(items).toList();
		logger.info("{} ", cartItems);

		cart.setCartItems(cartItems);
		double total = cartItems.stream().mapToDouble(item -> item.getItemTotal()).reduce(0, Double::sum);
		cart.setCartTotal(total);

		int count = cartItems.stream().map(item -> item.getQuantity()).reduce(0, Integer::sum);
		cart.setCount(count);

		return cartRepository.save(cart);

	}
	
	public Cart updateCart(Cart cart) {

		ResponseEntity<User> forEntity = restTemplate.getForEntity("http://USER-SERVICE/user/get/" + cart.getUserId(),
				User.class);
		User user = forEntity.getBody();
		cart.setCartId(user.getUserId());

		CartItem[] items = restTemplate.getForObject("http://CARTITEM-SERVICE/cartitem/get/" + cart.getUserId(),
				CartItem[].class);
		List<CartItem> cartItems = Arrays.stream(items).toList();
		logger.info("{} ", cartItems);

		cart.setCartItems(cartItems);
		double total = cartItems.stream().mapToDouble(item -> item.getItemTotal()).reduce(0, Double::sum);
		cart.setCartTotal(total);

		int count = cartItems.stream().map(item -> item.getQuantity()).reduce(0, Integer::sum);
		cart.setCount(count);

		return cartRepository.save(cart);

	}

	public Cart getCartById(int cartId) {
		Cart cart = cartRepository.findById(cartId)
				.orElseThrow(() -> new EntityNotFoundException("cart not found with id :" + cartId));

		CartItem[] items = restTemplate.getForObject("http://CARTITEM-SERVICE/cartitem/get/" + cart.getUserId(),
				CartItem[].class);
		List<CartItem> cartItems = Arrays.stream(items).toList();
		logger.info("{} ", cartItems);

		cart.setCartItems(cartItems);

		return cart;
	}

	public Cart getCartByUserId(int userId) {
		Cart cart = cartRepository.findByUserId(userId);
	     if(cart==null)
	     {
	    	 throw new EmptyCartException("Your cart is empty");
	     }

		CartItem[] items = restTemplate.getForObject("http://CARTITEM-SERVICE/cartitem/get/" + cart.getUserId(),
				CartItem[].class);
		List<CartItem> cartItems = Arrays.stream(items).toList();
		logger.info("{} ", cartItems);

		cart.setCartItems(cartItems);

		return cart;
	}

	public void emptyCart(int userId) {
		cartRepository.deleteById(userId);
	}
}
