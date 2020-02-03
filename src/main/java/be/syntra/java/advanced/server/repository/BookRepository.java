package be.syntra.java.advanced.server.repository;

import be.syntra.java.advanced.server.model.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getBooks();

    Book getBookByIsbn(String isbn);
}
