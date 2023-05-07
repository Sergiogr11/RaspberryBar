package com.RaspberryBar.controller.screenController.Articulos;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.CategoriaRepository;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.*;
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
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class CrearArticuloController implements Initializable {

    @FXML
    private JFXButton btnCrearArticulo;
    @FXML
    private JFXButton btnVolver;

    @FXML
    private JFXTextField nombreArticulo;
    @FXML
    private JFXTextArea descripcionArticulo;
    @FXML
    private JFXTextField precioArticulo;
    @FXML
    private JFXComboBox<String> comboBoxCategoria;


    @Autowired
    private ArticuloService articuloService;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private CategoriaService categoriaService;

    private List<String> nombresCategorias = new ArrayList<>();

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void crearArticulo(ActionEvent event) throws IOException {
        //Primero validamos los campos
        if (validar()) {
            //Creo el articulo, si el maximo id es null se pone a 1
            int id;
            try {
                id = articuloRepository.findMaxId() + 1;
            } catch (NullPointerException e) {
                id = 1;
            }

            Articulo articulo = new Articulo(id, getNombreArticulo(), getDescripcionArticulo(), getPrecioArticulo(), getCategoria());
            //Si articulo no existe lo creo
            if(!articuloExiste(articulo)) {
                articuloService.createArticulo(articulo);
                //Muestro alert de articulo creado
                CustomAlert alertCrearArticulo = new CustomAlert(Alert.AlertType.INFORMATION);
                alertCrearArticulo.setTitle("Articulo Creado");
                alertCrearArticulo.setHeaderText("Articulo creado satisfactoriamente");
                alertCrearArticulo.showAndWait();
                //Cambio de pantalla
                stageManager.switchScene(FxmlView.LISTAARTICULOS);
            }else{
                //Muestro alert de articulo no se ha podido crear
                CustomAlert alertCrearArticulo = new CustomAlert(Alert.AlertType.ERROR);
                alertCrearArticulo.setTitle("No se puede crear articulo");
                alertCrearArticulo.setHeaderText("No se puede crear articulo porque ya existe");
                alertCrearArticulo.showAndWait();

            }
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LISTAARTICULOS);
    }

    private boolean articuloExiste(Articulo articulo){
        if(articuloService.findIdByNombreArticulo(articulo.getNombreArticulo()) != null) {
            return true;
        }else{
            return false;
        }
    }

    @FXML
    private boolean validar() {
        boolean nombreCategoriaValid = nombreArticulo.validate();
        boolean descripcionCategoriaValid = descripcionArticulo.validate();
        boolean categoriaValid = comboBoxCategoria.validate();
        boolean precioValid = precioArticulo.validate();

        // Si alguno de los campos no es válido, retornar true
        if (!nombreCategoriaValid || !descripcionCategoriaValid || !precioValid || !categoriaValid) {
            return false;
        } else {
            return true;
        }
    }

    public String getNombreArticulo() {
        return nombreArticulo.getText();
    }

    public String getDescripcionArticulo() {
        return descripcionArticulo.getText();
    }

    public Float getPrecioArticulo() {
        return Float.parseFloat(precioArticulo.getText());
    }

    public Integer getCategoria() {
        return categoriaService.findIdByNombreCategoria(comboBoxCategoria.getValue());
    }

    private void actualizarComboBox() {

        nombresCategorias = categoriaService.readCategoriasName();
        comboBoxCategoria.setItems(FXCollections.observableList(nombresCategorias));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        actualizarComboBox();

        //Creamos los validadores
        //Valida que los campos sean obligatorios
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Campo Obligatorio");

        //Valido que el precio tenga formato de precio y sean numeros
        RegexValidator precioValidator = new RegexValidator();
        precioValidator.setRegexPattern("^\\d{1,5}(?:\\.\\d{0,2})?$");
        precioValidator.setMessage("Introduce un precio válido");

        //Seteamos los validadores a nuestros JFXTextFields
        nombreArticulo.getValidators().add(requiredFieldValidator);
        descripcionArticulo.getValidators().add(requiredFieldValidator);
        precioArticulo.getValidators().add(requiredFieldValidator);
        comboBoxCategoria.getValidators().add(requiredFieldValidator);

        precioArticulo.getValidators().add(precioValidator);

    }
}
