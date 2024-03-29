/*
 * Created on 27 oct 2016 ( Time 01:08:40 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao.impl.jdbc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.labruzeza.colectividades.modelo.Configuracion;
import org.labruzeza.colectividades.dao.ConfiguracionDAO;
import org.labruzeza.colectividades.dao.impl.jdbc.commons.GenericDAO;

/**
 * Configuracion DAO implementation 
 * 
 * @author Telosys Tools
 *
 */
public class ConfiguracionDAOImpl extends GenericDAO<Configuracion> implements ConfiguracionDAO {

	private final static String SQL_SELECT = 
		"select idConfiguracion, fecha, nroCaja, tipoCaja, nroFactura, titulo, subTitulo, parrafoDetalle, parrafoDireccion, parrafoOrganizacion, parrafoPromos, parrafoMensaje from configuracion where idConfiguracion = ?";

	private final static String SQL_INSERT = 
		"insert into configuracion ( nroCaja, fecha, tipoCaja, nroFactura, titulo, subTitulo, parrafoDetalle, parrafoDireccion, parrafoOrganizacion, parrafoPromos, parrafoMensaje ) values ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )";

	private final static String SQL_UPDATE = 
		"update configuracion set nroCaja = ?, tipoCaja = ?, fecha = ?, nroFactura=?, titulo=?, subTitulo=?, parrafoDetalle=?, parrafoDireccion=?, parrafoOrganizacion=?, parrafoPromos=?, parrafoMensaje=? where idConfiguracion = ?";

	private final static String SQL_DELETE = 
		"delete from configuracion where idConfiguracion = ?";

	private final static String SQL_COUNT_ALL = 
		"select count(*) from configuracion";

	private final static String SQL_COUNT = 
		"select count(*) from configuracion where idConfiguracion = ?";

    //----------------------------------------------------------------------
	/**
	 * DAO constructor
	 */
	public ConfiguracionDAOImpl() {
		super();
	}

