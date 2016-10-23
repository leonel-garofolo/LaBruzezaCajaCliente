package org.labruzeza.colectividades.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.labruzeza.colectividades.dao.ProductoDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.ProductoDAOImpl;
import org.labruzeza.colectividades.modelo.Producto;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PanelGrillaProducto extends PanelControlesABM implements Initializable, EventHandler<ActionEvent> {
	private ProductoDAO productoServicio;
	private Node top;
	private Node center;
	private Node bottom;
	private Tab tab;

	@FXML
	VBox pnlBotones;

	@FXML
	HBox hButtonFilter;

	@FXML
	private BorderPane pnlBorder;	

	@FXML
	private Button btnBuscar;
	
	@FXML
	private Button btnLimpiar;

	private ObservableList<Producto> data;
	
	@FXML	
	private TableView<Producto> tblProducto;

	@FXML
	private TextField txtidproducto;
	@FXML
	private TextField txtnombre;
	
	public PanelGrillaProducto(Tab tab) {
		this.tab = tab;
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(this.getClass().getSimpleName() + ".fxml"));
		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);
		fxmlLoader.setResources(ResourceBundle.getBundle("i18n.ValidationMessages"));
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

	public void initialize(URL location, ResourceBundle resources) {
		productoServicio = new ProductoDAOImpl();
		tblProducto.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	btnEditarAction();          
		        }
		    }
		});
		loadGrilla();

		pnlBotones.getChildren().add(0, generarPanel());
		pnlBorder.setPadding(new Insets(10, 0, 0, 0));	
		this.btnAgregar.setOnAction(this);
		this.btnEditar.setOnAction(this);
		this.btnEliminar.setOnAction(this);
		this.btnBuscar.setOnAction(this);
		this.btnLimpiar.setOnAction(this);
	}
	
	public void loadGrilla(){
		try {
			data = FXCollections.observableArrayList(productoServicio.loadAll());
			tblProducto.setItems(data);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	

	public PanelGrillaProducto getController() {
		return this;
	}	

	public TableView<Producto> getTblProducto() {
		return tblProducto;
	}

	public Tab getTab() {
		return tab;
	}

    public void reLoad() {
		tab.setContent(this);
		loadGrilla();
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource().equals(btnBuscar)) {	
			loadGrilla();
			
			ObservableList<Producto> filter= data;

			if(!txtidproducto.getText().trim().equals("")){
				filter=filter.filtered(p -> p.getIdproducto() != null && p.getIdproducto() == Integer.valueOf(txtidproducto.getText()));
			}
			if(!txtnombre.getText().trim().equals("")){
				filter= filter.filtered(p -> p.getNombre() != null && p.getNombre().contains(txtnombre.getText()));
			}
			tblProducto.setItems(new SortedList<>(filter));							
		}
		
		if (event.getSource().equals(btnLimpiar)) {			
			for(Node node: hButtonFilter.getChildren()){
				if(node instanceof TextField){
					((TextField)node).setText("");
				}								
			}		
			tblProducto.setItems(data);							
		}

		if (event.getSource().equals(btnAgregar)) {
			new PanelProducto(PanelGrillaProducto.this);
		}
		if (event.getSource().equals(btnEditar)) {
			btnEditarAction();					
		}
		if (event.getSource().equals(btnEliminar)) {
			
			Producto itemSelected = tblProducto.getSelectionModel().getSelectedItem();
			if(itemSelected != null){
				try {
					productoServicio.delete(itemSelected);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			loadGrilla();
		}
	}
	
	private void btnEditarAction(){
		int itemSelected = tblProducto.getSelectionModel().getSelectedItem().getIdproducto();
		if(itemSelected > 0){
			new PanelProducto(PanelGrillaProducto.this, itemSelected);
		}
	}
}