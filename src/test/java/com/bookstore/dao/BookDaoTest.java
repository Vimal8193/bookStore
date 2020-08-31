package com.bookstore.dao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookstore.model.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTest {

	
	@Autowired
	private BookDaoImpl dao;
	
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getBooksWithAllFilters() {
		dao.getBooksByFilter(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	public void getBooksWIthISBN() {
		dao.getBooksByFilter(null, null, Mockito.anyString());
	}
	
	@Test
	public void getBooksWithAuthor() {
		dao.getBooksByFilter(null, Mockito.anyString(), null);
	}
	
	@Test
	public void getBooksWithTitle() {
		dao.getBooksByFilter(Mockito.anyString(), null, null);
	}
	
	@Test
	public void getBooksWithAuthorAndIsbn() {
		dao.getBooksByFilter(null, Mockito.anyString(), Mockito.anyString());
	}
	
	@Test
	public void getBooksWithAuthorAndTitle() {
		dao.getBooksByFilter(Mockito.anyString(), Mockito.anyString(), null);
	}
	
	@Test
	public void getBooksWithTitleAndIsbn() {
		dao.getBooksByFilter(Mockito.anyString(), null, Mockito.anyString());
	}
	
	@Test
	public void addNewBook() {
		Book book = new Book("Home Dues", "Abel un", "8978909899", 200.00);
		dao.save(book);
	}
	
	@Test
	public void addIntoExistingBook() {
		Book book = new Book("Home Dues", "Abel un", "2222222222", 200.00);
		dao.update(book);
	}
	
	@Test
	public void buyBook() {
		Book book = new Book("Home Dues", "Abel un", "2222222222", 200.00);
		dao.update(book);
	}
	
}
