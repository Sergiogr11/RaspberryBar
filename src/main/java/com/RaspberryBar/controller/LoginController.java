package com.RaspberryBar.controller;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void loginButtonClicked() {
        // Aquí va la lógica de autenticación del usuario
        String username = usernameField.getText();
        String password = passwordField.getText();
        // Verifica que los datos sean válidos y redirecciona a la siguiente pantalla
    }
}
