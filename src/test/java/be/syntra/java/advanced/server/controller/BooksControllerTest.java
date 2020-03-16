package be.syntra.java.advanced.server.controller;

import be.syntra.java.advanced.server.controller.dto.ApiError;
import be.syntra.java.advanced.server.controller.dto.BookDto;
import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.controller.mapper.BookDtoMapper;
import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.exception.BookNotFoundException;
import be.syntra.java.advanced.server.security.CustomAccessDeniedHandler;
import be.syntra.java.advanced.server.security.CustomAuthenticationEntryPoint;
import be.syntra.java.advanced.server.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static be.syntra.java.advanced.server.TestUtils.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the BooksController demonstrating the preferred method to test controllers in Spring MVC.
 * It uses the @WebMvcTest annotation with the BooksController specified so only this controller is loaded into
 * the application context.
 * <p>
 * Dependencies such as the BookService and BookDtoMapper are mocked away using Mockito.
 *
 * @see <a href="https://spring.io/guides/gs/testing-web/">Spring.io - Testing Web</a>
 * @see <a href="https://reflectoring.io/spring-boot-web-controller-test/">Spring Boot Web Controller Test</a>
 */
@WebMvcTest(BooksController.class)
@Import({JsonUtil.class, CustomAuthenticationEntryPoint.class, CustomAccessDeniedHandler.class})
@WithMockUser(roles = "ADULT")
class BooksControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JsonUtil jsonUtil;

    @MockBean
    private BookService bookService;

    @MockBean
    private BookDtoMapper mapper;

    @Test
    void testGetAllBooks() throws Exception {
        // given
        final List<Book> books = List.of(aBook(), anotherBook());
        when(bookService.getAllBooks()).thenReturn(books);
        when(mapper.map(books)).thenReturn(new BookList(List.of(aBookDto(), anotherBookDto())));

        // when
        MvcResult response = mockMvc
                .perform(get("/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookList result = jsonUtil.fromJsonResult(response, BookList.class);

        // then
        BookList expected = new BookList(List.of(aBookDto(), anotherBookDto()));
        Assertions.assertEquals(expected, result);

        verify(bookService, times(1)).getAllBooks();
        verify(mapper, times(1)).map(books);
    }

    @Test
    void testGetBook_existingIsbn() throws Exception {
        // given
        when(bookService.getBook(anyString())).thenReturn(aBook());
        when(mapper.map(aBook())).thenReturn(aBookDto());

        // when
        MvcResult response = mockMvc
                .perform(get("/books/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        final BookDto result = jsonUtil.fromJsonResult(response, BookDto.class);

        // then
        final BookDto expected = aBookDto();
        Assertions.assertEquals(expected, result);

        verify(bookService, times(1)).getBook("123");
        verify(mapper, times(1)).map(aBook());
    }

    @Test
    void testGetBook_nonExistingIsbn() throws Exception {
        // given
        when(bookService.getBook(anyString())).thenThrow(new BookNotFoundException("Not found"));

        // when
        MvcResult response = mockMvc
                .perform(get("/books/123").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        final ApiError result = jsonUtil.fromJsonResult(response, ApiError.class);

        // then
        final ApiError expected = ApiError.builder()
                .title("Book not found")
                .message("Not found")
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.getReasonPhrase())
                .build();
        Assertions.assertEquals(expected, result);

        verify(bookService, times(1)).getBook("123");
        verifyNoMoreInteractions(bookService);
        verifyNoInteractions(mapper);
    }
}
