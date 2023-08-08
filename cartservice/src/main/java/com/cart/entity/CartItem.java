package com.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItem {
	
	private int cartItemId;

	private int bookId;
	
	private int userId;

	private double itemTotal;

	private int quantity;
	
	private Book book;

}
