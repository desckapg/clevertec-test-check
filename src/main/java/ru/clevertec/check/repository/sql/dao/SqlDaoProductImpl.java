package ru.clevertec.check.repository.sql.dao;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.repository.dao.Dao;
import ru.clevertec.check.repository.sql.SqlConnectionManager;
import ru.clevertec.check.util.FloatUtils;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SqlDaoProductImpl implements Dao<Product> {

    private final SqlConnectionManager connectionManager;

    public SqlDaoProductImpl(SqlConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public Product get(int id) throws CheckException {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
            SELECT * FROM product WHERE id=? LIMIT 1
            """);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new BadRequestException(String.format("WRONG PRODUCT ID %s", id));
            }
            resultSet.close();
            statement.close();
            connection.close();
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            e.getErrorCode();
        } catch (CheckException e) {
            throw e;
        }
        return null;
    }

    @Override
    public List<Product> getAll() throws CheckException {
        List<Product> products = new LinkedList<>();
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
            SELECT * FROM product
            """);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(parseResultSet(resultSet));
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (CheckException e) {
            throw e;
        }
        return products;
    }

    private Product parseResultSet(ResultSet rs) throws SQLException {
        Product.Builder builder = new Product.Builder();
        builder.setId(rs.getInt("id"));
        builder.setDescription(rs.getString("description"));
        builder.setPrice(rs.getFloat("price"));
        builder.setWholesale(rs.getBoolean("wholesale_product"));
        builder.setQuantityInStock(rs.getInt("quantity_in_stock"));
        return builder.build();
    }

    @Override
    public void add(Product product) throws CheckException {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO product (description, price, quantity_in_stock, wholesale_product) VALUES (?, ?, ?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, product.getDescription());
            statement.setFloat(2, FloatUtils.round(product.getPrice(), 2));
            statement.setInt(3, product.getQuantityInStock());
            statement.setBoolean(4, product.getWholesale());
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    product.setId(generatedKeys.getInt(1));
                }
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Product product) throws CheckException {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""       
                            UPDATE product SET description=?, price=?, quantity_in_stock=?, wholesale_product=? WHERE id=?
                    """);
            statement.setString(1, product.getDescription());
            statement.setFloat(2, FloatUtils.round(product.getPrice(), 2));
            statement.setInt(3, product.getQuantityInStock());
            statement.setBoolean(4, product.getWholesale());
            statement.setInt(5, product.getId());
            statement.executeUpdate();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    @Override
    public void delete(int id) throws CheckException {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""       
                            DELETE FROM product WHERE id=?
                    """);
            statement.setInt(1, id);
            statement.executeUpdate();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
