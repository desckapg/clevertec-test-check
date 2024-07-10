package ru.clevertec.check.repository.dao;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

public interface DaoFactory {

    Dao<Product> getProductDAO();
    Dao<DiscountCard> getDiscountCardDAO();

}
