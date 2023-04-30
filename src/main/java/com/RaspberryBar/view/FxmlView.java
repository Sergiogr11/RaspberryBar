package com.RaspberryBar.view;

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
    },
    ;

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
