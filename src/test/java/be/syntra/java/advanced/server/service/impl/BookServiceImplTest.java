package be.syntra.java.advanced.server.service.impl;

import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.exception.BookNotFoundException;
import be.syntra.java.advanced.server.persistence.entity.BookEntity;
import be.syntra.java.advanced.server.persistence.repository.BookRepository;
import be.syntra.java.advanced.server.service.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static be.syntra.java.advanced.server.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    BookRepository repository;

    @Mock
    BookMapper mapper;

    @InjectMocks
    BookServiceImpl bookService;

    @Test
    void testGetBooks() {
        // given
        final List<BookEntity> bookEntities = List.of(aBookEntity(), anotherBookEntity());
        when(repository.findAll()).thenReturn(bookEntities);

        final List<Book> books = List.of(aBook(), anotherBook());
        when(mapper.map(bookEntities)).thenReturn(books);

        // when
        List<Book> result = bookService.getAllBooks();

        // then
        assertEquals(books, result);

        verify(repository, times(1)).findAll();
        verify(mapper, times(1)).map(bookEntities);

        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void testGetBook_existingBook() {
        // given
        final String isbn = "123";
        when(repository.findByIsbn(anyString())).thenReturn(Optional.of(aBookEntity()));
        when(mapper.map(aBookEntity())).thenReturn(aBook());

        // when
        Book result = bookService.getBook(isbn);

        // then
        assertEquals(aBook(), result);

        verify(repository, times(1)).findByIsbn(isbn);
        verify(mapper, times(1)).map(aBookEntity());

        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void testGetBook_nonExistingBook() {
        // given
        final String isbn = "123";
        when(repository.findByIsbn(anyString())).thenReturn(Optional.empty());

        // when -- then
        assertThrows(BookNotFoundException.class, () -> bookService.getBook(isbn));

        verify(repository, times(1)).findByIsbn(isbn);

        verifyNoMoreInteractions(repository);
        verifyNoInteractions(mapper);
    }

    @Test
    void testAddBook_success() {
        // given
        final Book book = Book.builder()
                .isbn("10")
                .author("A Bestselling author")
                .title("A Bestselling book")
                .price(new BigDecimal("50.0"))
                .build();
        final BookEntity bookEntity = BookEntity.builder()
                .isbn("10")
                .author("A Bestselling author")
                .title("A Bestselling book")
                .price(new BigDecimal("50.0"))
                .build();
        when(mapper.map(book)).thenReturn(bookEntity);

        final BookEntity persistedBookEntity = BookEntity.builder()
                .id(3L)
                .isbn("10")
                .author("A Bestselling author")
                .title("A Bestselling book")
                .price(new BigDecimal("50.0"))
                .build();
        when(repository.save(bookEntity)).thenReturn(persistedBookEntity);

        // when
        bookService.addBook(book);

        // then
        verify(repository, times(1)).save(bookEntity);
        verify(mapper, times(1)).map(book);

        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }
}
