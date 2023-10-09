package org.labruzeza.colectividades.view;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class PanelControlesABM extends Pane {
	
	protected Button btnAgregar;
	protected Button btnEditar;
	protected Button btnEliminar;		
	protected Button btnInforme;
	
	//paneles de nuevos y editacion
	protected Button btnGuardar;
	protected Button btnCancelar;
	
	protected HBox generarPanel(){
		this.btnAgregar = new Button();
		this.btnEditar = new Button();
		this.btnEliminar = new Button();
		this.btnInforme = new Button();
		Image imageDecline = new Image(getClass().getResourceAsStream("/image/agregar.png"));
		btnAgregar.setGraphic(new ImageView(imageDecline));
		btnAgregar.setTooltip(new Tooltip("Agregar Elemento"));
		btnAgregar.setId("btnAgregar");		
		
		imageDecline = new Image(getClass().getResourceAsStream("/image/edit.png"));
		btnEditar.setGraphic(new ImageView(imageDecline));
		btnEditar.setTooltip(new Tooltip("Editar Elemento"));
		btnEditar.setId("btnEditar");

		imageDecline = new Image(getClass().getResourceAsStream("/image/rubbish-bin.png"));
		btnEliminar.setGraphic(new ImageView(imageDecline));
		btnEliminar.setTooltip(new Tooltip("Eliminar Elemento"));
		btnEliminar.setId("btnEliminar");
		
		imageDecline = new Image(getClass().getResourceAsStream("/image/printer.png"));
		btnInforme.setGraphic(new ImageView(imageDecline));
		btnInforme.setTooltip(new Tooltip("Imprimir Grilla"));
		btnInforme.setId("btnInforme");
				
		HBox hbox = new HBox();
		hbox.getChildren().addAll(btnAgregar, btnEditar, btnEliminar, btnInforme);

		
		return hbox;
	}
	
	public HBox generarPanelFormulario(){
		this.btnGuardar = new Button();
		this.btnCancelar = new Button();
		
		Image imageDecline = new Image(getClass().getResourceAsStream("/image/save.png"));
		btnGuardar.setGraphic(new ImageView(imageDecline));
		btnGuardar.setTooltip(new Tooltip("Guardar"));		
		btnGuardar.setId("btnGuardar");
		
		imageDecline = new Image(getClass().getResourceAsStream("/image/error.png"));
		btnCancelar.setGraphic(new ImageView(imageDecline));
		btnCancelar.setTooltip(new Tooltip("Cancelar"));	
		btnCancelar.setId("btnCancelar");
		
		HBox hbox = new HBox();		
		hbox.getChildren().addAll(btnGuardar, btnCancelar);
			
		return hbox;
	}
}
