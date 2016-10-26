package org.labruzeza.colectividades.view;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.javafx.controls.customs.NumberField;
import org.javafx.controls.customs.StringField;
import org.labruzeza.colectividades.PropertyResourceBundleMessageInterpolator;
import org.labruzeza.colectividades.dao.ConfiguracionDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.ConfiguracionDAOImpl;
import org.labruzeza.colectividades.modelo.Configuracion;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class PanelConfiguracion extends BorderPane implements EventHandler<ActionEvent>{
	private boolean modoEdit = false;
	private ConfiguracionDAO configuracionDao;
	
	@FXML
	private VBox vBoxMsg;


	@FXML
	private NumberField txtidconfiguracion;

	@FXML
	private NumberField txtnrocaja;	
	
	@FXML
	private StringField txtTipoCaja;
	
	private PanelControlesABM panelControles;
	
	public PanelConfiguracion() {
		this.modoEdit = false;		
		initComponentes();
		loadEntity(1);		
    }

	private void loadEntity(int id) {
		try {
			Configuracion unConfiguracion = new Configuracion();
			unConfiguracion.setIdconfiguracion(id);
			boolean eConfiguracion = configuracionDao.load(unConfiguracion);
			if(eConfiguracion){
				loadForm(unConfiguracion);	
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
        
        panelControles = new PanelControlesABM();
        this.setTop(panelControles.generarPanelFormulario());
        this.setLeft(null);
        this.setRight(null);
        panelControles.btnGuardar.setOnAction(this);        
        panelControles.btnCancelar.setOnAction(this);        		
		configuracionDao =  new ConfiguracionDAOImpl();				
	}

	public void loadForm(Configuracion configuracion){
		if(configuracion !=null){
			if(configuracion.getIdconfiguracion() != null){
				txtidconfiguracion.setValue(String.valueOf(configuracion.getIdconfiguracion()));
			}
			if(configuracion.getNrocaja() != null){
				txtnrocaja.setValue(configuracion.getNrocaja());
			}
			if(configuracion.getTipocaja() != null){
				txtTipoCaja.setValue(configuracion.getTipocaja());
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
			unConfiguracion.setNrocaja(txtnrocaja.getValue());
		}catch (NumberFormatException e) {
			unConfiguracion.setNrocaja(null);
		}		
		unConfiguracion.setTipocaja(txtTipoCaja.getText());
		
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
		if(event.getSource().equals(panelControles.btnGuardar)){
			Configuracion unConfiguracion = getConfiguracion();
			if(unConfiguracion != null){
				try {
					if(modoEdit){
						configuracionDao.update(unConfiguracion);
					}else{
						configuracionDao.insert(unConfiguracion);
					}
										
				} catch (Exception e) {
					Label label = new Label();
			    	label.setText("Se ha producido un error en el servidor. Intente mas tarde.");
			    	vBoxMsg.getChildren().addAll(label);
					e.printStackTrace();			
				}
			}		
		}
		if(event.getSource().equals(panelControles.btnCancelar)){
			closeWindows();
		}
	}
	
	private void closeWindows(){
		this.getScene().getWindow().hide();
	}
}