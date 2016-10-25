package org.labruzeza.colectividades.view;

import java.io.IOException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

public class PanelConfiguracionGeneral extends AnchorPane {	
	@FXML
	private TabPane tabPane;	

	
	public PanelConfiguracionGeneral() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.setResources(ResourceBundle.getBundle("i18n.ValidationMessages"));
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		this.setWidth(bounds.getWidth());
		this.setHeight(bounds.getHeight());
        
        PanelConfiguracion pnlConf = new PanelConfiguracion();        
        Tab tabConf= new Tab("Configuración");
        tabConf.setContent(pnlConf);
        tabPane.getTabs().add(tabConf);
       
        Tab tabProductos= new Tab("Productos");
        PanelGrillaProducto pnlProductos = new PanelGrillaProducto(tabProductos);        
        tabProductos.setContent(pnlProductos);
        tabPane.getTabs().add(tabProductos);
    }

}