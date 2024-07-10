package ru.clevertec.check.params;

import ru.clevertec.check.exception.CheckException;

public class ParamsProcessor {

    private final RequiredParams requiredParams;
    private final OptionalParams optionalParams;

    public ParamsProcessor(String[] args) {
        this.requiredParams = new RequiredParams(args);
        this.optionalParams = new OptionalParams(args);
    }

    public void process() throws CheckException {
        this.requiredParams.process();
        this.optionalParams.process();
    }

    public OptionalParams getOptionalParams() {
        return optionalParams;
    }

    public RequiredParams getRequiredParams() {
        return requiredParams;
    }
}
