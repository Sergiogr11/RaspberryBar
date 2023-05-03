package com.RaspberryBar.controller.screenController.Articulos;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.CategoriaRepository;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class EditarArticuloController implements Initializable {

    @FXML
    private JFXButton btnEditarArticulo;
    @FXML
    private JFXButton btnVolver;

    @FXML
    private JFXTextField nombreArticulo;
    @FXML
    private JFXTextArea descripcionArticulo;
    @FXML
    private JFXTextField precioArticulo;
    @FXML
    private JFXComboBox comboBoxCategoria;


    @Autowired
    private ArticuloService articuloService;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private ListaArticulosController listaArticulosController;
    private Articulo articuloEditar;
    private List<String> nombresCategorias = new ArrayList<>();

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void editarArticulo(ActionEvent event) throws IOException {
        //Primero validamos los campos
        if(validar()) {
            //Despues guardamos la categoria nuevo
            int id = articuloEditar.getArticuloId();
            Articulo articulo = new Articulo(id, getNombreArticulo(), getDescripcionArticulo(), getPrecioArticulo(), getCategoriaArticulo());

            articuloService.updateArticulo(articulo);

            //TODO-mostrar mensaje de confirmacon

            stageManager.switchScene(FxmlView.LISTAARTICULOS);
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
            stageManager.switchScene(FxmlView.LISTAARTICULOS);
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

    private void obtenerInfo(){
        articuloEditar = listaArticulosController.articuloEditar;
        nombreArticulo.setText(articuloEditar.getNombreArticulo());
        descripcionArticulo.setText(articuloEditar.getDescripcionArticulo());
        precioArticulo.setText(String.valueOf(articuloEditar.getPrecio()));

        comboBoxCategoria.setValue(categoriaService.findCategoria(articuloEditar.getCategoriaId()).getNombreCategoria());

    }

    public String getNombreArticulo(){
        return nombreArticulo.getText();
    }

    public String getDescripcionArticulo(){
        return descripcionArticulo.getText();
    }

    public Float getPrecioArticulo() {
        return Float.parseFloat(precioArticulo.getText());
    }

    public Integer getCategoriaArticulo(){
        return categoriaService.findIdByNombreCategoria(comboBoxCategoria.getValue().toString());
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

        obtenerInfo();
    }

}
