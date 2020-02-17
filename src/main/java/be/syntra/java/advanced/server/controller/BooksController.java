package be.syntra.java.advanced.server.controller;

import be.syntra.java.advanced.server.controller.dto.BookList;
import be.syntra.java.advanced.server.domain.Book;
import be.syntra.java.advanced.server.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {

    private BookService bookService;

    @Autowired
    public BooksController(@Qualifier("jpaBookService") BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public BookList getBooks() {
        return new BookList(bookService.getAllBooks());
    }

    @GetMapping("{isbn}")
    public Book getBook(@PathVariable("isbn") String isbn) {
        return bookService.getBook(isbn);
    }

}
