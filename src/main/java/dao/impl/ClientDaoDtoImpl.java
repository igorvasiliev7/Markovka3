package dao.impl;

import model.Client;
import model.ClientDTO;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class ClientDaoDtoImpl {
    private static final String URL = "jdbc:sqlite:identifier.sqlite";
    private String dtoWithCalls = "SELECT cl.id id, name, phone, status, card, " +
            "MAX(date) date FROM clients cl, calls ca where ca.client_id=cl.id GROUP BY \"client_id\" ORDER BY date DESC ;";

    private String dtoWithNoCalls = "SELECT * FROM clients cl where cl.id NOT IN (select ca.client_id FROM calls ca);";
    private String dtoByStatusNoCalls = "SELECT * FROM clients cl where cl.id NOT IN (select ca.client_id FROM calls ca) AND cl.status = ?;";
    private String dtoByStatusWithCalls = "SELECT cl.id id, name, phone, status, card, " +
            "MAX(date) date FROM clients cl, calls ca where ca.client_id=cl.id AND cl.status = ? GROUP BY \"client_id\" ORDER BY date DESC;";
    public ClientDaoDtoImpl() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);


    }

    public List<ClientDTO> findAllDto(){
        List<ClientDTO> clients = new LinkedList<>();
        clients.addAll(findDtoWithCall());
        clients.addAll(findDtoNoCalls());
        return clients;
    }

    public List<ClientDTO> findAllDtoByStatus(String status){
        List<ClientDTO> clients = new LinkedList<>();
        clients.addAll(findDtoByStatusWithCall(status));
        clients.addAll(findDtoByStatusNoCalls(status));
        return clients;
    }

    public List<ClientDTO> findDtoByStatusWithCall(String status) {
        List<ClientDTO> clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoByStatusWithCalls)) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientDTO client = new ClientDTO();
                client.setClientId(resultSet.getInt("id"));
                client.setClientName(resultSet.getString("name"));
                client.setClientPhone(resultSet.getString("phone"));
                client.setClientStatus(resultSet.getString("status"));
                client.setLastCallDate(resultSet.getString("date"));
                client.setCard(resultSet.getInt("card"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public List<ClientDTO> findDtoByStatusNoCalls(String status){
        List<ClientDTO> clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoByStatusNoCalls)) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientDTO client = new ClientDTO("0");
                client.setClientId(resultSet.getInt("id"));
                client.setClientName(resultSet.getString("name"));
                client.setClientPhone(resultSet.getString("phone"));
                client.setClientStatus(resultSet.getString("status"));
                client.setCard(resultSet.getInt("card"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;

    }

    public List<ClientDTO> findDtoWithCall() {
        List<ClientDTO> clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoWithCalls)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientDTO client = new ClientDTO();
                client.setClientId(resultSet.getInt("id"));
                client.setClientName(resultSet.getString("name"));
                client.setClientPhone(resultSet.getString("phone"));
                client.setClientStatus(resultSet.getString("status"));
                client.setLastCallDate(resultSet.getString("date"));
                client.setCard(resultSet.getInt("card"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public List<ClientDTO> findDtoNoCalls(){
        List<ClientDTO> clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoWithNoCalls)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ClientDTO client = new ClientDTO("0");
                client.setClientId(resultSet.getInt("id"));
                client.setClientName(resultSet.getString("name"));
                client.setClientPhone(resultSet.getString("phone"));
                client.setClientStatus(resultSet.getString("status"));
                client.setCard(resultSet.getInt("card"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;

    }



}
