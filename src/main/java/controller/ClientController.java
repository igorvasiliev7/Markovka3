package controller;

import dao.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Client;
import model.Visit;

import java.net.URL;
import java.text.ParseException;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    private static Client client;
    @FXML
    private Text txtWrongAmount;
    @FXML
    private TextField txtAmount;
    @FXML
    private DatePicker dateVisit;
    @FXML
    private Button btnAddVisit;
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

        printTable();



        btnAddVisit.setOnAction(event -> addVisit());
        //TODO make checkbox card active
        //TODO how to add one row, not new table
    }

    private void printTable() {
        visitList.addAll(DaoFactory.getVisitDao().findByUserId(client.getId()));
        visitAndSum();
        tableVisits.setItems(visitList);
    }

    private void visitAndSum() {
        txtVisits.setText(visitList.size()+" visits");
        int sum=0;
        for (Visit visit : visitList) {
            sum+=visit.getAmount();
        }
        txtSum.setText(""+sum+" grn");
    }

    private void addVisit() {
        String amount = txtAmount.getText();
        if(amount.isEmpty())amount = "0";
        if (dateVisit.getValue()==null||!amount.matches("[0-9]+")){
            txtWrongAmount.setText("Choose date or check amount");
            return;
        }
        final Visit visit = new Visit(client.getId(),dateVisit.getValue().toString(), Integer.parseInt(amount));
        DaoFactory.getVisitDao().add(visit);
        visitList.add(DaoFactory.getVisitDao().findTheLast());
        tableVisits.setItems(visitList);
        visitAndSum();
        txtWrongAmount.setText("Successfully added!");

    }

    public static void setClient(Client client) {
        ClientController.client = client;
    }
}
