package sample.controller;

import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class HelpController {
    public static final String URL_DB  ="jdbc:sqlite:D:\\Projekty\\Watch_Serwis\\src\\main\\resources\\database\\DB.db";
    public static void initializeExitButton(Button button, BorderPane pane) {
        button.setOnAction((x) -> {
            Stage stage = (Stage) pane.getScene().getWindow();
            stage.close();
        });
    }
}


