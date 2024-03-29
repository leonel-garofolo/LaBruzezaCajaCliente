/*
 * Created on 25 oct 2016 ( Time 10:26:00 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */

package org.labruzeza.colectividades.dao.commons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;
import org.labruzeza.colectividades.dao.impl.jdbc.commons.DataSourceProvider;

/**
 * Utility class for JUnit test cases
 *
 * @author Telosys Tools
 */
public class DAOTestUtil {

  public static boolean existTable(String table) {
    boolean isExist = false;
    try {
      final DataSource datasource = DataSourceProvider.getDataSource();
      isExist = existTable(table.toUpperCase(), datasource);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error on 'create table'");
    }
    return isExist;
  }

  /**
   * Creates a table that will be used by the DAO tests
   */
  public static void initDatabase(String createTableSQL) {

    System.out.println("===== initDatabase... ");

    //--- The datasource as defined in the 'jdbc properties' file
    final DataSource datasource = DataSourceProvider.getDataSource();

    //--- Execute the CREATE TABLE SQL statement
    try {
      createTable(createTableSQL, datasource);
    } catch (SQLException e) {
      e.printStackTrace();
      throw new RuntimeException("Error on 'create table'");
    }
  }

  private static void createTable(String createTableSQL, DataSource datasource) throws SQLException {
    Connection conn = datasource.getConnection();
    Statement stmt = conn.createStatement();
    int r = stmt.executeUpdate(createTableSQL);
    System.out.println("create table status : " + r);
    stmt.close();
    conn.close();
  }

  private static boolean existTable(String table, DataSource datasource) throws SQLException {
    boolean isExist = false;
    final Connection conn = datasource.getConnection();
    final PreparedStatement preparedStatement =
        conn.prepareStatement("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = ?");
		preparedStatement.setString(1, table);
    Boolean rq = preparedStatement.execute();
    if (rq) {
      ResultSet rs = preparedStatement.getResultSet();
      while (rs.next()) {
				isExist = true;
				break;
      }
    }
    preparedStatement.close();
    conn.close();
    return isExist;
  }
}
