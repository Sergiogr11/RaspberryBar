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
    EDITARUSUARIO {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("editarUsuario.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/editarUsuario.fxml";
        }
    },
    ARTICULOS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("articulos.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/articulos.fxml";
        }
    },
    LISTAARTICULOS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("listaArticulos.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/listaArticulos.fxml";
        }

    },
    LISTACATEGORIAS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("listaCategorias.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/listaCategorias.fxml";
        }
    },
    CREARCATEGORIA {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("crearCategoria.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/crearCategoria.fxml";
        }
    },
    EDITARCATEGORIA {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("editarCategoria.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/editarCategoria.fxml";
        }
    },
    CREARARTICULO {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("crearArticulo.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/crearArticulo.fxml";
        }
    },
    EDITARARTICULO {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("editarArticulo.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/editarArticulo.fxml";
        }
    },
    MESAS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("mesas.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/mesas.fxml";
        }
    },
    LISTAMESAS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("listaMesas.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/listaMesas.fxml";
        }
    },
    CREARMESA {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("crearMesa.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/crearMesa.fxml";
        }
    },
    EDITARMESA {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("editarMesa.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/editarMesa.fxml";
        }
    },
    COMANDAS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("comandas.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/comandas.fxml";
        }
    },
    COBRAR {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("cobrar.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/cobrar.fxml";
        }
    },
    CONFIGURACION {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("configuracion.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/configuracion.fxml";
        }
    },
    CONFIGURACIONRTE {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("configuracionRestaurante.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/configuracionRestaurante.fxml";
        }
    },
    BALANCEVENTAS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("balanceVentas.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/balanceVentas.fxml";
        }
    },
    CREARFACTURAS {
        @Override
        public String getTitle() {
            return getStringFromResourceBundle("crearFacturas.title");
        }

        @Override
        public String getFxmlFile() {
            return "/fxml/crearFacturas.fxml";
        }
    }
    ;

    public abstract String getTitle();
    public abstract String getFxmlFile();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }
}
