package com.cartItem.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cartItem.entity.Book;
import com.cartItem.entity.CartItem;
import com.cartItem.repository.CartItemRepository;

@Service
public class CartItemService {
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public CartItem saveCartItem(CartItem cartItem)
	{
		CartItem savedCartItem = cartItem;
		
		ResponseEntity<Book> forEntity=restTemplate.getForEntity("http://BOOK-SERVICE/book/get/"+savedCartItem.getBookId(), Book.class);
        Book book = forEntity.getBody();
        savedCartItem.setBook(book);
        
        double amount = book.getBookPrice()*savedCartItem.getQuantity();
        savedCartItem.setItemTotal(amount);
        
        return cartItemRepository.save(savedCartItem);
		
	}
	
	public List<CartItem> getCartItemByUserId(int userId)
	{
		
        
		List<CartItem> cartItems = cartItemRepository.findByUserId(userId);
		
		List<CartItem> cartItemList = cartItems.stream().map(item->{
			
			ResponseEntity<Book> forEntity=restTemplate.getForEntity("http://BOOK-SERVICE/book/get/"+item.getBookId(), Book.class);
	        Book book = forEntity.getBody();
	        
	         item.setBook(book);
	        
	        return item;
			
		}).collect(Collectors.toList());
		
	return cartItemList;
	}
	
	public void removeCartItem(int cartItemId)
	{
		cartItemRepository.deleteById(cartItemId);
	}
	
	

}
