package be.syntra.java.advanced.server.service.impl;

import be.syntra.java.advanced.server.exception.BookNotFoundException;
import be.syntra.java.advanced.server.model.Book;
import be.syntra.java.advanced.server.repository.BookRepository;
import be.syntra.java.advanced.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.getBooks();
    }

    @Override
    public Book getBook(String isbn) {
        Book book = bookRepository.getBookByIsbn(isbn);
        if (book != null) {
            return book;
        } else {
            throw new BookNotFoundException(
                    String.format("A book with isbn number %s was not found", isbn)
            );
        }
    }

}
