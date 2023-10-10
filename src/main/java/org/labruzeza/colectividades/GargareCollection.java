package org.labruzeza.colectividades;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class GargareCollection extends Thread {
	private static final Logger LOGGER = LoggerFactory.getLogger(GargareCollection.class);
	
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		do{
			try {
				this.sleep(100000);
			} catch (InterruptedException e) {
				LOGGER.error("error thead", e);
			}
			LOGGER.info("********** INICIO: 'LIMPIEZA GARBAGE COLECTOR' **********");
			Runtime basurero = Runtime.getRuntime();
			LOGGER.info("MEMORIA TOTAL 'JVM': " + basurero.totalMemory());
			LOGGER.info("MEMORIA [FREE] 'JVM' [ANTES]: " + basurero.freeMemory());
			basurero.gc(); // Solicitando ...
			LOGGER.info("MEMORIA [FREE] 'JVM' [DESPUES]: " + basurero.freeMemory());
			LOGGER.info("********** FIN: 'LIMPIEZA GARBAGE COLECTOR' **********");
		}while(true);
	}
}
