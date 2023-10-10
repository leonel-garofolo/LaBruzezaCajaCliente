package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UtilDB {

  final static Logger LOGGER = LoggerFactory.getLogger(UtilDB.class);

  protected void closeConnection(Connection conn) {
    try {
      conn.close();
    } catch (SQLException e) {
      LOGGER.error("fail close ", e);
    }
  }

  protected void closeConnection(Connection conn, Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				LOGGER.error("fail close ", e);
			}
		}
    closeConnection(conn);
  }
}
