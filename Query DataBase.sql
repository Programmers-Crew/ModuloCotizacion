create database GrupoAlcon;
use GrupoAlcon;

create table tipoCliente(
	tipoClienteId int(5) UNSIGNED ZEROFILL primary key auto_increment,
    tipoClienteDesc varchar(50) unique not null,
    tipoClienteDescuento double not null
);

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
create table factorVenta(
	factorVentaId int(5) UNSIGNED ZEROFILL primary key auto_increment,
    factorVentaDesc varchar(50) unique not null,
    factorVentaDescuento double not null
);

create table tipoDocumeto(
	tipoDocumentoId int(5) UNSIGNED ZEROFILL primary key auto_increment,
    tipoDocumentoDesc varchar(50) unique not null
);

create table camposEspeciales(
	campoId int(5) UNSIGNED ZEROFILL primary key auto_increment,
	campoPintura double not null,
    campoPasta double not null,
    campoOtros double not null
);

create table OrdenDeVenta(

	ordenId int(10) UNSIGNED ZEROFILL primary key auto_increment,
    ordenCliente int(5) UNSIGNED ZEROFILL not null ,
    ordenMensajero int(5) UNSIGNED ZEROFILL not null,
    ordenImg varchar(100) not null,
    ordenTipoDoc int(5) UNSIGNED ZEROFILL not null,
    ordenFecha date not null,
    ordenCantida double  not null,
    ordenModeloRef varchar(100) not null,
    ordenProducto varchar(7) not null ,
    ordenTipoPrecio varchar(50) not null,
	ordenAlto double not null,
    ordenAncho double not null,
    ordenLargo double not null,
    ordenFacVenta int(5) UNSIGNED ZEROFILL not null,
    ordenDesc varchar(100) not null,
    ordenDescuento double not null,
    ordenPrecioU double not null,
    ordentTotal double not null,
    ordenCamposEspeciales int(5) UNSIGNED ZEROFILL,
	CONSTRAINT FK_ordenCliente FOREIGN KEY (ordenCliente) REFERENCES Clientes(clienteId),
    CONSTRAINT FK_ordenVendedor FOREIGN KEY (ordenMensajero) REFERENCES Usuarios(usuarioId),
    CONSTRAINT FK_ordenTipoDocumento FOREIGN KEY (ordenTipoDoc) REFERENCES tipoDocumeto(tipoDocumentoId),
	CONSTRAINT FK_ordenProducto FOREIGN KEY (ordenProducto) REFERENCES InventarioProductos(productoId),
	CONSTRAINT FK_ordenFacVenta FOREIGN KEY (ordenFacVenta) REFERENCES factorVenta(factorVentaId),
	CONSTRAINT FK_ordenCampoEspeciales FOREIGN KEY (ordenCamposEspeciales) REFERENCES camposEspeciales(campoId)
);

create table modoPago(
	modoPagoId int(5) UNSIGNED ZEROFILL not null,
    modoPagoDesc varchar(20) not null
);


-- MODULO DE PRODUCCION
create table estadoProduccion(
	estadoProduccionId int(5) UNSIGNED ZEROFILL not null,
    estadoProduccionDesc varchar(20) not null
);

create table Produccion(
	produccionId int(5) UNSIGNED ZEROFILL not null,
    produccionCotizacion int(5) UNSIGNED ZEROFILL not null,
    produccionEstado int(5) UNSIGNED ZEROFILL not null,
    produccionOperador int(5) UNSIGNED ZEROFILL not null,
    produccionFechaEntrada date not null,
    produccionFechaSalida date not null,
    produccionDiasRestantes int not null,
    
	CONSTRAINT FK_produccionOrden FOREIGN KEY (produccionCotizacion) REFERENCES OrdenDeVenta(ordenId),
	CONSTRAINT FK_produccionEstado FOREIGN KEY (produccionEstado) REFERENCES estadoProduccion(estadoProduccionId),
	CONSTRAINT FK_produccionOperador FOREIGN KEY (produccionOperador) REFERENCES Usuarios(usuarioId)

);


create table pendienteFacturar(
	pendienteId int(5) UNSIGNED ZEROFILL primary key auto_increment,
	ordenId int(10) UNSIGNED ZEROFILL not null,
    modoPagoId int(5) UNSIGNED ZEROFILL not null,
    montoAbonado double,
    montoPendiente double,
    
	CONSTRAINT FK_pendienteOrden FOREIGN KEY (ordenId) REFERENCES OrdenDeVenta(ordenId),
    CONSTRAINT FK_pendientePago FOREIGN KEY (modoPagoId) REFERENCES modoPago(modoPagoId)
);
