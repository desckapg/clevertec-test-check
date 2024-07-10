package ru.clevertec.check.params;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import ru.clevertec.check.exception.CheckException;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class OptionalParamsTest {

    @Test
    void processWithNoArgs() {
        assertDoesNotThrow(() -> new OptionalParams(new String[]{}).process());
    }

    @Test
    void processWithWrongDiscountCardNumber() {
        assertThrows(CheckException.class, () -> new OptionalParams(new String[]{"discountCard="}).process());
        assertThrows(CheckException.class, () -> new OptionalParams(new String[]{"discountCard=asdd"}).process());
    }

    @Test
    @Order(1)
    void processWithRightArgs() {
        assertDoesNotThrow(() -> new OptionalParams(new String[]{"discountCard=1111"}).process());
    }


    @Test
    void checkDiscountCardAfterProcess() {
        assertTrue(() -> {
            try {
                OptionalParams params = new OptionalParams(new String[]{"discountCard=1111"});
                params.process();
                return params.isDiscountCardPresent() && Objects.nonNull(params.getDiscountCardNumber());
            } catch (CheckException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
