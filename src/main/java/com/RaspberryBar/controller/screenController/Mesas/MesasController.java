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
    private FlowPane tableroMesas;


    @FXML
    private void editarMesas(ActionEvent event) throws IOException{
        //stageManager.switchScene(FxmlView.EDITARMESAS);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mesasList = mesaService.readMesas();
        for (Mesa mesa : mesasList){
            JFXButton mesaButton = new JFXButton(mesa.getNombreMesa());
            mesaButton.setLayoutX(mesa.getX());
            mesaButton.setLayoutY(mesa.getY());
            mesaButton.setOnAction(event -> {
                //Entrar a la pantalla de comandas con la comanda correspondiente a la mesa
            });
            mesaButton.setPrefSize(150, 150); //Ajusta el tamaño del botón
            // Establecer margen del botón
            AnchorPane.setTopAnchor(mesaButton, 10.0);
            AnchorPane.setLeftAnchor(mesaButton, 10.0);
            tableroMesas.getChildren().add(mesaButton);

        }

        //Configura las propiedades del FlowPane
        tableroMesas.setHgap(10);
        tableroMesas.setVgap(10);
        tableroMesas.setPadding(new Insets(10));
    }

}
