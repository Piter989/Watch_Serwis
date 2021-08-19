package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class Main extends Application {

    private final int WITH = 1224;
    private final int HEIGHT = 800;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/app.fxml"));
        SQLiteHelper.DBConnector();
        SQLiteHelper.CheckConnection();
        primaryStage.setScene(new Scene(root, WITH, HEIGHT));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();



    }


    public static void main(String[] args) {
        launch(args);
    }
}
