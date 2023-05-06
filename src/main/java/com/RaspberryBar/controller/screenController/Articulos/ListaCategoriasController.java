package com.RaspberryBar.controller.screenController.Articulos;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.CategoriaRepository;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
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
public class ListaCategoriasController implements Initializable {


    @Autowired
    private CategoriaService categoriaService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private JFXListView<String> listaCategorias;

    private ObservableList<String> selectedCategorias;
    private List<String> categoriasList = new ArrayList<>();
    static Categoria categoriaEditar = new Categoria();


    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.ARTICULOS);
    }

    @FXML
    private void eliminarCategoria(ActionEvent event) throws IOException {
        //Obtengo la categoria seleccionada
        selectedCategorias = listaCategorias.getSelectionModel().getSelectedItems();

        //Obtengo el id correspondiente al nombre de la categoria seleccionada
        int idCategoria = categoriaService.findIdByNombreCategoria(selectedCategorias.get(0));

        //Busco en la base datos la categoria seleccionada
        Categoria categoriaEliminar = categoriaService.findCategoria(idCategoria);

        //Muestro mensaje de categoria eliminada
        CustomAlert alertEliminarCategoria = new CustomAlert(Alert.AlertType.INFORMATION);
        alertEliminarCategoria.setTitle("Categoría eliminada");
        alertEliminarCategoria.setHeaderText("Categoría satisfactoriamente eliminada");
        alertEliminarCategoria.showAndWait();

        //Borro la categoria seleccionada
        categoriaService.deleteCategoria(categoriaEliminar);
        actualizarListView();
    }

    @FXML
    private void editarCategoria(ActionEvent event) throws IOException {
        //Obtengo la categoria seleccionada
        selectedCategorias = listaCategorias.getSelectionModel().getSelectedItems();

        //Busco en la base datos la categoria seleccionada
        int idCategoria = categoriaService.findIdByNombreCategoria(selectedCategorias.get(0));
        categoriaEditar = categoriaService.findCategoria(idCategoria);

        //Cambio de escena
        stageManager.switchScene(FxmlView.EDITARCATEGORIA);
    }

    @FXML
    private void crearCategoria(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.CREARCATEGORIA);
    }


    private void actualizarListView(){
        categoriasList = categoriaService.readCategoriasName();

        listaCategorias.getItems().clear();
        listaCategorias.getItems().addAll(categoriasList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualizarListView();
    }

}

