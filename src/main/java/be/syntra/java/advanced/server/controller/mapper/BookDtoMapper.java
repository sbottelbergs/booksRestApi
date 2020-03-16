package be.syntra.java.advanced.server.controller.mapper;

import be.syntra.java.advanced.server.controller.dto.BookDto;
import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.domain.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookDtoMapper {

    public BookDto map(Book book) {
        return BookDto.builder()
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .title(book.getTitle())
                .price(book.getPrice())
                .build();
    }

    public BookList map(List<Book> books) {
        return new BookList(books.stream().map(this::map).collect(Collectors.toList()));
    }
}
