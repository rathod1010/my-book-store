package com.book.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.book.entity.Book;
import com.book.service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/save")
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {
		Book savedBook = bookService.saveBook(book);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

//	@GetMapping("/get/{bookId}")
//	public ResponseEntity<Book> getBook(@PathVariable int bookId) {
//		Book book = bookService.getBook(bookId);
//		return ResponseEntity.ok(book);
//	}
	
	@GetMapping("/get/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable int bookId) {
        Book book = bookService.getBook(bookId);
        if (book != null) {
            // Convert the byte[] image to a base64 encoded string
            String base64Image = Base64.getEncoder().encodeToString(book.getImage());
            // Set the base64 encoded image string in the book object
            book.setImageBase64(base64Image);
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

	@GetMapping("/get/all")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> allBooks = bookService.getAllBooks();
		return ResponseEntity.ok(allBooks);
	}
	
	@GetMapping("/get/category/{category}")
	public ResponseEntity<List<Book>> getAllBooksByCategory(@PathVariable("category") String category) {
		List<Book> allBooks = bookService.getAllBooksByCategory(category);
		return ResponseEntity.ok(allBooks);
	}
	
	@PostMapping("/uploadImage/{bookId}")
	public ResponseEntity<?> uploadImage(@PathVariable int bookId, @RequestParam("image") MultipartFile imageFile) {
	    try {
	        Book book = bookService.getBook(bookId);
	        if (book == null) {
	            return ResponseEntity.notFound().build();
	        }

	        byte[] imageData = imageFile.getBytes();
	        book.setImage(imageData);
	        bookService.saveBook(book);

	        return ResponseEntity.ok().build();
	    } catch (IOException e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


}
