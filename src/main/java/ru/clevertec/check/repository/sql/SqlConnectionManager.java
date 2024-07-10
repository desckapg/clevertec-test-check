package ru.clevertec.check.repository.sql;

import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.CheckException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqlConnectionManager {

    private final String url;
    private final String user;
    private final String password;

    public SqlConnectionManager(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Connection getConnection() throws CheckException {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ignored) {
            throw new BadRequestException("WRONG DB PARAMETERS");
        }
    }
}
