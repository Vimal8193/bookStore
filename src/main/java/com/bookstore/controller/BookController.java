package com.bookstore.controller;

import java.util.List;
import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.BookRequestDTO;
import com.bookstore.dto.BookResponseDTO;
import com.bookstore.service.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bookStore")
@Api(value="onlineBookstore")
public class BookController {

	@Autowired
	BookService bookService;

	@ApiOperation(value = "Add a book into Bookstore")
	@PostMapping(value = "/books")
	public ResponseEntity<String> addBook(@RequestBody BookRequestDTO book) {
		return ResponseEntity.ok(bookService.addBook(book));
	}

	@ApiOperation(value = "Get all books of Bookstore")
	@GetMapping(value = "/books")
	public ResponseEntity<List<BookResponseDTO>> getBooks(@QueryParam("title") String title, @QueryParam("author") String author, @QueryParam("isbn") String isbn) {
		return ResponseEntity.ok(bookService.getBooksByFilter(title,author,isbn));
	}


	@ApiOperation(value = "Search media coverage of book using ISBN number")
	@GetMapping(value = "/books/mediaCoverage/{isbn}")
	public ResponseEntity<List<String>> mediaCoverage(@PathVariable String isbn){
		return ResponseEntity.ok(bookService.mediaCoverage(isbn));
	}

	@ApiOperation(value = "Purchase a book from book store")
	@PutMapping(value = "/books/purchase/{isbn}")
	public ResponseEntity<String> buyBook(@PathVariable String isbn) {
		return ResponseEntity.ok(bookService.buyBook(isbn));
	}
}
