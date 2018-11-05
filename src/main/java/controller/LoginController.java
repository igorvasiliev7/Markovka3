package controller;

import dao.factory.DaoFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Client;
import service.AppManagerService;
import utilites.CVCtoDatabase;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Text txtExcel;
    @FXML
    private Button btnExportToExcel;
    @FXML
    private Button btnAllClients;
    @FXML
    private ChoiceBox choiceStatus;
    @FXML
    private Button btnByStatus;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtPhone;
    @FXML
    private Text txtWrongNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        choiceStatus.getItems().add("Новий клієнт");
        choiceStatus.getItems().add("Єдиноразовий");
        choiceStatus.getItems().add("Постійний клієнт");
        choiceStatus.getItems().add("Не повернулась");
        choiceStatus.getSelectionModel().selectFirst();

        btnLogin.setOnAction(event->login(event) );
        btnByStatus.setOnAction(event -> filterByStatus(event));
        btnAllClients.setOnAction(event -> filterAllClients(event));
        btnExportToExcel.setOnAction(event -> exportToExcel());

    }

    private void exportToExcel() {
     txtExcel.setText(new CVCtoDatabase().exportToExcel());
    }

    private void filterByStatus(Event event) {
        FilterController.clients = DaoFactory.getClientDaoDtoImpl().findAllDtoByStatus(choiceStatus.getValue().toString());
        nextStage(event, "filter", "Filter by status "+choiceStatus.getValue().toString());
    }

    private void filterAllClients(ActionEvent event) {
        FilterController.clients = DaoFactory.getClientDaoDtoImpl().findAllDto();
        nextStage(event, "filter", "All clients filter");
    }

    private void login(Event event) {
        String phone = txtPhone.getText();
        txtPhone.setText("");

        if(!checkIfPhone(phone)) return;

        try {
            Client client = DaoFactory.getClientDao().findByPhone(phone);
            if(client==null){
                CreateController.setPhone(phone);
                nextStage( event, "create", "Add new client");
            }else {
                ClientController.client = client;
                nextStage(event, "client", "Client`s info");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private boolean checkIfPhone(String phone) {
        if (phone.isEmpty()||phone.toCharArray()[0]!='0'||phone.toCharArray().length!=10||!phone.matches("[0-9]+")) {
            txtWrongNumber.setText("Wrong phone format `0671112233`");
            return false;
        }
        return true;
    }

    private void nextStage(Event event, String fileName, String title) {
       new AppManagerService().changeStageModal(event,fileName, title);
    }

    public void handleEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login(event);
        }
    }
}
