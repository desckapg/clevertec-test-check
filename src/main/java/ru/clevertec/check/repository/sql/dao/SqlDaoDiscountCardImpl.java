package ru.clevertec.check.repository.sql.dao;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.repository.dao.Dao;
import ru.clevertec.check.repository.sql.SqlConnectionManager;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class SqlDaoDiscountCardImpl implements Dao<DiscountCard> {

    private final SqlConnectionManager connectionManager;

    public SqlDaoDiscountCardImpl(SqlConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public DiscountCard get(int id) throws CheckException {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM discount_card WHERE id=? LIMIT 1
                    """);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                throw new BadRequestException(String.format("WRONG DISCOUNT CARD ID %s", id));
            }
            return parseResultSet(resultSet);
        } catch (SQLException e) {
            e.getErrorCode();
        }
        return null;
    }

    @Override
    public List<DiscountCard> getAll() throws CheckException {
        List<DiscountCard> cards = new LinkedList<>();
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    SELECT * FROM discount_card
                    """);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                cards.add(parseResultSet(resultSet));
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    private DiscountCard parseResultSet(ResultSet rs) throws SQLException {
        DiscountCard.Builder builder = new DiscountCard.Builder();
        builder.setId(rs.getInt("id"));
        builder.setNumber(rs.getInt("number"));
        builder.setDiscount(rs.getInt("amount"));
        return builder.build();
    }

    @Override
    public void add(DiscountCard card) throws CheckException {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""
                    INSERT INTO discount_card (number, amount) VALUES (?, ?)
                    """, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, card.getNumber());
            statement.setInt(2, card.getDiscount());
            statement.executeUpdate();
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    card.setId(generatedKeys.getInt(1));
                }
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DiscountCard card) throws CheckException {
        try {
            Connection connection = connectionManager.getConnection();
            PreparedStatement statement = connection.prepareStatement("""       
                            UPDATE discount_card SET amount=? WHERE id=?
                    """);
            statement.setInt(1, card.getDiscount());
            statement.setInt(2, card.getId());
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
                            DELETE FROM discount_card WHERE id=?
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
