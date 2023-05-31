package com.RaspberryBar.controller.screenController.Facturacion;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.controller.entitiesController.ImpresoraController;
import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.entities.Factura;
import com.RaspberryBar.entities.Restaurante;
import com.RaspberryBar.service.ComandaService;
import com.RaspberryBar.service.FacturaService;
import com.RaspberryBar.service.RestauranteService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class CrearFacturaController implements Initializable {


    @Lazy
    @Autowired
    StageManager stageManager;

    @Autowired
    ImpresoraController impresoraController;

    @Autowired
    ComandaService comandaService;
    @Autowired
    RestauranteService restauranteService;
    @Autowired
    FacturaService facturaService;


    @FXML
    private JFXTextField textFieldNombreReceptor;
    @FXML
    private JFXTextField textFieldDniReceptor;
    @FXML
    private JFXTextField textFieldIdComanda;


    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.FACTURACION);
    }

    @FXML
    private void imprimirFactura(ActionEvent event) throws IOException {
        //Primero obtenemos los valores a añadir en la Factura
        //Obtengo la fecha y hora actuales
        Date currentDate = new Date();
        long fechaEmision = currentDate.getTime();

        //Obtengo nombre y DNI de receptor, además de comandaId de los JFXTextFields
        String nombreReceptor = textFieldNombreReceptor.getText();
        String dniReceptor = textFieldDniReceptor.getText();
        int comandaId = Integer.parseInt(textFieldIdComanda.getText());

        //Obtengo de restauranteService el restauranteId
        Restaurante restaurante = restauranteService.readRestaurante().get(0);
        int restauranteId = restaurante.getRestauranteId();

        //Obtengo el importe total de comandaService
        Comanda comanda = comandaService.findComandaById(comandaId).orElse(null);
        if (comanda != null) {
            float importeTotal = comanda.getPrecioTotal();
            //Obtengo base imponible respecto al iva y al importe total
            int iva = Integer.parseInt(restaurante.getTipoImpositivo());
            float baseImponible = importeTotal - (importeTotal * iva / 100);
            float baseImponibleRedondeada = Math.round(baseImponible * 100) / 100f;


            //Creamos la factura en la base de datos
            Factura factura = new Factura();
            factura.setFechaEmision(fechaEmision);
            factura.setNombreReceptor(nombreReceptor);
            factura.setDniReceptor(dniReceptor);
            factura.setBaseImponible(baseImponibleRedondeada);
            factura.setImporteTotal(importeTotal);
            factura.setRestauranteId(restauranteId);
            factura.setComandaId(comandaId);


            facturaService.createFactura(factura);
            //Imprimimos la factura
            impresoraController.imprimirFactura(factura);
        } else {
            // No existe la comanda, mostramos un mensaje de error
            CustomAlert alertErrorBalanceVentas = new CustomAlert(Alert.AlertType.INFORMATION);
            alertErrorBalanceVentas.setTitle("No existe comanda");
            alertErrorBalanceVentas.setHeaderText("No hay una comanda asociada a ese Id ");
            alertErrorBalanceVentas.showAndWait();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}