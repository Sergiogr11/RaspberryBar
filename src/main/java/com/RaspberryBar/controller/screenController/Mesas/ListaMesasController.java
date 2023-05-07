package com.RaspberryBar.controller.screenController.Mesas;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Categoria;
import com.RaspberryBar.entities.Mesa;
import com.RaspberryBar.service.CategoriaService;
import com.RaspberryBar.service.MesaService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
public class ListaMesasController implements Initializable {


    @Autowired
    private MesaService mesaService;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private JFXListView<String> listaMesas;
    @FXML
    private JFXComboBox<String> comboBoxPosicion;

    private ObservableList<String> selectedMesa;
    private List<String> mesasList = new ArrayList<>();
    static Mesa mesaEditar = new Mesa();


    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.MESAS);
    }

    @FXML
    private void eliminarMesa(ActionEvent event) throws IOException {
        //Obtengo la mesa seleccionada
        selectedMesa = listaMesas.getSelectionModel().getSelectedItems();

        //Obtengo el id correspondiente al nombre de la mesa seleccionada
        int idMesa = mesaService.findIdByNombreMesa(selectedMesa.get(0));

        //Busco en la base datos la mesa seleccionada
        Mesa mesaEliminar = mesaService.findMesa(idMesa);

        //Muestro mensaje de mesa eliminada
        CustomAlert alertEliminarCategoria = new CustomAlert(Alert.AlertType.INFORMATION);
        alertEliminarCategoria.setTitle("Mesa eliminada");
        alertEliminarCategoria.setHeaderText("Mesa satisfactoriamente eliminada");
        alertEliminarCategoria.showAndWait();

        //Borro la categoria seleccionada
        mesaService.deleteMesa(mesaEliminar);
        comboBoxPosicion.getSelectionModel().clearSelection();
        actualizarListView();
    }

    @FXML
    private void editarMesa(ActionEvent event) throws IOException {
        //Obtengo la mesa seleccionada
        selectedMesa = listaMesas.getSelectionModel().getSelectedItems();

        //Busco en la base datos la mesa seleccionada
        int idMesa = mesaService.findIdByNombreMesa(selectedMesa.get(0));
        mesaEditar = mesaService.findMesa(idMesa);

        //Cambio de escena
        //TODO stageManager.switchScene(FxmlView.EDITARMESA);
    }

    @FXML
    private void crearMesa(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.CREARMESA);
    }


    private void actualizarListView(){
        mesasList = mesaService.readMesasName();

        listaMesas.getItems().clear();
        listaMesas.getItems().addAll(mesasList);
    }

    private void actualizarComboBox(){
        //Seteo el nombre de las posiciones
        List<String> nombresPosiciones = Arrays.asList("Restaurante", "Bar", "Terraza");
        //Añado el nombre de las posiciones al ComboBox
        comboBoxPosicion.setItems(FXCollections.observableList(nombresPosiciones));
    }

    private void actionComboBox(){
        comboBoxPosicion.setOnAction(event -> {
            //Obtengo la mesa seleccionada y el indice correspondiente a la misma
            String selectedPosicion = comboBoxPosicion.getSelectionModel().getSelectedItem();

            //Limpio la lista de articulos y añado solo las correspondientes a la categoria
            mesasList.clear();
            mesasList = mesaService.findNombreMesaByPosicion(selectedPosicion);
            listaMesas.getItems().clear();
            listaMesas.getItems().addAll(mesasList);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualizarListView();
        actualizarComboBox();
        actionComboBox();
    }

}

