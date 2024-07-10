package ru.clevertec.check.exception;

public abstract class CheckException extends Exception {

    public abstract String getType();

    public CheckException(String errorMessage) {
        super(errorMessage);
    }

}
