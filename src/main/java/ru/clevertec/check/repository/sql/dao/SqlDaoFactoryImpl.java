package ru.clevertec.check.repository.sql.dao;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.dao.Dao;
import ru.clevertec.check.repository.dao.DaoFactory;
import ru.clevertec.check.repository.sql.SqlConnectionManager;

public class SqlDaoFactoryImpl implements DaoFactory {

    private final SqlDaoProductImpl sqlDaoProduct;
    private final SqlDaoDiscountCardImpl sqlDaoDiscountCard;

    public SqlDaoFactoryImpl(SqlConnectionManager connectionManager) {
        this.sqlDaoProduct = new SqlDaoProductImpl(connectionManager);
        this.sqlDaoDiscountCard = new SqlDaoDiscountCardImpl(connectionManager);
    }

    @Override
    public Dao<Product> getProductDAO() {
        return this.sqlDaoProduct;
    }

    @Override
    public Dao<DiscountCard> getDiscountCardDAO() {
        return this.sqlDaoDiscountCard;
    }
}
