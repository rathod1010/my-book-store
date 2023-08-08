package com.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.order.entity.Order;
import com.order.service.OrderService;

@RestController
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	@PostMapping("/save")
	public Order createOrder(@RequestBody Order order)
	{
		return orderService.createOrder(order);
	}
	
	@GetMapping("/get/user/{userId}")
	public List<Order> getOrdersByUserId(@PathVariable int userId)
	{
		return orderService.getAllOrdersByUserId(userId);
	}
	
	@GetMapping("/get/{orderId}")
	public Order getOrderByOrderId(@PathVariable int orderId)
	{
		return orderService.getOrderByOrderId(orderId);
	}

}
