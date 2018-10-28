package controller;

import dao.factory.DaoFactory;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import model.Client;
import model.Visit;
import start.AppManager;
import service.AppManagerService;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CreateController implements Initializable {

    private static String phone;

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
    public Text txtWrongAmount;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    AppManager.getStage().setTitle("Client creation");

    txtPhone.setText(phone);

    choiceStatus.getItems().add("Новий клієнт");
    choiceStatus.getItems().add("Єдиноразовий");
    choiceStatus.getItems().add("Постійний клієнт");
    choiceStatus.getItems().add("Не повернулась");
    choiceStatus.getSelectionModel().selectFirst();

    btnAdd.setOnAction(event -> add("client", "Client`s info"));

    }
    private void add(String file, String title) {
        String amount = txtAmount.getText();
        if (amount.isEmpty()){
            amount = "0";
        }
        if(txtDate.getValue()==null||!amount.matches("[0-9]+")){
            txtWrongAmount.setText("Fill amount correctly or choose date");
            return;
        }

        Client client = new Client(txtName.getText(),txtPhone.getText(), choiceStatus.getValue().toString());

        try {
          client = DaoFactory.getClientDao().add(client);
           Visit visit = new Visit(client.getId(),txtDate.getValue().toString(), Integer.valueOf(amount));
           DaoFactory.getVisitDao().add(visit);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ClientController.setClient(client);
        nextStage(file, title);
    }

    private void nextStage(String fileName, String title) {
        new AppManagerService().changeStageAfterModal(fileName, title);
    }

    public static void setPhone(String phone) {
        CreateController.phone = phone;
    }

    public void pressedEnter(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            add("client", "Client`s info");
        }
    }
}
