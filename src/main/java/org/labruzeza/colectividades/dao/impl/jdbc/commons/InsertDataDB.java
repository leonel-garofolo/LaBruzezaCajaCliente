package org.labruzeza.colectividades.dao.impl.jdbc.commons;

public class InsertDataDB implements ScriptExecuter {

	private static String SQL_FILE = "/dababase/data.sql";

	private SqlExecuter sqlExecuter;

	@Override
	public void execute() {
		sqlExecuter = new SqlExecuterImpl();
		sqlExecuter.execute(SQL_FILE);
	}
}
