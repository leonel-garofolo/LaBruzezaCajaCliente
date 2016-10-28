package org.labruzeza.colectividades;

public class App {
	@SuppressWarnings("static-access")
	private void iniciar(){
		GargareCollection gargare = new GargareCollection();
		gargare.start();		
	}
	
	public static void main(String[] args) throws Exception {
		App app = new App();
		app.iniciar();		
	}
}
