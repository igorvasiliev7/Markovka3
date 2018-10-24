package controller;

import dao.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Client;
import model.Visit;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    private static Client client;
    @FXML
    private Text txtVisits;
    @FXML
    private Text txtSum;
    @FXML
    public CheckBox checkCard;
    @FXML
    private Text txtClientName;
    @FXML
    private Text txtClientPhone;
    @FXML
    private Text txtClientStatus;
    @FXML
    private TableView<Visit> tableVisits;
    @FXML
    private TableColumn<Visit, String> columnDate;
    @FXML
    private TableColumn<Visit, Integer> columnAmount;

    private ObservableList<Visit> visitList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnDate.setCellValueFactory(new PropertyValueFactory<Visit, String>("date"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<Visit, Integer>("amount"));

        txtClientName.setText(client.getName());
        txtClientPhone.setText(client.getPhone());
        txtClientStatus.setText(client.getStatus());

        visitList.addAll(DaoFactory.getVisitDao().findByUserId(client.getId()));
        txtVisits.setText(visitList.size()+" visits");
        int sum=0;
        for (Visit visit : visitList) {
            sum+=visit.getAmount();
        }
        txtSum.setText(""+sum+" grn");
        tableVisits.setItems(visitList);
        //TODO make checkbox card active
    }

    public static void setClient(Client client) {
        ClientController.client = client;
    }
}
