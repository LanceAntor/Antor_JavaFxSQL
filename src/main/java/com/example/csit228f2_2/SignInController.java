package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SignInController {

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Text actionTarget;

    @FXML
    protected void handleSignIn(ActionEvent event) {
        String username = tfUsername.getText();
        String password = pfPassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            actionTarget.setText("Username or password cannot be empty");
            actionTarget.setOpacity(1);
            return;
        }
        try (Connection c = MySQLConnection.getConnection();
             Statement statement = c.createStatement()) {
            String selectQuery = "SELECT * FROM users";
            ResultSet result = statement.executeQuery(selectQuery);
            boolean found = false;
            while (result.next()) {
                String dbUsername = result.getString("username");
                String dbPassword = result.getString("password");
                if (username.equals(dbUsername) && password.equals(dbPassword)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
                    try {
                        Scene scene = new Scene(loader.load());
                        Stage stage = (Stage) tfUsername.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            actionTarget.setText("Invalid username/password");
            actionTarget.setOpacity(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void handleRegister(ActionEvent actionEvent) {
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
}
