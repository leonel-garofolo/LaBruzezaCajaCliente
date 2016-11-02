package org.labruzeza.colectividades.view;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javafx.controls.customs.DecimalField;
import org.javafx.controls.customs.NumberField;
import org.labruzeza.colectividades.dao.ConfiguracionDAO;
import org.labruzeza.colectividades.dao.LineadeventaDAO;
import org.labruzeza.colectividades.dao.ProductoDAO;
import org.labruzeza.colectividades.dao.VcajaDAO;
import org.labruzeza.colectividades.dao.VentaDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.ConfiguracionDAOImpl;
import org.labruzeza.colectividades.dao.impl.jdbc.LineadeventaDAOImpl;
import org.labruzeza.colectividades.dao.impl.jdbc.ProductoDAOImpl;
import org.labruzeza.colectividades.dao.impl.jdbc.VcajaDAOImpl;
import org.labruzeza.colectividades.dao.impl.jdbc.VentaDAOImpl;
import org.labruzeza.colectividades.informes.CajaDiaria;
import org.labruzeza.colectividades.informes.Ticket;
import org.labruzeza.colectividades.modelo.Configuracion;
import org.labruzeza.colectividades.modelo.Lineadeventa;
import org.labruzeza.colectividades.modelo.Producto;
import org.labruzeza.colectividades.modelo.Vcaja;
import org.labruzeza.colectividades.modelo.Venta;
import org.labruzeza.colectividades.utils.MiPrinterJob;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.sf.jasperreports.engine.JasperPrint;

public class Principal extends AnchorPane implements EventHandler<ActionEvent>{
	private static final Logger logger = LogManager.getLogger(MiPrinterJob.class);
	 
	private ProductoDAO productoDAO;
	private VentaDAO ventaDAO;
	private LineadeventaDAO lineadeventaDAO;
	private ConfiguracionDAO configuracionDAO;
	private VcajaDAO vcajaDAO;
	
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
	
	@FXML
	private Label lblNroFactura;
	
	@FXML
	private TextField txtNroFactura;
	
	private static String codigoCaja = "";
	
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
		btnConfig.setFocusTraversable(false);
		btnConfig.setOnAction(this);
		btnSiguiente.setOnAction(this);
		btnNuevo.setOnAction(this);
		btnVolverPrimero.setOnAction(this);
		btnTicket.setOnAction(this);
		btnCajaDiaria.setOnAction(this);
		txtTotal.setValue(new BigDecimal(0));
		lblTotal.setStyle(""
		        + "-fx-font-size: 14px;"		       			       
		        + "-fx-text-fill: red;");
				
