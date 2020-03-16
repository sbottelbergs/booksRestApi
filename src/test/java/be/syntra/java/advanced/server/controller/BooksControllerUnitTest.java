package be.syntra.java.advanced.server.controller;

import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.controller.mapper.BookDtoMapper;
import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static be.syntra.java.advanced.server.TestUtils.*;
import static org.mockito.Mockito.*;

/**
 * A unit test of the BooksController that uses Mockito but doesn't rely on Spring at all.
 * It is faster because it doesn't have to spin up a Spring Application context.
 * It's not a good idea to test controllers this way because we completely bypass the HTTP layer, including
 * security etc...
 *
 * @see <a href="https://reflectoring.io/spring-boot-web-controller-test/">Spring Boot Web Controller Test</a>
 * @deprecated This test class is merely for demonstrative purposes. Don't test controllers this way
 */
@ExtendWith(MockitoExtension.class)
@Deprecated
public class BooksControllerUnitTest {

    @InjectMocks
    private BooksController booksController;

    @Mock
    private BookService bookService;

    @Mock
    private BookDtoMapper mapper;

    @Test
    void testGetAllBooks() {
        // given
        final List<Book> books = List.of(aBook(), anotherBook());
        when(bookService.getAllBooks()).thenReturn(books);
        when(mapper.map(books)).thenReturn(new BookList(List.of(aBookDto(), anotherBookDto())));

        // when
        BookList bookList = booksController.getBooks();

        // then
        BookList expected = new BookList(List.of(aBookDto(), anotherBookDto()));
        Assertions.assertEquals(expected, bookList);

        verify(bookService, times(1)).getAllBooks();
        verify(mapper, times(1)).map(books);
    }

}
