/*
 * Created on 21 oct 2016 ( Time 11:36:55 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao;

import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.labruzeza.colectividades.dao.commons.DAOProvider;
import org.labruzeza.colectividades.dao.commons.DAOTestUtil;
import org.labruzeza.colectividades.modelo.Configuracion;

/**
 * JUnit tests for ConfiguracionDAO
 * 
 * @author Telosys Tools
 *
 */
public class ConfiguracionDAOTest {


	private static final String CREATE_TABLE = 
			 "CREATE TABLE configuracion ("
			+ "idConfiguracion IDENTITY AUTO_INCREMENT NOT NULL,"
			+ "nroCaja INTEGER ,"
			+ "fecha DATE ,"
			+ "PRIMARY KEY(idConfiguracion)"
			+ ");"
			;

	@BeforeClass
	public static void init() {
		DAOTestUtil.initDatabase(CREATE_TABLE) ;
	}

	@Test
	public void testDAO() throws SQLException {
    	System.out.println("test ConfiguracionDAO ");
    	ConfiguracionDAO dao = DAOProvider.getDAO(ConfiguracionDAO.class);

    	Assert.assertTrue( dao.count() == 0 );

    	Configuracion configuracion = new Configuracion();
		//--- Key values
		// Auto-incremented key : nothing to set in the Primary Key
		//--- Other values
		configuracion.setNrocaja(100); // "nroCaja" : java.lang.Integer
		configuracion.setFecha(java.sql.Date.valueOf("2001-06-22")); // "fecha" : java.util.Date

    	//--- INSERT
    	System.out.println("Insert : " + configuracion );
    	Integer pkAutoIncr = dao.insert(configuracion);
    	configuracion.setIdconfiguracion( pkAutoIncr );
    	Assert.assertTrue( dao.exists(pkAutoIncr) );
    	Assert.assertTrue( dao.count() == 1 );
    	Assert.assertTrue( dao.exists(configuracion) );
    	
    	//--- FIND
    	System.out.println("Find..." );
    	Configuracion configuracion2 = dao.find(pkAutoIncr);
    	Assert.assertNotNull(configuracion2);
    	Assert.assertTrue( dao.exists(configuracion2) ) ;
    	
    	//--- UPDATE
		//--- Change values
		configuracion2.setNrocaja(200); // "nroCaja" : java.lang.Integer
		configuracion2.setFecha(java.sql.Date.valueOf("2002-06-22")); // "fecha" : java.util.Date
    	System.out.println("Update : " + configuracion2 );
    	Assert.assertTrue( dao.update(configuracion2) == 1 );
    	
    	//--- LOAD
    	Configuracion configuracion3 = new Configuracion();
    	configuracion3.setIdconfiguracion( pkAutoIncr );
    	Assert.assertTrue( dao.load(configuracion3) );
    	System.out.println("Loaded : " + configuracion3 );
		Assert.assertEquals(configuracion2.getNrocaja(), configuracion3.getNrocaja() ); 
		Assert.assertEquals(configuracion2.getFecha(), configuracion3.getFecha() ); 


    	configuracion3.setIdconfiguracion(300);
    	Assert.assertFalse( dao.load(configuracion3) );
    	
    	//--- DELETE
    	System.out.println("Delete : " + configuracion2 );
    	Assert.assertTrue( dao.delete(configuracion2) == 1 );
    	Assert.assertTrue( dao.delete(configuracion2) == 0 );
    	Assert.assertTrue( dao.delete(100) == 0 );

    	Assert.assertTrue( dao.count() == 0 );
    	Assert.assertFalse( dao.exists(100) ) ;
    	Assert.assertFalse( dao.exists(configuracion2) ) ;
    	configuracion2 = dao.find(100);
    	Assert.assertNull( configuracion2 );
    	
    	System.out.println("Normal end of DAO test." );
	}

}
