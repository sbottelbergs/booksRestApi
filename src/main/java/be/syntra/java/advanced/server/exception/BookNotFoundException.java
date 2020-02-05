package be.syntra.java.advanced.server.exception;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {
        super(message, "Book not found");
    }
}
