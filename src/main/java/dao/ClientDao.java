package dao;

import model.Client;

import java.sql.SQLException;

public interface ClientDao {
   void add(Client client) throws SQLException;
   Client findByPhone(String phone) throws SQLException;
}
