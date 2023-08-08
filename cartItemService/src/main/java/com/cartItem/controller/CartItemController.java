package com.cartItem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cartItem.entity.CartItem;
import com.cartItem.service.CartItemService;

@RestController
@RequestMapping("/cartitem")
public class CartItemController {
	
	@Autowired
	private CartItemService cartItemService;
	
	@PostMapping("/save")
	public CartItem saveCartItem(@RequestBody CartItem cartItem)
	{
		return cartItemService.saveCartItem(cartItem);
	}
	
	@GetMapping("/get/{userId}")
	public List<CartItem> getAllItems(@PathVariable int userId)
	{
		return cartItemService.getCartItemByUserId(userId);
	}
	
	@DeleteMapping("/delete/{cartItemId}")
	public void deleteCartItem(@PathVariable int cartItemId)
	{
		 cartItemService.removeCartItem(cartItemId);
	}

}
