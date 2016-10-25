package org.labruzeza.colectividades.view;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javafx.controls.customs.DecimalField;
import org.javafx.controls.customs.NumberField;
import org.labruzeza.colectividades.dao.ConfiguracionDAO;
import org.labruzeza.colectividades.dao.LineadeventaDAO;
import org.labruzeza.colectividades.dao.ProductoDAO;
import org.labruzeza.colectividades.dao.VentaDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.ConfiguracionDAOImpl;
import org.labruzeza.colectividades.dao.impl.jdbc.LineadeventaDAOImpl;
import org.labruzeza.colectividades.dao.impl.jdbc.ProductoDAOImpl;
import org.labruzeza.colectividades.dao.impl.jdbc.VentaDAOImpl;
import org.labruzeza.colectividades.informes.Ticket;
import org.labruzeza.colectividades.modelo.Configuracion;
import org.labruzeza.colectividades.modelo.Lineadeventa;
import org.labruzeza.colectividades.modelo.Producto;
import org.labruzeza.colectividades.modelo.Venta;
import org.labruzeza.colectividades.utils.PrinterJob;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JasperPrint;

public class Principal extends AnchorPane implements EventHandler<ActionEvent>{
	private ProductoDAO productoDAO;
	private VentaDAO ventaDAO;
	private LineadeventaDAO lineadeventaDAO;
	private ConfiguracionDAO configuracionDAO;
	
	private Configuracion configuracion;
	
	@FXML
	private static Scene scene;
	
	@FXML
	private BorderPane borderPane;
	
	@FXML
	private GridPane gridPane;	
	
	
	@FXML
	private Button btnConfig;
	
	@FXML
	private Button btnSiguiente;
	
	@FXML
	private Button btnNuevo;
	
	@FXML
	private Button btnVolverPrimero;	
	
	@FXML
	private Button btnTicket;
	
	@FXML
	private Button btnCajaDiaria;	
	
	@FXML
	private DecimalField txtTotal;
	
	@FXML
	private Label lblTotal;
	
	@FXML
	private Label lblCaja;
	
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
        
        Screen screen = Screen.getPrimary();
		Rectangle2D bounds = screen.getVisualBounds();
		this.setWidth(bounds.getWidth());
		this.setHeight(bounds.getHeight());		
        
        Image imageDecline = new Image(getClass().getResourceAsStream("/image/settings.png"));
		btnConfig.setGraphic(new ImageView(imageDecline));
		btnConfig.setFocusTraversable(false);
		btnConfig.setOnAction(this);
		btnSiguiente.setOnAction(this);
		btnNuevo.setOnAction(this);
		btnVolverPrimero.setOnAction(this);
		btnTicket.setOnAction(this);
		txtTotal.setValue(new BigDecimal(0));
		lblTotal.setStyle(""
		        + "-fx-font-size: 14px;"		       			       
		        + "-fx-text-fill: red;");
				
