package be.syntra.java.advanced.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ApiError {
    private String title;
    private String message;
    private String status;
    private int code;
}
