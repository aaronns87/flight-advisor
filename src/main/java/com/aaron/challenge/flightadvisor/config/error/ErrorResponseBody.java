package com.aaron.challenge.flightadvisor.config.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@ToString
public class ErrorResponseBody {

    private HttpStatus status;

    private int statusCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private List<String> errors;

    private ErrorResponseBody() {
        timestamp = LocalDateTime.now();
    }

    ErrorResponseBody(HttpStatus status, String message, String error) {
        this();
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.errors = Collections.singletonList(error);
    }

    ErrorResponseBody(HttpStatus status, String message, List<String> errors) {
        this();
        this.status = status;
        this.statusCode = status.value();
        this.message = message;
        this.errors = errors;
    }
}
