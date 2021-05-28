alter table Facturas change facturaId facturaId int(5) UNSIGNED ZEROFILL unique not null;



DELIMITER $$
	create procedure SpvalidarFactura(serie varchar(5),idFactura int(10))
		begin
			select * from Facturas
				where (facturaSerie = serie) and (facturaId = idFactura);
        end $$ 
DELIMITER ;

DELIMITER $$
	create procedure SpUpdateClientes(nit varchar(9) ,nombre varchar(25), direccion varchar(100))
		begin
			update clientes
				set 
                    clienteNombre = nombre,
                    clienteDireccion = direccion
			where 
				clienteNit = nit;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpBuscarCliente(nit varchar(9))
		begin
			select * from clientes
			where
             (clienteNit = nit);
		end $$
DELIMITER ;

DELIMITER $$
	create procedure SpBuscarCF(nit varchar(9))
		begin
			select * from clientes
			where
             (clienteNit = nit);
		end $$
DELIMITER ;

DELIMITER $$
	create procedure SpUpdateCF(nit varchar(9))
		begin
			update clientes
				set 
                    clienteNombre = "C/F",
                    clienteDireccion = "Ciudad de Guatemala"
			where 
				clienteNit = nit;
        end $$
DELIMITER ;



## Cambios 30-03-2021

DELIMITER $$
	create procedure SpValidarProducto(idbuscado varchar(8))
		begin 
			select * from productos
				where  productoId= idBuscado;
        end $$
DELIMITER ;


DELIMITER $$
	create procedure SpValidarProveedor(idbuscado varchar(8))
		begin 
			select * from proveedores
				where  proveedorId= idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpBuscarProductoProv(nombre varchar(50))
		BEGIN
			select 
				p.proveedorId,
                p.proveedorNombre, 
                pr.productoDesc
			from 
				Proveedores as p
			inner join
				productos as pr
			on pr.proveedorId = p.proveedorId
			where proveedorNombre = nombre
			order by proveedorId asc;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure SpBuscarPrecioProd(nombre varchar(40))
		begin
			select 
				productoPrecio
			from 
				productos
			where productoDesc = nombre;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpBucarCreditosVencidos()
		begin 
			select idCredito
				from creditos
					where creditoDiasRestantes < 0;
        end $$
DELIMITER ;


-- 04 - 04
DELIMITER $$
	create procedure SpListarInventarioProveedores(proveedor varchar(50))
		BEGIN
			select
				p.productoId,
                ip.inventarioProductoCant,
                pr.proveedorNombre,
                p.productoDesc,
                ep.estadoProductoDesc,
                p.precioCosto,
                tp.tipoProdDesc
		from
			InventarioProductos as ip
		inner join Productos as p
			on ip.productoId = p.productoId
		inner join EstadoProductos as ep
			on ip.estadoProductoId = ep.estadoProductoId
		inner join Proveedores as pr
			on p.proveedorId = pr.proveedorId
		inner join tipoproducto as tp
			on p.tipoProductoId = tp.tipoProdId
		where pr.proveedorNombre = proveedor
		order by 
			p.productoId ASC;
        END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE SpBuscarFacCredito(noFac varchar(10))
	BEGIN
		SELECT c.idCredito, creditoMonto
        FROM creditos as c
        WHERE noFac = c.noFactura;
    END $$
DELIMITER ;

DELIMITER $$
	CREATE PROCEDURE SpActualizarCreditoInventario(monto double, nofac varchar(10))
		BEGIN
			UPDATE creditos 
            SET creditoMonto = monto
            where noFactura = nofac;
        END $$

DELIMITER ;

DELIMITER $$
	create procedure SpUpdateDetalleCredito(noFac varchar(10))
		begin
			            
            insert into creditos(noFactura,creaditoFechaInicio,creditoFechaFinal,creditoFechaActual,creditoDiasRestantes,creditoDesc,creditoMonto,creditoEstado,creditoDetalle)
				select c.noFactura,c.creaditoFechaInicio,c.creditoFechaFinal,c.creditoFechaActual,c.creditoDiasRestantes,c.creditoDesc,c.creditoMonto,c.creditoEstado,cdb.idCreditoDetalle
                from creditos as c, creditodetallebackup as cdb
				 where noFactura = nofac
                 ;
		end $$
DELIMITER ;
 