package org.labruzeza.colectividades.views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.javafx.controls.customs.NumberField;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Principal extends Application implements Initializable {
	@FXML
	private static Scene scene;
	
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private GridPane gridPane;	
	
	@FXML
	private Label lbl1;
	@FXML
	private Label lbl2;
	@FXML
	private Label lbl3;
	@FXML
	private Label lbl4;
	@FXML
	private Label lbl5;
	@FXML
	private Label lbl6;
	@FXML
	private Label lbl7;
	@FXML
	private Label lbl8;
	@FXML
	private Label lbl9;
	@FXML
	private Label lbl10;
	@FXML
	private Label lbl11;
	@FXML
	private Label lbl12;
	@FXML
	private Label lbl13;
	@FXML
	private Label lbl14;
	@FXML
	private Label lbl15;
	@FXML
	private Label lbl16;
	@FXML
	private Label lbl17;
	@FXML
	private Label lbl18;
	
	@FXML
	private NumberField txtCant1;
	@FXML
	private NumberField txtCant2;
	@FXML
	private NumberField txtCant3;
	@FXML
	private NumberField txtCant4;
	@FXML
	private NumberField txtCant5;
	@FXML
	private NumberField txtCant6;
	@FXML
	private NumberField txtCant7;
	@FXML
	private NumberField txtCant8;
	@FXML
	private NumberField txtCant9;
	@FXML
	private NumberField txtCant10;
	@FXML
	private NumberField txtCant11;
	@FXML
	private NumberField txtCant12;
	@FXML
	private NumberField txtCant13;
	@FXML
	private NumberField txtCant14;
	@FXML
	private NumberField txtCant15;
	@FXML
	private NumberField txtCant16;
	@FXML
	private NumberField txtCant17;
	@FXML
	private NumberField txtCant18;
	
	
	@FXML
	private TextField txtPrecio1;
	@FXML
	private TextField txtPrecio2;
	@FXML
	private TextField txtPrecio3;
	@FXML
	private TextField txtPrecio4;
	@FXML
	private TextField txtPrecio5;
	@FXML
	private TextField txtPrecio6;
	@FXML
	private TextField txtPrecio7;
	@FXML
	private TextField txtPrecio8;
	@FXML
	private TextField txtPrecio9;
	@FXML
	private TextField txtPrecio10;
	@FXML
	private TextField txtPrecio11;
	@FXML
	private TextField txtPrecio12;
	@FXML
	private TextField txtPrecio13;
	@FXML
	private TextField txtPrecio14;
	@FXML
	private TextField txtPrecio15;
	@FXML
	private TextField txtPrecio16;
	@FXML
	private TextField txtPrecio17;
	@FXML
	private TextField txtPrecio18;

	private AnchorPane myPane;
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Principal");
		
		Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();

		primaryStage.setX(bounds.getMinX());
		primaryStage.setY(bounds.getMinY());
		primaryStage.setWidth(bounds.getWidth());
		primaryStage.setHeight(bounds.getHeight());		
		loadPage();
		primaryStage.setScene(scene);
		primaryStage.resizableProperty().set(false);
		primaryStage.show();
		
		primaryStage.setOnHiding(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent event) {
            	System.exit(0);
            }
        });
	}
	
	
	@SuppressWarnings("static-access")
	private void loadPage() throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		this.myPane = (AnchorPane) fxmlLoader.load();					
		this.scene = new Scene(myPane);
	}
	
	public static void iniciar(){
		String[] args = {};
		launch(args);
	}

	public static void main(String[] args) {
		Principal.iniciar();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {		
	}

}
