package dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ClientDtoFullImpl {
    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    private String dtoByStatusWithCalls = "SELECT cl.id id, name, phone, status, card, " +
            "MAX(date) date FROM clients cl, calls ca where ca.client_id=cl.id AND cl.status = ? GROUP BY \"client_id\" ORDER BY date DESC;";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}
