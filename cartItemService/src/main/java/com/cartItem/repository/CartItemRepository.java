package com.cartItem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cartItem.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	
	List<CartItem> findByUserId(int userId);

}
