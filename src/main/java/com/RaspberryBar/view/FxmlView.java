package com.RaspberryBar.view;

import com.RaspberryBar.entities.Usuario;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import javax.print.DocFlavor;
import javax.swing.text.View;
import java.util.ResourceBundle;


public enum FxmlView {

    HOME {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("home.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/home.fxml";
        }

        @Override
        public FxmlView getView() {
            return this;
        }
    },

    LOGIN {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/login.fxml";
        }

        @Override
        public FxmlView getView() {
            return this;
        }
    },
    REGISTRO {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("registro.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/registro.fxml";
        }

        @Override
        public FxmlView getView() {
            return this;
        }
    },
    USUARIOS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("usuarios.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/usuarios.fxml";
        }

        @Override
        public FxmlView getView() {
            return this;
        }
    },
    EDITARUSUARIO {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("editarUsuario.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/editarUsuario.fxml";
        }

        public FxmlView getView(){
            return this;
        }

    },
    ;

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }


    public abstract FxmlView getView();
}
