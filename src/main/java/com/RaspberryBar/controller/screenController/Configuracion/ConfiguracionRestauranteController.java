package com.RaspberryBar.controller.screenController.Configuracion;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.controller.screenController.Usuarios.UsuariosController;
import com.RaspberryBar.entities.Restaurante;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.service.ImpresoraService;
import com.RaspberryBar.service.RestauranteService;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Attribute;
import javax.print.attribute.standard.PrinterName;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ConfiguracionRestauranteController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private RestauranteService restauranteService;

    @FXML
    private JFXTextField nombreRestaurante;
    @FXML
    private JFXTextField cif;
    @FXML
    private JFXTextField direccion;
    @FXML
    private JFXTextField iva;
    @FXML
    private JFXTextField telefono;

    private Restaurante restaurante = new Restaurante();
    private boolean existe = false;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.CONFIGURACION);
    }

    @FXML
    private void guardarRestaurante(ActionEvent event) throws IOException {
        if (validar()) {
            Restaurante restaurante = new Restaurante(1, getNombreRestaurante(), getIva(), getCif(), getDireccion(), getTelefono());

            if (existe) {
                restauranteService.updateRestaurante(restaurante);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Información del Restaurante Actualizada",
                        "Información del Restaurante Actualizada Satisfactoriamente");
            } else {
                restauranteService.createRestaurante(restaurante);
                mostrarAlerta(Alert.AlertType.ERROR, "Restaurante creado",
                        "Restaurante satisfactoriamente creado");
            }

            stageManager.switchScene(FxmlView.CONFIGURACION);
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        CustomAlert alert = new CustomAlert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(mensaje);
        alert.showAndWait();
    }


    private boolean validar() {
        boolean nombreRestauranteValid = nombreRestaurante.validate();
        boolean cifValid = cif.validate();
        boolean telefonoValid = telefono.validate();
        boolean direccionValid = direccion.validate();
        boolean ivaValid = iva.validate();

        // Si alguno de los campos no es válido, retornar true
        return nombreRestauranteValid && cifValid && telefonoValid && direccionValid && ivaValid;
    }

    private void obtenerInfo(){

        if (restauranteService.readRestaurante().size() != 0) {
            restaurante = restauranteService.readRestaurante().get(0);
        }else{
            restaurante = null;
        }

        if(restaurante != null){
            existe = true;
            nombreRestaurante.setText(restaurante.getNombre());
            cif.setText(restaurante.getCif());
            direccion.setText(restaurante.getDireccion());
            telefono.setText(restaurante.getTelefono());
            iva.setText(restaurante.getTipoImpositivo());
        }else{
            existe = false;
        }
    }

    public String getNombreRestaurante() {
        return nombreRestaurante.getText();
    }

    public String getCif() {
        return cif.getText();
    }

    public String getDireccion() {
        return direccion.getText();
    }

    public String getIva() {
        return iva.getText();
    }

    public String getTelefono() {
        return telefono.getText();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Creamos los validadores
        //Valida que los campos sean obligatorios
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Campo Obligatorio");

        //Valida que el numero de telefono tenga 9 digitos y sean numeros
        RegexValidator telefonoValidator = new RegexValidator();
        telefonoValidator.setRegexPattern("^[0-9]{9}$");
        telefonoValidator.setMessage("Introduce un numero de telefono valido");

        //Valida que el tipo impositivo este entre 0 y 100
        RegexValidator ivaValidator = new RegexValidator();
        ivaValidator.setRegexPattern("^(?:100|[1-9]?[0-9])$");
        ivaValidator.setMessage("Introduce un IVA valido");

        //Seteamos los validadores a nuestros JFXTextFields
        nombreRestaurante.getValidators().add(requiredFieldValidator);
        cif.getValidators().add(requiredFieldValidator);
        telefono.getValidators().addAll(requiredFieldValidator, telefonoValidator);
        iva.getValidators().addAll(requiredFieldValidator, ivaValidator);
        direccion.getValidators().add(requiredFieldValidator);

        obtenerInfo();

    }
}
