package be.syntra.java.advanced.server.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ApiError {
    private String title;
    private String message;
    private String status;
    private int code;
}
