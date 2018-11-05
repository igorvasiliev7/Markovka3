package dao.impl;

import model.Client;
import model.ClientDTO;

import java.sql.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class ClientDaoDtoImpl {
    private int number;
    List<ClientDTO> clients = new LinkedList<>();

    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    private String dtoWithCalls = "SELECT cl.id, cl.name, cl.phone, cl.status, cl.card, ca_date, ca.comment, vi_date, vi_sum\n" +
            "from clients cl,\n" +
            "  (SELECT client_id, Max(date) as ca_date, comment FROM calls GROUP BY client_id) as ca,\n" +
            "  (SELECT client_id, Max(date) as vi_date, SUM(amount) as vi_sum FROM visits GROUP BY client_id) AS vi\n" +
            "WHERE vi.client_id=cl.id AND ca.client_id=cl.id;";

    private String dtoByStatusWithCalls = "SELECT cl.id, cl.name, cl.phone, cl.status, cl.card, ca_date, ca.comment, vi_date, vi_sum\n" +
            "from clients cl,\n" +
            "  (SELECT client_id, Max(date) as ca_date, comment FROM calls GROUP BY client_id) as ca,\n" +
            "  (SELECT client_id, Max(date) as vi_date, SUM(amount) as vi_sum FROM visits GROUP BY client_id) AS vi\n" +
            "WHERE vi.client_id=cl.id AND ca.client_id=cl.id AND cl.status=?;";

    private String dtoWithNoCalls = "SELECT cl.id, cl.name, cl.phone, cl.status, cl.card,vi.client_id, vi_date, vi_sum\n" +
            "from clients cl,\n" +
            "  (SELECT client_id, Max(date) as vi_date, SUM(amount) as vi_sum FROM visits GROUP BY client_id) AS vi\n" +
            "WHERE vi.client_id=cl.id and cl.id NOT IN (SELECT ca.client_id FROM calls ca);";

    private String dtoByStatusNoCalls = "SELECT cl.id, cl.name, cl.phone, cl.status, cl.card,vi.client_id, vi_date, vi_sum\n" +
            "from clients cl,\n" +
            "  (SELECT client_id, Max(date) as vi_date, SUM(amount) as vi_sum FROM visits GROUP BY client_id) AS vi\n" +
            "WHERE vi.client_id=cl.id and cl.id NOT IN (SELECT ca.client_id FROM calls ca) AND cl.status=?;";

    private String dtoExportWithCalls = "SELECT id, date as vi_date, amount as vi_sum, name, phone, status, card, ca_date, comment\n" +
            "FROM visits vi,\n" +
            "  (SELECT id as id2, name, phone, status, card FROM clients ) as cl,\n" +
            "  (SELECT client_id, Max(date) as ca_date, comment FROM calls GROUP BY client_id) as ca\n" +
            "  WHERE vi.client_id=id2 AND vi.client_id=ca.client_id;";

    private String getDtoExportNoCalls = "SELECT id, date as vi_date, amount as vi_sum, name, phone, status, card\n" +
            "FROM visits vi,\n" +
            "  (SELECT id as id2, name, phone, status, card FROM clients ) as cl\n" +
            "  WHERE vi.client_id=id2 AND id2 NOT IN (SELECT ca.client_id FROM calls ca);";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public List<ClientDTO> exportAllDto(){
        List<ClientDTO> clientsFull = new LinkedList<>();
        clientsFull.addAll(exportDtoWithCall());
        clientsFull.addAll(exportDtoNoCalls());
        return clientsFull;
    }

    private List<ClientDTO> exportDtoNoCalls() {
        clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getDtoExportNoCalls)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            iterNoCalls(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    private List<ClientDTO> exportDtoWithCall() {
        clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoExportWithCalls)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            iterWithCalls(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }



    public List<ClientDTO> findAllDto(){
        number = 0;
        List<ClientDTO> clientsFull = new LinkedList<>();
        clientsFull.addAll(findDtoWithCall());
        clientsFull.addAll(findDtoNoCalls());
        return clientsFull;
    }

    public List<ClientDTO> findAllDtoByStatus(String status){
        number = 0;
        List<ClientDTO> clientsFull = new LinkedList<>();
        clientsFull.addAll(findDtoByStatusWithCall(status));
        clientsFull.addAll(findDtoByStatusNoCalls(status));
        return clientsFull;
    }

    public List<ClientDTO> findDtoWithCall() {
        clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoWithCalls)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            iterWithCalls(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public List<ClientDTO> findDtoNoCalls(){
        clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoWithNoCalls)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            iterNoCalls(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;

    }

    public List<ClientDTO> findDtoByStatusWithCall(String status) {
            clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoByStatusWithCalls)) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            iterWithCalls(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    public List<ClientDTO> findDtoByStatusNoCalls(String status){
        clients = new LinkedList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(dtoByStatusNoCalls)) {
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            iterNoCalls(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;

    }

    private void iterWithCalls(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                ClientDTO client = new ClientDTO(++number);
               client.setClientId(resultSet.getInt("id"));
                client.setClientName(resultSet.getString("name"));
                client.setClientPhone(resultSet.getString("phone"));
                client.setClientStatus(resultSet.getString("status"));
                client.setLastCallDate(resultSet.getString("ca_date"));
                client.setVisitDate(resultSet.getString("vi_date"));
                client.setComment(resultSet.getString("comment"));
                client.setCard(resultSet.getInt("card"));
                client.setVisitsSum(resultSet.getInt("vi_sum"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void iterNoCalls(ResultSet resultSet) {
        try {
            while (resultSet.next()) {
                ClientDTO client = new ClientDTO(++number,"-","-");
                client.setClientId(resultSet.getInt("id"));
                client.setClientName(resultSet.getString("name"));
                client.setClientPhone(resultSet.getString("phone"));
                client.setClientStatus(resultSet.getString("status"));
                client.setCard(resultSet.getInt("card"));
                client.setVisitDate(resultSet.getString("vi_date"));
                client.setVisitsSum(resultSet.getInt("vi_sum"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
