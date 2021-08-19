package sample.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.SQLiteHelper;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddWatchController implements Initializable {

    public File file;
    private FileInputStream fis;

    String query = null;
    Connection connection = null;
    PreparedStatement preparedStatement = null;

    Image image;
    @FXML
    private BorderPane addEmployeeBorderPane;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField idWatch;


    @FXML
    private TextField nameWatchTextField;

    @FXML
    private TextField currentPriceTextField;

    @FXML
    private TextField actuallyPriceTextField;

    @FXML
    private TextArea commentsTextArea;

    @FXML
    private TextField ulrTextField;
    @FXML
    private Button addUrlButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSaveButton();
        initializeAddUrlButton();
        HelpController.initializeExitButton(cancelButton,addEmployeeBorderPane);

    }

    private void initializeSaveButton() {
        saveButton.setOnAction((x)->{
            connection = SQLiteHelper.DBConnector();

            String idWatch = this.idWatch.getText();
            String nameWatch = nameWatchTextField.getText();
            String currentPrice = currentPriceTextField.getText();
            String actuallyPrice = actuallyPriceTextField.getText();
            String textArea = commentsTextArea.getText();
            String urlImage = ulrTextField.getText();
            String urlText = ulrTextField.getText();


            if(idWatch.isEmpty()|| nameWatch.isEmpty() || currentPrice.isEmpty() || actuallyPrice.isEmpty() || textArea.isEmpty() || urlText.isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Uzupełnij wszystkie pola");
                alert.showAndWait();
            }else {
                getQuery();
                insert();
                JOptionPane.showMessageDialog(null,"Dodano nową pozycję");
                closeWindow();
            }

        });
    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, idWatch.getText());
            preparedStatement.setString(2, nameWatchTextField.getText());
            preparedStatement.setString(3, currentPriceTextField.getText());
            preparedStatement.setString(4, actuallyPriceTextField.getText());
            preparedStatement.setString(5, commentsTextArea.getText());

            fis = new FileInputStream(file);
            preparedStatement.setBinaryStream(6,(InputStream) fis,(int)file.length());
            preparedStatement.setString(7, ulrTextField.getText());
            preparedStatement.execute();

        } catch (SQLException | FileNotFoundException ex) {
            Logger.getLogger(AddWatchController.class.getName()).log(Level.SEVERE,null,ex);
        }

    }

    private void getQuery() {
        query = "INSERT INTO Watch ( idWatch, nameWatch, currentPrice, actuallyPrice, textArea, urlImage, urlText) VALUES  (?,?,?,?,?,?,?)";
    }

    private void closeWindow(){
        Stage stage = (Stage) addEmployeeBorderPane.getScene().getWindow();
        stage.close();
    }


    private void initializeAddUrlButton() {
        addUrlButton.setOnAction((x)->{
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) addEmployeeBorderPane.getScene().getWindow();
        file = fileChooser.showOpenDialog(stage);
            if(file !=null){
                Desktop desktop = Desktop.getDesktop();
                ulrTextField.setText(file.getAbsolutePath());

                try {
                    desktop.open(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
    }
    void setTextField( String idWatch,String nameWatch,String currentPrice,String actuallyPrice, String textArea, String url ){
        this.idWatch.setText(idWatch);
        this.nameWatchTextField.setText(nameWatch);
        this.currentPriceTextField.setText(currentPrice);
        this.actuallyPriceTextField.setText(actuallyPrice);
        this.commentsTextArea.setText(textArea);
        this.ulrTextField.setText(url);
    }

}

