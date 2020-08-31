package com.bookstore.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bookstore.customexceptions.BookNotFoundException;
import com.bookstore.dao.BookDao;
import com.bookstore.dto.BookRequestDTO;
import com.bookstore.dto.BookResponseDTO;
import com.bookstore.model.Book;
import com.bookstore.model.MediaCoverageEntity;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	BookDao bookDao;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public String addBook(BookRequestDTO bookDTO) {
		Book book = new Book(bookDTO.getTitle(),bookDTO.getAuthor(),bookDTO.getIsbn(),bookDTO.getPrice());
		
		Book existingBook = bookDao.getBookByISBN(book.getIsbn());
		
		if(null == existingBook) {
			bookDao.save(book);
		} else {
			existingBook.setInventory(existingBook.getInventory()+1);
			bookDao.update(existingBook);
		}
		
		return "Book added with isbn - "+book.getIsbn();
	}


	@Override
	public List<String> mediaCoverage(String isbn) {
		Book book = bookDao.getBookByISBN(isbn);
		
		if(null == book) {
			throw new BookNotFoundException("Book with ISBN - "+ isbn);
		}

		List<MediaCoverageEntity> mediaList = getMediaCoverage().stream().filter(media -> media.getTitle().contains(book.getTitle()) || media.getBody().contains(book.getTitle()))
				.collect(Collectors.toList());

		List<String> result = mediaList.stream().map(media -> media.getTitle())
				.collect(Collectors.toList());
		return result;
	}

	public List<MediaCoverageEntity> getMediaCoverage() {
		String url = "http://jsonplaceholder.typicode.com/posts";
		ResponseEntity<MediaCoverageEntity[]> response = restTemplate.getForEntity(url, MediaCoverageEntity[].class);
		List<MediaCoverageEntity> list = Arrays.asList(response.getBody());
		return list;
	}

	@Override
	public String buyBook(String isbn) {
		Book book = bookDao.getBookByISBN(isbn);
		
		if(null == book) {
			throw new BookNotFoundException("Book with ISBN - "+isbn);
		}

		if(book.getInventory()==0) {
			return "Book not available with isbn - "+isbn;
		}else { 
			book.setInventory(book.getInventory()-1);
			bookDao.update(book);
		}

		return "Book purchased with isbn - "+isbn;
	}



	@Override
	public List<BookResponseDTO> getBooksByFilter(String title, String author, String isbn) {
		List<Book> books = bookDao.getBooksByFilter(title,author,isbn);
		List<BookResponseDTO> response = books.stream().map(BookResponseDTO::convertToResponse).collect(Collectors.toList());
		return response;
	}

}
