package com.cart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cart.entity.Cart;
import com.cart.service.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@PostMapping("/add")
	public Cart addItemToCart(@RequestBody Cart cart) {

		return cartService.saveCart(cart);

	}
	
	@PutMapping("/update")
	public Cart updateCart(@RequestBody Cart cart) {

		return cartService.updateCart(cart);

	}

	@GetMapping("/{cartId}")
	public Cart getCart(@PathVariable int cartId) {
		Cart cart = cartService.getCartById(cartId);
		return cart;
	}

	@GetMapping("/user/{userId}")
	public Cart getCartByUser(@PathVariable int userId) {
		Cart cart = cartService.getCartByUserId(userId);
		return cart;
	}

	@DeleteMapping("/delete/{userId}")
	public void deleteCart(@PathVariable int userId) {
		cartService.emptyCart(userId);
	}
}
