/*
 * Created on 26 oct 2016 ( Time 15:51:33 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package org.labruzeza.colectividades.dao;

import java.util.List;

import org.labruzeza.colectividades.modelo.Vcaja;

/**
 * Vcaja DAO interface
 * 
 * @author Telosys Tools
 */
public interface VcajaDAO {

	//----------------------------------------------------------------------
	/**
	 * Finds a bean by its primary key 
	 * @return the bean found or null if not found 
	 */
	public Vcaja find(  ) ;

	//----------------------------------------------------------------------
	/**
	 * Loads the given bean, it is supposed to contains the primary key value(s) in its attribute(s)<br>
	 * If found, the given instance is populated with the values retrieved from the database<br>
	 * If not found, the given instance remains unchanged
	 * @param vcaja
	 * @return true if found, false if not found
	 */
	public boolean load( Vcaja vcaja ) ;
	
    //----------------------------------------------------------------------
	/**
	 * Inserts the given bean in the database 
	 * @param vcaja
	 */
	public void insert(Vcaja vcaja) ;

    //----------------------------------------------------------------------
	/**
	 * Updates the given bean in the database 
	 * @param vcaja
	 * @return
	 */
	public int update(Vcaja vcaja) ;

    //----------------------------------------------------------------------
	/**
	 * Deletes the record in the database using the given primary key value(s) 
	 * @return
	 */
	public int delete(  ) ;

    //----------------------------------------------------------------------
	/**
	 * Deletes the given bean in the database 
	 * @param vcaja
	 * @return
	 */
	public int delete( Vcaja vcaja ) ;

    //----------------------------------------------------------------------
	/**
	 * Checks the existence of a record in the database using the given primary key value(s)
	 * @return
	 */
	public boolean exists(  ) ;

	//----------------------------------------------------------------------
	/**
	 * Checks the existence of the given bean in the database 
	 * @param vcaja
	 * @return
	 */
	public boolean exists( Vcaja vcaja ) ;

    //----------------------------------------------------------------------
	/**
	 * Counts all the records present in the database table
	 * @return
	 */
	public long count() ;

	public List<Vcaja> load(String codigo);

}
