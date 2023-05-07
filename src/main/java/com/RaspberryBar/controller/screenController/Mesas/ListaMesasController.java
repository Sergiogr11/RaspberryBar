package com.RaspberryBar.controller.screenController.Mesas;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.MesaService;
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
public class ListaMesasController implements Initializable {


    @Autowired
    private MesaService mesaService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private JFXListView<String> listaMesas;

    private ObservableList<String> selectedMesa;
    private List<String> mesasList = new ArrayList<>();
    static Mesa mesaEditar = new Mesa();


    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.MESAS);
    }

    @FXML
    private void eliminarMesa(ActionEvent event) throws IOException {
        //Obtengo la mesa seleccionada
        selectedMesa = listaMesas.getSelectionModel().getSelectedItems();

        //Obtengo el id correspondiente al nombre de la mesa seleccionada
        int idMesa = mesaService.findMesaByNombre(selectedMesa.get(0));

        //Busco en la base datos la categoria seleccionada
        Mesa mesaEliminar = mesaService.(idCategoria);

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

