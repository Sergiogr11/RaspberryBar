package com.RaspberryBar.controller.screenController.Articulos;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.ArticuloRepository;
import com.RaspberryBar.repository.UsuarioRepository;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
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
public class ListaArticulosController implements Initializable {


    @Autowired
    private ArticuloService articuloService;
    @Autowired
    private ArticuloRepository articuloRepository;
    @Autowired
    private CategoriaService categoriaService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private JFXListView<String> listaArticulos;
    @FXML
    private JFXComboBox<String> comboBoxCategoria;

    private ObservableList<String> selectedArticulos;
    private List<String> articulosNombresList = new ArrayList<>();
    private List<Integer> articulosIdList = new ArrayList<>();
    private List<Integer> categoriaIdList = new ArrayList<>();
    private List<String> nombresCategorias = new ArrayList<>();
    static Articulo articuloEditar = new Articulo();

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
    }

    @FXML
    private void eliminarArticulo(ActionEvent event) throws IOException {
        //Obtengo el articulo seleccionado
        selectedArticulos = listaArticulos.getSelectionModel().getSelectedItems();

        //Obtengo el id correspondiente al nombre del articulo seleccionado
        int idArticulo = articuloService.findIdByNombreArticulo(selectedArticulos.get(0));

        //Busco en la base datos el articulo seleccionado
        Articulo articuloEliminar = articuloService.findArticulo(idArticulo);

        //Borro el usuario seleccionado
        articuloService.deleteArticulo(articuloEliminar);
        comboBoxCategoria.getSelectionModel().clearSelection();
        actualizarListView();
    }

    @FXML
    private void editarArticulo(ActionEvent event) throws IOException {
        //Obtengo el articulo seleccionado
        selectedArticulos = listaArticulos.getSelectionModel().getSelectedItems();
        //Busco en la base datos el usuario seleccionado
        int idArticulo = articuloService.findIdByNombreArticulo(selectedArticulos.get(0));
        articuloEditar = articuloService.findArticulo(idArticulo);
        //Paso como parametro el usuarioEditar
        stageManager.switchScene(FxmlView.EDITARARTICULO);
    }

    @FXML
    private void crearArticulo(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.CREARARTICULO);
    }

    private void actualizarListView(){
        articulosNombresList = articuloService.readNameArticulos();

        listaArticulos.getItems().clear();
        listaArticulos.getItems().addAll(articulosNombresList);
    }

    private void actualizarComboBox(){
        //Obtengo el nombre de las categorias
        nombresCategorias = categoriaService.readCategoriasName();
        //Añado el nombre de las categorias al comboBox
        comboBoxCategoria.setItems(FXCollections.observableList(nombresCategorias));
    }


    private void actionComboBox(){
        comboBoxCategoria.setOnAction(event -> {
            //Obtengo la categoria seleccionada y el indice correspondiente a la misma
            String selectedCategoria = comboBoxCategoria.getSelectionModel().getSelectedItem();
            int idCategoria = categoriaService.findIdByNombreCategoria(selectedCategoria);

            //Limpio la lista de articulos y añado solo las correspondientes a la categoria
            articulosNombresList.clear();
            articulosNombresList = articuloService.findbyCategoria(idCategoria);
            listaArticulos.getItems().clear();
            listaArticulos.getItems().addAll(articulosNombresList);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualizarListView();
        actualizarComboBox();
        actionComboBox();
    }

}

