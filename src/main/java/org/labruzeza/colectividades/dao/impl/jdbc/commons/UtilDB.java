package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UtilDB {
	final static Logger logger = LogManager.getLogger(UtilDB.class);

	protected void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			logger.error("fail close ", e);
		}
	}

	protected void closeConnection(Connection conn, Statement st) {
		if (st != null)
			try {
				st.close();
			} catch (SQLException e) {
				logger.error("fail close ", e);
			}
		closeConnection(conn);
	}
}
