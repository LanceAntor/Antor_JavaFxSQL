package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;

import javax.swing.text.LabelView;
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
    private Label messageLabel, emptyMessage;

    @FXML
    private CheckBox cbBtn;

    @FXML
    private TextField ShowPassword;

    @FXML
    protected void initialize() {
        tfUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                emptyMessage.setVisible(false);
                messageLabel.setVisible(false);
            }
        });

        pfPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                emptyMessage.setVisible(false);
                messageLabel.setVisible(false);
            }
        });
        tfUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyMessage.setVisible(false);
                messageLabel.setVisible(false);
            }
        });

        pfPassword.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                emptyMessage.setVisible(false);
                messageLabel.setVisible(false);
            }
        });
    }

    @FXML
    protected void handleSignIn(ActionEvent event) {
        String username = tfUsername.getText();
        String password = pfPassword.getText();

        if (username.equals("admin") && password.equals("123456")) {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("dashboard.fxml"));
                Parent root = loader.load();
                Scene scene = new Scene(root);
                Stage stage = (Stage) tfUsername.getScene().getWindow();
                stage.setTitle("Admin Dashboard");
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }

        if (username.isEmpty() || password.isEmpty()) {
            emptyMessage.setOpacity(1);
            emptyMessage.setVisible(true);
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
                        stage.setTitle("User Area");
                        stage.show();
                        return;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            messageLabel.setOpacity(1);
            messageLabel.setVisible(true);
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

    @FXML
    protected void handleTextFieldFocus(ActionEvent event) {
        emptyMessage.setVisible(false);
        messageLabel.setVisible(false);
    }

    public void handleRegister(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

            stage.setScene(scene);
            stage.setTitle("Registration");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
