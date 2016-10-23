/*
 * Created on 21 oct 2016 ( Time 11:36:55 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

/**
 * Generic abstract class for basic JDBC DAO
 * 
 * @author Telosys Tools
 *
 * @param <T>
 */
public abstract class GenericDAO<T> {
	
	private final static int INITIAL_POSITION = 1 ;
	
	/**
	 * The DataSource providing the connections
	 */
	private final DataSource dataSource;
	
	/**
	 * Constructor
	 */
	protected GenericDAO() {
		super();
		this.dataSource = DataSourceProvider.getDataSource() ;
	}

	/**
	 * Returns the SQL SELECT REQUEST to be used to retrieve the bean data from the database
	 * @return
	 */
	protected abstract String  getSqlSelect();
	
	/**
	 * Returns the SQL INSERT REQUEST to be used to insert the bean in the database
	 * @return
	 */
	protected abstract String  getSqlInsert();
	
	/**
	 * Returns the SQL UPDATE REQUEST to be used to update the bean in the database
	 * @return
	 */
	protected abstract String  getSqlUpdate();
	
	/**
	 * Returns the SQL DELETE REQUEST to be used to delete the bean from the database
	 * @return
	 */
	protected abstract String  getSqlDelete();
	
	/**
	 * Returns the SQL COUNT REQUEST to be used to check if the bean exists in the database
	 * @return
	 */
	protected abstract String  getSqlCount();
	
	/**
	 * Returns the SQL COUNT REQUEST to be used to count all the beans present in the database
	 * @return
	 */
	protected abstract String  getSqlCountAll();
	
	protected abstract String  getSqlLoadAll();
	
	/**
	 * Set the primary key value(s) in the given PreparedStatement
	 * @param ps
	 * @param i 
	 * @param bean
	 * @throws SQLException
	 */
	protected abstract void    setValuesForPrimaryKey(PreparedStatement ps, int i, T bean) throws SQLException ;
	
	/**
	 * Set the bean values in the given PreparedStatement before SQL INSERT
	 * @param ps
	 * @param i
	 * @param bean
	 * @throws SQLException
	 */
	protected abstract void    setValuesForInsert(PreparedStatement ps, int i, T bean) throws SQLException ; 
	
	/**
	 * Set the bean values in the given PreparedStatement before SQL UPDATE
	 * @param ps
	 * @param i
	 * @param bean
	 * @throws SQLException
	 */
	protected abstract void    setValuesForUpdate(PreparedStatement ps, int i, T bean) throws SQLException ; 
	
