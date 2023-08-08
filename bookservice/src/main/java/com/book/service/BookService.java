package com.book.service;

import java.util.List;

import com.book.entity.Book;

public interface BookService {
	
	Book saveBook(Book book);
	
	List<Book> getAllBooks();
	
	Book getBook(int bookId);
	
	List<Book> getAllBooksByCategory(String category );
	

}
