package org.labruzeza.colectividades;

import org.labruzeza.colectividades.views.Principal;

public class AppClient extends Thread {

	public void run() {		
		Principal.iniciar();
	}
}