		productoDAO = new ProductoDAOImpl();
		ventaDAO = new VentaDAOImpl();
		lineadeventaDAO = new LineadeventaDAOImpl();		
		loadPage();
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
		if(event.getSource().equals(btnTicket)){
			SimpleDateFormat format = new SimpleDateFormat("dd");
			Venta venta = new Venta();
			venta.setFecha(new Date());
			venta.setCodigo(format.format(new Date()) + "1A");
			int id= ventaDAO.insert(venta);
			venta.setIdventa(id);
			
			Lineadeventa linea = new Lineadeventa();	
			linea.setIdventa(id);
			List<Lineadeventa> listOfLineadeventa = new ArrayList<Lineadeventa>();
			for (Node child : this.gridPane.getChildrenUnmodifiable()) {	
				
				if(child.getId() != null && child.getId().contains("txtIdProducto")){
					linea.setIdproducto(((NumberField)child).getValue());
				}
				if(child.getId() != null && child.getId().contains("txtCant")){
					linea.setCantidad(((NumberField)child).getValue());
				}
				if(child.getId() != null && child.getId().contains("txtPrecio")){
					linea.setPrecio(((DecimalField)child).getValue());					
				}
				if(linea.getIdproducto() != null && linea.getCantidad() != null && linea.getPrecio() != null){
					lineadeventaDAO.insert(linea);
					Producto prod = new Producto();
					prod.setIdproducto(linea.getIdproducto());
					if(productoDAO.load(prod)){
						linea.setProducto(prod);
					}
					
					listOfLineadeventa.add(linea);
					linea = new Lineadeventa();	
					linea.setIdventa(id);
				}				
			}						
			venta.setListOfLineadeventa(listOfLineadeventa);
			Ticket ticket = new Ticket();
			JasperPrint print = ticket.generar(venta);
			PrinterJob.sendPDF(print);
		}		
	}
	
	private void loadPage(){
		gridPane.getChildren().removeAll(gridPane.getChildren());
		configuracionDAO = new ConfiguracionDAOImpl();
		configuracion = new Configuracion();
		configuracion.setIdconfiguracion(1);
		txtTotal.setStyle(""
		        + "-fx-font-size: 18px;"
		        + "-fx-font-weight: bold;"			        
		        + "-fx-text-fill: red;");
		
		if(configuracionDAO.load(configuracion)){
			lblCaja.setFont(new Font("MS Sans Serif", 18));		
			lblCaja.setStyle(""	       			       
			        + "-fx-font-weight: bold;"
			        + "-fx-text-fill: red;");
			SimpleDateFormat format = new SimpleDateFormat("dd");			
			lblCaja.setText("Cod. Caja: " + format.format(configuracion.getFecha()) + configuracion.getNrocaja() + configuracion.getTipocaja());
			
			ObservableList<Producto> data = FXCollections.observableArrayList(productoDAO.loadAll());
			int i= 1;
			for(Producto unProd: data){
				Label lblProd = new Label(unProd.getNombre() + ":");
				lblProd.setFont(new Font("MS Sans Serif", 14));			
				NumberField txtCant = new NumberField();
				txtCant.setId("txtCant" + i);
				txtCant.setPrefWidth(47);
				txtCant.setMaxValue(2);
				txtCant.setValue(0);
				txtCant.setStyle("-fx-font-size: 14px;");
				
				DecimalField txtPrecio = new DecimalField();
				txtPrecio.setId("txtPrecio" + i);
				txtPrecio.setPrefWidth(47);			
				txtPrecio.setEditable(false);
				txtPrecio.setValue(new BigDecimal(0));
				txtPrecio.setStyle(""
				        + "-fx-font-size: 14px;"
				        + "-fx-font-weight: bold;"			        
				        + "-fx-text-fill: red;");
				
				NumberField txtIdProducto = new NumberField();
				txtIdProducto.setId("txtIdProducto" + i);
				txtIdProducto.setPrefWidth(47);
				txtIdProducto.setEditable(false);
				txtIdProducto.setVisible(false);
				txtIdProducto.setValue(unProd.getIdproducto());			
				
				DecimalField txtPrecioInvisible = new DecimalField();
				txtPrecioInvisible.setId("txtInvisiblePrecio" + i);
				txtPrecioInvisible.setPrefWidth(47);
				txtPrecioInvisible.setEditable(false);
				txtPrecioInvisible.setVisible(false);
				txtPrecioInvisible.setValue(unProd.getPrecio());
							
				txtCant.setOnAction((ActionEvent e) -> {				
					double subTotal = txtCant.getValue() * txtPrecioInvisible.getValue().doubleValue();
					txtPrecio.setValue(new BigDecimal(subTotal));
					
					double total = 0;
					for (Node child : this.gridPane.getChildrenUnmodifiable()) {					
						if(child.getId() != null && child.getId().contains("txtPrecio")){
							total += ((DecimalField)child).getValue().doubleValue();
						}
					}
					txtTotal.setValue(new BigDecimal(total));
				    boolean isThisField = false;
				    for (Node child : this.gridPane.getChildrenUnmodifiable()) {			    	
				    	if (isThisField) {

				            //This code will only execute after the current Node
				            if (child.isFocusTraversable() && !child.isDisabled()) {
				            	if(child.getClass().equals(NumberField.class) && ((NumberField)child).isEditable()){
				            		 child.requestFocus();

						             //Reset check to prevent later Node from pulling focus
						             isThisField = false;
				            	}
				            }
				        } else {

				            //Check if this is the current Node
				            isThisField = child.equals(txtCant);
				        }
				    }
				  //Check if current Node still has focus
				    boolean focusChanged = !txtCant.isFocused();
				    if (!focusChanged) {
				        for (Node child : this.gridPane.getChildrenUnmodifiable()) {
				            if (!focusChanged && child.isFocusTraversable() && !child.isDisabled()) {
				                child.requestFocus();

				                //Update to prevent later Node from pulling focus
				                focusChanged = true;
				            }
				        }
				    }
				});	
				
				gridPane.add(lblProd, 0, i);
				gridPane.add(txtCant, 1, i);
				gridPane.add(txtPrecio, 2, i);
				gridPane.add(txtPrecioInvisible, 3, i);
				gridPane.add(txtIdProducto, 4, i);									
				i++;
			}
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
        stage.setOnHiding(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent event) {            	
            	loadPage();
            }
        });	
	}
}
