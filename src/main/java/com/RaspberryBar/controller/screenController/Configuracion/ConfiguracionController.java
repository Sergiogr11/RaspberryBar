package com.RaspberryBar.controller.screenController.Configuracion;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.service.ImpresoraService;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.Printer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Attribute;
import javax.print.attribute.standard.PrinterName;
import javax.swing.*;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;

@Controller
public class ConfiguracionController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;
    @FXML
    private JFXTextField ipServer;
    @FXML
    private JFXTextField port;
    @FXML
    private JFXComboBox<String> comboBoxImpBarra;
    @FXML
    private JFXComboBox<String> comboBoxImpCocina;

    @Value("${server.port}")
    private String serverPort;


    @FXML
    private void volver(ActionEvent event) throws IOException {

        ImpresoraService impresoraService = ImpresoraService.getInstance();
        // Guardo las impresoras seleccionadas
        impresoraService.setImpresoraBarra(comboBoxImpBarra.getValue());
        impresoraService.setImpresoraCocina(comboBoxImpCocina.getValue());
        impresoraService.saveConfig();

        stageManager.switchScene(FxmlView.HOME);
    }

    @FXML
    private void entrarConfiguracionRestaurante(ActionEvent event) throws IOException{
        ImpresoraService impresoraService = ImpresoraService.getInstance();
        // Guardo las impresoras seleccionadas
        impresoraService.setImpresoraBarra(comboBoxImpBarra.getValue());
        impresoraService.setImpresoraCocina(comboBoxImpCocina.getValue());
        impresoraService.saveConfig();

        stageManager.switchScene(FxmlView.CONFIGURACIONRTE);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Obtiene la dirección IP local
            String localIpAddress = InetAddress.getLocalHost().getHostAddress();
            // Establece la dirección IP en el campo de texto
            ipServer.setText(localIpAddress);
            //Establezco el puerto en el campo de texto
            port.setText(serverPort);

            // Obtengo la lista de servicios de impresión disponibles
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            System.out.println(printServices);

            // Añado los nombres de las impresoras a los ComboBox
            for (PrintService printer : printServices) {
                Attribute printerNameAttribute = printer.getAttribute(PrinterName.class);
                if (printerNameAttribute != null) {
                    String printerName = ((PrinterName) printerNameAttribute).getValue();
                    comboBoxImpBarra.getItems().add(printerName);
                    comboBoxImpCocina.getItems().add(printerName);
                }
            }

            // Cargar la configuración de la impresora
            ImpresoraService impresoraService = ImpresoraService.getInstance();
            impresoraService.loadConfig();

            // Establecer los valores de las impresoras en los ComboBox
            comboBoxImpBarra.setValue(impresoraService.getImpresoraBarra());
            comboBoxImpCocina.setValue(impresoraService.getImpresoraCocina());


        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
