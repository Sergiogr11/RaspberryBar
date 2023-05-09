package com.RaspberryBar.controller.screenController.Articulos;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.controller.screenController.Usuarios.UsuariosController;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.CategoriaRepository;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
public class EditarCategoriaController implements Initializable {

    @FXML
    private JFXButton btnCrearCategoria;
    @FXML
    private JFXButton btnVolver;

    @FXML
    private JFXTextField nombreCategoria;
    @FXML
    private JFXTextArea descripcionCategoria;


    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ListaCategoriasController listaCategoriasController;
    private Categoria categoriaEditar;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void editarCategoria(ActionEvent event) throws IOException {
        //Primero validamos los campos
        if (validar()) {
            //Despues guardamos la categoria nuevo
            int id = categoriaEditar.getCategoriaId();
            Categoria categoria = new Categoria(id, getNombreCategoria(), getDescripcionCategoria());

            if (!categoriaExiste(getNombreCategoria())) {
                categoriaService.createCategoria(categoria);
                //Muestro alert de categoria creada
                CustomAlert alertEditarCategoria = new CustomAlert(Alert.AlertType.INFORMATION);
                alertEditarCategoria.setTitle("Categoria actualizada");
                alertEditarCategoria.setHeaderText("Categoria satisfactoriamente actualizada");
                alertEditarCategoria.showAndWait();
                //Cambio de pantalla
                stageManager.switchScene(FxmlView.LISTACATEGORIAS);

            } else {
                //Muestro Alert de categoria no se puede actualizar
                CustomAlert alertEditarCategoria = new CustomAlert(Alert.AlertType.ERROR);
                alertEditarCategoria.setTitle("No se puede actualizar categoria");
                alertEditarCategoria.setHeaderText("Ya existe una categoria con ese nombre");
                alertEditarCategoria.showAndWait();
            }
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
            stageManager.switchScene(FxmlView.LISTACATEGORIAS);
    }

    private boolean categoriaExiste(String categoriaNombre){
        int idCategoria = categoriaService.findIdByNombreCategoria(categoriaNombre);
        Categoria categoriaExistente = categoriaService.findCategoria(idCategoria);
        if(categoriaExistente != null && categoriaExistente.getCategoriaId() != categoriaEditar.getCategoriaId()){
            return true;
        }
        return false;
    }

    @FXML
    private boolean validar() {
        boolean nombreCategoriaValid = nombreCategoria.validate();
        boolean descripcionCategoriaValid = descripcionCategoria.validate();

        // Verificar si el nombre del artículo a editar ya existe en la base de datos
        if (categoriaExiste(nombreCategoria.getText())) {
            CustomAlert alertEditarCategoria = new CustomAlert(Alert.AlertType.ERROR);
            alertEditarCategoria.setTitle("No se puede actualizar categoría");
            alertEditarCategoria.setHeaderText("Ya existe una categoría con ese nombre");
            alertEditarCategoria.showAndWait();
            return false;
        }

        // Si alguno de los campos no es válido, retornar true
        if (!nombreCategoriaValid || !descripcionCategoriaValid) {
            return false;
        } else {
            return true;
        }
    }

    private void obtenerInfo(){
        categoriaEditar = listaCategoriasController.categoriaEditar;
        nombreCategoria.setText(categoriaEditar.getNombreCategoria());
        descripcionCategoria.setText(categoriaEditar.getDescripcionCategoria());
    }

    public String getNombreCategoria(){
        return nombreCategoria.getText();
    }

    public String getDescripcionCategoria(){
        return descripcionCategoria.getText();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //Creamos los validadores
        //Valida que los campos sean obligatorios
        RequiredFieldValidator requiredFieldValidator = new RequiredFieldValidator();
        requiredFieldValidator.setMessage("Campo Obligatorio");

        //Seteamos los validadores a nuestros JFXTextFields
        nombreCategoria.getValidators().add(requiredFieldValidator);
        descripcionCategoria.getValidators().add(requiredFieldValidator);

        obtenerInfo();
    }

}
