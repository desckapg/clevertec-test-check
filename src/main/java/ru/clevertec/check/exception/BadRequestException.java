package ru.clevertec.check.exception;

public class BadRequestException extends CheckException {

    @Override
    public String getType() {
        return "BAD REQUEST";
    }

    public BadRequestException(String errorMessage) {
        super(errorMessage);
    }

}
