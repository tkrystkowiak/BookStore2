package pl.jstk.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.jstk.entity.BookEntity;
import pl.jstk.mapper.BookMapper;
import pl.jstk.repository.BookRepository;
import pl.jstk.service.BookService;
import pl.jstk.to.BookTo;

@Service
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;

	@Autowired
	public BookServiceImpl(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@Override
	public List<BookTo> findAllBooks() {
		return BookMapper.map2To(bookRepository.findAll());
	}

	@Override
	public List<BookTo> findBooksByTitle(String title) {
		return BookMapper.map2To(bookRepository.findBookByTitle(title));
	}

	@Override
	public List<BookTo> findBooksByAuthor(String author) {
		return BookMapper.map2To(bookRepository.findBookByAuthor(author));
	}

	@Override
	@Transactional
	public BookTo saveBook(BookTo book) {
		BookEntity entity = BookMapper.map(book);
		System.out.println(entity.getAuthors() + entity.getTitle() + entity.getId() + entity.getStatus());
		entity = bookRepository.save(entity);
		return BookMapper.map(entity);
	}

	@Override
	@Transactional
	public void deleteBook(Long id) {
		bookRepository.deleteById(id);

	}

	@Override
	public BookTo findBookById(Long id) {
		Optional<BookEntity> optEntity = bookRepository.findById(id);
		if (optEntity.isPresent()) {
			BookEntity entity = optEntity.get();
			return BookMapper.map(entity);
		} else {
			return null;
		}
	}

	@Override
	public List<BookTo> searchBooks(String authors, String title) {
		if (!authors.equals("") && (!title.equals(""))) {
			return BookMapper.map2To(bookRepository.findBookByAuthorAndTitle(authors, title));
		}
		if (title.equals("")) {
			return BookMapper.map2To(bookRepository.findBookByAuthor(authors));
		}
		if (authors.equals("")) {
			return BookMapper.map2To(bookRepository.findBookByTitle(title));
		}
		return null;
	}
}
