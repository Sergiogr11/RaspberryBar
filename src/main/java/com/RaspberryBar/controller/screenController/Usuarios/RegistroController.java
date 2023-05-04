package com.RaspberryBar.controller.screenController.Usuarios;
import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.UsuarioRepository;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
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
public class RegistroController implements Initializable {

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

    @FXML
    private void registrar(ActionEvent event) throws IOException {
        //Primero validamos los campos
        if(validar()) {
            //Creo el usuario, si el maximo id es null se pone a 0
            int id;
            try {
                id = usuarioRepository.findMaxId() + 1;
            } catch (NullPointerException e) {
                id = 0;
            }
            //Encripto contraseña
            String encrypted_password = UsuarioService.encrypt(getPassword());
            Usuario usuario = new Usuario(id, getUsername(), getEmail(), encrypted_password, getRol(), getTelefono());

            //Si usuario no existe lo creo
            if(!usuarioExiste(usuario)) {
                usuarioService.createUsuario(usuario);
                //Muestro alert de usuario creado
                CustomAlert alertCrearUsuario = new CustomAlert(Alert.AlertType.INFORMATION);
                alertCrearUsuario.setTitle("Usuario Creado");
                alertCrearUsuario.setHeaderText("Usuario creado satisfactoriamente");
                alertCrearUsuario.showAndWait();
                //Cambio de pantalla
                stageManager.switchScene(FxmlView.USUARIOS);
            }else{
                //Muestro alert de usuario no se ha podido crear
                CustomAlert alertCrearUsuario = new CustomAlert(Alert.AlertType.ERROR);
                alertCrearUsuario.setTitle("No se puede crear usuario");
                alertCrearUsuario.setHeaderText("No se puede crear usuario porque ya existe");
                alertCrearUsuario.showAndWait();
            }


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

        //Valida que la contraseña tenga 6 digitos minimo y un numero
        RegexValidator passwordValidator = new RegexValidator();
        passwordValidator.setRegexPattern("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$");
        passwordValidator.setMessage("La contraseña debe tener al menos 6 digitos y un numero");


        //Seteamos los validadores a nuestros JFXTextFields
        email.getValidators().add(requiredFieldValidator);
        password.getValidators().add(requiredFieldValidator);
        username.getValidators().add(requiredFieldValidator);
        rol.getValidators().add(requiredFieldValidator);
        telefono.getValidators().add(requiredFieldValidator);

        email.getValidators().add(emailValidator);
        password.getValidators().add(passwordValidator);
        telefono.getValidators().add(telefonoValidator);
    }

}
