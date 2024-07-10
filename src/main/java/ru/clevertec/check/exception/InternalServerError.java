package ru.clevertec.check.exception;

public class InternalServerError extends CheckException {

    public InternalServerError(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getType() {
        return "INTERNAL SERVER ERROR";
    }
}
