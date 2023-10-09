DROP TABLE IF EXISTS configuracion;
CREATE TABLE configuracion (
  idConfiguracion IDENTITY AUTO_INCREMENT NOT NULL ,
  nroCaja INTEGER DEFAULT NULL,
  nroFactura INTEGER DEFAULT NULL,
  tipoCaja VARCHAR(2) DEFAULT NULL,
  fecha datetime DEFAULT NULL,
  titulo VARCHAR(8000) DEFAULT NULL,
  subTitulo VARCHAR(8000) DEFAULT NULL,
  parrafoDetalle VARCHAR(8000) DEFAULT NULL,
  parrafoDireccion VARCHAR(8000) DEFAULT NULL,
  parrafoOrganizacion VARCHAR(8000) DEFAULT NULL,
  parrafoPromos VARCHAR(8000) DEFAULT NULL,
  parrafoMensaje VARCHAR(8000) DEFAULT NULL,
  PRIMARY KEY (idConfiguracion)
);

DROP TABLE IF EXISTS producto;
CREATE TABLE producto (
  idproducto IDENTITY AUTO_INCREMENT NOT NULL,
  nombre VARCHAR(90) DEFAULT NULL,
  precio DECIMAL(9,2) DEFAULT NULL,
  orden INTEGER DEFAULT NULL,
  PRIMARY KEY (idproducto)
);

DROP TABLE IF EXISTS venta;
CREATE TABLE venta (
  idventa IDENTITY AUTO_INCREMENT NOT NULL,
  codigo VARCHAR(45) DEFAULT NULL,
  fecha datetime DEFAULT NULL,
  codFactura INTEGER DEFAULT NULL,
  PRIMARY KEY (idventa)
);

DROP TABLE IF EXISTS lineadeventa;
CREATE TABLE lineadeventa (
  idLineaDeVenta IDENTITY AUTO_INCREMENT NOT NULL,
  idVenta INTEGER DEFAULT NULL,
  idProducto INTEGER DEFAULT NULL,
  precio DECIMAL(9,2) DEFAULT NULL,
  cantidad INTEGER DEFAULT NULL,
  PRIMARY KEY (idLineaDeVenta),
  FOREIGN KEY (idProducto) REFERENCES producto (idproducto) ON DELETE NO ACTION ON UPDATE NO ACTION,
  FOREIGN KEY (idVenta) REFERENCES venta (idventa) ON DELETE NO ACTION ON UPDATE NO ACTION
);

DROP TABLE IF EXISTS vcaja;
CREATE VIEW vcaja AS SELECT
 '1' AS codigo,
 1 AS idProducto,
 1 AS nombre,
 1 AS cantidad,
 1 AS subtotal;

DROP TABLE IF EXISTS versionado;
CREATE TABLE versionado (
    idversionado  IDENTITY AUTO_INCREMENT NOT NULL,
    numero_sql INTEGER NOT NULL,
    update_sql DATETIME NULL,
    PRIMARY KEY (idversionado)
);