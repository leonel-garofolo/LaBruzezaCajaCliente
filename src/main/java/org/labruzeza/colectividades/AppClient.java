package org.labruzeza.colectividades;

import org.labruzeza.colectividades.view.Principal;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AppClient extends Application {

	private Principal principalPane;
	private Scene scene;

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("La Bruzeza");
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());		
		this.principalPane = new Principal();					
		this.scene = new Scene(principalPane);				
		
		primaryStage.setScene(scene);
		primaryStage.resizableProperty().set(false);
		primaryStage.setMaximized(true);
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
}