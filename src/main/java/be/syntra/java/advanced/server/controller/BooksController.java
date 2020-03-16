package be.syntra.java.advanced.server.controller;

import be.syntra.java.advanced.server.controller.dto.BookDto;
import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.controller.mapper.BookDtoMapper;
import be.syntra.java.advanced.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public BookList getBooks() {
        return mapper.map(bookService.getAllBooks());
    }

    @GetMapping("{isbn}")
    public BookDto getBook(@PathVariable("isbn") String isbn) {
        return mapper.map(bookService.getBook(isbn));
    }

}
