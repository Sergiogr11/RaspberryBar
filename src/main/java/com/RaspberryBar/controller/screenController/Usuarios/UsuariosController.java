package com.RaspberryBar.controller.screenController.Usuarios;

import com.RaspberryBar.config.StageManager;
import com.RaspberryBar.entities.Usuario;
import com.RaspberryBar.repository.UsuarioRepository;
import com.RaspberryBar.service.UsuarioService;
import com.RaspberryBar.view.CustomAlert;
import com.RaspberryBar.view.FxmlView;
import com.jfoenix.controls.*;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RegexValidator;
import com.jfoenix.validation.RequiredFieldValidator;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class UsuariosController implements Initializable {


    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @FXML
    private JFXListView<String> listaUsuarios;

    private ObservableList<String> selectedUsuarios;
    private List<String> usernameList = new ArrayList<>();
    static Usuario usuarioEditar = new Usuario();

    @FXML
    private void volver(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.HOME);
    }

    @FXML
    private void eliminar(ActionEvent event) throws IOException {
        //Obtengo el usuario seleccionado y su username
        selectedUsuarios = listaUsuarios.getSelectionModel().getSelectedItems();

        //Busco en la base datos el usuario seleccionado
        Usuario usuarioEliminar = usuarioService.findUsuario(selectedUsuarios.get(0));

        //Muestro alert de usuario eliminado
        CustomAlert alertCrearArticulo = new CustomAlert(Alert.AlertType.ERROR);
        alertCrearArticulo.setTitle("Usuario eliminado");
        alertCrearArticulo.setHeaderText("Usuario satisfactoriamente eliminado");
        alertCrearArticulo.showAndWait();
        
        //Borro el usuario seleccionado
        usuarioService.deleteUsuario(usuarioEliminar);
        actualizarListView();
    }

    @FXML
    private void editar(ActionEvent event) throws IOException {
        //Obtengo el usuario seleccionado y su username
        selectedUsuarios = listaUsuarios.getSelectionModel().getSelectedItems();
        //Busco en la base datos el usuario seleccionado
        usuarioEditar = usuarioService.findUsuario(selectedUsuarios.get(0));
        //Paso como parametro el usuarioEditar
        stageManager.switchScene(FxmlView.EDITARUSUARIO);
    }

    @FXML
    private void crear(ActionEvent event) throws IOException {
        stageManager.switchScene(FxmlView.REGISTRO);
    }


    private void actualizarListView(){
        //Borro elementos antiguos de la lista
        usernameList.clear();
        //Obtengo los usuarios y sus usernames para inicializar la ListView
        List<Usuario> userList = usuarioService.readUsuarios();

        for (Usuario user : userList) {
            String username = user.getUsername();
            usernameList.add(username);
        }

        listaUsuarios.getItems().clear();
        listaUsuarios.getItems().addAll(usernameList);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        actualizarListView();
    }

}