		productoDAO = new ProductoDAOImpl();
		ventaDAO = new VentaDAOImpl();
		lineadeventaDAO = new LineadeventaDAOImpl();
		vcajaDAO = new VcajaDAOImpl();
		loadPage();
 	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(btnConfig)){
			openConfiguracion(event);
		}
		if(event.getSource().equals(btnSiguiente)){
			loadUltimo();
		}
		if(event.getSource().equals(btnNuevo)){
			loadPage();
		}
		if(event.getSource().equals(btnVolverPrimero)){
			loadAnterior();
		}
		
		
		if(event.getSource().equals(btnTicket)){
			generarTicket();	
		}
		
		if(event.getSource().equals(btnCajaDiaria)){
			generarCajaDiaria();	
		}
	}
	
	private void generarTicket(){		
		Venta venta = new Venta();			
		venta.setCodigo(codigoCaja);
		int codfactura = 0;
		try{
			codfactura =Integer.valueOf(txtNroFactura.getText());
		}catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Información");
			alert.setHeaderText("Debe ingresar al menos una cantidad para imprimir el ticket.");				
			alert.showAndWait();
			return;
		}				
		venta.setCodfactura(codfactura);		
		
		Calendar calendarA = Calendar.getInstance();
		calendarA.setTime(configuracion.getFecha());

		Calendar calendarB = Calendar.getInstance();
		calendarB.setTime(new Date());

		calendarA.set(Calendar.HOUR_OF_DAY, calendarB.get(Calendar.HOUR_OF_DAY));
		calendarA.set(Calendar.MINUTE, calendarB.get(Calendar.MINUTE));
		calendarA.set(Calendar.SECOND, calendarB.get(Calendar.SECOND));
		calendarA.set(Calendar.MILLISECOND, calendarB.get(Calendar.MILLISECOND));

		Date result = calendarA.getTime();
		venta.setFecha(result);
		int id= ventaDAO.insert(venta);
		venta.setIdventa(id);			
		
		double precio = 0;
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
			if(child.getId() != null && child.getId().contains("txtInvisiblePrecio")){
				precio = ((DecimalField)child).getValue().doubleValue();					
			}
			
			if(linea.getIdproducto() != null && linea.getCantidad() != null && linea.getPrecio() != null && precio > 0){
				linea.setPrecio(new BigDecimal(linea.getCantidad().intValue() * precio));
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
		logger.info("imprimiendo Ticket: " + id);
		JasperPrint print = ticket.generar(configuracion, venta);
		if(print != null){
			logger.info("send PDF: " + id);
			MiPrinterJob.sendPDF(print);	
			loadPage();
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Ticket");
			alert.setHeaderText("No se ha podido imprimir el ticket, reintente mas tarde.");				
			alert.showAndWait();
		}		
	}
	
	private void generarCajaDiaria(){		
		List<Vcaja> caja =vcajaDAO.load(codigoCaja);
		CajaDiaria iCaja = new CajaDiaria();
		int cantVentas = ventaDAO.countVenta(codigoCaja);
		JasperPrint print = iCaja.generar(configuracion, caja, cantVentas);		
		if(print != null){
			logger.info("send PDF: " + "");
			MiPrinterJob.sendPDF(print);	
			loadPage();
		}else{
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error Ticket");
			alert.setHeaderText("No se ha podido imprimir el ticket, reintente mas tarde.");				
			alert.showAndWait();
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
		txtTotal.setValue(new BigDecimal(0));
		txtNroFactura.setStyle(""	  
				 + "-fx-font-size: 12px;"
		        + "-fx-font-weight: bold;"
		        + "-fx-text-fill: red;");
		txtNroFactura.setText("(NUEVO)");
	
		if(configuracionDAO.load(configuracion)){
			SimpleDateFormat format = new SimpleDateFormat("dd");
			codigoCaja = format.format(configuracion.getFecha()) + configuracion.getNrocaja() + configuracion.getTipocaja();
			lblCaja.setFont(new Font("MS Sans Serif", 18));		
			lblCaja.setStyle(""	       			       
			        + "-fx-font-weight: bold;"
			        + "-fx-text-fill: red;");
						
			lblCaja.setText("Cod. Caja: " + codigoCaja);
			
			ObservableList<Producto> data = FXCollections.observableArrayList(productoDAO.loadAll());
			int i= 1;
			for(Producto unProd: data){
				Label lblProd = new Label(unProd.getNombre() + ":");
				lblProd.setFont(new Font("MS Sans Serif", 12));			
				NumberField txtCant = new NumberField();
				txtCant.setId("txtCant" + i);
				txtCant.setPrefWidth(47);
				txtCant.setMaxValue(2);
				txtCant.setValue(0);			
				txtCant.setStyle("-fx-font-size: 12px;");
				
				DecimalField txtPrecio = new DecimalField();
				txtPrecio.setId("txtPrecio" + i);
				txtPrecio.setPrefWidth(47);			
				txtPrecio.setEditable(false);
				txtPrecio.setFocusTraversable(false);
				txtPrecio.setValue(new BigDecimal(0));
				txtPrecio.setStyle(""
				        + "-fx-font-size: 12px;"
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
					for (Node child : gridPane.getChildrenUnmodifiable()) {					
						if(child.getId() != null && child.getId().contains("txtPrecio")){
							total += ((DecimalField)child).getValue().doubleValue();							
						}
					}
					txtTotal.setText(new DecimalFormat("#,###.00").format(total));					
					txtNroFactura.setText(String.valueOf(ventaDAO.doCountAll(codigoCaja))); 
					
					 boolean isThisField = false;
					    for (Node child : gridPane.getChildrenUnmodifiable()) {			    	
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
					        for (Node child : gridPane.getChildrenUnmodifiable()) {
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
			if(gridPane.getChildren().size() > 0 && gridPane.getChildren().get(1) != null){
				gridPane.getChildren().get(1).requestFocus();		
				((NumberField)gridPane.getChildren().get(1)).selectAll();		
			}
		}		
	}
	
	private void loadUltimo(){
		int ultimaVenta = ventaDAO.getUltimo(codigoCaja);
		if(ultimaVenta > 0){
			Venta venta = new Venta();
			venta.setIdventa(ultimaVenta);			
			if(ventaDAO.load(venta)){
				txtNroFactura.setText(String.valueOf(venta.getCodfactura())); 
				loadItems(ultimaVenta);				
			}
		}
	}
	
	private void loadAnterior(){
		if(txtNroFactura.getText().contains("NUEVO")){
			loadUltimo();
		}else{			
			Venta ventaPrevia =ventaDAO.getPrevio(codigoCaja, Integer.valueOf(txtNroFactura.getText()));							
			if(ventaPrevia != null){
				txtNroFactura.setText(String.valueOf(ventaPrevia.getCodfactura()));	
				loadItems(ventaPrevia.getIdventa());
			}
		}		
	}
	
	private void loadItems(int idVenta){			
		List<Lineadeventa> lineas = lineadeventaDAO.getAll(idVenta);
		int total =1;
		double dTotal = 0;				
		for(Lineadeventa linea: lineas){
			for (Node child : this.gridPane.getChildrenUnmodifiable()) {	
				
				if(child.getId() != null && child.getId().contains("txtIdProducto"+total)){
					((NumberField)child).setValue(linea.getIdproducto());
					((NumberField)child).setEditable(false);
				}
				if(child.getId() != null && child.getId().contains("txtCant"+total)){
					((NumberField)child).setValue(linea.getCantidad());
					((NumberField)child).setEditable(false);
				}
				if(child.getId() != null && child.getId().contains("txtPrecio"+total)){
					((DecimalField)child).setValue(linea.getPrecio());
					((DecimalField)child).setEditable(false);
				}								
			}	
			total++;
			dTotal += linea.getPrecio().doubleValue();
		}
		txtTotal.setText(new DecimalFormat("#,###.00").format(dTotal));
	}
	
	private void openConfiguracion(ActionEvent event){
		if(!openDialogPass()){
			return;
		}
		
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
	
	private boolean openDialogPass(){
		PasswordDialog pd = new PasswordDialog();
	    Optional<String> result = pd.showAndWait();	   	    
	    if(result.isPresent() && result.get().equals("1334")){
	    	return true;
	    }
	    return false;
	}
}
