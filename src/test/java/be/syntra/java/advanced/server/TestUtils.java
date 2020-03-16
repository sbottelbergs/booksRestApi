package be.syntra.java.advanced.server;

import be.syntra.java.advanced.server.controller.dto.BookDto;
import be.syntra.java.advanced.server.domain.Book;

import java.math.BigDecimal;

public class TestUtils {

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
}
