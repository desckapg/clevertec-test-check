package ru.clevertec.check.service;

import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.dao.Dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DiscountCardService {

    private final Dao<DiscountCard> discountCardDao;

    public final Map<String, DiscountCard> discountCards;

    public DiscountCardService(Dao<DiscountCard> discountCardDao) {
        this.discountCardDao = discountCardDao;
        this.discountCards = new HashMap<>();
    }

    public void load() throws CheckException {
        for (DiscountCard discountCard : discountCardDao.getAll()) {
            discountCards.put(discountCard.getNumber(), discountCard);
        }
    }

    public boolean isDiscountCardExist(String number) {
        return discountCards.containsKey(number);
    }

    public Optional<DiscountCard> getDiscountCard(String number) {
        return Optional.ofNullable(discountCards.get(number));
    }


}
