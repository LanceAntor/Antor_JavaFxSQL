package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleRegister() {
        String username = tfUsername.getText().trim();
        String password = pfPassword.getText().trim();

        if(username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please Enter Username & Password.");
            return;
        }

        try (Connection connection = MySQLConnection.getConnection()) {
            String checkQuery = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setString(1, username);
                ResultSet resultSet = checkStatement.executeQuery();
                if (resultSet.next()) {
                    messageLabel.setText("Username already exists.");
                    return;
                }
            }

            String insertQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                insertStatement.setString(1, username);
                insertStatement.setString(2, password);
                int rowsInserted = insertStatement.executeUpdate();
                if (rowsInserted > 0) {
                    messageLabel.setText("User registered successfully!");
                    tfUsername.clear();
                    pfPassword.clear();
                } else {
                    messageLabel.setText("Failed to register user.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void handleSignIn(ActionEvent actionEvent) {
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

