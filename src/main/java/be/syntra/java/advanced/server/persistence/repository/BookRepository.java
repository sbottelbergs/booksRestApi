package be.syntra.java.advanced.server.persistence.repository;

import be.syntra.java.advanced.server.domain.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getBooks();

    Book getBookByIsbn(String isbn);
}
