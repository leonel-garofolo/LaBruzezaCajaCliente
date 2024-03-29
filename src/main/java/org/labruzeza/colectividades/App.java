package org.labruzeza.colectividades;

import org.labruzeza.colectividades.dao.impl.jdbc.commons.UpdateDB;
import org.labruzeza.colectividades.utils.MyPrinterJob;

public class App {
	@SuppressWarnings("static-access")
	private void iniciar(){
		/*
		GargareCollection gargare = new GargareCollection();
		gargare.start();
		*/
		UpdateDB updateDB = new UpdateDB();
		updateDB.run();
		MyPrinterJob.preparedPrinter();
		AppClient.iniciar();		
	}
	
	public static void main(String[] args) throws Exception {
		App app = new App();
		app.iniciar();		
	}
}
