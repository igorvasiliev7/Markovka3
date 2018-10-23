package controller;

import dao.factory.DaoFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtPhone;
    @FXML
    private Text txtWrongNumber;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    btnLogin.setOnAction(event->login() );
    }

    private void login() {
        String phone = txtPhone.getText();
       if(!checkIfPhone(phone)) return;
        DaoFactory.getClientDao().findByPhone(phone);

    }
//||txtPhone.getText().toCharArray().length!=10
    private boolean checkIfPhone(String phone) {
        txtWrongNumber.setText("1");
      // System.out.println(txtPhone.getText().toCharArray()[0]);
        if (phone.isEmpty()||phone.toCharArray()[0]!='0'||phone.toCharArray().length!=10||!phone.matches("[0-9]+")) {
            txtWrongNumber.setText("Wrong phone format `0671112233`");
            return false;
        }
        return true;
    }
}
