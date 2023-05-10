package com.RaspberryBar.controller.screenController.Comandas;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Articulo;
import com.RaspberryBar.entities.LineaComanda;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.ComandaService;
import com.RaspberryBar.service.LineaComandaService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ComandasController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    JFXTreeTableView TableLineasComanda;
    @FXML
    FlowPane ListaArticulos;
    @FXML
    GridPane gridPaneTeclado;
    @FXML
    TextField prueba;

    @Autowired
    CategoriaService categoriaService;
    @Autowired
    ArticuloService articuloService;
    @Autowired
    LineaComandaService lineaComandaService;
    @Autowired
    ComandaService comandaService;

    List<String> articulosNameList = new ArrayList<>();
    List<String> categoriasNameList = new ArrayList<>();
    int cantidad;
    Articulo articuloSeleccionado;
    int comandaId = 0;

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
    }

    private void inicializarTecladoNumerico(){
        gridPaneTeclado.setHgap(15);
        gridPaneTeclado.setVgap(15);

        // Crea los botones numéricos
        for (int i = 1; i <= 9; i++) {
            final int j = i;
            JFXButton button = new JFXButton(Integer.toString(i));
            button.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
            button.setPrefSize(200, 200);
            //Añado accion a los botones numericos
            button.setOnAction(event -> {
                String temp = cantidad + Integer.toString(j);
                cantidad = Integer.parseInt(temp);
                prueba.setText(String.valueOf(cantidad));
            });
            gridPaneTeclado.add(button, (i - 1) % 3, (i - 1) / 3);
        }

        JFXButton zeroButton = new JFXButton("0");
        zeroButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        zeroButton.setPrefSize(200, 200);
        //Añado accion al boton 0
        zeroButton.setOnAction(event -> {
            String temp = cantidad + zeroButton.getText();
            cantidad = Integer.parseInt(temp);
            prueba.setText(String.valueOf(cantidad));
        });
        gridPaneTeclado.add(zeroButton, 1, 3);

        JFXButton clearButton = new JFXButton("C");
        clearButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        clearButton.setPrefSize(200, 200);
        //Añado accion al boton clear
        clearButton.setOnAction(event -> {
            cantidad = 0;
            prueba.setText(String.valueOf(cantidad));
        });
        gridPaneTeclado.add(clearButton, 2, 3);

        JFXButton negativeButton = new JFXButton("-");
        negativeButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        negativeButton.setPrefSize(200, 200);
        //Añado accion al boton -
        negativeButton.setOnAction(event -> {
            cantidad *= -1;
            prueba.setText(String.valueOf(cantidad));
        });
        gridPaneTeclado.add(negativeButton, 0, 3);
    }

    private void inicializarListaCategorias(){
        categoriasNameList.clear();
        ListaArticulos.getChildren().clear();
        categoriasNameList = categoriaService.readCategoriasName();

        for (String categoria : categoriasNameList){
            JFXButton categoriaButton = new JFXButton(categoria);
            categoriaButton.setOnAction(event -> {
                //Limpio el FlowPane
                ListaArticulos.getChildren().clear();
                //Inicializo los botones de los articulos correspondientes
                inicializarListaArticulos(categoria);
            });
            //Ajusta el tamaño del botón
            categoriaButton.setPrefSize(185, 150);
            categoriaButton.setStyle("-fx-background-color: #6b0c0c; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
            // Establecer margen del botón
            AnchorPane.setTopAnchor(categoriaButton, 50.0);
            AnchorPane.setLeftAnchor(categoriaButton, 50.0);
            ListaArticulos.getChildren().add(categoriaButton);
        }
        //Configura las propiedades del FlowPane
        ListaArticulos.setHgap(25);
        ListaArticulos.setVgap(25);
        ListaArticulos.setPadding(new Insets(25));
    }

    private void inicializarListaArticulos(String categoriaName){
        //Obtengo el id categoria
        int idCategoria = categoriaService.findIdByNombreCategoria(categoriaName);
        articulosNameList = articuloService.findbyCategoria(idCategoria);

        for (String articulo : articulosNameList){
            JFXButton articuloButton = new JFXButton(articulo);
            articuloButton.setOnAction(event -> {
                int articuloId = articuloService.findIdByNombreArticulo(articulo);
                articuloSeleccionado = articuloService.findArticulo(articuloId);

                //Calculo el precio total segun la cantidad seleccionada
                float precioTotal = cantidad * articuloSeleccionado.getPrecio();

                // Crear un nuevo objeto LineaComanda con los datos correspondientes
                int lineaComandaId = lineaComandaService.findMaxId();
                if(comandaId == 0){
                    comandaId = comandaService.findMaxId();
                }
                LineaComanda nuevaLinea = new LineaComanda(
                        lineaComandaId,
                        comandaId,
                        cantidad,
                        precioTotal,
                        articuloSeleccionado.getArticuloId()
                );

                // Agregar la nueva fila al JFXTreeTableView
                TableLineasComanda.getRoot().getChildren().add(new TreeItem<>(nuevaLinea));

            });
            //Ajusta el tamaño del botón
            articuloButton.setPrefSize(185, 150);
            articuloButton.setStyle("-fx-background-color: #6b0c0c; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 16px;");
            // Establecer margen del botón
            AnchorPane.setTopAnchor(articuloButton, 50.0);
            AnchorPane.setLeftAnchor(articuloButton, 50.0);
            ListaArticulos.getChildren().add(articuloButton);
        }

    }

    @FXML
    private void volverCategorias(ActionEvent event) throws IOException {
        inicializarListaCategorias();
    }

    @FXML
    private void imprimirTicket(ActionEvent event) throws IOException {
        //TODO
        //Imprimir con la impresora
    }

    @FXML
    private void cobrar(ActionEvent event) throws IOException {
        //Ventana emergente para cobrar
    }

    @FXML
    private void eliminarTicket(ActionEvent event) throws IOException {
        //Borrar las lineas de comanda creadas
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //TODO
        //Obtengo comandaId desde MesasController

        //Inicializo teclado numerico
        inicializarTecladoNumerico();
        //Inicializo Lista categorias
        inicializarListaCategorias();
    }
}
