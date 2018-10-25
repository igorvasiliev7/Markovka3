package controller;

import dao.factory.DaoFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Client;
import start.AppManagerService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
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

        btnByStatus.setOnAction(event -> filterByStatus());
        btnAllClients.setOnAction(event -> filterAllClients());
        btnLogin.setOnAction(event->login() );
    }

    private void filterAllClients() {
        FilterController.clients = DaoFactory.getClientDao().findAll();
        new AppManagerService().changeStage("Filter by status", "filter");
    }

    private void filterByStatus() {
      FilterController.clients = DaoFactory.getClientDao().findByStatus(choiceStatus.getValue().toString());
      new AppManagerService().changeStage("Filter by status", "filter");
    }

    private void login() {
        String phone = txtPhone.getText();

        if(!checkIfPhone(phone)) return;

        try {
            Client client = DaoFactory.getClientDao().findByPhone(phone);
            if(client==null){
                CreateController.setPhone(phone);
                nextStage("Add client", "create");
            }else {
                ClientController.setClient(client);
                nextStage("Client`s card", "client");
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

    private void nextStage(String title, String fileName) {
        new AppManagerService().changeStage(title,fileName);
    }
}
