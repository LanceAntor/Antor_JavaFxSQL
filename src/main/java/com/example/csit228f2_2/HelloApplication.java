package com.example.csit228f2_2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane pnMain = new AnchorPane();
        GridPane grid = new GridPane();
        pnMain.getChildren().add(grid);
        grid.setAlignment(Pos.CENTER);
        Text sceneTitle = new Text("Welcome to CSIT228");
        sceneTitle.setFill(Paint.valueOf("#325622"));
        sceneTitle.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 69));
        grid.add(sceneTitle, 0, 0, 2, 1);

        Button btnSignIn = new Button("Sign In");
        btnSignIn.setFont(Font.font(45));
        HBox hbSignIn = new HBox();
        hbSignIn.getChildren().add(btnSignIn);
        hbSignIn.setAlignment(Pos.CENTER);
        grid.add(hbSignIn, 0, 1);

        // Existing code
        Text dontHaveAccountText = new Text("Don't have an account?");
        dontHaveAccountText.setFill(Paint.valueOf("#325622"));
        dontHaveAccountText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        grid.add(dontHaveAccountText, 0, 2);

        // Corrected code: Set the alignment of the HBox containing the Register button
        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font(45));
        HBox hbRegister = new HBox();
        hbRegister.getChildren().add(btnRegister);
        hbRegister.setAlignment(Pos.CENTER); // Center the HBox
        grid.add(hbRegister, 0, 3);


        btnSignIn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
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
        });

        btnRegister.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        Scene scene = new Scene(pnMain, 700, 560);
        stage.setScene(scene);
        stage.show();
    }
}
