package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.SneakyThrows;
import sample.SQLiteHelper;

import sample.table.WatchTableModel;


import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AppController implements Initializable {

    public final String ADD_WATCH = "/fxml/add-watch.fxml";
    public final String EDIT_WATCH = "/fxml/edit-watch.fxml";
    public final static String TABLE_NAME_SQL = "Watch";


    ObservableList<WatchTableModel> data = FXCollections.observableArrayList();

    @FXML
    private BorderPane BorderPane;

    @FXML
    private TableView<WatchTableModel> watchTableView;

    @FXML
    private Button addButton;

    @FXML
    private ImageView imageView;
    public Image image;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button refreshButton;

    @FXML
    private Button exitButton;

    @FXML
    private TableColumn<WatchTableModel, Long> id_Column;

    @FXML
    private TableColumn<WatchTableModel, String> name_Column;

    @FXML
    private TableColumn<WatchTableModel, String> price_Column;

    @FXML
    private TableColumn<WatchTableModel, String> currentPrice_Column;

    @FXML
    private TableColumn<WatchTableModel, TextField> information_Column;




    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeViewPicture();
        initializeTableView();
        initializeAddButton();
        initializeEditButton();
        initializeRefresh();
        initializeDeleteButton();

        HelpController.initializeExitButton(exitButton, BorderPane);
    }

    private void initializeViewPicture(){
       watchTableView.setOnMouseClicked((x)->{
           try {
           WatchTableModel selectedWatch = watchTableView.getSelectionModel().getSelectedItem();
           Connection connection = SQLiteHelper.DBConnector();
           PreparedStatement statement = connection.prepareStatement("SELECT * FROM Watch WHERE idWatch =" + selectedWatch.getIdWatch());
           ResultSet rs = statement.executeQuery();
           if(rs.next()){
               InputStream is = rs.getBinaryStream("urlImage");
               OutputStream os = new FileOutputStream(new File("photo.jpg"));
               byte[] contents = new byte[1024];
               int size = 0;
               while ((size = is.read(contents)) != -1){
                   os.write(contents,0,size);
               }
               image = new Image("file:photo.jpg", 350,350,true,true);
               imageView.setPreserveRatio(true);
               imageView.setImage(image);

           }
               rs.close();
               statement.close();

       } catch (SQLException throwables) {
               throwables.printStackTrace();
           } catch (FileNotFoundException e) {
               e.printStackTrace();
           } catch (IOException e) {
               e.printStackTrace();
           }

       });


    }


    private void initializeEditButton() {
        editButton.setOnAction((x)->{
            WatchTableModel selectedWatch = watchTableView.getSelectionModel().getSelectedItem();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(EDIT_WATCH));
            try {
                loader.load();

            } catch (IOException e ) {
                e.printStackTrace();
            }
            AddWatchController addWatchController = loader.getController();
            addWatchController.setTextField(selectedWatch.getIdWatch(),selectedWatch.getNameWatch(),selectedWatch.getCurrentPrice(),selectedWatch.getActuallyPrice(),selectedWatch.getTextArea(),selectedWatch.getUrl());
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();

        });
    }

    private void initializeRefresh() {
        refreshButton.setOnAction((x)->{
            loadData();
        });
    }


    private  void initializeTableView() {
        id_Column.setCellValueFactory(new PropertyValueFactory<>("idWatch"));
        name_Column.setCellValueFactory(new PropertyValueFactory<>("nameWatch"));
        price_Column.setCellValueFactory(new PropertyValueFactory<>("currentPrice"));
        currentPrice_Column.setCellValueFactory(new PropertyValueFactory<>("actuallyPrice"));
        information_Column.setCellValueFactory(new PropertyValueFactory<>("textArea"));
        loadData();
        watchTableView.setItems(data);


    }
   public void loadData() {
        data.clear();
        String query = "SELECT * FROM " + TABLE_NAME_SQL;
        try {
            Connection connection = SQLiteHelper.DBConnector();
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                data.add(new WatchTableModel(
                        rs.getString("idWatch"),
                        rs.getString("nameWatch"),
                        rs.getString("currentPrice"),
                        rs.getString("actuallyPrice"),
                        rs.getString("textArea"),
                        rs.getString("urlImage"),
                        rs.getString("urlText")

                ));
            }
            rs.close();
            statement.close();
        } catch (SQLException e) {
            Logger.getAnonymousLogger().log(
                    Level.SEVERE,
                    LocalDateTime.now() + ": Could not load Watch from database ");
            data.clear();
        }


    }

    private void initializeDeleteButton() {
        deleteButton.setOnAction((x)->{
            try {
                WatchTableModel selectedWatch = watchTableView.getSelectionModel().getSelectedItem();
                String query = "DELETE FROM Watch WHERE idWatch =" + selectedWatch.getIdWatch();
                Connection connection = SQLiteHelper.DBConnector();
                PreparedStatement statement = connection.prepareStatement(query);
                statement.execute();
                JOptionPane.showMessageDialog(null,"Usunięto pozycję");
                loadData();
                connection.close();
                statement.close();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        });
    }

    private void initializeAddButton() {
        addButton.setOnAction((x) -> {
            Stage addEmployeeStage = new Stage();
            addEmployeeStage.initStyle(StageStyle.UNDECORATED);
            addEmployeeStage.initModality(Modality.APPLICATION_MODAL);
            try {
                Parent addEmployeeParent = FXMLLoader.load(getClass().getResource(ADD_WATCH));
                Scene scene = new Scene(addEmployeeParent);
                addEmployeeStage.setScene(scene);
                addEmployeeStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

}