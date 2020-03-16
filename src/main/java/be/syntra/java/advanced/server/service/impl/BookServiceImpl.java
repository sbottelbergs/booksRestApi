package be.syntra.java.advanced.server.service.impl;

import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.exception.BookNotFoundException;
import be.syntra.java.advanced.server.persistence.entity.BookEntity;
import be.syntra.java.advanced.server.persistence.repository.BookRepository;
import be.syntra.java.advanced.server.service.BookMapper;
import be.syntra.java.advanced.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("jpaBookService")
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;
    private BookMapper mapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, BookMapper mapper) {
        this.bookRepository = bookRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Book> getAllBooks() {
        return mapper.map(bookRepository.findAll());
    }

    @Override
    @Transactional
    public Book getBook(String isbn) {
        return bookRepository
                .findByIsbn(isbn)
                .map(mapper::map)
                .orElseThrow(() ->
                        new BookNotFoundException(
                                String.format("Book with isbn %s could not be found", isbn)
                        )
                );
    }

    @Override
    @Transactional
    public void addBook(Book book) {
        BookEntity bookEntity = mapper.map(book);
        bookRepository.save(bookEntity);
    }
}
