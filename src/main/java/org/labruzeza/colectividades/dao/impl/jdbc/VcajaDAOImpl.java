/*
 * Created on 26 oct 2016 ( Time 15:51:33 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.labruzeza.colectividades.modelo.Vcaja;
import org.labruzeza.colectividades.dao.VcajaDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.commons.GenericDAO;

/**
 * Vcaja DAO implementation 
 * 
 * @author Telosys Tools
 *
 */
public class VcajaDAOImpl extends GenericDAO<Vcaja> implements VcajaDAO {

	private final static String SQL_SELECT = 
		"select idProducto, nombre, cantidad, subtotal from vcaja where ";

	private final static String SQL_INSERT = 
		"insert into vcaja ( idProducto, nombre, cantidad, subtotal ) values ( ?, ?, ?, ? )";

	private final static String SQL_UPDATE = 
		"update vcaja set idProducto = ?, nombre = ?, cantidad = ?, subtotal = ? where ";

	private final static String SQL_DELETE = 
		"delete from vcaja where ";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from vcaja";

	private final static String SQL_COUNT = 
		"select count(*) from vcaja where ";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public VcajaDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @return the new instance
	 */
	private Vcaja newInstanceWithPrimaryKey(  ) {
		Vcaja vcaja = new Vcaja();
		return vcaja ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @return the bean found or null if not found 
	 */
	@Override
	public Vcaja find(  ) {
		Vcaja vcaja = newInstanceWithPrimaryKey(  ) ;
		if ( super.doSelect(vcaja) ) {
			return vcaja ;
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
	 * @param vcaja
	 * @return true if found, false if not found
	 */
	@Override
	public boolean load( Vcaja vcaja ) {
		return super.doSelect(vcaja) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param vcaja
	 */
	@Override
	public void insert(Vcaja vcaja) {
		super.doInsert(vcaja);
	}	

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param vcaja
	 * @return
	 */
	@Override
	public int update(Vcaja vcaja) {
		return super.doUpdate(vcaja);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @return
	 */
	@Override
	public int delete(  ) {
		Vcaja vcaja = newInstanceWithPrimaryKey(  ) ;
		return super.doDelete(vcaja);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param vcaja
	 * @return
	 */
	@Override
	public int delete( Vcaja vcaja ) {
		return super.doDelete(vcaja);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @return
	 */
	@Override
	public boolean exists(  ) {
		Vcaja vcaja = newInstanceWithPrimaryKey(  ) ;
		return super.doExists(vcaja);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param vcaja
	 * @return
	 */
	@Override
	public boolean exists( Vcaja vcaja ) {
		return super.doExists(vcaja);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, Vcaja vcaja) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
	}

    //----------------------------------------------------------------------
	@Override
	protected Vcaja populateBean(ResultSet rs, Vcaja vcaja) throws SQLException {

		//--- Set data from ResultSet to Bean attributes
		vcaja.setCodigo(rs.getString("codigo")); // java.lang.String
		vcaja.setIdproducto(rs.getInt("idProducto")); // java.lang.Integer
		if ( rs.wasNull() ) { vcaja.setIdproducto(null); }; // not primitive number => keep null value if any
		vcaja.setNombre(rs.getString("nombre")); // java.lang.String
		vcaja.setCantidad(rs.getBigDecimal("cantidad")); // java.math.BigDecimal
		if ( rs.wasNull() ) { vcaja.setCantidad(null); }; // not primitive number => keep null value if any
		vcaja.setSubtotal(rs.getBigDecimal("subtotal")); // java.math.BigDecimal
		if ( rs.wasNull() ) { vcaja.setSubtotal(null); }; // not primitive number => keep null value if any
		return vcaja ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, Vcaja vcaja) throws SQLException {

		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, vcaja.getIdproducto() ) ; // "idProducto" : java.lang.Integer
		setValue(ps, i++, vcaja.getNombre() ) ; // "nombre" : java.lang.String
		setValue(ps, i++, vcaja.getCantidad() ) ; // "cantidad" : java.math.BigDecimal
		setValue(ps, i++, vcaja.getSubtotal() ) ; // "subtotal" : java.math.BigDecimal
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, Vcaja vcaja) throws SQLException {

		//--- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, vcaja.getIdproducto() ) ; // "idProducto" : java.lang.Integer
		setValue(ps, i++, vcaja.getNombre() ) ; // "nombre" : java.lang.String
		setValue(ps, i++, vcaja.getCantidad() ) ; // "cantidad" : java.math.BigDecimal
		setValue(ps, i++, vcaja.getSubtotal() ) ; // "subtotal" : java.math.BigDecimal
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
	}

	@Override
	protected String getSqlLoadAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Vcaja populateBeanAll(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vcaja> load(String codigo) {
		Connection conn = null;
		 
		try {
			conn = getConnection();
			PreparedStatement ps = conn.prepareStatement( "select * from vcaja where codigo = ? order by idProducto asc" );		
			List<Vcaja> array = new ArrayList<Vcaja>();
			ps.setString(1, codigo);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				array.add(populateBean(rs, new Vcaja()));				
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

}
