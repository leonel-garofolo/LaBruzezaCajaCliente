package org.labruzeza.colectividades.dao.impl.jdbc.commons;

import org.labruzeza.colectividades.utils.DAOUtil;

public class SchemaBuilder {
  private static final String CONFIGURATION_TABLE =
      "CREATE TABLE configuracion ("
          + "idConfiguracion IDENTITY AUTO_INCREMENT NOT NULL,"
          + "nroCaja INTEGER ,"
          + "tipoCaja VARCHAR(2) ,"
          + "fecha TIMESTAMP,"
          + "html_encabezado VARCHAR(8000) ,"
          + "html_pie VARCHAR(8000) ,"
          + "PRIMARY KEY(idConfiguracion)"
          + ");"
      ;
  private static final String LINE_TABLE =
      "CREATE TABLE lineadeventa ("
          + "idLineaDeVenta IDENTITY AUTO_INCREMENT NOT NULL,"
          + "idVenta INTEGER ,"
          + "idProducto INTEGER ,"
          + "precio DECIMAL ,"
          + "cantidad INTEGER ,"
          + "PRIMARY KEY(idLineaDeVenta)"
          + ");"
      ;

  private static final String PRODUCT_TABLE =
      "CREATE TABLE producto ("
          + "idproducto IDENTITY AUTO_INCREMENT NOT NULL,"
          + "nombre VARCHAR(90) ,"
          + "precio DECIMAL ,"
          + "PRIMARY KEY(idproducto)"
          + ");"
      ;

    public static void initializer(){
      boolean existTable = DAOUtil.existTable("configuracion");
      if(!existTable){
        DAOUtil.initDatabase(CONFIGURATION_TABLE) ;
      }
      if(!DAOUtil.existTable("lineadeventa")){
        DAOUtil.initDatabase(LINE_TABLE) ;
      }
      if(!DAOUtil.existTable("producto")){
        DAOUtil.initDatabase(PRODUCT_TABLE) ;
      }

      if(!existTable){
        DAOUtil.runInitialScript();
      }
    }

}
