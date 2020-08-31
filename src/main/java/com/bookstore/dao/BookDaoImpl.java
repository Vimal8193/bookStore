package com.bookstore.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.bookstore.commons.Constants;
import com.bookstore.model.Book;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {

	@Autowired
	private EntityManager entityManager;


	Logger logger = LoggerFactory.getLogger(BookDaoImpl.class);

	@Override
	public void save(Book book) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.save(book);
	}


	@Override
	public Book getBookByISBN(String isbn) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery("from Book where isbn = :keyword", Book.class);
		query.setParameter(Constants.KEYWORD,isbn);
		List<Book> books = query.getResultList();
		if(books.isEmpty())
			return null;
		return books.get(0);
	}



	@Override
	public void update(Book book) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(book);
	}



	@Override
	public List<Book> getBooksByFilter(String title, String author, String isbn) {
		Session currentSession = entityManager.unwrap(Session.class);
		Criteria cr = currentSession.createCriteria(Book.class);
		if(null!=isbn) {
			cr.add(Restrictions.eq("isbn", isbn));
		}
		else {
			if(null!=title) {
				cr.add(Restrictions.like("title", Constants.PERCENTAGE+title+Constants.PERCENTAGE));
			}
			if(null!=author) {
				cr.add(Restrictions.like("author", Constants.PERCENTAGE+author+Constants.PERCENTAGE));
			}
		}

		List<Book> books = cr.list();
		return books;
	}




}
