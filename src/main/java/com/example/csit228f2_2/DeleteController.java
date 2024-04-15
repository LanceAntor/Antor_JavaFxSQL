package com.example.csit228f2_2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeleteController {

    @FXML
    private TextField tfUsername;

    @FXML
    private Label messageLabel;
    @FXML
    private void handleDelete() {
        String usernameToDelete = tfUsername.getText();

        // Check if username is empty
        if (usernameToDelete.isEmpty()) {
            messageLabel.setText("Please enter username.");
            return;
        }

        try (Connection connection = MySQLConnection.getConnection()) {
            // Prepare the select query to check if the username exists
            String selectQuery = "SELECT * FROM users WHERE username = ?";
            try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
                selectStatement.setString(1, usernameToDelete);
                ResultSet resultSet = selectStatement.executeQuery();

                // If username exists, proceed with deletion
                if (resultSet.next()) {
                    // Prepare the delete query
                    String deleteQuery = "DELETE FROM users WHERE username = ?";
                    try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
                        deleteStatement.setString(1, usernameToDelete);

                        // Execute the delete query
                        int rowsDeleted = deleteStatement.executeUpdate();
                        if (rowsDeleted > 0) {
                            messageLabel.setText("User with username " + usernameToDelete + " deleted successfully!");
                            // Clear the username text field after successful deletion
                            tfUsername.clear();
                        } else {
                            messageLabel.setText("Failed to delete username");
                        }
                    }
                } else {
                    messageLabel.setText("No username found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void onClickGoBack(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
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
