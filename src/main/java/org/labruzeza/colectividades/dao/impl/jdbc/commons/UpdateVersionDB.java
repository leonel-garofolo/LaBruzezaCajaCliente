package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

public class UpdateVersionDB extends UtilDB implements ScriptExecuter {

  private static String SQL_FILE = "dababase/data.sql";

  private final int numVersion;

  public UpdateVersionDB(int numVersion) {
    this.numVersion = numVersion;
  }

  @Override
  public void execute() {
    final DataSource dataSource = DataSourceProvider.getDataSource();
    Connection connection = null;
    try {
      connection= dataSource.getConnection();
      final PreparedStatement st = connection.prepareStatement("insert into VERSIONADO(NUMERO_SQL, UPDATE_SQL) values(?, CURRENT_TIMESTAMP());");
      st.setInt(1, numVersion);
      st.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if(connection != null){
        closeConnection(connection);
      }
    }
  }
}
