package com.order.entity;

import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	
	private int cartId;
	
	private int userId;
	
	private double cartTotal;
	
	private int count;
	
	private List<CartItem> cartItems = new ArrayList<>();
	

}
