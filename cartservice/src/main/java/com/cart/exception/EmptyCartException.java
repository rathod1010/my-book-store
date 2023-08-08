package com.cart.exception;

public class EmptyCartException extends RuntimeException {
	
	public EmptyCartException()
	{
		super("Resource not found on server !!");
	}
	
	public EmptyCartException(String message)
	{
		super(message);
	}

}
