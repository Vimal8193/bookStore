package com.bookstore.service;

import java.util.List;
import com.bookstore.dto.BookRequestDTO;
import com.bookstore.dto.BookResponseDTO;

public interface BookService {

	String addBook(BookRequestDTO book);

	List<String> mediaCoverage(String isbn);

	String buyBook(String isbn);

	List<BookResponseDTO> getBooksByFilter(String title, String author, String isbn);
}
