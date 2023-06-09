package com.RaspberryBar.config;

import com.RaspberryBar.view.FxmlView;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Objects;

/**
 * Manages switching Scenes on the Primary Stage
 */
public class StageManager {
    private final Stage primaryStage;

    @Autowired
    private final SpringFXMLLoader springFXMLLoader;

    public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage) {
        this.springFXMLLoader = springFXMLLoader;
        this.primaryStage = stage;
    }

    public void switchScene(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        show(viewRootNodeHierarchy, view.getTitle());
        setFullScreen();
    }

    private void show(final Parent rootnode, String title) {

        if (rootnode == null) {
            logAndExit("Unable to load FXML view", new IOException("FXML file is null"));
            return;
        }

        Scene scene = prepareScene(rootnode);

        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        setFullScreen();

        try {
            primaryStage.show();
        } catch (Exception exception) {
            logAndExit ("Unable to show scene for title" + title,  exception);
        }
    }

    private void setFullScreen() {
        primaryStage.setFullScreen(true);
    }

    private Scene prepareScene(Parent rootnode){
        Scene scene = primaryStage.getScene();

        if (scene == null) {
            scene = new Scene(rootnode);
        }
        scene.setRoot(rootnode);

       return scene;
    }

    /**
     * Loads the object hierarchy from a FXML document and returns to root node
     * of that hierarchy.
     *
     * @return Parent root node of the FXML document hierarchy
     */

    private Parent loadViewNodeHierarchy(String fxmlFilePath) {
        Parent rootNode = null;
        try {
            rootNode = springFXMLLoader.load(fxmlFilePath);
            Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
        } catch (Exception exception) {
            logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
        }
        return rootNode;
    }


    private void logAndExit(String errorMsg, Exception exception) {
        Platform.exit();
    }

    public void showPopup(final FxmlView view) {
        Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.getFxmlFile());
        showPopup(viewRootNodeHierarchy, view.getTitle());
    }

    private void showPopup(final Parent rootnode, String title) {
        if (rootnode == null) {
            logAndExit("Unable to load FXML view", new IOException("FXML file is null"));
            return;
        }

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle(title);

        Scene scene = new Scene(rootnode);
        popupStage.setScene(scene);

        try {
            popupStage.showAndWait();
        } catch (Exception exception) {
            logAndExit("Unable to show popup for title" + title, exception);
        }
    }

}

