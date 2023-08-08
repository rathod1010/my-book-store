package com.cart.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cart.payload.ApiResponse;


@RestControllerAdvice
public class GlobalExceptionalHandler {
	
	@ExceptionHandler(EmptyCartException.class)
	public ResponseEntity<ApiResponse> handlerResourceNotFoundException(EmptyCartException ex)
	{
		String message = ex.getMessage();
	ApiResponse response =	ApiResponse.builder().message(message).success(true).status(HttpStatus.NOT_FOUND).build();
		
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

}
