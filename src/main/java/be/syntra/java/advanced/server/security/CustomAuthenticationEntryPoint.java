package be.syntra.java.advanced.server.security;

import be.syntra.java.advanced.server.controller.dto.ApiError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    Logger logger = LoggerFactory.getLogger(getClass());


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        String message = String.format(
                "Not authorized to perform %s operation on '%s'",
                request.getMethod(),
                request.getServletPath()
        );

        logger.info(message, authException);

        ApiError apiError = ApiError.builder()
                .title("Unauthorized")
                .message(message)
                .status(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .code(HttpStatus.UNAUTHORIZED.value())
                .build();

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}