	//----------------------------------------------------------------------
	/**
	 * Creates a new instance of the bean and populates it with the given primary value(s)
	 * @param idconfiguracion;
	 * @return the new instance
	 */
	private Configuracion newInstanceWithPrimaryKey( Integer idconfiguracion ) {
		Configuracion configuracion = new Configuracion();
		configuracion.setIdconfiguracion( idconfiguracion );
		return configuracion ;
	}

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @param idconfiguracion;
	 * @return the bean found or null if not found 
	 */
	public Configuracion find( Integer idconfiguracion ) {
		Configuracion configuracion = newInstanceWithPrimaryKey( idconfiguracion ) ;
		if ( super.doSelect(configuracion) ) {
			return configuracion ;
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
	 * @param configuracion
	 * @return true if found, false if not found
	 */
	public boolean load( Configuracion configuracion ) {
		return super.doSelect(configuracion) ;
	}
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param configuracion
	 */
	public Integer insert(Configuracion configuracion) {
		Long key = super.doInsertAutoIncr(configuracion);
		return key.intValue();
	}

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param configuracion
	 * @return
	 */
	public int update(Configuracion configuracion) {
		return super.doUpdate(configuracion);
	}	

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @param idconfiguracion;
	 * @return
	 */
	public int delete( Integer idconfiguracion ) {
		Configuracion configuracion = newInstanceWithPrimaryKey( idconfiguracion ) ;
		return super.doDelete(configuracion);
	}

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param configuracion
	 * @return
	 */
	public int delete( Configuracion configuracion ) {
		return super.doDelete(configuracion);
	}

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @param idconfiguracion;
	 * @return
	 */
	public boolean exists( Integer idconfiguracion ) {
		Configuracion configuracion = newInstanceWithPrimaryKey( idconfiguracion ) ;
		return super.doExists(configuracion);
	}
    //----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param configuracion
	 * @return
	 */
	public boolean exists( Configuracion configuracion ) {
		return super.doExists(configuracion);
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
	protected void setValuesForPrimaryKey(PreparedStatement ps, int i, Configuracion configuracion) throws SQLException {
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, configuracion.getIdconfiguracion() ) ; // "idConfiguracion" : java.lang.Integer
	}

    //----------------------------------------------------------------------
	@Override
	protected Configuracion populateBean(ResultSet rs, Configuracion configuracion) throws SQLException {

		//--- Set data from ResultSet to Bean attributes
		configuracion.setIdconfiguracion(rs.getInt("idConfiguracion")); // java.lang.Integer
		if ( rs.wasNull() ) { configuracion.setIdconfiguracion(null); }; // not primitive number => keep null value if any
		configuracion.setNrocaja(rs.getInt("nroCaja")); // java.lang.Integer
		if ( rs.wasNull() ) { configuracion.setNrocaja(null); }; // not primitive number => keep null value if any
		configuracion.setTipocaja(rs.getString("tipoCaja")); // java.lang.String		
		if ( rs.wasNull() ) { configuracion.setFecha(null); }; // not primitive number => keep null value if any
		configuracion.setFecha(rs.getDate("fecha")); // java.lang.String
		configuracion.setNrofactura(rs.getInt("nroFactura")); 		
		configuracion.setTitulo(rs.getString("titulo"));
		configuracion.setSubTitulo(rs.getString("subTitulo"));
		configuracion.setParrafoDetalle(rs.getString("parrafoDetalle"));
		configuracion.setParrafoDirreccion(rs.getString("parrafoDireccion"));
		configuracion.setParrafoOrganizacion(rs.getString("parrafoOrganizacion"));		
		configuracion.setParrafoPromos(rs.getString("parrafoPromos"));
		configuracion.setParrafoMensaje(rs.getString("parrafoMensaje"));
		
		return configuracion ;
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForInsert(PreparedStatement ps, int i, Configuracion configuracion) throws SQLException {

		//--- Set PRIMARY KEY and DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		// "idConfiguracion" is auto-incremented => no set in insert		
		setValue(ps, i++, configuracion.getNrocaja() ) ; // "nroCaja" : java.lang.Integer
		setValue(ps, i++, configuracion.getFecha()) ; 
		setValue(ps, i++, configuracion.getTipocaja() ) ; // "tipoCaja" : java.lang.String
		setValue(ps, i++, configuracion.getNrofactura() ) ;
		
		setValue(ps, i++, configuracion.getTitulo() ) ;
		setValue(ps, i++, configuracion.getSubTitulo() ) ;
		setValue(ps, i++, configuracion.getParrafoDetalle() ) ;
		setValue(ps, i++, configuracion.getParrafoDirreccion() ) ;
		setValue(ps, i++, configuracion.getParrafoOrganizacion() ) ;	
		setValue(ps, i++, configuracion.getParrafoPromos() ) ;	
		setValue(ps, i++, configuracion.getParrafoMensaje()) ;	
		
	}

    //----------------------------------------------------------------------
	@Override
	protected void setValuesForUpdate(PreparedStatement ps, int i, Configuracion configuracion) throws SQLException {

		//--- Set DATA from bean to PreparedStatement ( SQL "SET x=?, y=?, ..." )
		setValue(ps, i++, configuracion.getNrocaja() ) ; // "nroCaja" : java.lang.Integer
		setValue(ps, i++, configuracion.getTipocaja() ) ; // "tipoCaja" : java.lang.String
		//--- Set PRIMARY KEY from bean to PreparedStatement ( SQL "WHERE key=?, ..." )
		setValue(ps, i++, configuracion.getFecha()) ; // "fecha" : java.lang.Date
		setValue(ps, i++, configuracion.getNrofactura() ) ;		
		setValue(ps, i++, configuracion.getTitulo() ) ;
		setValue(ps, i++, configuracion.getSubTitulo() ) ;
		setValue(ps, i++, configuracion.getParrafoDetalle() ) ;
		setValue(ps, i++, configuracion.getParrafoDirreccion() ) ;
		setValue(ps, i++, configuracion.getParrafoOrganizacion() ) ;
		setValue(ps, i++, configuracion.getParrafoPromos() ) ;	
		setValue(ps, i++, configuracion.getParrafoMensaje()) ;	
		
		setValue(ps, i++, configuracion.getIdconfiguracion() ) ; // "idConfiguracion" : java.lang.Integer
		
	}

	@Override
	protected String getSqlLoadAll() {
		return null;
	}

	@Override
	protected Configuracion populateBeanAll(ResultSet rs) throws SQLException {
		return null;
	}

}
