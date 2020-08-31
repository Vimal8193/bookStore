package com.bookstore.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequestDTO {

	@NotNull
	private String title;

	@NotNull
	private String author;

	@NotNull
	private String isbn;

	@NotNull
	private Double price;
	
	private int inventory;
}
