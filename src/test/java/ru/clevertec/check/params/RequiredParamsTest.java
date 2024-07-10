package ru.clevertec.check.params;

import org.junit.jupiter.api.ClassOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import ru.clevertec.check.exception.CheckException;

import static org.junit.jupiter.api.Assertions.*;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class RequiredParamsTest {

    @Test
    @Order(1)
    void processWithNoArgs() {
        assertThrows(CheckException.class, () -> new RequiredParams(new String[]{}).process());
    }

    @Test
    @Order(1)
    void processWithWrongBalanceDebitCard() {
        assertThrows(CheckException.class,
                () -> new RequiredParams(new String[]{"3-1 2-5 5-1"}).process());
        assertThrows(CheckException.class,
                () -> new RequiredParams(new String[]{"3-1 2-5 5-1 balanceDebitCard="}).process());
        assertThrows(CheckException.class,
                () -> new RequiredParams(new String[]{"3-1 2-5 5-1 balanceDebitCard=gfhf"}).process());
    }


    @Test
    @Order(1)
    void processWithWrongID() {
        assertThrows(CheckException.class,
                () -> new RequiredParams(new String[]{"balanceDebitCard=100"}).process());
    }

    @Test
    @Order(2)
    void processWithRightArgs() {
        assertDoesNotThrow(() -> new RequiredParams(new String[]{"3-1", "2-5", "5-1",
                "balanceDebitCard=100",
                "saveToFile=result.csv",
                "datasource.url=jdbc:postgresql://localhost:5432/test",
                "datasource.username=postgres",
                "datasource.password=postgres"
        }).process());
    }


    @Test
    void checkParamsAfterProcess() {
        assertDoesNotThrow(() -> {
            try {
                RequiredParams params =  new RequiredParams(new String[]{"3-1", "2-5", "5-1",
                        "balanceDebitCard=100",
                        "saveToFile=result.csv",
                        "saveToFile=result.csv",
                        "datasource.url=jdbc:postgresql://localhost:5432/test",
                        "datasource.username=postgres",
                        "datasource.password=postgres"
                });
                params.process();
            } catch (CheckException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
