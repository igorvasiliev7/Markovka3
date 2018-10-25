package controller;

import dao.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Call;
import model.Client;
import start.AppManagerService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FilterController implements Initializable {
    public static List<Client> clients;
    @FXML
    private TableView<Client> tableClient;
    @FXML
    private Button btnToLogin;
    @FXML
    private TableColumn<Client, String> columnName;
    @FXML
    private TableColumn<Client, String> columnPhone;
    @FXML
    private TableColumn<Client, String> columnStatus;
    @FXML
    private TableColumn<Client, Integer> columnCard;

    private ObservableList<Client> listClient = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        columnPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        columnCard.setCellValueFactory(new PropertyValueFactory<>("card"));
        printClientTable();
        btnToLogin.setOnAction(event -> toLogin());
    }

    private void toLogin() {
        new AppManagerService().changeStage("To login", "login");
    }

    private void printClientTable() {
        listClient.addAll(clients);
        tableClient.setItems(listClient);
    }
}
