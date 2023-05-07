package com.RaspberryBar.controller.screenController.Mesas;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.MesaRepository;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.MesaService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class CrearMesaController implements Initializable {

    @FXML
    private JFXButton btnCrearMesa;
    @FXML
    private JFXButton btnVolver;

    @FXML
    private JFXTextField nombreMesa;
    @FXML
    private JFXTextField capacidadMesa;
    @FXML
    private JFXComboBox<String> comboBoxPosicion;


    @Autowired
    private MesaService mesaService;
    @Autowired
    private MesaRepository mesaRepository;

    private List<String> nombresMesas = new ArrayList<>();

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void crearMesa(ActionEvent event) throws IOException {
        //Primero validamos los campos
        if (validar()) {
            //Creo la esa, si el maximo id es null se pone a 1
            int id;
            try {
                id = mesaRepository.findMaxId() + 1;
            } catch (NullPointerException e) {
                id = 1;
            }

            Mesa mesa = new Mesa(id, getPosicion(), getCapacidadMesa(), "Libre", getNombreMesa());
            //Si la mesa no existe la creo
            if(!mesaExiste(mesa)) {
                mesaService.createMesa(mesa);
                //Muestro alert de mesa creada
                CustomAlert alertCrearMesa = new CustomAlert(Alert.AlertType.INFORMATION);
                alertCrearMesa.setTitle("Mesa Creado");
                alertCrearMesa.setHeaderText("Mesa creada satisfactoriamente");
                alertCrearMesa.showAndWait();
                //Cambio de pantalla
                stageManager.switchScene(FxmlView.LISTAMESAS);
            }else{
                //Muestro alert de articulo no se ha podido crear
                CustomAlert alertCrearMesa = new CustomAlert(Alert.AlertType.ERROR);
                alertCrearMesa.setTitle("No se puede crear mesa");
                alertCrearMesa.setHeaderText("No se puede crear mesa porque ya existe");
                alertCrearMesa.showAndWait();

            }
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LISTAMESAS);
    }

    private boolean mesaExiste(Mesa mesa){
        if(mesaService.findIdByNombreMesa(mesa.getNombreMesa()) != null) {
            return true;
        }else{
            return false;
        }
    }

    @FXML
    private boolean validar() {
        boolean nombreMesaValid = nombreMesa.validate();
        boolean posicionValid = comboBoxPosicion.validate();
        boolean capacidadValid = capacidadMesa.validate();

        // Si alguno de los campos no es válido, retornar true
        if (!nombreMesaValid || !posicionValid || !capacidadValid ) {
            return false;
        } else {
            return true;
        }
    }

    public String getNombreMesa() {
        return nombreMesa.getText();
    }

    public int getCapacidadMesa() {
        return Integer.parseInt(capacidadMesa.getText());
    }

    public String getPosicion() {
        return comboBoxPosicion.getValue();
    }

    private void actualizarComboBox() {
        //Seteo el nombre de las posiciones
        List<String> nombresPosiciones = Arrays.asList("Restaurante", "Bar", "Terraza");
        //Añado el nombre de las posiciones al ComboBox
        comboBoxPosicion.setItems(FXCollections.observableList(nombresPosiciones));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        actualizarComboBox();

        //Creamos los validadores
        //Valida que los campos sean obligatorios
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Campo Obligatorio");

        //Valido que el precio tenga formato de precio y sean numeros
        RegexValidator capacidadValidator = new RegexValidator();
        capacidadValidator.setRegexPattern("^\\d+$");
        capacidadValidator.setMessage("Introduce una capacidad válida (numero)");

        //Seteamos los validadores a nuestros JFXTextFields
        nombreMesa.getValidators().add(requiredFieldValidator);
        capacidadMesa.getValidators().add(requiredFieldValidator);
        comboBoxPosicion.getValidators().add(requiredFieldValidator);

        capacidadMesa.getValidators().add(capacidadValidator);

    }
}
