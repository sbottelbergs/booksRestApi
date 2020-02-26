package be.syntra.java.advanced.server.security;

import be.syntra.java.advanced.server.controller.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        String message = String.format(
                "Not allowed to perform %s operation on '%s'",
                request.getMethod(),
                request.getServletPath()
        );

        logger.info(message, accessDeniedException);

        ApiError apiError = ApiError.builder()
                .title("Forbidden")
                .message(message)
                .status(HttpStatus.FORBIDDEN.getReasonPhrase())
                .code(HttpStatus.FORBIDDEN.value())
                .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}
