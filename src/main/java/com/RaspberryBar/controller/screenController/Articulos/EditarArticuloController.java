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

            if(!articuloExiste(getNombreArticulo())) {
                articuloService.updateArticulo(articulo);
                CustomAlert alertEditarArticulo = new CustomAlert(Alert.AlertType.INFORMATION);
                alertEditarArticulo.setTitle("Artículo actualizado");
                alertEditarArticulo.setHeaderText("Artículo satisfactoriamente actualizado");
                alertEditarArticulo.showAndWait();
                stageManager.switchScene(FxmlView.LISTAARTICULOS);
            }else{
                CustomAlert alertEditarArticulo = new CustomAlert(Alert.AlertType.ERROR);
                alertEditarArticulo.setTitle("No se puede actualizar articulo");
                alertEditarArticulo.setHeaderText("Ya existe un articulo con ese nombre");
                alertEditarArticulo.showAndWait();
            }
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
            stageManager.switchScene(FxmlView.LISTAARTICULOS);
    }

    @FXML
    private boolean validar() {
        boolean nombreArticuloValid = nombreArticulo.validate();
        boolean descripcionArticuloValid = descripcionArticulo.validate();
        boolean categoriaValid = comboBoxCategoria.validate();
        boolean precioValid = precioArticulo.validate();

        // Verificar si el nombre del artículo a editar ya existe en la base de datos
        if (articuloExiste(nombreArticulo.getText())) {
            CustomAlert alertEditarArticulo = new CustomAlert(Alert.AlertType.ERROR);
            alertEditarArticulo.setTitle("No se puede actualizar articulo");
            alertEditarArticulo.setHeaderText("Ya existe un articulo con ese nombre");
            alertEditarArticulo.showAndWait();
            return false;
        }

        // Si alguno de los campos no es válido, retornar true
        if (!nombreArticuloValid || !descripcionArticuloValid || !precioValid || !categoriaValid) {
            return false;
        } else {
            return true;
        }
    }

    private boolean articuloExiste(String nombreArticulo){
        int idArticulo = articuloService.findIdByNombreArticulo(nombreArticulo);
        Articulo articuloExistente = articuloService.findArticulo(idArticulo);
        if (articuloExistente != null && articuloExistente.getArticuloId() != articuloEditar.getArticuloId()) {
            return true;
        }
        return false;
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
