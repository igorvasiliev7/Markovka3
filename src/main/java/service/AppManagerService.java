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
import start.AppManager;

import java.io.IOException;

public class AppManagerService {
    private static Stage stage;
    public void changeStageAfterModal(String file, String title){
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/fxml/"+file+".fxml"))));
            stage.setTitle(title);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeStageModal(Event event, String file, String title) {
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
            stage.setTitle(title);
            stage.show();
            this.stage = stage;

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

