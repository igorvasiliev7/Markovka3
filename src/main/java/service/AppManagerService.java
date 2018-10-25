package service;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class AppManagerService {
    public void changeStage(Event event, String file) {
        try {
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/"+file+".fxml"));
            final Parent parent = loader.load();

            final Stage stage = new Stage();
            final Scene scene = new Scene(parent);
           // scene.getStylesheets().add("css/main.css");
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            Window window = ((Node) event.getSource()).getScene().getWindow();
            stage.initOwner(window);
            stage.show();

//            stage.setOnHiding(e -> {
//                todoList.add(0,findTheLastOne());
//                todosTable.setItems(todoList);
                // TODO: 24.09.2018 first
//            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

