package org.labruzeza.colectividades;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.labruzeza.colectividades.utils.PrinterJob;

public class GargareCollection extends Thread {
	private static final Logger logger = LogManager.getLogger(PrinterJob.class);
	
	
	@SuppressWarnings("static-access")
	@Override
	public void run() {
		try{
			AppClient.iniciar();
		}catch (Exception e) {
			logger.error(e);
			e.printStackTrace();		
		}
		
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
