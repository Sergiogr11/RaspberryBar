package com.RaspberryBar.view;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class CustomAlert extends Alert {
    public CustomAlert(AlertType alertType) {
        super(alertType);

        // Agrega la clase CSS personalizada a la ventana de diálogo
        getDialogPane().getStyleClass().add("custom-alert");

        // Obtener el botón de acción predeterminado
        ButtonType defaultButton = getButtonTypes().stream()
                .filter(buttonType -> buttonType.getButtonData() == ButtonBar.ButtonData.OK_DONE)
                .findFirst()
                .orElse(null);

        Node defaultButtonNode = getDialogPane().lookupButton(defaultButton);
        defaultButtonNode.getStyleClass().add("button");

        Label contentLabel = new Label(getContentText());
        contentLabel.getStyleClass().add("content-label");
        getDialogPane().setContent(contentLabel);

        getDialogPane().getStylesheets().add(getClass().getResource("/styles/Styles.css").toExternalForm());
    }
}
