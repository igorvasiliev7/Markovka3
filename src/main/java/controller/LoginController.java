package controller;

import dao.factory.DaoFactory;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Client;
import service.AppManagerService;


import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
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

        btnByStatus.setOnAction(event -> filterByStatus(event));
        btnAllClients.setOnAction(event -> filterAllClients(event));
        btnLogin.setOnAction(event->login(event) );
    }

    private void filterAllClients(ActionEvent event) {
        FilterController.clients = DaoFactory.getClientDao().findAll();
        nextStage(event, "filter");
    }

    private void filterByStatus(Event event) {
      FilterController.clients = DaoFactory.getClientDao().findByStatus(choiceStatus.getValue().toString());
      nextStage(event, "filter");
    }

    private void login(Event event) {
        String phone = txtPhone.getText();

        if(!checkIfPhone(phone)) return;

        try {
            Client client = DaoFactory.getClientDao().findByPhone(phone);
            if(client==null){
                CreateController.setPhone(phone);
                nextStage( event, "create");
            }else {
                ClientController.setClient(client);
                nextStage(event, "client");
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

    private void nextStage(Event event, String fileName) {
       new AppManagerService().changeStage(event,fileName);
    }
}
