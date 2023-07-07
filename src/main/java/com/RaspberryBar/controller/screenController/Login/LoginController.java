package com.RaspberryBar.controller.screenController.Login;
import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class LoginController implements Initializable {

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    private Usuario usuario;

    @Autowired
    private UsuarioService usuarioService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void login(ActionEvent event) throws IOException {
        usuario = usuarioService.findUsuario(getUsername());
        if(usuarioService.authenticate(getUsername(), getPassword())){
            if(usuario.getRol().equals("ADMIN")){
                stageManager.switchScene(FxmlView.HOME);
            } else if (usuario.getRol().equals("USER")) {
                CustomAlert alertCrearArticulo = new CustomAlert(Alert.AlertType.ERROR);
                alertCrearArticulo.setTitle("El usuario no es administrador");
                alertCrearArticulo.setHeaderText("No se puede iniciar sesión porque el usuario no es administrador");
                alertCrearArticulo.showAndWait();
            }
        }else{
            CustomAlert alertCrearArticulo = new CustomAlert(Alert.AlertType.ERROR);
            alertCrearArticulo.setTitle("Fallo al iniciar sesión");
            alertCrearArticulo.setHeaderText("El usuario o la contraseña son incorrectos");
            alertCrearArticulo.showAndWait();
        }
    }

    public String getPassword() {
        return password.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}