package com.aaron.challenge.flightadvisor.config.error.imports;

public class MissingImportFieldException extends RuntimeException {

    public MissingImportFieldException(String message) {
        super(message);
    }
}
