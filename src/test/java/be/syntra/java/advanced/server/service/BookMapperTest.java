package be.syntra.java.advanced.server.service;

import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.persistence.entity.BookEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.syntra.java.advanced.server.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookMapperTest {

    private BookMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new BookMapper();
    }

    @Test
    void testMapBookEntityToBook() {
        // given
        final BookEntity bookEntity = aBookEntity();

        // when
        Book result = mapper.map(bookEntity);

        // then
        assertEquals(aBook(), result);
    }

    @Test
    void testMapBookToBookEntity() {
        // given
        final Book book = aBook();

        // when
        BookEntity result = mapper.map(book);

        // then
        BookEntity expected = BookEntity.builder()
                .id(null)
                .isbn(book.getIsbn())
                .author(book.getAuthor())
                .title(book.getTitle())
                .price(book.getPrice())
                .build();
        assertEquals(expected, result);
    }

    @Test
    void testMapListOfBookEntitiesToListOfBooks() {
        // given
        final List<BookEntity> bookEntities = List.of(aBookEntity(), anotherBookEntity());

        // when
        List<Book> result = mapper.map(bookEntities);

        // then
        final List<Book> expected = List.of(aBook(), anotherBook());
        assertEquals(expected, result);
    }
}
