package be.syntra.java.advanced.server.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends CustomException {
    public NotFoundException(String message, String title) {
        super(message, HttpStatus.NOT_FOUND, title);
    }
}
