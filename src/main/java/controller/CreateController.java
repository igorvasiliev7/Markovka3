package controller;

import dao.factory.DaoFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Client;
import start.AppManager;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    public static String phone;

    @FXML
    public TextField txtName;
    @FXML
    public Text txtPhone;
    @FXML
    public ChoiceBox choiceStatus = new ChoiceBox();
    @FXML
    public TextField txtAmount;
    @FXML
    public DatePicker txtDate;
    @FXML
    public Button btnAdd;
    @FXML
    public Button btnBackToLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    AppManager.getStage().setTitle("Client creation");

    txtPhone.setText(phone);

    choiceStatus.getItems().add("Choice 1");
    choiceStatus.getItems().add("Choice 2");
    choiceStatus.getItems().add("Choice 3");

    btnBackToLogin.setOnAction(event -> back());
    btnAdd.setOnAction(event -> add());

    }

    private void add() {
        Client client = new Client(txtName.getText(),txtPhone.getText(), choiceStatus.getValue().toString());

        try {
           client = DaoFactory.getClientDao().add(client);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void back() {
        AppManager.getStage().setTitle("Log In");
        try {
            AppManager.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/main.fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}