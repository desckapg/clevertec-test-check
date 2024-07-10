package ru.clevertec.check.repository.sql;

import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.repository.sql.dao.SqlDaoFactoryImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlRepository {

    private final String url;
    private final String user;
    private final String password;

    private SqlConnectionManager connectionManager;
    private SqlDaoFactoryImpl sqlDaoFactory;


    public SqlRepository(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public void load() throws CheckException {
        this.connectionManager = new SqlConnectionManager(url, user, password);

        try {
            Connection connection = connectionManager.getConnection();

            Statement statement = connection.createStatement();
            statement.execute("""
               CREATE TABLE IF NOT EXISTS product (
               id BIGSERIAL PRIMARY KEY,
               description varchar(50) NOT NULL,
               price DECIMAL NOT NULL,
               quantity_in_stock INTEGER NOT NULL,
               wholesale_product BOOLEAN NOT NULL)
               """);
            statement.close();

            statement = connection.createStatement();
            statement.execute("""
               CREATE TABLE IF NOT EXISTS discount_card (
               id SERIAL PRIMARY KEY,
               number INTEGER NOT NULL UNIQUE,
               amount SMALLINT CHECK (amount >= 0 AND amount <= 100))
               """);
            statement.close();

            connection.close();

            this.sqlDaoFactory = new SqlDaoFactoryImpl(connectionManager);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CheckException e) {
            throw e;
        }
    }

    public SqlConnectionManager getConnectionManager() {
        return connectionManager;
    }

    public SqlDaoFactoryImpl getDaoFactoryImpl() {
        return sqlDaoFactory;
    }
}
