package ru.clevertec.check.exception;

public class NotEnoughMoneyException extends CheckException {

    public NotEnoughMoneyException(String errorMessage) {
        super(errorMessage);
    }

    @Override
    public String getType() {
        return "NOT ENOUGH MONEY";
    }
}
