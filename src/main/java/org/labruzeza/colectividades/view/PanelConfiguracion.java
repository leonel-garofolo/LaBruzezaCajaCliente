package org.labruzeza.colectividades.view;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.javafx.controls.customs.NumberField;
import org.labruzeza.colectividades.PropertyResourceBundleMessageInterpolator;
import org.labruzeza.colectividades.dao.ConfiguracionDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.ConfiguracionDAOImpl;
import org.labruzeza.colectividades.modelo.Configuracion;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PanelConfiguracion extends BorderPane implements EventHandler<ActionEvent>{
	private boolean modoEdit = false;	
	private ConfiguracionDAO configuracionDAO;
	private PanelControlesABM pnlABM;
	
	@FXML
	private VBox vBoxMsg;


	@FXML
	private NumberField txtidconfiguracion;

	@FXML
	private NumberField txtnrocaja;

	@FXML
	private DatePicker dprfecha;

	public PanelConfiguracion() {
		this.modoEdit = false;		
		initComponentes();
    }

	public PanelConfiguracion(int id) {
		this.modoEdit = true;		
		initComponentes();
		loadEntity(id);		
    }

	private void loadEntity(int id) {
		try {
			Configuracion unConfiguracion = new Configuracion();
			unConfiguracion.setIdconfiguracion(id);
			boolean bConfiguracion = configuracionDAO.load(unConfiguracion);
			if(bConfiguracion){
				loadForm(unConfiguracion);
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
        configuracionDAO = new ConfiguracionDAOImpl();
        
        pnlABM = new PanelControlesABM();
        
        this.setTop(pnlABM.generarPanelFormulario());
        this.setLeft(null);
        this.setRight(null);
        pnlABM.btnGuardar.setOnAction(this);        
        pnlABM.btnCancelar.setOnAction(this);      
        
		dprfecha.setValue(LocalDate.now());			
	}

	public void loadForm(Configuracion configuracion){
		if(configuracion !=null){
			if(configuracion.getIdconfiguracion() != null){
				txtidconfiguracion.setText(String.valueOf(configuracion.getIdconfiguracion()));
			}
			if(configuracion.getNrocaja() != null){
				txtnrocaja.setText(String.valueOf(configuracion.getNrocaja()));
			}
			if(configuracion.getFecha() != null){
				dprfecha.setValue(new java.sql.Date(configuracion.getFecha().getTime()).toLocalDate());		
			}
		}
	}

	private Configuracion getConfiguracion() {
		Configuracion unConfiguracion = new Configuracion();
		try{
			unConfiguracion.setIdconfiguracion(Integer.valueOf(txtidconfiguracion.getText()));
		}catch (NumberFormatException e) {
			unConfiguracion.setIdconfiguracion(null);
		}
		try{
			unConfiguracion.setNrocaja(Integer.valueOf(txtnrocaja.getText()));
		}catch (NumberFormatException e) {
			unConfiguracion.setNrocaja(null);
		}
		unConfiguracion.setFecha(java.sql.Date.valueOf(dprfecha.getValue()));
		
		Label label = null;	
		vBoxMsg.getChildren().clear();
		Validator validator =PropertyResourceBundleMessageInterpolator.getValidation();
	    Set<ConstraintViolation<Configuracion>> inputErrors = validator.validate(unConfiguracion); 
	    for(ConstraintViolation<Configuracion> error: inputErrors){	    	
	    	label = new Label();
	    	label.setText(error.getMessage());
	    	vBoxMsg.getChildren().addAll(label);	    	
	    }
	    if(vBoxMsg.getChildren().size() > 0){
	    	return null;
	    }
		return unConfiguracion;
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource().equals(pnlABM.btnGuardar)){
			Configuracion unConfiguracion = getConfiguracion();
			if(unConfiguracion != null){
				try {
					if(modoEdit){
						configuracionDAO.update(unConfiguracion);
					}else{
						configuracionDAO.insert(unConfiguracion);
					}										
				} catch (Exception e) {
					Label label = new Label();
			    	label.setText("Se ha producido un error en el servidor. Intente mas tarde.");
			    	vBoxMsg.getChildren().addAll(label);
					e.printStackTrace();			
				}
			}		
		}
		if(event.getSource().equals(pnlABM.btnCancelar)){			
			reLoad();    
		}
	}

	private void reLoad() {
		// TODO Auto-generated method stub
		
	}
}