package com.sber.javaschool.finalproject.json2http.service.exceptions;

public class InvalidHttpBodyException extends RuntimeException {
    public InvalidHttpBodyException(String message) {
        super(message);
    }
}
