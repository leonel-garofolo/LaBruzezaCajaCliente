package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import java.io.FileReader;
import java.sql.Connection;

import org.h2.tools.RunScript;

public class SqlExecuterImpl extends UtilDB implements SqlExecuter {
	@Override
	public void execute(String fileName) {
		Connection conn = null;
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			RunScript.execute(conn, new FileReader(this.getClass().getClassLoader().getResource(fileName).getFile()));
		} catch (Exception e) {
			logger.error("createTableVersion ERROR", e);
		} finally {
			if (conn != null) {
				closeConnection(conn);
			}
		}
	}
}
