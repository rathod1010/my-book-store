package com.order.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.order.entity.Book;
import com.order.entity.Cart;
import com.order.entity.CartItem;
import com.order.entity.Order;
import com.order.entity.OrderItem;
import com.order.repository.OrderRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Order createOrder(Order order) {
//		Cart cart =cartService.getCartByUser(order.getUserId());

		ResponseEntity<Cart> forEntity = restTemplate
				.getForEntity("http://CART-SERVICE/cart/user/" + order.getUserId(), Cart.class);
		Cart cart = forEntity.getBody();
		order.setOrderDate(LocalDate.now());
		order.setTotalAmount(cart.getCartTotal());

		LocalDateTime dateTime = LocalDateTime.now();

		// order number generation
//		String orderNum = "ORD"+dateTime.getYear()+dateTime.getMonthValue()+dateTime.getHour()+dateTime.getMinute()+dateTime.getSecond();
		String orderNum = "ORD" + UUID.randomUUID().toString().replace("-", "").toUpperCase();
		order.setOrderNum(orderNum);
		order.setOrderStatus("Cash On Delivery");

//		List<CartItem> cartItems = cart.getCartItems();
//		System.out.println("==========="+cartItems.isEmpty());

		CartItem[] items = restTemplate.getForObject("http://CARTITEM-SERVICE/cartitem/get/" + order.getUserId(),
				CartItem[].class);
		List<CartItem> cartItems = Arrays.stream(items).toList();
		List<OrderItem> orderItems = order.getOrderList();

		cartItems.forEach(item -> {

			OrderItem orderItem = new OrderItem();
//			orderItem.setOrderItemId(item.getCartItemId());
			orderItem.setQuantity(item.getQuantity());
			orderItem.setItemTotal(item.getItemTotal());
			orderItem.setBookId(item.getBookId());
			orderItem.setBook(item.getBook());

			orderItems.add(orderItem);

		});

		order.setOrderList(orderItems);

		Order newOrder = orderRepository.save(order);

		// clear the cart and cartItems

		String url = "http://CART-SERVICE/cart/delete/" + order.getUserId();
		restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);

//		String url2 = "http://localhost:8093/cartitem/delete/"+

		cartItems.forEach(item -> {
			String url2 = "http://CARTITEM-SERVICE/cartitem/delete/" + item.getCartItemId();
			restTemplate.exchange(url2, HttpMethod.DELETE, null, Void.class);
		});

		return newOrder;
	}

	public List<Order> getAllOrdersByUserId(int userId) {
		
		return orderRepository.findByUserId(userId);
	}

	public Order getOrderByOrderId(int orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new EntityNotFoundException("Order not found with id : " + orderId));

		List<OrderItem> orderList = order.getOrderList();
		List<OrderItem> orderItemList = orderList.stream().map(item -> {

			ResponseEntity<Book> forEntity = restTemplate
					.getForEntity("http://BOOK-SERVICE/book/get/" + item.getBookId(), Book.class);
			Book book = forEntity.getBody();

			item.setBook(book);

			return item;

		}).collect(Collectors.toList());

		order.setOrderList(orderItemList);

		return order;
	}

}
