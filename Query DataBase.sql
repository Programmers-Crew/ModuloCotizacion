create database GrupoAlcon;
use GrupoAlcon;

create table Clientes(
	clienteId	int(5)  UNSIGNED ZEROFILL primary key auto_increment,
	clienteNit	varchar(19) unique not null,
	clienteNombre varchar(50) not null,
	clienteDireccion varchar(100) not null DEFAULT 'Ciudad de Guatemala'
);


create table EstadoProductos(
	estadoProductoId tinyint(1) primary key auto_increment,
    estadoProductoDesc varchar(100) unique not null
);


create table Proveedores(
	proveedorId varchar(7) primary key,
    proveedorNombre varchar(50) unique not null,
	proveedorTelefono varchar(8) unique not null,
    proveedorNit varchar(20) unique not null
);


create table CategoriaProductos(
	categoriaId varchar(7) primary key,
    categoriaNombre varchar(50) unique not null
);


create table tipoProducto(
	tipoProdId int auto_increment primary key,
    tipoProdDesc varchar(20) not null unique
);


create table InventarioProductos(
	productoId	varchar(7) primary key,
    productoDesc varchar(50) not null,
	proveedorId varchar(7) not null,
    categoriaId varchar(7) not null,
    productoCostoAntiguo decimal(10,2),
	prductoCosto decimal(10,2) not null,
    productoPrecio decimal(10,2) not null,
    tipoProductoId int not null,
    inventarioProductoCant double  not null,
    estadoProductoId tinyint(1) not null,    
	CONSTRAINT FK_EstadoProductoInventario FOREIGN KEY (estadoProductoId) REFERENCES EstadoProductos(estadoProductoId),
	CONSTRAINT FK_ProveedorProductos FOREIGN KEY (proveedorId) REFERENCES Proveedores(proveedorId),
    CONSTRAINT FK_CategoriaProductos FOREIGN KEY (categoriaId) REFERENCES CategoriaProductos(categoriaId),
    CONSTRAINT FK_TipoProductos FOREIGN KEY (tipoProductoId) REFERENCES tipoProducto(tipoProdId)
);


create table TipoUsuario(
	tipoUsuarioId tinyint(1) primary key auto_increment,
    tipoUsuario varchar(20) not null unique
);


create table Usuarios(
	usuarioId int(5) UNSIGNED ZEROFILL primary key not null auto_increment,
    usuarioNombre varchar(30) not null unique,
    usuarioPassword varchar(40)  not null,
    tipoUsuarioId tinyint(1) not null,
    CONSTRAINT FK_UsuariosTipoUsuario FOREIGN KEY (tipoUsuarioId) REFERENCES TipoUsuario(tipoUsuarioId)
);


create table FacturaDetalle(
	facturaDetalleId int(5) UNSIGNED ZEROFILL primary key,
    productoId varchar(7) not null,
    cantidad double  not null, 
    totalParcial decimal(10,2),
	CONSTRAINT FK_productoFacDetalle FOREIGN KEY (productoId) REFERENCES InventarioProductos(productoId)
);



create table FacturaDetalleBackUp(
	facturaDetalleIdBackup int(5)UNSIGNED ZEROFILL primary key auto_increment ,
    productoIdBackup varchar(7) not null,
    cantidadBackup double  not null, 
    totalParcialBackup decimal(10,2) not null,
    
	CONSTRAINT FK_productoFacDetalleBackup FOREIGN KEY (productoIdBackup) REFERENCES InventarioProductos(productoId)
);


create table TipoFactura(
	tipoFactura int not null,
    tipoFacturaDesc varchar(25),
    PRIMARY KEY (tipoFactura) 
);

create table EstadoFactura(
	estadoFactura int not null,
    estadoFacturaDesc varchar(25),
    PRIMARY KEY (estadoFactura) 
);

create table Facturas(
	codigo int(5) UNSIGNED ZEROFILL PRIMARY KEY auto_increment,
    facturaSerie varchar(5),
	facturaId int(5) UNSIGNED ZEROFILL not null,
	facturaDetalleId int(5) UNSIGNED ZEROFILL not null, 
    clienteId int(5) UNSIGNED ZEROFILL not null,
    facturaFecha date not null,
    usuarioId int(5) UNSIGNED ZEROFILL not null,
    facturaTotalNeto decimal(10,2) not null,
    facturaTotalIva decimal(10,2) not null,
    facturaTotal decimal(10,2) not null,
	facturaTipo int,
    estadoFactura
    int default 1,
	CONSTRAINT FK_facturaDetalle FOREIGN KEY (facturaDetalleId) REFERENCES facturadetalle(facturaDetalleId),
	CONSTRAINT FK_clienteFactura FOREIGN KEY (clienteId) REFERENCES Clientes(clienteId),
	CONSTRAINT FK_usuarioFactura FOREIGN KEY (usuarioId) REFERENCES Usuarios(usuarioId),
	CONSTRAINT FK_tipoFactura FOREIGN KEY (facturaTipo) REFERENCES TipoFactura(tipoFactura),
    CONSTRAINT FK_estadoFactura FOREIGN KEY (estadoFactura) REFERENCES EstadoFactura(estadoFactura)
);



-- MODULO COTIZACION



-- MODULO DE PRODUCCION