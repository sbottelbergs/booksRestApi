package be.syntra.java.advanced.server.controller;

import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.controller.mapper.BookDtoMapper;
import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.security.CustomAccessDeniedHandler;
import be.syntra.java.advanced.server.security.CustomAuthenticationEntryPoint;
import be.syntra.java.advanced.server.service.BookService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
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
        MvcResult result = mockMvc
                .perform(get("/books").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookList bookList = jsonUtil.fromJsonResult(result, BookList.class);

        // then
        BookList expected = new BookList(List.of(aBookDto(), anotherBookDto()));
        Assertions.assertEquals(expected, bookList);

        verify(bookService, times(1)).getAllBooks();
        verify(mapper, times(1)).map(books);
    }
}
