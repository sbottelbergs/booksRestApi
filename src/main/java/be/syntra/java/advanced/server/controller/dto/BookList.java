package be.syntra.java.advanced.server.controller.dto;

import be.syntra.java.advanced.server.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookList {
    private List<Book> books;
}
