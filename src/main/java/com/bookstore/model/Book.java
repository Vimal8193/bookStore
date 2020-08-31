package com.bookstore.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "book")
@Data
public class Book {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@NotNull
    private String title;
	
	@NotNull
    private String author;
    
	@NotNull
    private String isbn;
    
    private Double price;
    
    private int inventory;
    
    @Version
    private int version;
    
	public Book(String title, String author, String isbn, Double price) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
	}
	
	public Book(String title, String author, String isbn, Double price, int inventory) {
		super();
		this.title = title;
		this.author = author;
		this.isbn = isbn;
		this.price = price;
		this.inventory = inventory;
	}

	public Book() {
		
	}
    
}
