package com.sber.javaschool.finalproject.json2http.service.exceptions;

public class InvalidHeaderValue extends RuntimeException {
    public InvalidHeaderValue(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidHeaderValue(String message) {
        super(message);
    }
}
