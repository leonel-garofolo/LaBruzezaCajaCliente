/*
 * Created on 25 oct 2016 ( Time 10:26:00 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao.commons;

import org.junit.Test;
import org.labruzeza.colectividades.dao.impl.jdbc.commons.CreateSchemaDB;
import org.labruzeza.colectividades.dao.impl.jdbc.commons.InsertDataDB;

/**
 * JUnit tests for ConfiguracionDAO
 * 
 * @author Telosys Tools
 *
 */
public class InsertDataDBTest {
	@Test
	public void insert_data() {
		CreateSchemaDB createSchemaDB = new CreateSchemaDB();
		createSchemaDB.execute();
		InsertDataDB insertDataDB = new InsertDataDB();
		insertDataDB.execute();
	}
}
