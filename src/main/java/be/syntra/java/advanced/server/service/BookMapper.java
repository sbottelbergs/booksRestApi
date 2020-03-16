package be.syntra.java.advanced.server.service;

import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.persistence.entity.BookEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public Book map(BookEntity bookEntity) {
        return Book.builder()
                .author(bookEntity.getAuthor())
                .title(bookEntity.getTitle())
                .price(bookEntity.getPrice())
                .isbn(bookEntity.getIsbn())
                .build();
    }

    public List<Book> map(List<BookEntity> bookEntities) {
        return bookEntities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    public BookEntity map(Book book) {
        return BookEntity.builder()
                .author(book.getAuthor())
                .title(book.getTitle())
                .price(book.getPrice())
                .isbn(book.getIsbn())
                .build();
    }
}
