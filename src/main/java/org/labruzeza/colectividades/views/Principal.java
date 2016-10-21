package org.labruzeza.colectividades.views;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Principal extends Application {
	@FXML
	private static Scene scene;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Principal");
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());
			
		primaryStage.setScene(scene);
		primaryStage.resizableProperty().set(false);
		primaryStage.show();
		
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent event) {
            	System.exit(0);
            }
        });
	}
	
	public static void iniciar(){
		String[] args = {};
		launch(args);
	}

	public static void main(String[] args) {
		Principal.iniciar();
	}

}
