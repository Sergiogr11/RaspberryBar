package com.RaspberryBar.controller.screenController.Cobrar;

import com.RaspberryBar.controller.screenController.Comandas.ComandasController;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.service.ComandaService;
import com.RaspberryBar.service.MesaService;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class MenuCobrarController implements Initializable {

    @Autowired
    ComandasController comandasController;
    @Autowired
    MesaService mesaService;

    @FXML
    GridPane numerosGridPane;
    @FXML
    TextField precioTotal;
    @FXML
    TextField dineroRecibido;
    @FXML
    TextField aDevolver;
    @FXML
    JFXButton btnCancelar;


    double cantidad = 0.0;

    private void inicializarTecladoNumerico() {
        numerosGridPane.setHgap(7);
        numerosGridPane.setVgap(15);

        // Crea los botones numéricos
        for (int i = 1; i <= 9; i++) {
            final int j = i;
            JFXButton button = new JFXButton(Integer.toString(i));
            button.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
            button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // Hace que el botón ocupe todo el espacio disponible
            GridPane.setHgrow(button, Priority.ALWAYS);
            GridPane.setVgrow(button, Priority.ALWAYS);

            // Añado acción a los botones numéricos
            button.setOnAction(event -> {
                String currentText = dineroRecibido.getText();
                if (currentText.contains(".")) {
                    String[] parts = currentText.split("\\.");
                    if (parts.length > 1 && parts[1].length() >= 2) {
                        return; // No agregar más números después del punto decimal.
                    }
                }
                dineroRecibido.setText(currentText + button.getText());
            });

            numerosGridPane.add(button, (i - 1) % 3, (i - 1) / 3);
        }

        JFXButton zeroButton = new JFXButton("0");
        zeroButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        zeroButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(zeroButton, Priority.ALWAYS);
        GridPane.setVgrow(zeroButton, Priority.ALWAYS);
        //Añado accion al boton 0
        zeroButton.setOnAction(event -> {
            String currentText = dineroRecibido.getText();
            if (currentText.contains(".")) {
                String[] parts = currentText.split("\\.");
                if (parts.length > 1 && parts[1].length() >= 2) {
                    return; // No agregar más números después del punto decimal.
                }
            }
            dineroRecibido.setText(currentText + zeroButton.getText());
        });


        numerosGridPane.add(zeroButton, 1, 3);

        JFXButton clearButton = new JFXButton("C");
        clearButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        clearButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(clearButton, Priority.ALWAYS);
        GridPane.setVgrow(clearButton, Priority.ALWAYS);
        //Añado accion al boton clear
        clearButton.setOnAction(event -> {
            dineroRecibido.setText(""); // Muestra solo hasta dos decimales.
        });
        numerosGridPane.add(clearButton, 0, 3);

        JFXButton decimalButton = new JFXButton(".");
        decimalButton.setStyle("-fx-background-color: #757575; -fx-text-fill: #ffffff; -fx-font-style: Verdana; -fx-font-size: 24px;");
        decimalButton.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        GridPane.setHgrow(decimalButton, Priority.ALWAYS);
        GridPane.setVgrow(decimalButton, Priority.ALWAYS);

        decimalButton.setOnAction(event -> {
            String currentText = dineroRecibido.getText();
            if (!currentText.contains(".")) {
                dineroRecibido.setText(currentText + ".");
            } else {
                String[] parts = currentText.split("\\.");
                if (parts.length > 1 && parts[1].length() < 2) {
                    dineroRecibido.setText(currentText + ".");
                }
            }
        });

        numerosGridPane.add(decimalButton, 2, 3);
    }
    @FXML
    public void cobrar(ActionEvent event ) throws IOException{
        Mesa mesa = mesaService.findMesa(comandasController.mesaId);
        mesa.setEstadoMesa("Libre");
        mesaService.updateMesa(mesa);

        cantidad = 0;
        dineroRecibido.setText("0");

        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
        comandasController.reiniciarValores();
    }

    @FXML
    public void cancelar(ActionEvent event ) throws IOException{
        cantidad = 0;
        dineroRecibido.setText("0");
        Stage stage = (Stage) btnCancelar.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarTecladoNumerico();
        precioTotal.setText(String.valueOf(comandasController.totalPrice));

        dineroRecibido.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.isEmpty()) {
                try {
                    float cantidadRecibida = Float.parseFloat(newValue);
                    float aDevolverDinero = cantidadRecibida - comandasController.totalPrice;
                    aDevolver.setText(String.format("%.2f", aDevolverDinero)); // Muestra solo hasta dos decimales
                } catch (NumberFormatException e) {
                    // Manejar la excepción si el valor ingresado no es un número válido
                    aDevolver.setText("0.00");
                }
            } else {
                aDevolver.setText("0.00");
            }
        });
    }
}
