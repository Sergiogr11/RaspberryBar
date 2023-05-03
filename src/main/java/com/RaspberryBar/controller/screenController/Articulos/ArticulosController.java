package com.RaspberryBar.controller.screenController.Articulos;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.view.FxmlView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class ArticulosController implements Initializable {

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private void entrarListadoArticulos(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LISTAARTICULOS);
    }

    @FXML
    private void entrarListadoCategorias(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.LISTACATEGORIAS);
    }

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
