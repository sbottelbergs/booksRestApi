package be.syntra.java.advanced.server.controller;

import be.syntra.java.advanced.server.controller.dto.BookDto;
import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.controller.mapper.BookDtoMapper;
import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {

    private BookService bookService;

    private BookDtoMapper mapper;

    @Autowired
    public BooksController(BookService bookService, BookDtoMapper mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BookList getBooks() {
        return mapper.map(bookService.getAllBooks());
    }

    @GetMapping("{isbn}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBook(@PathVariable("isbn") String isbn) {
        return mapper.map(bookService.getBook(isbn));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody BookDto bookDto) {
        final Book book = mapper.map(bookDto);
        bookService.addBook(book);
    }

}
