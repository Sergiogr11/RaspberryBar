package com.RaspberryBar.controller.screenController.Usuarios;
import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.UsuarioRepository;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class EditarUsuarioController implements Initializable {

    @FXML
    private JFXButton btnRegistro;
    @FXML
    private JFXButton btnVolver;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField telefono;
    @FXML
    private JFXComboBox rol;


    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private UsuariosController usuariosController;
    private Usuario usuarioEditar;

    @FXML
    private void registrar(ActionEvent event) throws IOException {
        //Primero validamos los campos
        if(validar()) {
            int id = usuarioEditar.getUserId();
            String encrypted_password = UsuarioService.encrypt(getPassword());
            Usuario usuario = new Usuario(id, getUsername(), getEmail(), encrypted_password, getRol(), getTelefono());

            usuarioService.updateUsuario(usuario);
            //Muestro alert de usuario creado
            CustomAlert alertEditarUsuario = new CustomAlert(Alert.AlertType.INFORMATION);
            alertEditarUsuario.setTitle("Usuario Actualizado");
            alertEditarUsuario.setHeaderText("Usuario actualizado satisfactoriamente");
            alertEditarUsuario.showAndWait();
            //Cambio de pantalla
            stageManager.switchScene(FxmlView.USUARIOS);

        }else{
             //Muestro alert de usuario no se ha podido crear
             CustomAlert alertEditarUsuario = new CustomAlert(Alert.AlertType.ERROR);
             alertEditarUsuario.setTitle("No se puede actualizar usuario");
             alertEditarUsuario.setHeaderText("No se ha podido actualizar el usuario");
             alertEditarUsuario.showAndWait();
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
            stageManager.switchScene(FxmlView.USUARIOS);
    }

    @FXML
    private boolean validar() {
        boolean passwordValid = password.validate();
        boolean usernameValid = username.validate();
        boolean telefonoValid = telefono.validate();
        boolean emailValid = email.validate();
        boolean rolValid = rol.validate();

        // Si alguno de los campos no es válido, retornar true
        if (!usernameValid || !passwordValid || !telefonoValid || !emailValid || !rolValid) {
            return false;
        } else {
            return true;
        }
    }

    private boolean usuarioExiste(Usuario usuario){
        if(usuarioService.findUsuario(usuario.getUsername()) != null) {
            return true;
        }else{
            return false;
        }
    }

    private void obtenerInfo(){
        usuarioEditar = usuariosController.usuarioEditar;
        password.setText(usuarioEditar.getPassword());
        email.setText(usuarioEditar.getCorreo());
        telefono.setText(usuarioEditar.getTelefono());
        rol.setValue(usuarioEditar.getRol());
        username.setText(usuarioEditar.getUsername());
        username.setEditable(false);
    }

    public String getPassword() {
        return password.getText();
    }

    public String getUsername() {
        return username.getText();
    }

    public String getTelefono() { return telefono.getText(); }

    public String getEmail() { return email.getText(); }

    public String getRol() {return rol.getValue().toString();}


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        rol.getItems().add("USER");
        rol.getItems().add("ADMIN");
        //Creamos los validadores
        //Valida que los campos sean obligatorios
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Campo Obligatorio");

        //Valida que el numero de telefono tenga 9 digitos y sean numeros
        RegexValidator telefonoValidator = new RegexValidator();
        telefonoValidator.setRegexPattern("^[0-9]{9}$");
        telefonoValidator.setMessage("Introduce un numero de telefono valido");

        //Valida que el correo electronico tenga el formato correcto
        RegexValidator emailValidator = new RegexValidator();
        emailValidator.setRegexPattern("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        emailValidator.setMessage("Introduce un correo electrónico válido");


        //Seteamos los validadores a nuestros JFXTextFields
        email.getValidators().add(requiredFieldValidator);
        password.getValidators().add(requiredFieldValidator);
        username.getValidators().add(requiredFieldValidator);
        rol.getValidators().add(requiredFieldValidator);
        telefono.getValidators().add(requiredFieldValidator);

        email.getValidators().add(emailValidator);
        telefono.getValidators().add(telefonoValidator);

        obtenerInfo();
    }

}
