package ru.clevertec.check.repository.csv.dao;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.dao.Dao;
import ru.clevertec.check.repository.dao.DaoFactory;

import java.io.BufferedReader;
import java.io.File;

public class CSVDaoFactoryImpl implements DaoFactory {

    private final CSVDaoProductImpl daoProductImpl;
    private final CSVDaoDiscountCardImpl daoDiscountCardImpl;

    public CSVDaoFactoryImpl(String productFilePath, String cardFilePath) {
        this.daoProductImpl = new CSVDaoProductImpl(productFilePath);
        this.daoDiscountCardImpl = new CSVDaoDiscountCardImpl(cardFilePath);
    }

    @Override
    public Dao<Product> getProductDAO() {
        return this.daoProductImpl;
    }

    @Override
    public Dao<DiscountCard> getDiscountCardDAO() {
        return this.daoDiscountCardImpl;
    }
}
