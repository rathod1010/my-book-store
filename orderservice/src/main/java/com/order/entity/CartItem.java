package com.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	
	private int cartItemId;

	private int bookId;
	
	private int userId;

	private double itemTotal;

	private int quantity;
	
	private Book book;

}
