package com.RaspberryBar.controller.screenController.Articulos;
import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.CategoriaRepository;
import com.RaspberryBar.repository.UsuarioRepository;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.*;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class CrearCategoriaController implements Initializable {

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

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void crearCategoria(ActionEvent event) throws IOException {
        //Primero validamos los campos
        if(validar()) {
            //Despues guardamos la categoria nuevo
            int id;
            try {
                id = categoriaRepository.findMaxId();
            }catch (NullPointerException e){
                id = 0;
            }
            Categoria categoria = new Categoria(id, getNombreCategoria(), getDescripcionCategoria());

            categoriaService.createCategoria(categoria);

            //TODO-mostrar mensaje de confirmacon

            stageManager.switchScene(FxmlView.LISTACATEGORIAS);
        }
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
            stageManager.switchScene(FxmlView.LISTACATEGORIAS);
    }

    @FXML
    private boolean validar() {
        boolean nombreCategoriaValid = nombreCategoria.validate();
        boolean descripcionCategoriaValid = descripcionCategoria.validate();

        // Si alguno de los campos no es válido, retornar true
        if (!nombreCategoriaValid || !descripcionCategoriaValid) {
            return false;
        } else {
            return true;
        }
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

    }

}
