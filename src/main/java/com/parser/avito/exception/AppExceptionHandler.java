package com.parser.avito.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ItemServiceException.class})
    public ResponseEntity<Object> handleBadRequestExceptions(Exception ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .time(LocalDateTime.now())
                .path(request.getDescription(false))
                .message(ex.getLocalizedMessage())
                .build();
        return ResponseEntity.badRequest().body(errorMessage);
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ErrorMessage {
        private LocalDateTime time;
        private String path;
        private String message;
    }
}
