package org.labruzeza.colectividades;

public class App {
	private void iniciar(){
		AppClient.iniciar();		
	}
	
	public static void main(String[] args) throws Exception {
		App app = new App();
		app.iniciar();		
	}
}
