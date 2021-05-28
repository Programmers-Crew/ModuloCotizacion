DELIMITER $$
	create procedure SpCostoPromedio(idBuscado varchar(50), precioNuevo double)
		begin
			update Productos as p
				set p.costoAntiguo = p.precioCosto,
					p.precioCosto = (p.costoAntiguo + precioNuevo)/2
				where productoId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpCostoPromedioSinCantidad(idBuscado varchar(50), precioNuevo double)
		begin
			update Productos as p
				set p.costoAntiguo = p.precioCosto,
					p.precioCosto =  precioNuevo
				where productoId = idBuscado;
        end $$
DELIMITER ;

# QUERYS PARA REALIZAR EL CARDEX

DELIMITER $$
	create procedure SpGenerarCardex(prodId varchar(50))
		begin 
			SELECT DISTINCT  c.fechaCardex, c.noFacCardex,tc.idTipoDesc ,c.saldoCardex,c.entradaCardex, c.totalCardex, p.productoDesc, td.DescTipoDocumento
			from cardex as c
			inner join tipocardex as tc
				on tc.idTipoCardex = c.tipoCardex
			inner join productos as p
				on p.productoId = producto
			inner join tipodocumento as td
				on td.idTipoDocumento = c.tipoDocumento
			WHERE p.productoId = prodId or p.productoDesc = prodId 
            order by c.idCardex desc
;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpGenerarCardexProd(prodId varchar(30))
		begin 
			SELECT DISTINCT  c.fechaCardex, c.noFacCardex,tc.idTipoDesc ,c.saldoCardex, c.entradaCardex,c.totalCardex, p.productoDesc, td.DescTipoDocumento, pr.proveedorNombre
			from cardex as c
			inner join tipocardex as tc
				on tc.idTipoCardex = c.tipoCardex
			inner join productos as p
				on p.productoId = producto
			inner join proveedores as pr
				on pr.proveedorId = p.proveedorId
			inner join tipodocumento as td
				on td.idTipoDocumento = c.tipoDocumento
			WHERE p.productoId = prodId or p.productoDesc = prodId
            order by c.idCardex desc
;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpGenerarCardexFechaProd(prodId varchar(30), inicio date, finalFecha date)
		begin 
			SELECT DISTINCT  c.fechaCardex, c.noFacCardex,tc.idTipoDesc ,c.saldoCardex, c.entradaCardex,c.totalCardex, p.productoDesc, td.DescTipoDocumento
			from cardex as c
			inner join tipocardex as tc
				on tc.idTipoCardex = c.tipoCardex
			inner join productos as p
				on p.productoId = producto
			inner join tipodocumento as td
				on td.idTipoDocumento = c.tipoDocumento
			WHERE p.productoId = prodId or p.productoDesc = prodId and c.fechaCardex BETWEEN inicio AND finalFecha
            order by c.idCardex desc;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpGenerarCardexFecha(prodId varchar(30), inicio date, final date)
		begin 
			SELECT DISTINCT  c.fechaCardex, c.noFacCardex,tc.idTipoDesc ,c.saldoCardex, c.entradaCardex,c.totalCardex, p.productoDesc, td.DescTipoDocumento
			from cardex as c
			inner join tipocardex as tc
				on tc.idTipoCardex = c.tipoCardex
			inner join productos as p
				on p.productoId = producto
			inner join tipodocumento as td
				on td.idTipoDocumento = c.tipoDocumento
			WHERE p.productoId = prodId and c.fechaCardex BETWEEN inicio AND final
                        order by c.idCardex desc
;
        end $$
DELIMITER ;
drop procedure SpAgregarCardexFac
DELIMITER $$
	create procedure SpAgregarCardexFac(fecha date, nombre varchar(60),NoFac int, tipo int, cantidad int, documento int(5))
		begin
			insert into cardex (fechaCardex,noFacCardex,tipoCardex,saldoCardex, totalCardex, producto, tipoDocumento)
				select fecha, noFac, tipo,cantidad,cantidad-ip.inventarioProductoCant, p.productoId, documento
					from inventarioproductos as ip
						inner join productos as p
							on ip.productoId = p.productoId
						where p.productoDesc = nombre;
        end $$
