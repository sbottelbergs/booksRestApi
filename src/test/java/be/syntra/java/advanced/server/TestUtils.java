package be.syntra.java.advanced.server;

import be.syntra.java.advanced.server.controller.dto.BookDto;
import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.persistence.entity.BookEntity;

import java.math.BigDecimal;

public class TestUtils {

    // DTO

    public static BookDto aBookDto() {
        return BookDto.builder()
                .isbn("1")
                .title("A book title")
                .author("An author")
                .price(new BigDecimal("10.0"))
                .build();
    }

    public static BookDto anotherBookDto() {
        return BookDto.builder()
                .isbn("2")
                .title("Another book title")
                .author("Another author")
                .price(new BigDecimal("12.50"))
                .build();
    }

    // Domain

    public static Book aBook() {
        return Book.builder()
                .isbn("1")
                .title("A book title")
                .author("An author")
                .price(new BigDecimal("10.0"))
                .build();
    }

    public static Book anotherBook() {
        return Book.builder()
                .isbn("2")
                .title("Another book title")
                .author("Another author")
                .price(new BigDecimal("12.50"))
                .build();
    }

    // Entity

    public static BookEntity aBookEntity() {
        return BookEntity.builder()
                .id(1L)
                .isbn("1")
                .title("A book title")
                .author("An author")
                .price(new BigDecimal("10.0"))
                .build();
    }

    public static BookEntity anotherBookEntity() {
        return BookEntity.builder()
                .id(2L)
                .isbn("2")
                .title("Another book title")
                .author("Another author")
                .price(new BigDecimal("12.50"))
                .build();
    }
}
