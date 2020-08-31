package com.bookstore.dao;

import java.util.List;
import com.bookstore.model.Book;

public interface BookDao {

	void save(Book book);

	void update(Book book);

	List<Book> getBooksByFilter(String title, String author, String isbn);

	Book getBookByISBN(String isbn);
}
