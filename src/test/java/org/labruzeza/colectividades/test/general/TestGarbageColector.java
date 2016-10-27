package org.labruzeza.colectividades.test.general;

import java.sql.Date;

/**
 * @author: Ricardo Guerra.
 * @clase: TestGarbageColector.java
 * @descripción: Clase utilizada para probar el manejo del Garbage Colector en
 *               casos que las aplicaciones consuman mucha memoria.
 * @author_web: http://frameworksjava2008.blogspot.com
 *              http://viviendoconjavaynomoririntentandolo.blogspot.com
 * @author_email: cesarricardo_guerra19@hotmail.com.
 * @fecha_de_creación: 05-08-2009.
 * @fecha_de_ultima_actualización: 20-03-2009.
 * @versión: 1.0
 */
public class TestGarbageColector {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestGarbageColector test = new TestGarbageColector();
		test.testGarbageColector();
	}

	/**
	 * testGarbageColector Método que prueba un incremento de memoria para ver
	 * el manejo de la liveracion de esta al solicitar a la 'JVM' el 'GC'.
	 */
	public void testGarbageColector() {

		Date fecha = null;

		for (int i = 0; i < 1000000; i++) {
			fecha = new Date(10, 10, 2009);
			fecha = null;
		}

		// Solicitamos a la 'JVM' el 'GC', para liverar memoria.
		this.getSolicitaGarbageColector();
	}

	/**
	 * getSolicitaGarbageColector Método que invoca al Garbage Colector( NO LO
	 * FUERZA a venir, envia una solicitud a la JVM para su proceso..!!! )
	 *
	 */
	public void getSolicitaGarbageColector() {

		try {
			System.out.println("********** INICIO: 'LIMPIEZA GARBAGE COLECTOR' **********");
			Runtime basurero = Runtime.getRuntime();
			System.out.println("MEMORIA TOTAL 'JVM': " + basurero.totalMemory());
			System.out.println("MEMORIA [FREE] 'JVM' [ANTES]: " + basurero.freeMemory());
			basurero.gc(); // Solicitando ...
			System.out.println("MEMORIA [FREE] 'JVM' [DESPUES]: " + basurero.freeMemory());
			System.out.println("********** FIN: 'LIMPIEZA GARBAGE COLECTOR' **********");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
