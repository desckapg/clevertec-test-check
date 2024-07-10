package ru.clevertec.check.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.paramresolver.DiscountCardParamResolver;
import ru.clevertec.check.paramresolver.ServiceParamResolver;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith({
        ServiceParamResolver.class,
        DiscountCardParamResolver.class
})
public class DiscountCardServiceTest {

    private final DiscountCardService discountCardService;

    DiscountCardServiceTest(DiscountCardService discountCardService) {
        this.discountCardService = discountCardService;
    }

    @BeforeAll
    void load() throws CheckException {
        this.discountCardService.load();
    }

    @Test
    @Order(1)
    void add(DiscountCard discountCard) {
        assertDoesNotThrow(() -> this.discountCardService.addDiscountCard(discountCard));
    }

    @Test
    @Order(2)
    void get(DiscountCard discountCard) {
        assertTrue(this.discountCardService.isDiscountCardExist(discountCard.getNumber()));
        assertTrue(this.discountCardService.getDiscountCard(discountCard.getNumber()).isPresent());
    }

    @Test
    void update(DiscountCard discountCard) {
        assertDoesNotThrow(() -> this.discountCardService.updateDiscountCard(discountCard));
    }

    @Test
    void delete(DiscountCard discountCard) {
        assertDoesNotThrow(() -> this.discountCardService.deleteDiscountCard(discountCard));
    }

    @AfterAll
    void clear(DiscountCard discountCard) {
        assertDoesNotThrow(() -> this.discountCardService.deleteDiscountCard(discountCard));
    }

}
