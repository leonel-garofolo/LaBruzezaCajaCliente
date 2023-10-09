/*
 * Created on 26 oct 2016 ( Time 00:56:19 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.labruzeza.colectividades.dao.VentaDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.commons.GenericDAO;
import org.labruzeza.colectividades.modelo.Venta;
import org.labruzeza.colectividades.utils.MiPrinterJob;

/**
 * Venta DAO implementation 
 * 
 * @author Telosys Tools
 *
 */
public class VentaDAOImpl extends GenericDAO<Venta> implements VentaDAO {
	private static final Logger logger = LogManager.getLogger(VentaDAOImpl.class);
	
	private final static String SQL_SELECT = 
		"select idventa, codigo, fecha, codFactura from venta where idventa = ?";

	private final static String SQL_INSERT = 
		"insert into venta ( codigo, fecha, codFactura ) values ( ?, ?, ? )";

	private final static String SQL_UPDATE = 
		"update venta set codigo = ?, fecha = ?, codFactura = ? where idventa = ?";

	private final static String SQL_DELETE = 
		"delete from venta where idventa = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from venta";

	private final static String SQL_COUNT = 
		"select count(*) from venta where idventa = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public VentaDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param idventa;
	 * @return the new instance
	 */
	private Venta newInstanceWithPrimaryKey( Integer idventa ) {
		Venta venta = new Venta();
		venta.setIdventa( idventa );
		return venta ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param idventa;
	 * @return the bean found or null if not found 
	 */
	
	public Venta find( Integer idventa ) {
		Venta venta = newInstanceWithPrimaryKey( idventa ) ;
		if ( super.doSelect(venta) ) {
			return venta ;
		}
		else {
			return null ; // Not found
		}
	}
	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param venta
	 * @return true if found, false if not found
	 */
	
	public boolean load( Venta venta ) {
		return super.doSelect(venta) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param venta
	 */
	
	public Integer insert(Venta venta) {
		Long key = super.doInsertAutoIncr(venta);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param venta
	 * @return
	 */
	
	public int update(Venta venta) {
		return super.doUpdate(venta);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param idventa;
	 * @return
	 */
	
	public int delete( Integer idventa ) {
		Venta venta = newInstanceWithPrimaryKey( idventa ) ;
		return super.doDelete(venta);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param venta
	 * @return
	 */
	
	public int delete( Venta venta ) {
		return super.doDelete(venta);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param idventa;
	 * @return
	 */
	
	public boolean exists( Integer idventa ) {
		Venta venta = newInstanceWithPrimaryKey( idventa ) ;
		return super.doExists(venta);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param venta
	 * @return
	 */
	
	public boolean exists( Venta venta ) {
		return super.doExists(venta);
	}

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database
	 * @return
	 */
	
	public long count() {
		return super.doCountAll();
	}

    //----------------------------------------------------------------------
	@Override
	protected String getSqlSelect() {
		return SQL_SELECT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlInsert() {
		return SQL_INSERT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlUpdate() {
		return SQL_UPDATE ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlDelete() {
		return SQL_DELETE ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCount() {
		return SQL_COUNT ;
	}
    //----------------------------------------------------------------------
	@Override
	protected String getSqlCountAll() {
		return SQL_COUNT_ALL ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, Venta venta) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, venta.getIdventa() ) ; // "idventa" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected Venta populateBean(ResultSet rs, Venta venta) throws SQLException {

		//--- Set data from ResultSet to Bean attributes
		venta.setIdventa(rs.getInt("idventa")); // java.lang.Integer
		if ( rs.wasNull() ) { venta.setIdventa(null); }; // not primitive number => keep null value if any
		venta.setCodigo(rs.getString("codigo")); // java.lang.String
		venta.setFecha(rs.getDate("fecha")); // java.util.Date
		venta.setCodfactura(rs.getInt("codFactura")); // java.lang.Integer
		if ( rs.wasNull() ) { venta.setCodfactura(null); }; // not primitive number => keep null value if any
		return venta ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, Venta venta) throws SQLException {

		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		// "idventa" is auto-incremented => no set in insert		
		setValue(ps, i++, venta.getCodigo() ) ; // "codigo" : java.lang.String
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		setValue(ps, i++, format.format(venta.getFecha()) ) ; // "fecha" : java.util.Date
		setValue(ps, i++, venta.getCodfactura() ) ; // "codFactura" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, Venta venta) throws SQLException {

		//--- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, venta.getCodigo() ) ; // "codigo" : java.lang.String
		//setValue(ps, i++, venta.getFecha() ) ; // "fecha" : java.util.Date
		setValue(ps, i++, venta.getCodfactura() ) ; // "codFactura" : java.lang.Integer
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, venta.getIdventa() ) ; // "idventa" : java.lang.Integer
	}

	
	public long doCountAll(String codigo) {

		long result = 1 ;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select max(codFactura) as codFactura from venta where codigo = ?");
			ps.setString(1, codigo);		
			logger.info(ps);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {				
				result = (rs.getLong(1) + 1);
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
	
	public int getUltimo(String codigo) {

		int result = 0 ;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select idVenta from venta where codigo= ? order by idVenta desc");
			//--- Execute SQL COUNT (without where clause)						
			ps.setString(1, codigo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
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
	
	public int countVenta(String codigo) {

		int result = 0 ;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select count(idVenta) from venta where codigo= ? order by idVenta desc");
			//--- Execute SQL COUNT (without where clause)						
			ps.setString(1, codigo);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
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
	
	public Venta getPrevio(String codigo, int nroFactura) {
		Venta result = null;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select * from venta where codigo = ? and codFactura < ? order by idVenta desc");
			ps.setString(1, codigo);	
			ps.setInt(2, nroFactura);					
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = new Venta();
				result = populateBean(rs, result);
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
	
	public long nextId(String codigo) {

		long result = 0 ;
		Connection conn = null;
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement("select codFactura from venta where codigo = ? order by idVenta desc");
			ps.setString(1, codigo);				
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				result = rs.getLong(1) + 1;
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

	@Override
	protected String getSqlLoadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Venta populateBeanAll(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
