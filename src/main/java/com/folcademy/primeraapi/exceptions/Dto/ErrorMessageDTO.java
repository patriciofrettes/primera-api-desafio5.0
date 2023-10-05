package com.folcademy.primeraapi.exceptions.Dto;

public class ErrorMessageDTO {

    private final String message;

    public ErrorMessageDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
