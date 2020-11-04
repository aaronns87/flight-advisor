package com.aaron.challenge.flightadvisor.config.error;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DATA_VALIDATION_ERROR = "Data validation error.";
    private static final String FIELD_VALIDATION_ERROR = "Field validation error.";
    private static final String CONSTRAINT_VALIDATION_ERROR = "Constraint validation error.";
    private static final String CONSTRAINT_VIOLATION_ERROR = "Constraint violation error.";
    private static final String FILE_TOO_LARGE_ERROR = "File too large.";

    @ExceptionHandler({ResponseStatusException.class})
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException exception) {
        return getResponseEntity(new ErrorResponseBody(exception.getStatus(), DATA_VALIDATION_ERROR, exception.getReason()));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxSizeException(MaxUploadSizeExceededException exception) {
        return getResponseEntity(new ErrorResponseBody(HttpStatus.EXPECTATION_FAILED, FILE_TOO_LARGE_ERROR, exception.getLocalizedMessage()));
    }

    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<Object> handleValidationException(ValidationException exception) {
        var errors = Arrays.stream(exception.getMessage().split(", "))
                .map(splitByComma -> {
                    var splitByColon = splitByComma.split(":");
                    return splitByColon[splitByColon.length - 1].trim();
                })
                .collect(Collectors.toList());
        return getResponseEntity(new ErrorResponseBody(HttpStatus.BAD_REQUEST, FIELD_VALIDATION_ERROR, errors));
    }

    @NotNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NotNull HttpHeaders headers, @NotNull HttpStatus status, @NotNull WebRequest request) {
        var errors = new ArrayList<String>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return getResponseEntity(new ErrorResponseBody(HttpStatus.BAD_REQUEST, FIELD_VALIDATION_ERROR, errors));
    }

    @ExceptionHandler({RepositoryConstraintViolationException.class})
    public ResponseEntity<Object> handleRepositoryConstraintViolationException(RepositoryConstraintViolationException exception) {
        var errors = exception.getErrors().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        return getResponseEntity(new ErrorResponseBody(HttpStatus.BAD_REQUEST, CONSTRAINT_VALIDATION_ERROR, errors));
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception) {
        return getResponseEntity(
                new ErrorResponseBody(HttpStatus.CONFLICT, CONSTRAINT_VIOLATION_ERROR, parseConstraintViolationExceptionMessage(exception.getSQLException().getMessage().replace("\n", ","))));
    }

    private ResponseEntity<Object> getResponseEntity(ErrorResponseBody apiErrorResponseBody) {
        logger.info("Error happened: " + apiErrorResponseBody);
        return new ResponseEntity<>(apiErrorResponseBody, new HttpHeaders(), apiErrorResponseBody.getStatus());
    }

    private String parseConstraintViolationExceptionMessage(String message) {
        if (message.contains("duplicate key value violates unique constraint")) {
            Pattern pattern = Pattern.compile("\\((.*?)\\)");
            Matcher matcher = pattern.matcher(message);

            if (matcher.find()) {
                String[] keys = matcher.group(1).split(",");

                if (matcher.find()) {
                    String[] values = matcher.group(1).split(",");

                    List<String> violations = new ArrayList<>();

                    for (int i = 0; i < keys.length; i++) {
                        violations.add(keys[i].replace("_", " ").trim() + " = " + values[i].trim());
                    }

                    return String.format("Entry with %s already exists.", StringUtils.join(violations, ", "));
                }
            }
        }

        return message;
    }
}
