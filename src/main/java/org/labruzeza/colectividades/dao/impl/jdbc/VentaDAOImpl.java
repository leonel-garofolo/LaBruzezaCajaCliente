/*
 * Created on 21 oct 2016 ( Time 11:36:55 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.labruzeza.colectividades.modelo.Venta;
import org.labruzeza.colectividades.dao.VentaDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.commons.GenericDAO;

/**
 * Venta DAO implementation 
 * 
 * @author Telosys Tools
 *
 */
public class VentaDAOImpl extends GenericDAO<Venta> implements VentaDAO {

	private final static String SQL_SELECT = 
		"select idventa, codigo, fecha from venta where idventa = ?";

	private final static String SQL_INSERT = 
		"insert into venta ( codigo, fecha ) values ( ?, ? )";

	private final static String SQL_UPDATE = 
		"update venta set codigo = ?, fecha = ? where idventa = ?";

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
	@Override
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
	@Override
	public boolean load( Venta venta ) {
		return super.doSelect(venta) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param venta
	 */
	@Override
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
	@Override
	public int update(Venta venta) {
		return super.doUpdate(venta);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param idventa;
	 * @return
	 */
	@Override
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
	@Override
	public int delete( Venta venta ) {
		return super.doDelete(venta);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param idventa;
	 * @return
	 */
	@Override
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
	@Override
	public boolean exists( Venta venta ) {
		return super.doExists(venta);
	}

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database
	 * @return
	 */
	@Override
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
		return venta ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, Venta venta) throws SQLException {

		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		// "idventa" is auto-incremented => no set in insert		
		setValue(ps, i++, venta.getCodigo() ) ; // "codigo" : java.lang.String
		setValue(ps, i++, venta.getFecha() ) ; // "fecha" : java.util.Date
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, Venta venta) throws SQLException {

		//--- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, venta.getCodigo() ) ; // "codigo" : java.lang.String
		setValue(ps, i++, venta.getFecha() ) ; // "fecha" : java.util.Date
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, venta.getIdventa() ) ; // "idventa" : java.lang.Integer
	}

}
