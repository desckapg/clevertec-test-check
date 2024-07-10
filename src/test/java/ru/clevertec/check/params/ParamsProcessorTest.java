package ru.clevertec.check.params;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import ru.clevertec.check.exception.CheckException;

import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class ParamsProcessorTest {

    @Test
    void processWithWrongArgs() {
        assertThrows(CheckException.class, () -> {
            new OptionalParams(new String[]{"1-1", "balanceDebitCard=", "discountCard="}).process();
            new RequiredParams(new String[]{"1-1", "balanceDebitCard=", "discountCard="}).process();
        });
    }


    @Test
    @Order(1)
    void processWithRightArgs() {
        assertDoesNotThrow(() -> {
            new OptionalParams(new String[]{"1-1", "balanceDebitCard=100", "discountCard=1111"}).process();
            new RequiredParams(new String[]{"1-1", "balanceDebitCard=100", "discountCard=1111"}).process();
        });
    }


}
