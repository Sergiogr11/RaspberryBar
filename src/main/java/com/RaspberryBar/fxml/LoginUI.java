package com.RaspberryBar.fxml;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class LoginUI extends Application {


    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Login Form");

        // Create the GridPane layout
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(5, 50, 25, 5));

        // Add the login label
        Label loginLabel = new Label("Bienvenido");
        loginLabel.setStyle("-fx-font-size: 4em; -fx-font-weight: bold; -fx-text-fill: #1a237e;");
        grid.add(loginLabel, 0, 0, 2, 1);

        // Add the username label and text field
        Label usernameLabel = new Label("Username:");
        usernameLabel.setStyle("-fx-font-size: 1.5em; -fx-text-fill: #1a237e;");
        grid.add(usernameLabel, 0, 1);
        TextField usernameField = new TextField();
        grid.add(usernameField, 1, 1);

        // Add the password label and text field
        Label passwordLabel = new Label("Password:");
        passwordLabel.setStyle("-fx-font-size: 1.5em; -fx-text-fill: #1a237e;");
        grid.add(passwordLabel, 0, 2);
        PasswordField passwordField = new PasswordField();
        grid.add(passwordField, 1, 2);

        // Add the login button
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-font-size: 1.5em; -fx-background-color: #1a237e; -fx-text-fill: white;");
        grid.add(loginButton, 1, 3);

        // Add the action for the login button
        loginButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            //Verify Login
            //Shows message login successful
        });

        // Create the scene and set it on the stage
        Scene scene = new Scene(grid, 1880, 950);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
