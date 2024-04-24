package com.example.csit228f2_2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class RegisterController {

    @FXML
    private TextField tfUsername, tfFirstname, tfLastname, tfGender, tfEmail;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Label emptyLabel, usernameExists, emailExists, successMessage, failedMessage;

    @FXML
    private CheckBox cbBtn;

    @FXML
    private TextField ShowPassword;

    @FXML
    private void initialize() {
        addPromptClearListeners(tfUsername, emptyLabel);
        addPromptClearListeners(tfFirstname, emptyLabel);
        addPromptClearListeners(tfLastname, emptyLabel);
        addPromptClearListeners(tfGender, emptyLabel);
        addPromptClearListeners(tfEmail, emptyLabel);
        addPromptClearListeners(pfPassword, emptyLabel);

        tfUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyLabel.setVisible(false);
                usernameExists.setVisible(false);
            }
        });
        tfFirstname.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyLabel.setVisible(false);
            }
        });
        tfLastname.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyLabel.setVisible(false);
            }
        });
        tfGender.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyLabel.setVisible(false);
            }
        });
        tfEmail.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyLabel.setVisible(false);
                emailExists.setVisible(false);
            }
        });
        pfPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyLabel.setVisible(false);
            }
        });
    }

    private void addPromptClearListeners(TextField textField, Label promptLabel) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                promptLabel.setVisible(false);
            }
        });
    }

    private void addPromptClearListeners(PasswordField passwordField, Label promptLabel) {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                promptLabel.setVisible(false);
            }
        });
    }


    @FXML
    private void handleRegister() {
        String username = tfUsername.getText().trim();
        String password = pfPassword.getText().trim();
        String firstname = tfFirstname.getText().trim();
        String lastname = tfLastname.getText().trim();
        String gender = tfGender.getText().trim();
        String email = tfEmail.getText().trim();

        if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || gender.isEmpty() || email.isEmpty()) {
            emptyLabel.setOpacity(1);
            emptyLabel.setVisible(true);
            return;
        }

        try (Connection connection = MySQLConnection.getConnection()) {
            String checkUsernameQuery = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement checkUsernameStatement = connection.prepareStatement(checkUsernameQuery)) {
                checkUsernameStatement.setString(1, username);
                ResultSet resultSet = checkUsernameStatement.executeQuery();
                if (resultSet.next()) {
                    usernameExists.setOpacity(1);
                    usernameExists.setVisible(true);
                    return;
                }
            }

            String checkEmailQuery = "SELECT * FROM userprofile WHERE email = ?";
            try (PreparedStatement checkEmailStatement = connection.prepareStatement(checkEmailQuery)) {
                checkEmailStatement.setString(1, email);
                ResultSet resultSet = checkEmailStatement.executeQuery();
                if (resultSet.next()) {
                    emailExists.setOpacity(1);
                    emailExists.setVisible(true);
                    return;
                }
            }

            String insertUserQuery = "INSERT INTO users (username, password) VALUES (?, ?)";
            try (PreparedStatement insertUserStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS)) {
                insertUserStatement.setString(1, username);
                insertUserStatement.setString(2, password);
                int rowsInserted = insertUserStatement.executeUpdate();
                if (rowsInserted > 0) {
                    ResultSet generatedKeys = insertUserStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int userId = generatedKeys.getInt(1);
                        String insertProfileQuery = "INSERT INTO userprofile (firstname, lastname, gender, email, user_id) VALUES (?, ?, ?, ?, ?)";
                        try (PreparedStatement insertProfileStatement = connection.prepareStatement(insertProfileQuery)) {
                            insertProfileStatement.setString(1, firstname);
                            insertProfileStatement.setString(2, lastname);
                            insertProfileStatement.setString(3, gender);
                            insertProfileStatement.setString(4, email);
                            insertProfileStatement.setInt(5, userId);
                            int profileRowsInserted = insertProfileStatement.executeUpdate();
                            if (profileRowsInserted > 0) {
                                successMessage.setOpacity(1);
                                successMessage.setVisible(true);
                                tfUsername.clear();
                                pfPassword.clear();
                                tfFirstname.clear();
                                tfLastname.clear();
                                tfGender.clear();
                                tfEmail.clear();
                            } else {
                                failedMessage.setOpacity(1);
                                failedMessage.setVisible(true);
                            }
                        }
                    }
                } else {
                    failedMessage.setOpacity(1);
                    failedMessage.setVisible(true);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void showPassword(){
        if(cbBtn.isSelected()){
            ShowPassword.setText(pfPassword.getText());
            ShowPassword.setVisible(true);
            pfPassword.setVisible(false);
        } else {
            pfPassword.setText(ShowPassword.getText());
            ShowPassword.setVisible(false);
            pfPassword.setVisible(true);
        }
    }

    public void handleSignIn(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signin.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Sign In");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