	/**
	 * Populates the bean attributes from the given ResultSet
	 * @param rs
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	protected abstract T       populateBean(ResultSet rs, T bean) throws SQLException ;
	
    //-----------------------------------------------------------------------------------------
	private Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
    //-----------------------------------------------------------------------------------------
	private void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {}
		}
	}
    //-----------------------------------------------------------------------------------------
	/**
	 * Loads the given bean from the database using its primary key (SQL SELECT)<br>
	 * The given bean is populated from the ResultSet if found
	 * @param bean
	 * @return true if found and loaded, false if not found
	 */
	protected boolean doSelect(T bean) {
 
		boolean result = false ;
		Connection conn = null;
 
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlSelect() );
			//--- Set the PRIMARY KEY ( SQL "WHERE ..." )
			setValuesForPrimaryKey(ps, INITIAL_POSITION, bean); 
			//--- Execute SQL SELECT 
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				populateBean(rs, bean);
				result = true ;
			}
			else {
				result = false ;
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return result ;
	}
	
	
	protected List<T> doLoad(T bean) {
		Connection conn = null;
 
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlSelect() );		
			List<T> array = new ArrayList<T>();
			//--- Execute SQL SELECT 
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				array.add(populateBean(rs, bean));				
			}			
			rs.close();
			ps.close();
			return array;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}		
	}

	//-----------------------------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database (SQL INSERT)
	 * @param bean
	 */
	protected void doInsert(T bean) {
		 
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlInsert() );
			//--- Call specific method to set the values to be inserted
			setValuesForInsert(ps, INITIAL_POSITION, bean); 
			//--- Execute SQL INSERT
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
	}	
    
	//-----------------------------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database (SQL INSERT) with an auto-incremented columns
	 * @param bean
	 */
	protected Long doInsertAutoIncr(T bean) {
		Long generatedKey = 0L ;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlInsert(), PreparedStatement.RETURN_GENERATED_KEYS );
			//--- Call specific method to set the values to be inserted
			setValuesForInsert(ps, INITIAL_POSITION, bean); 
			//--- Execute SQL INSERT
			ps.executeUpdate();
			//--- Retrieve the generated key 
			ResultSet rs = ps.getGeneratedKeys();
			if ( rs.next() ) {
				generatedKey = rs.getLong(1);
			}
			rs.close();
			//--- End
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return generatedKey ;
	}	
    
	//-----------------------------------------------------------------------------------------
	/**
	 * Updates the given bean in the database (SQL UPDATE)
	 * @param bean
	 * @return the JDBC return code (i.e. the row count affected by the UPDATE operation : 0 or 1 )
	 */
	protected int doUpdate(T bean) {
		int result = 0 ;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlUpdate() );
			//--- Call specific method to set the values to be updated and the primary key
			setValuesForUpdate(ps, INITIAL_POSITION, bean); 
			//--- Execute SQL UPDATE
			result = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return result ;
	}	
	//-----------------------------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database (SQL DELETE)
	 * @param bean
	 * @return the JDBC return code (i.e. the row count affected by the DELETE operation : 0 or 1 )
	 */
	protected int doDelete(T bean) {
		int result = 0 ;
		Connection conn = null;
 
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlDelete() );
			//--- Set the PRIMARY KEY ( SQL "WHERE ..." )
			setValuesForPrimaryKey(ps, INITIAL_POSITION, bean); 
			//--- Execute SQL DELETE
			result = ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return result ;
	}
	
	//-----------------------------------------------------------------------------------------
	/**
	 * Checks if the given bean exists in the database (SQL SELECT COUNT(*) )
	 * @param bean
	 * @return
	 */
	protected boolean doExists(T bean) {

		long result = 0 ;
		Connection conn = null;
 
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlCount() );
			//--- Set the PRIMARY KEY ( SQL "WHERE ..." )
			setValuesForPrimaryKey(ps, INITIAL_POSITION, bean); 
			//--- Execute SQL COUNT 
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return result > 0 ;
	}
	//-----------------------------------------------------------------------------------------
	/**
	 * Counts all the occurrences in the table ( SQL SELECT COUNT(*) )
	 * @return
	 */
	protected long doCountAll() {

		long result = 0 ;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( getSqlCountAll() );
			//--- Execute SQL COUNT (without where clause)
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1);
			}
			rs.close();
			ps.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			closeConnection(conn);
		}
		return result ;
	}
	
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, String value) throws SQLException {
		ps.setString(i, value);
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, java.util.Date value) throws SQLException {
		if ( value != null ) {
			ps.setDate(i, new java.sql.Date(value.getTime())); // Convert util.Date to sql.Date
		}
		else {
			ps.setNull(i, java.sql.Types.DATE); 
		}
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, java.sql.Date value) throws SQLException {
		ps.setDate(i, value);
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, java.sql.Time value) throws SQLException {
		ps.setTime(i, value);
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, java.sql.Timestamp value) throws SQLException {
		ps.setTimestamp(i, value);
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, Byte value) throws SQLException {
		if ( value != null ) {
			ps.setByte(i, value.byteValue());
		}
		else {
			ps.setNull(i, java.sql.Types.TINYINT); // JDBC : "TINYINT" => getByte/setByte
		}
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, Short value) throws SQLException {
		if ( value != null ) {
			ps.setShort(i, value.shortValue());
		}
		else {
			ps.setNull(i, java.sql.Types.SMALLINT);
		}
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, Integer value) throws SQLException {
		if ( value != null ) {
			ps.setInt(i, value.intValue());
		}
		else {
			ps.setNull(i, java.sql.Types.INTEGER);
		}
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, Long value) throws SQLException {
		if ( value != null ) {
			ps.setLong(i, value.longValue());
		}
		else {
			ps.setNull(i, java.sql.Types.BIGINT); // JDBC : "BIGINT" => getLong/setLong
		}
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, Float value) throws SQLException {
		if ( value != null ) {
			ps.setFloat(i, value.floatValue());
		}
		else {
			ps.setNull(i, java.sql.Types.FLOAT); 
		}
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, Double value) throws SQLException {
		if ( value != null ) {
			ps.setDouble(i, value.doubleValue());
		}
		else {
			ps.setNull(i, java.sql.Types.DOUBLE); 
		}
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, BigDecimal value) throws SQLException {
		ps.setBigDecimal(i, value );
	}
	//-----------------------------------------------------------------------------------------
	protected void setValue(PreparedStatement ps, int i, byte[] value) throws SQLException {
		ps.setBytes(i, value );
	}

}

