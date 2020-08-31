package com.bookstore.dto;

import com.bookstore.model.Book;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookResponseDTO {

	private Integer id;

	private String title;

	private String author;

	private String isbn;

	private Double price;
	
	public static BookResponseDTO convertToResponse(Book book) {
		return new BookResponseDTO(book.getId(),book.getTitle(),book.getAuthor(),book.getIsbn(),book.getPrice());
	}
}
