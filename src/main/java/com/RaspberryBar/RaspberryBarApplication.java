package com.RaspberryBar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class RaspberryBarApplication extends Application {

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void start(Stage stage) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
		loader.setControllerFactory(applicationContext::getBean);

		Parent p = ((FXMLLoader) loader.getRoot()).load();
		stage.setScene(new Scene(p));
		stage.show();
		/*
		Parent root = loader.load();
		Scene scene = new Scene(root);

		stage.setTitle("RaspberryBar");
		stage.setScene(scene);
		stage.show();
		 */
	}

	public static void main(String[] args) {
		SpringApplication.run(RaspberryBarApplication.class, args);
	}
}
