package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;

import org.h2.tools.RunScript;

public class SqlExecuterImpl extends UtilDB implements SqlExecuter {
	@Override
	public void execute(String fileName) {
		Connection conn = null;
		try {
			conn = DataSourceProvider.getDataSource().getConnection();
			final String rootPath = System.getProperty("user.dir");
			final File filePath = new File(rootPath + fileName);
			if(filePath.exists()){
				RunScript.execute(conn, new FileReader(filePath.getPath()));
			} else {
				throw new Exception("File not found: " + filePath.getPath());
			}

		} catch (Exception e) {
			LOGGER.error("createTableVersion ERROR", e);
		} finally {
			if (conn != null) {
				closeConnection(conn);
			}
		}
	}
}
