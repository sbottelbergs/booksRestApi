package be.syntra.java.advanced.server.controller.mapper;

import be.syntra.java.advanced.server.controller.dto.BookDto;
import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.domain.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static be.syntra.java.advanced.server.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BookDtoMapperTest {

    private BookDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new BookDtoMapper();
    }

    @Test
    void testMapBookDtoBookDto() {
        // given
        final BookDto bookDto = aBookDto();

        // when
        Book result = mapper.map(bookDto);

        // then
        assertEquals(aBook(), result);
    }

    @Test
    void testMapBookToBookDto() {
        // given
        final Book book = aBook();

        // when
        BookDto result = mapper.map(book);

        // then
        assertEquals(aBookDto(), result);
    }

    @Test
    void testMapListOfBooksToBookList() {
        // given
        final List<Book> books = List.of(aBook(), anotherBook());

        // when
        BookList result = mapper.map(books);

        // then
        final BookList expected = new BookList(List.of(aBookDto(), anotherBookDto()));
        assertEquals(expected, result);
    }
}
