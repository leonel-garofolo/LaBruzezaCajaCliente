package org.labruzeza.colectividades;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class GargareCollection extends Thread {
	private static final Logger logger = LoggerFactory.getLogger(GargareCollection.class);
	
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		do{
			try {
				this.sleep(100000);
			} catch (InterruptedException e) {
				logger.error("error thead", e);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("********** INICIO: 'LIMPIEZA GARBAGE COLECTOR' **********");
			Runtime basurero = Runtime.getRuntime();
			logger.info("MEMORIA TOTAL 'JVM': " + basurero.totalMemory());
			logger.info("MEMORIA [FREE] 'JVM' [ANTES]: " + basurero.freeMemory());
			basurero.gc(); // Solicitando ...
			logger.info("MEMORIA [FREE] 'JVM' [DESPUES]: " + basurero.freeMemory());
			logger.info("********** FIN: 'LIMPIEZA GARBAGE COLECTOR' **********");
		}while(true);
	}
}
