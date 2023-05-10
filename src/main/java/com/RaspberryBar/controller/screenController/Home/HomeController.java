package com.RaspberryBar.controller.screenController.Home;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class HomeController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void entrarLogout(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LOGIN);
    }

    @FXML
    private void entrarComandas(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.COMANDAS);
    }

    @FXML
    private void entrarArticulos(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.ARTICULOS);
    }

    @FXML
    private void entrarFacturacion(ActionEvent event) throws IOException {
        //stageManager.switchScene(FxmlView.FACTURACION);
    }

    @FXML
    private void entrarRegistrar(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.USUARIOS);
    }

    @FXML
    private void entrarMesas(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.MESAS);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
