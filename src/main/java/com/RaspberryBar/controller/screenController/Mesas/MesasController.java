package com.RaspberryBar.controller.screenController.Mesas;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.service.MesaService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class MesasController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Autowired
    private MesaService mesaService;

    List<Mesa> mesasList = new ArrayList<>();

    @FXML
    private JFXButton btnRestaurante;
    @FXML
    private JFXButton btnBar;
    @FXML
    private JFXButton btnTerraza;

    @FXML
    private FlowPane tableroMesas;


    @FXML
    private void configurarMesas(ActionEvent event) throws IOException{
        stageManager.switchScene(FxmlView.LISTAMESAS);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
    }

    @FXML
    private void disposicionRestaurante(ActionEvent event) throws IOException {
        cargarDisposicion("Restaurante");
    }

    @FXML
    private void disposicionBar(ActionEvent event) throws IOException {
        cargarDisposicion("Bar");
    }

    @FXML
    private void disposicionTerraza(ActionEvent event) throws IOException {
        cargarDisposicion("Terraza");
    }

    private void seleccionarBoton(JFXButton botonSeleccionado) {
        for (JFXButton boton : Arrays.asList(btnRestaurante, btnBar, btnTerraza)) {
            if (boton.equals(botonSeleccionado)) {
                boton.getStyleClass().remove("btnGreen");
                boton.getStyleClass().add("btnSelected");
            } else {
                boton.getStyleClass().remove("btnSelected");
                boton.getStyleClass().add("btnGreen");
            }
        }
    }

    private void cargarDisposicion(String posicionMesas){
        mesasList.clear();
        tableroMesas.getChildren().clear();
        mesasList = mesaService.findMesaByPosicion(posicionMesas);
        switch (posicionMesas) {
            case "Restaurante":
                seleccionarBoton(btnRestaurante);
                break;
            case "Bar":
                seleccionarBoton(btnBar);
                break;
            case "Terraza":
                seleccionarBoton(btnTerraza);
                break;
        }

        for (Mesa mesa : mesasList){
            JFXButton mesaButton = new JFXButton(mesa.getNombreMesa());
            mesaButton.setOnAction(event -> {
                //TODO
                // Setear la mesa seleccionada a un atributo global
                // Cambiar de escena a comandas
            });
            mesaButton.setPrefSize(150, 150); //Ajusta el tamaño del botón
            mesaButton.getStyleClass().add("btnGreen");
            //mesaButton.setStyle("-fx-background-color: #00FF00");
            // Establecer margen del botón
            AnchorPane.setTopAnchor(mesaButton, 50.0);
            AnchorPane.setLeftAnchor(mesaButton, 50.0);
            tableroMesas.getChildren().add(mesaButton);
        }
        //Configura las propiedades del FlowPane
        tableroMesas.setHgap(25);
        tableroMesas.setVgap(25);
        tableroMesas.setPadding(new Insets(25));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDisposicion("Restaurante");
    }
}
