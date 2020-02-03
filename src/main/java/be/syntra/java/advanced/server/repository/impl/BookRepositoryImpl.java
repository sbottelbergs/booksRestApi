package be.syntra.java.advanced.server.repository.impl;

import be.syntra.java.advanced.server.model.Book;
import be.syntra.java.advanced.server.repository.BookRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private Map<String, Book> books;

    @PostConstruct
    private void init() {
        books = new HashMap<>();
        books.put("1",
                Book.builder()
                        .isbn("1")
                        .author("J.K. Rowling")
                        .title("Harry Potter And The Philosopher’s Stone")
                        .price(new BigDecimal("15.0"))
                        .build()
        );
        books.put("2",
                Book.builder()
                        .isbn("2")
                        .author("Michelle Obama")
                        .title("Becoming - A Guided Journal for Discovering Your Voice")
                        .price(new BigDecimal("10.0"))
                        .build()
        );
        books.put("3",
                Book.builder()
                        .isbn("3")
                        .author("Irma S. Rombauer")
                        .title("Joy of Cooking")
                        .price(new BigDecimal("24.95"))
                        .build()
        );
    }

    @Override
    public List<Book> getBooks() {
        return new ArrayList<>(books.values());
    }

    @Override
    public Book getBookByIsbn(String isbn) {
        return books.get(isbn);
    }

}
