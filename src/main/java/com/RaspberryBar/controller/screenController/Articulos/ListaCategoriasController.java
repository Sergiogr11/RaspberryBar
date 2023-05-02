package com.RaspberryBar.controller.screenController.Articulos;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.CategoriaRepository;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXListView;
import javafx.collections.ObservableList;
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
public class ListaCategoriasController implements Initializable {


    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private JFXListView<String> listaCategorias;

    private ObservableList<String> selectedCategorias;
    private List<String> categoriasList = new ArrayList<>();
    private List<Integer> categoriasIdList = new ArrayList<>();
    static Categoria categoriaEditar = new Categoria();


    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.ARTICULOS);
    }

    @FXML
    private void eliminarCategoria(ActionEvent event) throws IOException {
        //Obtengo la categoria seleccionada y su nombre
        selectedCategorias = listaCategorias.getSelectionModel().getSelectedItems();
        //Obtengo el id correspondiente al nombre de la categoria seleccionada
        int indice = categoriasList.indexOf(selectedCategorias.get(0));
        int indice_categoria = categoriasIdList.get(indice);
        //Busco en la base datos el usuario seleccionado
        Categoria categoriaEliminar = categoriaService.findCategoria(indice_categoria);
        //Borro el usuario seleccionado
        categoriaService.deleteCategoria(categoriaEliminar);
        actualizarListView();
    }

    @FXML
    private void editarCategoria(ActionEvent event) throws IOException {
        //Obtengo la categoria seleccionada
        selectedCategorias = listaCategorias.getSelectionModel().getSelectedItems();
        //Obtengo el id correspondiente al nombre de la categoria seleccionada
        int indice = categoriasList.indexOf(selectedCategorias.get(0));
        int indice_categoria = categoriasIdList.get(indice);
        //Busco en la base datos el usuario seleccionado
        categoriaEditar = categoriaService.findCategoria(indice_categoria);
        //Cambio de escena con la variable global ya inicializada
        stageManager.switchScene(FxmlView.EDITARCATEGORIA);
    }

    @FXML
    private void crearCategoria(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.CREARCATEGORIA);
    }


    private void actualizarListView(){
        //Borro elementos antiguos de la lista
        categoriasList.clear();
        categoriasIdList.clear();
        //Obtengo los usuarios y sus usernames para inicializar la ListView
        List<Categoria> categoriaList = categoriaService.readCategoria();

        for (Categoria categoria : categoriaList) {
            String nombreCategoria = categoria.getNombreCategoria();
            int idCategoria = categoria.getCategoriaId();
            categoriasIdList.add(idCategoria);
            categoriasList.add(nombreCategoria);
        }

        listaCategorias.getItems().clear();
        listaCategorias.getItems().addAll(categoriasList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualizarListView();
    }

}

