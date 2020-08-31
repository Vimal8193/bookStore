package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bookstore.customexceptions.BookNotFoundException;
import com.bookstore.dao.BookDao;
import com.bookstore.dto.BookRequestDTO;
import com.bookstore.model.Book;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {

	@MockBean
	private BookDao bookDao;

	@Autowired
	private BookServiceImpl bookService;

	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getBooksByFilterWithAllFilters() {
		when(bookDao.getBooksByFilter(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
		.thenReturn(Stream.of(new Book("Home Dues 1", "Author", "9087898790", 500.00), new Book("Home Dues 2", "Author", "9087898790", 600.00))
		.collect(Collectors.toList()));
		assertEquals(2, bookService.getBooksByFilter(Mockito.anyString(), Mockito.anyString(), Mockito.anyString()).size());
	}
	
	@Test
	public void addNewBook() {
		Book b = new Book("Home Dues 1", "Author", "9087898790", 500.00);
		String msg = "Book added with isbn - "+b.getIsbn();
		doNothing().when(bookDao).save(b);
		assertEquals(msg, bookService.addBook(new BookRequestDTO("Home Dues 1", "Author", "9087898790", 500.00, 1)));
	}
	
	@Test
	public void addIntoExistingBook() {
		Book b = new Book("Home Dues 1", "Author", "9087898790", 500.00);
		String msg = "Book added with isbn - "+b.getIsbn();
		when(bookDao.getBookByISBN(b.getIsbn())).thenReturn(b);
		doNothing().when(bookDao).update(b);
		assertEquals(msg, bookService.addBook(new BookRequestDTO("Home Dues 1", "Author", "9087898790", 500.00, 1)));
	}
	
	@Test
	public void buyBookWithBookNotFoundException() {
		Book book = new Book("Home Dues", "Abel un", "2222222222", 200.00);
		doNothing().when(bookDao).update(book);
		assertThrows(BookNotFoundException.class, () -> {
			bookService.buyBook("2222222222");
		});
	}
	
	@Test
	public void buyBookWhenAvailable() {	
		Book book = new Book("Home Dues", "Abel un", "2222222222", 200.00,2);
		String msg = "Book purchased with isbn - "+book.getIsbn();
		when(bookDao.getBookByISBN(book.getIsbn())).thenReturn(book);
		doNothing().when(bookDao).update(book);
		assertEquals(msg, bookService.buyBook("2222222222"));
	}
	
	@Test
	public void buyBookWhenNotAvailable() {
		
		Book book = new Book("Home Dues", "Abel un", "2222222222", 200.00,0);
		String msg ="Book not available with isbn - "+book.getIsbn();
		when(bookDao.getBookByISBN(book.getIsbn())).thenReturn(book);
		doNothing().when(bookDao).update(book);
		assertEquals(msg, bookService.buyBook("2222222222"));
	}
		
	
	@Test
	public void mediaCoverageWhenBookNotAvailable(){
		when(bookDao.getBookByISBN(Mockito.anyString())).thenReturn(new Book("Home Dues 1", "Author", "9087898790", 500.00));
		assertEquals(0, bookService.mediaCoverage(Mockito.anyString()).size());
	}
	
	@Test
	public void mediaCoverageWhenBookNotFoundException(){
		when(bookDao.getBookByISBN(Mockito.anyString())).thenReturn(null);
		assertThrows(BookNotFoundException.class, () -> {
			bookService.mediaCoverage(Mockito.anyString());
		});
	}
	
	@Test
	public void mediaCoverageWhenBookAvailable(){
		when(bookDao.getBookByISBN(Mockito.anyString())).thenReturn(new Book("sunt aut facere repellat provident occaecati excepturi optio reprehenderit", "consequatur", "9780062464319", 500.00));
		assertEquals(1, bookService.mediaCoverage(Mockito.anyString()).size());
	}
	
}
