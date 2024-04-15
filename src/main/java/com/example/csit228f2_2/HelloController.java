package com.example.csit228f2_2;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class HelloController {

    public ToggleButton tbNight;
    public Button editProfile;


    @FXML
    private Label welcomeText;

    @FXML
    private void onHelloButtonClick() {
//        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    private void onNightModeClick() {
        if (tbNight.isSelected()) {
            // night mode
            tbNight.getScene().getStylesheets().add(
                    getClass().getResource("styles.css").toExternalForm());
        } else {
            tbNight.getScene().getStylesheets().clear();
        }
    }


    public void onClickEdit(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("edit.fxml"));
            Scene scene = new Scene(root);
            Stage stage =  (Stage) editProfile.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onClickDelete(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("delete.fxml"));
            Scene scene = new Scene(root);
            Stage stage =  (Stage) editProfile.getScene().getWindow();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void onClickLogOut(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signin.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}