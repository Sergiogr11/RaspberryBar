package com.RaspberryBar.controller.screenController.Facturacion;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.controller.entitiesController.ImpresoraController;
import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.service.ComandaService;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.FxmlView;
import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import java.time.*;
import java.time.temporal.ChronoUnit;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class FacturacionController implements Initializable {

    @Autowired
    ComandaService comandaService;

    @Autowired
    ImpresoraController impresoraController;

    @Lazy
    @Autowired
    StageManager stageManager;

    @FXML
    private void resumenCajaDiario(ActionEvent event) throws IOException {
        //Obtener día de hoy
        LocalDate hoy = LocalDate.now(); // la fecha de hoy

        // conseguir el inicio del día en milisegundos
        ZonedDateTime inicioDelDia = hoy.atStartOfDay(ZoneId.systemDefault());
        long inicioDelDiaMillis = inicioDelDia.toInstant().toEpochMilli();

        // conseguir el final del día en milisegundos
        ZonedDateTime finalDelDia = inicioDelDia.plus(1, ChronoUnit.DAYS);
        long finalDelDiaMillis = finalDelDia.toInstant().toEpochMilli();

        //Buscar tickets que tengan el día correspondiente
        List<Comanda> comandaListDiario;
        comandaListDiario = comandaService.findComandasByFechaHoraCierreBetween(inicioDelDiaMillis, finalDelDiaMillis);

        //Imprimir resumen hoy
        impresoraController.imprimirBalanceVentas(comandaListDiario);
    }

    @FXML
    private void entrarBalanceVentas(ActionEvent event) throws IOException {
        //Cambio escena a balance ventas
        stageManager.switchScene(FxmlView.BALANCEVENTAS);
    }

    @FXML
    private void entrarCrearFactura(ActionEvent event) throws IOException {
        //Cambio escena a crear facturas
        stageManager.switchScene(FxmlView.CREARFACTURAS);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
