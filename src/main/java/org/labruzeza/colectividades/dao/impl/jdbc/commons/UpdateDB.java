package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("rawtypes")
public class UpdateDB extends UtilDB{
	
	final static Logger LOGGER = LoggerFactory.getLogger(UpdateDB.class);
	
	private int newVersion;

	public void run() {
		Connection conn = null;
		Statement st = null;
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			final int lastVersionExecuted = getCurrentVersion();
			st = conn.createStatement();
			newVersion = 1;
			if (lastVersionExecuted < newVersion) {
				CreateSchemaDB createSchemaDB = new CreateSchemaDB();
				createSchemaDB.execute();
				new UpdateVersionDB(newVersion).execute();
			}

			newVersion = 2;
			if (lastVersionExecuted < newVersion) {
				InsertDataDB insertDataDB = new InsertDataDB();
				insertDataDB.execute();
				new UpdateVersionDB(newVersion).execute();
			}
		} catch (Exception e) {
			LOGGER.error("update DDBB ERROR", e);
		} finally {
			closeConnection(conn, st);
		}
	}

	private Integer getCurrentVersion() {
		Integer currentVersionDB = 0;
		Connection conn = null;
		Statement st;
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			st = conn.createStatement();
			boolean existTable = false;
			ResultSet rs = conn.getMetaData().getTables(null, null, "VERSIONADO", null);
			if (rs.next())
			{
				existTable = true;
			}
			rs.close();

			if (existTable) {
				rs = st.executeQuery("select max(numero_sql) as numero_sql from versionado");
				if (rs.next()) {
					currentVersionDB = rs.getInt("numero_sql");
				}
				rs.close();
			}
			st.close();
		} catch (Exception e) {
			LOGGER.error("createTableVersion ERROR", e);
		} finally {
			if (conn != null) {
				closeConnection(conn);
			}
		}
		return currentVersionDB;
	}
}
