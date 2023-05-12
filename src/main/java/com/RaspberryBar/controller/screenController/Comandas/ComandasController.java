package com.RaspberryBar.controller.screenController.Comandas;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.controller.screenController.Mesas.MesasController;
import com.RaspberryBar.entities.*;
import com.RaspberryBar.service.ArticuloService;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.ComandaService;
import com.RaspberryBar.service.LineaComandaService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Controller
public class ComandasController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    TableView<LineaComanda> TableLineasComanda;
    @FXML
    FlowPane ListaArticulos;
    @FXML
    GridPane gridPaneTeclado;
    @FXML
    TextField precioTotal;

    @Autowired
    CategoriaService categoriaService;
    @Autowired
    ArticuloService articuloService;
    @Autowired
    LineaComandaService lineaComandaService;
    @Autowired
    ComandaService comandaService;
    @Autowired
    MesasController mesasController;

    List<String> articulosNameList = new ArrayList<>();
    List<String> categoriasNameList = new ArrayList<>();
    ObservableList<LineaComanda> lineasComanda = FXCollections.observableArrayList();

    int cantidad = 0;
    Articulo articuloSeleccionado;
    int comandaId = 1;
    float totalPrice;
    LocalDateTime fechaApertura;
    int mesaId;
    int usuarioId;
    //Inicializo contador de linea Comanda
    int numeroLinea  = 0;

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
                String temp = (String.valueOf(cantidad)) + Integer.toString(j);
                cantidad = Integer.parseInt(temp);
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
        });
        gridPaneTeclado.add(zeroButton, 1, 3);

        JFXButton clearButton = new JFXButton("C");
        clearButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        clearButton.setPrefSize(200, 200);
        //Añado accion al boton clear
        clearButton.setOnAction(event -> {
            cantidad = 0;
        });
        gridPaneTeclado.add(clearButton, 2, 3);

        JFXButton negativeButton = new JFXButton("-");
        negativeButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        negativeButton.setPrefSize(200, 200);
        //Añado accion al boton -
        negativeButton.setOnAction(event -> {
            cantidad *= -1;
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

                // Si no se selecciona un número, la cantidad por defecto será 1
                int cantidadFinal = (cantidad == 0 ? 1 : cantidad);

                //Calculo el precio total segun la cantidad seleccionada
                float precio = cantidadFinal * articuloSeleccionado.getPrecio();
                //Acepto solo 2 decimales
                BigDecimal bd = new BigDecimal(Float.toString(precio));
                bd = bd.setScale(2, RoundingMode.HALF_UP);
                precio = bd.floatValue();
                //Actualizo el precio total
                totalPrice += precio;
                //Acepto solo 2 decimales
                BigDecimal bd2 = new BigDecimal(Float.toString(totalPrice));
                bd2 = bd2.setScale(2, RoundingMode.HALF_UP);
                totalPrice = bd2.floatValue();
                precioTotal.setText(String.valueOf(totalPrice));

                if(comandaId == 0){
                    comandaId = comandaService.findMaxId();
                }

                LineaComandaId lineaComandaId = new LineaComandaId(numeroLinea, comandaId);

                LineaComanda nuevaLinea = new LineaComanda(
                        lineaComandaId,
                        cantidadFinal,
                        precio,
                        articuloSeleccionado.getArticuloId()
                );

                // Agregar la nueva fila al TreeTableView
                lineasComanda.add(nuevaLinea);
                //Seteo la cantidad de nuevo
                cantidad = 0;
                numeroLinea++;
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

    private void inicializarTablaComanda(){
        //Agrando el tamaño de las filas
        TableLineasComanda.setStyle("-fx-cell-size : 80px; -fx-font-size : 20px");
        //Inicializo las columnas
        TableColumn<LineaComanda, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad")); //Asumiendo que LineaComanda tiene un método getCantidad()

        TableColumn<LineaComanda, String> columnaArticulo = new TableColumn<>("Articulo");
        columnaArticulo.setCellValueFactory(new PropertyValueFactory<>("articuloId")); // Aquí necesitarás convertir el id del artículo a su nombre

        TableColumn<LineaComanda, Double> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio")); //Asumiendo que LineaComanda tiene un método getPrecioTotal()

        //Hago que la columna de articulo tenga el nombre en lugar del id del articulo
        columnaArticulo.setCellValueFactory(cellData -> {
            Integer articuloId = cellData.getValue().getArticuloId();
            String nombreArticulo = articuloService.findNombrebyId(articuloId);
            return new SimpleStringProperty(nombreArticulo);
        });

        //Ajusto el tamaño de cada columna
        columnaCantidad.prefWidthProperty().bind(TableLineasComanda.widthProperty().multiply(0.33));
        columnaArticulo.prefWidthProperty().bind(TableLineasComanda.widthProperty().multiply(0.33));
        columnaPrecio.prefWidthProperty().bind(TableLineasComanda.widthProperty().multiply(0.33));

        TableLineasComanda.getColumns().addAll(columnaArticulo, columnaCantidad, columnaPrecio);
        TableLineasComanda.setItems(lineasComanda);

    }

    @FXML
    private void volverCategorias(ActionEvent event) throws IOException {
        inicializarListaCategorias();
    }

    @FXML
    private void imprimirTicket(ActionEvent event) throws IOException {
        //Crear la comanda
        Comanda comanda;
        Optional<Comanda> optionalComanda = comandaService.findComandaById(comandaId);
        if (optionalComanda.isPresent()) {
            // Si la comanda ya existe, actualizarla
            comanda = optionalComanda.get();
            comanda.setPrecioTotal(totalPrice); // asumir totalPrice es el precio total de la comanda
            comanda.setFechaHoraCierre(LocalDateTime.now());
        } else {
            // Si la comanda no existe, crear una nueva
            comanda = new Comanda();
            comanda.setNumeroComanda(comandaId);
            comanda.setPrecioTotal(totalPrice);
            comanda.setFechaHoraApertura(fechaApertura);
            comanda.setFechaHoraCierre(LocalDateTime.now());
            //TODO momentaneamente pongo 1 sólo
            comanda.setNumeroComensales(1);
            //Ver como obtenerla
            comanda.setMesaId(mesaId);
            //Ver como obtenerlo
            comanda.setUsuarioId(usuarioId);
        }

        // Guardar la comanda en la base de datos
        comandaService.save(comanda);

        //Guardar lineas comanda creadas en la bbdd
        for (LineaComanda lineaComanda : lineasComanda) {
            lineaComandaService.createLineaComanda(lineaComanda);
        }
        // Limpiar la lista de lineas de comanda después de guardarlas y precio total
        //lineasComanda.clear();
        //totalPrice = 0;
        //precioTotal.setText("0");
        numeroLinea = 1;

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

    @FXML
    private void eliminarLinea(ActionEvent event) throws IOException {
        //Obtengo la fila seleccionada
        LineaComanda selectedLineaComanda = TableLineasComanda.getSelectionModel().getSelectedItem();
        //Resto el precio de la linea eliminada
        totalPrice -= selectedLineaComanda.getPrecio();
        //Acepto solo 2 decimales
        BigDecimal bd = new BigDecimal(Float.toString(totalPrice));
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        totalPrice = bd.floatValue();

        precioTotal.setText(String.valueOf(totalPrice));
        if (selectedLineaComanda != null) {
            lineasComanda.remove(selectedLineaComanda);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        inicializarTablaComanda();
        //TODO
        //Obtengo comandaId desde MesasController

        //Inicializo teclado numerico
        inicializarTecladoNumerico();
        //Inicializo Lista categorias
        inicializarListaCategorias();
    }
}
