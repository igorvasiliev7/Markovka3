package controller;

import dao.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Client;
import model.ClientDTO;
import service.AppManagerService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    public static List<ClientDTO> clients;
    @FXML
    private TableColumn columnNumber;
    @FXML
    private TableColumn columnVisitDate;
    @FXML
    private TableColumn columnVisitSum;
    @FXML
    private TableColumn columnComment;
    @FXML
    private TableColumn<ClientDTO, String> columnCallDate;
    @FXML
    private TableView<ClientDTO> tableClient;
    @FXML
    private TableColumn<ClientDTO, String> columnName;
    @FXML
    private TableColumn<ClientDTO, String> columnPhone;
    @FXML
    private TableColumn<ClientDTO, String> columnStatus;
    @FXML
    private TableColumn<ClientDTO, Integer> columnCard;

    private ObservableList<ClientDTO> listClient = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        columnName.setCellValueFactory(new PropertyValueFactory<>("clientName"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("clientPhone"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("clientStatus"));
        columnCard.setCellValueFactory(new PropertyValueFactory<>("card"));
        columnCallDate.setCellValueFactory(new PropertyValueFactory<>("lastCallDate"));
        columnComment.setCellValueFactory(new PropertyValueFactory<>("comment"));
        columnVisitDate.setCellValueFactory(new PropertyValueFactory<>("visitDate"));
        columnVisitSum.setCellValueFactory(new PropertyValueFactory<>("visitsSum"));
        printClientTable();
    }


    private void printClientTable() {
        listClient.addAll(clients);
        tableClient.setItems(listClient);

        tableClient.setRowFactory(tv -> {
            TableRow<ClientDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty())) {
                    ClientDTO rowData = row.getItem();

                        ClientController.client = new Client(rowData.getClientId(), rowData.getClientPhone(), rowData.getClientName(),rowData.getClientStatus(),rowData.getCard());
                    System.out.println(ClientController.client);

                    changeStage(event, "client", "Client`s info");
                }
            });
            return row ;
        });
}

    private void changeStage(Event event, String file, String title) {
        new AppManagerService().changeStageModal(event, file, title);
    }
}
