package be.syntra.java.advanced.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
public class BookDto {
    private String isbn;
    private String title;
    private String author;
    private BigDecimal price;
}
