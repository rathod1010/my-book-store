package com.book.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.entity.Book;
import com.book.exception.ResourceNotFoundException;
import com.book.repository.BookRepository;

import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Override
	public Book saveBook(Book book) {

		return bookRepository.save(book);
	}

	@Override
	public List<Book> getAllBooks() {

		return bookRepository.findAll();
	}
	
	@Override
	@Transactional
	public List<Book> getAllBooksByCategory(String category ) {

		return bookRepository.findByCategory(category);
	}

	@Override
	public Book getBook(int bookId) {

		return bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + bookId));
	}

}
