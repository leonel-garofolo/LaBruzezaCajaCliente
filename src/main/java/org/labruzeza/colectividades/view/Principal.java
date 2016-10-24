package org.labruzeza.colectividades.view;

import java.io.IOException;

import org.javafx.controls.customs.NumberField;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Principal extends AnchorPane implements EventHandler<ActionEvent>{
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
	
	@FXML
	private Button btnConfig;
	
	@FXML
	private Button btnSiguiente;
	
	@FXML
	private Button btnNuevo;
	
	@FXML
	private Button btnVolverPrimero;			
	
 	public Principal(){
 		initComponentes();
 	}
	
 	private void initComponentes(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        Image imageDecline = new Image(getClass().getResourceAsStream("/image/settings.png"));
		btnConfig.setGraphic(new ImageView(imageDecline));	
		btnConfig.setOnAction(this);
		btnSiguiente.setOnAction(this);
		btnNuevo.setOnAction(this);
		btnVolverPrimero.setOnAction(this);
 	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(btnConfig)){
			openConfiguracion(event);
		}
		if(event.getSource().equals(btnSiguiente)){
					
		}
		if(event.getSource().equals(btnNuevo)){
			
		}
		if(event.getSource().equals(btnVolverPrimero)){
			
		}
		
	}
	
	private void openConfiguracion(ActionEvent event){
		PanelConfiguracionGeneral pnlConf = new PanelConfiguracionGeneral();	
        Scene scene = new Scene(pnlConf);
        Stage stage = new Stage();
        stage.setTitle("Panel de Configuración");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(this.getScene().getWindow());
        stage.setScene(scene);
        stage.show();
	}
}
