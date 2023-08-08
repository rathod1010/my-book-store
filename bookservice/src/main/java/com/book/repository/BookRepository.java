package com.book.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Integer> {
	
	List<Book> findByCategory(String category);

}
