package com.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	private int bookId;

	private String bookTitle;

	private double bookPrice;

	private String author;

	private double originalPrice;

	private String category;
	
	private byte[] image;

	private String imageBase64;

}
