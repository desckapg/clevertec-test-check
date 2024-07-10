package ru.clevertec.check.core.writer;

import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Check;

public interface ICheckWriter {

    void write(Check check) throws CheckException;

}
