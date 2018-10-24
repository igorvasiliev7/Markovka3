package start;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class AppManagerService {
    public void changeStage(String title, String fileName) {
        try {
            AppManager.getStage().setTitle(title);
            AppManager.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/"+fileName+".fxml"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
}}
