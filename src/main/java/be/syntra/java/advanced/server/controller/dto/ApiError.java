package be.syntra.java.advanced.server.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ApiError {
    private String title;
    private String message;
    private String status;
    private int code;
}
