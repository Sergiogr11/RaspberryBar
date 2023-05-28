package com.RaspberryBar.controller.screenController.Facturacion;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.controller.entitiesController.ImpresoraController;
import com.RaspberryBar.entities.Comanda;
import com.RaspberryBar.service.ComandaService;
import com.RaspberryBar.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class BalanceVentasController implements Initializable {

    @Lazy
    @Autowired
    StageManager stageManager;

    @Autowired
    ImpresoraController impresoraController;

    @Autowired
    ComandaService comandaService;

    @FXML
    private DatePicker datePickerInicial;
    @FXML
    private DatePicker datePickerFinal;


    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.FACTURACION);
    }

    @FXML
    private void imprimirBalanceVentas(ActionEvent event) throws IOException {

        LocalDate fechaInicial = datePickerInicial.getValue();
        LocalDate fechaFinal = datePickerFinal.getValue();

        if (fechaInicial != null && fechaFinal != null) {
            // Ambas fechas están seleccionadas, realizar la llamada al método
            long fechaInicialMillis = fechaInicial.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();
            long fechaFinalMillis = fechaFinal.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli();

            List<Comanda> comandaListDiario = comandaService.findComandasByFechaHoraCierreBetween(fechaInicialMillis, fechaFinalMillis);
            //Imprimir resumen hoy
            impresoraController.imprimirBalanceVentas(comandaListDiario);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
