package dao;

import model.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDao {
   Client add(Client client) throws SQLException;
   Client findByPhone(String phone) throws SQLException;

    List<Client> findByStatus(String status);

    void updateCard(Client client);

    List<Client> findAll();
}
