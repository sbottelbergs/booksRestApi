package be.syntra.java.advanced.server.service;

import be.syntra.java.advanced.server.domain.Book;

import java.util.List;

public interface BookService {
    List<Book> getAllBooks();

    Book getBook(String isbn);

    void addBook(Book book);
}