DELIMITER ;
 

DELIMITER $$
	create procedure SpAgregarCardexFacUpdate(nombre varchar(60),tipo int, cantidad int, idBuscado int, documento int(5))
		begin
			insert into cardex (fechaCardex,noFacCardex,tipoCardex,saldoCardex, totalCardex, producto,tipoDocumento )
				select creditos.creaditoFechaInicio, creditos.noFactura, tipo,cantidad,inventarioproductos.inventarioProductoCant, p.productoId,documento
					from inventarioproductos
						inner join productos as p					
							on inventarioproductos.productoId = p.productoId,
					Creditos
						where (p.productoDesc = nombre) and (creditos.noFactura = idBuscado);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpLlenarCmbCardex()
		begin
			select DISTINCT p.productoId, p.productoDesc
				from productos as p
                inner join cardex as c
					on c.producto = p.productoId;
        end $$
DELIMITER ;


#Cambio solicitado 05-05-21
#Guardar datos de factura, clientes y prod en un backup.

create table BackupFacturacionF(
	idDetalle int auto_increment,
    numeroFac varchar(50) ,
    serieFac varchar(50),
    tipoFac varchar(50),
    PRIMARY KEY (idDetalle)
);

create table BackupFacturacionC(
	idDetalleCliente int auto_increment,
    nitCliente varchar(19),
    nombreCliente varchar(50),
	direccionCliente varchar(100),
    PRIMARY KEY (idDetalleCliente)
);

create table BackupFacturacionP(
	idDetalleProductos int auto_increment,
    nombreProducto varchar(50),
	precioProducto varchar(50),
    existenciasProducto varchar(50),
    proveedorProducto varchar(50),
    cantidadProducto varchar(50),
    PRIMARY KEY (idDetalleProductos)
);

#backUp Fac
DELIMITER $$
	create procedure AgregarBackupFacturacionF(numero varchar(50), serie varchar(50), tipo varchar(50))
		begin 
			insert into BackupFacturacionF(numeroFac,serieFac,tipoFac)
				values(numero, serie, tipo);
        end $$
DELIMITER ;


DELIMITER $$
	create procedure ListarBackupFacturacionF()
		begin 
			select numeroFac,serieFac,tipoFac
				from BackupFacturacionF;
        end $$
DELIMITER ;

#backuo cliente 
DELIMITER $$
	create procedure SpAgregarBackupFacturacionC(nit varchar(19), nombre varchar(50), direccion varchar(100))
		begin 
			insert into BackupFacturacionC(nitCliente,nombreCliente,direccionCliente)
				values(nit, nombre, direccion);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpListarBackupFacturacionC()
		begin 
			select nitCliente,nombreCliente,direccionCliente
				from BackupFacturacionC;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpAgregarBackupFacturacionP(nombre varchar(50), precio varchar(50), existencia varchar(50), proveedor varchar(50), cantidad varchar(50))
		begin 
			insert into BackupFacturacionP(nombreProducto,precioProducto,existenciasProducto,proveedorProducto,cantidadProducto)
            values(nombre, precio, existencia, proveedor, cantidad);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpListarBackupFacturacionP()
		begin 
			select nombreProducto,precioProducto,existenciasProducto,proveedorProducto,cantidadProducto
            from BackupFacturacionP;
        end $$
DELIMITER ;

#Eliminar todos los backUp
DELIMITER $$
	create procedure SpElimarBackFCP()
		begin
			delete from backupfacturacionp;
            delete from backupfacturacionf;
            delete from backupfacturacionc;
        end $$
DELIMITER ;
SET SQL_SAFE_UPDATES = 0;
call SpElimarBackFCP();

DELIMITER $$	
	create procedure SpBuscarProveedorCardex(nombre varchar(50))
		begin 
			select proveedorNombre
				from proveedores as p
					inner join productos as pr
						on pr.productoId = p.proveedorId
			where (productoDesc = nombre) and (productoId = nombre );
        end $$
DELIMITER 

DELIMITER $$
	create procedure SpUpdateClientes(nit varchar(10)m cliente varchar(20))
DELIMITER ;