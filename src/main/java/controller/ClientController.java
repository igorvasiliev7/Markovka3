package controller;

import dao.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Call;
import model.Client;
import model.Visit;

import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    private static Client client;
    @FXML
    private Button btnAddCard;
    @FXML
    private Button btnDelCatd;
    @FXML
    private Button btnDelVisit;
    @FXML
    private Button btnDelCall;
    @FXML
    private TableView<Call> tableCalls;
    @FXML
    private TableColumn<Call, String> columnCallDate;
    @FXML
    private TableColumn<Call, String> columnComment;
    @FXML
    private DatePicker dateCall;
    @FXML
    private TextField txtComment;
    @FXML
    private Button btnAddCall;
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
    private ObservableList<Call> callList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        columnDate.setCellValueFactory(new PropertyValueFactory<Visit, String>("date"));
        columnAmount.setCellValueFactory(new PropertyValueFactory<Visit, Integer>("amount"));
        columnCallDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        columnComment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        txtClientName.setText(client.getName());
        txtClientPhone.setText(client.getPhone());
        txtClientStatus.setText(client.getStatus());
        checkCard.setSelected(client.getCard() == 1);

        printVisitTable();
        printCallTable();

        btnAddVisit.setOnAction(event -> addVisit());
        btnAddCall.setOnAction(event -> addCall());
        btnDelVisit.setOnAction(event -> delVisit());
        btnDelCall.setOnAction(event -> delCall());
        btnAddCard.setOnAction(event -> update(1));
        btnDelCatd.setOnAction(event -> update(0));
    }

    private void update(int i) {
        client.setCard(i);
        DaoFactory.getClientDao().updateCard(client);
        if(i==1)checkCard.setSelected(true);
        else checkCard.setSelected(false);
    }

    private void delCall() {
        Call call = tableCalls.getSelectionModel().getSelectedItem();
        DaoFactory.getCallDao().delete(call);
        callList.remove(call);
    }

    private void delVisit() {
      Visit visit = tableVisits.getSelectionModel().getSelectedItem();
      DaoFactory.getVisitDao().delete(visit);
        visitList.remove(visit);
        visitAndSum();
    }

    private void addCall() {
        if (dateCall.getValue() == null) {
            txtWrongAmount.setText("Choose call date");
            return;
        }
        final Call call = new Call(client.getId(), dateCall.getValue().toString(), txtComment.getText());
        DaoFactory.getCallDao().call(call);
        callList.add(DaoFactory.getCallDao().findTheLast());
        tableCalls.setItems(callList);
        txtWrongAmount.setText("Added!");

    }

    private void printCallTable() {
        callList.addAll(DaoFactory.getCallDao().findByUserId(client.getId()));
        tableCalls.setItems(callList);
    }

    private void printVisitTable() {
        visitList.addAll(DaoFactory.getVisitDao().findByUserId(client.getId()));
        visitAndSum();
        tableVisits.setItems(visitList);
    }

    private void visitAndSum() {
        txtVisits.setText(visitList.size() + " visits");
        int sum = 0;
        for (Visit visit : visitList) {
            sum += visit.getAmount();
        }
        txtSum.setText("" + sum + " grn");
    }

    private void addVisit() {
        String amount = txtAmount.getText();
        if (amount.isEmpty()) amount = "0";
        if (dateVisit.getValue() == null || !amount.matches("[0-9]+")) {
            txtWrongAmount.setText("Wrong date/amount");
            return;
        }
        final Visit visit = new Visit(client.getId(), dateVisit.getValue().toString(), Integer.parseInt(amount));
        DaoFactory.getVisitDao().add(visit);
        visitList.add(DaoFactory.getVisitDao().findTheLast());
        tableVisits.setItems(visitList);
        visitAndSum();
        txtWrongAmount.setText("Added!");

    }

    public static void setClient(Client client) {
        ClientController.client = client;
    }

    public void pressedEnterCall(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addCall();
        }

    }

    public void pressedEnterVisit(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addVisit();
        }
    }
}
