package org.labruzeza.colectividades.view;

import java.io.IOException;
import java.util.ResourceBundle;

import org.javafx.controls.customs.DecimalField;
import org.javafx.controls.customs.NumberField;
import org.javafx.controls.customs.StringField;
import org.labruzeza.colectividades.modelo.Producto;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PanelProducto extends BorderPane implements EventHandler<ActionEvent>{
	private boolean modoEdit = false;
	private PanelGrillaProducto father;
	
	@FXML
	private VBox vBoxMsg;


	@FXML
	private NumberField txtidproducto;

	@FXML
	private StringField txtnombre;

	@FXML
	private DecimalField txtprecio;

	public PanelProducto(PanelGrillaProducto father) {
		this.modoEdit = false;
		this.father = father;
		initComponentes();
    }

	public PanelProducto(PanelGrillaProducto father, int id) {
		this.modoEdit = true;
		this.father = father;
		initComponentes();
		loadEntity(id);		
    }

	private void loadEntity(int id) {
		try {
			Producto unProducto = new Producto();
			unProducto.setIdproducto(id);	
			boolean eProducto =father.getService().load(unProducto);
			if(eProducto){
				loadForm(unProducto);
			}else{
				throw new Exception();
			}
			
		} catch (Exception e) {
			Label label = new Label();
	    	label.setText("Se ha producido un error en el servidor. Intente mas tarde.");
	    	vBoxMsg.getChildren().addAll(label);
			e.printStackTrace();
		}
	}

	private void initComponentes(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.setResources(ResourceBundle.getBundle("i18n.ValidationMessages"));

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        
        this.setTop(father.generarPanelFormulario());
        this.setLeft(null);
        this.setRight(null);
        father.btnGuardar.setOnAction(this);        
        father.btnCancelar.setOnAction(this);
        father.getTab().setContent(this);
        
	}

	public void loadForm(Producto producto){
		if(producto !=null){
			if(producto.getIdproducto() != null){
				txtidproducto.setText(String.valueOf(producto.getIdproducto()));
			}
			if(producto.getNombre() != null){
				txtnombre.setText(producto.getNombre());
			}
			if(producto.getPrecio() != null){
				txtprecio.setText(String.valueOf(producto.getPrecio()));
			}
		}
	}

	private Producto getProducto() {
		Producto unProducto = new Producto();
		try{
			unProducto.setIdproducto(Integer.valueOf(txtidproducto.getText()));
		}catch (NumberFormatException e) {
			unProducto.setIdproducto(null);
		}
		unProducto.setNombre(txtnombre.getText());
		unProducto.setPrecio(txtprecio.getValue());
				
		vBoxMsg.getChildren().clear();		
		return unProducto;
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(father.btnGuardar)){
			Producto unProducto = getProducto();
			if(unProducto != null){
				try {
					if(modoEdit){
						father.getService().update(unProducto);
					}else{
						father.getService().insert(unProducto);
					}
					
					father.reLoad();    
				} catch (Exception e) {
					Label label = new Label();
			    	label.setText("Se ha producido un error en el servidor. Intente mas tarde.");
			    	vBoxMsg.getChildren().addAll(label);
					e.printStackTrace();			
				}
			}		
		}
		if(event.getSource().equals(father.btnCancelar)){
			father.reLoad();    
		}
	}
}