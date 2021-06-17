-- QUERYS TABLE 
DELIMITER $$
	create procedure Sp_AddFactorVenta( facVentaDesc varchar(50), descuento double)
		begin
			insert into FactorVenta(factorVentaDesc, factorVentaDescuento)
				values(facVentaDesc,descuento);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateFactorVenta(idBuscado int(5),facVentaDesc varchar(50), descuento double)
		begin
			update FactorVenta
				set factorVentaDesc = facVentaDesc,
					factorVentaDescuento = descuento
				where factorVentaId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteFactorVenta(idBuscado int(5))
		begin
			delete from  FactorVenta
				where factorVentaId  = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListFactorVenta()
		begin
			select fv.factorVentaId , fv.factorVentaDesc  , fv.factorVentaDescuento 
				from FactorVenta as fv;
        end $$
DELIMITER ;


-- Campos Especiales
DELIMITER $$
	create procedure  Sp_AddCamposEspeciales(nombre varchar(150), precio double)
		begin
			insert into CamposEspeciales(campoNombre,campoPrecio)
				values(nombre, precio);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure  Sp_AddCamposEspecialesCotizacion(nombre varchar(150), precio double,cotizacion int(10))
		begin
			insert into CamposEspeciales(campoNombre,campoPrecio,campoCotizacion)
				values(nombre, precio, cotizacion);
        end $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_UpdateCamposEspeciales(idBuscado int(5),nombre varchar(150), precio double)
		begin
			update CamposEspeciales
				set campoNombre  = nombre,
					campoPrecio = precio
				where campoId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateCamposCotizacion(campoN varchar(150), campo int(10))
		begin
			update CamposEspeciales
				SET campoCotizacion = campo
				where campoNombre = campoN;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteCamposEspeciales(idBuscado int(5))
		begin
			delete from CamposEspeciales
				where campoId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListCamposEspeciales()
		begin 
			select ce.campoId  , ce.campoNombre  , ce.campoPrecio 
				from CamposEspeciales as ce;
        end $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_SearchNameCamposEspeciales( nameCampo varchar(150))
		begin 
			select ce.campoId  , ce.campoNombre  , ce.campoPrecio 
				from CamposEspeciales as ce
                where ce.campoNombre = nameCampo;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_SearchCamposEspeciales( codigo int(5))
		begin 
			select ce.campoId  , ce.campoNombre  , ce.campoPrecio 
				from CamposEspeciales as ce
                where ce.campoId = codigo;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_SearchCamposEspecialescotizacion( codigo int(5))
		begin 
			select ce.campoId  , ce.campoNombre  , ce.campoPrecio 
				from CamposEspeciales as ce
                where ce.campoCotizacion = codigo;
        end $$
DELIMITER ;

-- Tipo Cliente
DELIMITER $$
	create procedure Sp_AddTipoCliente(TipoDesc varchar(50), tipoDescuento double)
		begin
			insert into TipoCliente(tipoClienteDesc  , tipoClienteDescuento)
				values(Tipodesc , tipoDescuento);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateTipoCliente(idBuscado int(5) , tipoDesc varchar(50), tipoDescuento double)
		begin
		update TipoCliente 
			set tipoClienteDesc =  tipoDesc,
				tipoClienteDescuento = tipoDescuento
			where tipoClienteId = idBuscado;
		end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteTipoCliente(idBuscado int)
		begin
			delete from TipoCliente
				where tipoClienteId = idBuscado;
		end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListTipoCliente()
		begin 
			select tc.tipoClienteId  , tc.tipoClienteDesc  , tc.tipoClienteDescuento 
				from TipoCliente as tc;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListTipoClienteC(nameBuscado varchar(50))
		begin 
			select tc.tipoClienteDescuento 
				from TipoCliente as tc
					where TC.tipoClienteDesc = nameBuscado;
        end $$
DELIMITER ;



DELIMITER $$
	create procedure Sp_ListTipoClienteCC(id int)
		begin 
			select tc.tipoClienteId , tipoClienteDesc, tipoClienteDescuento
				from TipoCliente as tc
					where TC.tipoClienteId = id;
        end $$
DELIMITER ; 

-- Cotizacion 
DELIMITER $$
	create procedure Sp_AddCotizacion(codigo int(5), cliente varchar(10), tipoCliente int(5), mesajero int(5), img varchar(50), fecha date,   descuento double, descNeto double, total double)
		begin
			insert into Cotizacion(cotizacionId,cotizacionCliente, cotizacionTipoClienteId, cotizacionMensajero, cotizacionImg, cotizacionFecha,   cotizacionDescuento, cotizacionDescuentoNeto, cotizacionTotal )
				values(codigo , cliente,tipoCliente, mesajero, img, fecha,  descuento, descNeto, total);
        end $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_UpdateCotizacion(idBuscado int(5),cliente int(5), tipoCliente int(5), mesajero int(5), img varchar(50), fecha date, cantidad double, referencia varchar(100), tipoPrecio varchar(50), alto double, ancho double, largo double, facVenta int(5), observaicion varchar(50), descuento double, descNeto double ,precioU double, total double, camposEspeciales int(5))
		begin
			update Cotizacion
				set cotizacionCliente = cliente, 
                cotizacionTipoClienteId = tipoCliente,
                cotizacionMensajero = mesajero,
				cotizacionImg = img,
                cotizacionFecha = fecha,
                cotizacionCantida = cantidad,
                cotizacionModeloRef = referencia,
                cotizacionProducto = producto,
                cotizacionTipoPrecio = tipoPrecio,
                cotizacionAlto = alto,
                cotizacionAncho = ancho,
                cotizacionLargo = largo,
                cotizacionFacVenta = facVenta,
                cotizacionDesc = observaicion,
                cotizacionDescuento = descuento,
                cotizacionPrecioU = precioU,
                cotizacionTotal = total,
                cotizacionCamposEspeciales = camposEspeciales ,
                cotizacionDescuentoNeto = descNeto
			where cotizacionId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateCotizacionTotal(idBuscado int(5),total double, descuento double)
		begin
			update Cotizacion
				set cotizacionDescuentoNeto = descuento,
                cotizacionTotal = total
			where cotizacionId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteCotizacion(idBuscado int(5))
		begin
			delete from Cotizacion
				where cotizacionId  = idBuscado;
        end $$
DELIMITER ;

drop procedure if exists Sp_ListarCotizaciones;
DELIMITER $$
	create procedure Sp_ListarCotizaciones()
		begin 
			select c.cotizacionId, c.cotizacionImg , c.cotizacionFecha,
					c.cotizacionDescuento  , c.cotizacionTotal, c.cotizacionDescuentoNeto,
				   c.cotizacionCliente,
                   cl.clienteNombre,
                   tc.tipoClienteDesc,
                   tc.tipoClienteDescuento,
                   u.usuarioNombre
				from Cotizacion as c
				inner join Clientes as cl
					on c.cotizacionCliente = cl.clienteNit
				inner join TipoCliente as tc
					on c.cotizacionTipoClienteId = tc.tipoClienteId
				inner join Usuarios as u
					on c.cotizacionMensajero = u.usuarioId;
        end $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_SearchCotizaciones( codigo int)
		begin 
			select c.cotizacionId, c.cotizacionImg , c.cotizacionFecha , 
					c.cotizacionDescuento , c.cotizacionTotal, c.cotizacionDescuentoNeto,
				   c.cotizacionCliente,
                   cl.clienteNombre,
                   cl.clienteDireccion,
                   tc.tipoClienteDesc,
                   tc.tipoClienteDescuento,
                   u.usuarioNombre, u.usuarioId
				from Cotizacion as c
				inner join Clientes as cl
					on c.cotizacionCliente = cl.clienteNit
				inner join TipoCliente as tc
					on c.cotizacionTipoClienteId = tc.tipoClienteId
				inner join Usuarios as u
					on c.cotizacionMensajero = u.usuarioId
				where c.cotizacionId=codigo;
        end $$
DELIMITER ;

-- cotizacion detalle y cotizacion detalle backup
DELIMITER $$
	create procedure Sp_AddCotizacionBackup(cantidad double,  alto double, ancho double, largo double, observaicion varchar(50), precioU double, total double)
		begin
			insert into CotizacionDetalleBackup(cotizacionCantida,  cotizacionAlto, cotizacionAncho, cotizacionLargo, cotizacionDesc, cotizacionPrecioU, cotizacionTotalParcial)
				values(cantidad, alto, ancho, largo, observaicion,precioU, total);
        end $$
DELIMITER ;
DELIMITER $$
	create procedure SpTransferirBackupCotizacion(codigo int)
		BEGIN
			insert into cotizaciondetalle(detalleId,cotizacionCantida,cotizacionDesc,cotizacionAlto,cotizacionLargo,cotizacionAncho,cotizacionPrecioU,cotizacionTotalParcial,cotizacionid)
				select backupId,cotizacionCantida,cotizacionDesc,cotizacionAlto, cotizacionLargo,cotizacionAncho,cotizacionPrecioU,cotizacionTotalParcial, codigo
					from CotizacionDetalleBackup;
			delete from CotizacionDetalleBackup where backupId>0;
		END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListCotizacionBackUp()
		begin
			SELECT backupId, cotizacionCantida, cotizacionDesc, cotizacionAlto, cotizacionLargo, cotizacionAncho, cotizacionPrecioU, cotizacionTotalParcial
            FROM  cotizaciondetallebackup;
            
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateBackUpCotizacion(idBuscado int(10), cantidad double, desccrip varchar(100), alto double, largo double, ancho double, precioU double, totalP double)
		begin 
			update cotizaciondetallebackup as cb
				set cotizacionCantida = cantidad,
					cotizacionDesc = desccrip,
                    cotizacionAlto = alto,
                    cotizacionLargo = largo,
                    cotizacionAncho = ancho,
                    cotizacionPrecioU = precioU,
                    cotizacionTotalParcial = totalP
					where backupId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpEliminarBackuCo(idBuscado int(5))
		begin
			delete from cotizaciondetallebackup
				where backupId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure SpEliminarDetalleBack(idBuscado int(5))
		begin
			delete from cotizaciondetalle
				where detalleId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListCotizacionDetalle(codigo int)
		begin
			SELECT detalleId, cotizacionCantida, cotizacionDesc, cotizacionAlto, cotizacionLargo, cotizacionAncho, cotizacionPrecioU, cotizacionTotalParcial,cotizacionid
            FROM  cotizaciondetalle
            where cotizacionid = codigo;
            
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_SearchCotizacionDetalle(codigo int)
		begin
			SELECT detalleId, cotizacionCantida, cotizacionDesc, cotizacionAlto, cotizacionLargo, cotizacionAncho, cotizacionPrecioU, cotizacionTotalParcial,cotizacionid
            FROM  cotizaciondetalle
            where detalleId = codigo;
            
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListCotizacionDetalleS()
		begin
			SELECT detalleId, cotizacionCantida, cotizacionDesc, cotizacionAlto, cotizacionLargo, cotizacionAncho, cotizacionPrecioU, cotizacionTotalParcial,cotizacionid
            FROM  cotizaciondetalle;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateDetalleCotizacion(idBuscado int(10), cantidad double, desccrip varchar(100), alto double, largo double, ancho double, precioU double, totalP double, cotizacion int)
		begin 
			update cotizaciondetalle as cb
				set cotizacionCantida = cantidad,
					cotizacionDesc = desccrip,
                    cotizacionAlto = alto,
                    cotizacionLargo = largo,
                    cotizacionAncho = ancho,
                    cotizacionPrecioU = precioU,
                    cotizacionTotalParcial = totalP
					where detalleId = idBuscado;
                    
				UPDATE cotizacion as c
                set cotizacionDescuentoNeto = ((select sum(cotizacionTotalParcial) from cotizaciondetalle where cotizacionid = cotizacion)+ (select campoPrecio from camposespeciales where campoCotizacion = cotizacion))*cotizacionDescuento,
                 cotizacionTotal = (select sum(cotizacionTotalParcial) from cotizaciondetalle where cotizacionid = cotizacion)+ (select campoPrecio from camposespeciales where campoCotizacion = cotizacion)-cotizacionDescuentoNeto
                 where cotizacionId = cotizacion;
                
        end $$
DELIMITER ;

-- Modo Pago 
DELIMITER $$
	create procedure Sp_AddModoPago( descrip varchar(20))
		begin
			insert into ModoPago(modoPagoDesc)
				value (descrip);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateModoPago(idBuscado int(5), descrip varchar(20))
		begin
			update ModoPago
				set modoPagoDesc = descrip
			where modoPagoId  = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteModoPago(idBuscado int(5))
		begin
			delete from ModoPago
				where modoPagoId  = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListModoPago()
		begin
			select mp.modoPagoId, mp.modoPagoDesc 
				from ModoPago as mp;
        end $$
DELIMITER ;

-- EstadoProduccion

DELIMITER $$
	create procedure Sp_AddEstadoProduccion(codigo int(5),descript varchar(20))
		begin
			insert into EstadoProduccion(estadoProduccionId,estadoProduccionDesc)
				values (codigo,descript);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateEstadoProduccion(idBuscado int(5), descript varchar(20))
		begin 
			update EstadoProduccion
				set estadoProduccionDesc = descript
					where estadoProduccionId  = idBuscado;
        end $$
DELIMITER ;
call Sp_ListEstadoProduccion()
DELIMITER $$
	create procedure Sp_DeleteEstadoProduccion(idBuscado int(5))
		begin 
			delete from EstadoProduccion
            where estadoProduccionId  = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure  Sp_ListEstadoProduccion()
		begin
			select ep.estadoProduccionId  , ep.estadoProduccionDesc 
				from EstadoProduccion as ep;
        end $$
DELIMITER ;


DELIMITER $$
	create procedure  Sp_SearchEstadoProduccionName(name1 varchar(20))
		begin
			select ep.estadoProduccionId  , ep.estadoProduccionDesc 
				from EstadoProduccion as ep where name1 = ep.estadoProduccionDesc ;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure  Sp_SearchEstadoProduccion(codigo int(5))
		begin
			select ep.estadoProduccionId  , ep.estadoProduccionDesc 
				from EstadoProduccion as ep where codigo = ep.estadoProduccionId ;
        end $$
DELIMITER ;

-- Produccion
DELIMITER $$
	create procedure Sp_AddProduccion(cotizacion int(5), estado int(5), operador int(5), entrada date, salida date, restantes int)
		begin 
			insert into Produccion(produccionCotizacion, produccionEstado, produccionOperador, produccionFechaEntrada, produccionFechaSalida, produccionDiasRestantes)
            values(cotizacion, estado, operador, entrada, salida, restantes);
        end $$
DELIMITER ;


DELIMITER $$	
	create procedure Sp_UpdateProduccion(idBuscado int(5),estado int(5),entrada date, salida date, restantes int, operador int)
		begin
			update Produccion
				set 
					produccionEstado = estado,
                    produccionDiasRestantes = restantes,
                    produccionFechaEntrada = entrada,
                    produccionFechaSalida = salida,
                    produccionOperador = operador
				where produccionId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteProduccion(idBuscado int(5))
		begin 
			delete from Produccion
				where produccionId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListProduccion()
		begin 
			select p.produccionId, p.produccionFechaEntrada, p.produccionFechaSalida, p.produccionDiasRestantes, p.produccionCotizacion ,
                e.estadoProduccionDesc, u. usuarioNombre, c.cotizacionTotal
				from Produccion as p
				inner join Estadoproduccion as e
					on p.produccionEstado = e.estadoProduccionId
				inner join Usuarios as u
					on p.produccionOperador = usuarioId
				inner join cotizacion as c
					on c.cotizacionId = produccionCotizacion;
		 end $$
DELIMITER ;



DELIMITER $$
	create procedure Sp_SearchProduccion(codigo int(5))
		begin 
			select p.produccionId, p.produccionFechaEntrada, p.produccionFechaSalida, p.produccionDiasRestantes, p.produccionCotizacion ,
                e.estadoProduccionDesc, u. usuarioNombre, c.cotizacionTotal
				from Produccion as p
				inner join Estadoproduccion as e
					on p.produccionEstado = e.estadoProduccionId
				inner join Usuarios as u
					on p.produccionOperador = usuarioId
				inner join cotizacion as c
					on c.cotizacionId = produccionCotizacion
				where produccionCotizacion = codigo;
		 end $$
DELIMITER ;

-- Pendiente de Fac
DELIMITER $$
	create procedure Sp_AddPendienteFacturar(produccion int(5), modoPago int(5), abonado double, pendiente double)
		begin
			insert into PendienteFacturar(cotizacionId,modoPagoId,montoAbonado,montoPendiente)
				values(produccion,modoPago, abonado, pendiente);
        end $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_UpdatePendienteFac(idBuscado int(5),abonado double, pendiente double)
		begin
			update PendienteFacturar
				set montoAbonado = montoAbonado,
					montoPendiente = montoPendiente
				where pendienteId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeletePendienteFac(idBuscado int(5))
		begin
			delete from PendienteFacturar
				where pendienteId = idBuscado;
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListPendienteFac()
		begin 
			select pf.pendienteId, pf.modoPagoId , pf.montoAbonado, pf.montoPendiente,
				p.produccionEstado, p.produccionFechaEntrada,
                c.cotizacionId,
                mp.modoPagoDesc
				from PendienteFacturar as pf
				inner join Produccion as p
					on pf.produccionId = p.produccionId
				inner join Cotizacion as c
					on p.produccionId = c.cotizacionId
				inner join ModoPago as mp
					on pf.modoPagoId = mp.modoPagoId;
        end $$
DELIMITER ;

-- Inventario

#----------------- Entidad Inventario de productos

DELIMITER $$	
	create procedure Sp_AddInventario(id varchar(7), descripcion varchar(30), proveedor varchar(7), categoria varchar(7), precio double,cantidad double, estado int)
		BEGIN
			insert into InventarioProductos(productoId ,productoDesc ,proveedorId ,categoriaId ,productoPrecio ,inventarioProductoCant ,estadoProductoId)
				values(id, descripcion, proveedor, categoria, precio, cantidad, estado);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_UpdateInventarioProducto(idBuscado varchar(7), descripcion varchar(30), precio double, estado int)
		BEGIN
			update InventarioProductos
				set  productoDesc = descripcion,
					 productoPrecio = precio,
                     estadoProductoId = estado
					where productoId = idBuscado;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteInventarioProducto(idBuscado varchar(7))
		BEGIN
			delete from InventarioProductos
				where productoId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_SumarInventar(idBuscado varchar(7), cantidadSumar double)
		begin 
			update InventarioProductos 
				set inventarioProductoCant = inventarioProductoCant + cantidadSumar
			where productoId = idBuscado;
        end $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_RestarInventar(idBuscado varchar(7), cantidadSumar double)
		begin 
			update InventarioProductos 
				set inventarioProductoCant = inventarioProductoCant - cantidadSumar
			where productoId = idBuscado;
        end $$
DELIMITER ;



DELIMITER $$
	create procedure Sp_ListInventarioProducto()
		BEGIN
			select
				ip.productoId,
                ip.inventarioProductoCant,
                pr.proveedorNombre,
                ip.productoDesc,
                ep.estadoProductoDesc,
                ip.productoPrecio,
                c.categoriaNombre
		from
			InventarioProductos as ip
		inner join EstadoProductos as ep
			on ip.estadoProductoId = ep.estadoProductoId
		inner join Proveedores as pr
			on ip.proveedorId = pr.proveedorId
		inner join categoriaproductos as c
			on ip.categoriaId = c.categoriaId
        group by 
         ip.productoId
		order by         
			ip.productoId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindInventarioProducto(idBuscado varchar(7))
		BEGIN
			select
				ip.productoId,
                ip.inventarioProductoCant,
                pr.proveedorId,
                pr.proveedorNombre,
                ip.productoDesc,
                ep.estadoProductoDesc,
                ip.productoPrecio,
                c.categoriaNombre
		from
			InventarioProductos as ip
		inner join EstadoProductos as ep
			on ip.estadoProductoId = ep.estadoProductoId
		inner join Proveedores as pr
			on ip.proveedorId = pr.proveedorId
		inner join categoriaproductos as c
			on ip.categoriaId = c.categoriaId
		where ip.productoId = idBuscado
        group by 
         ip.productoId
		order by         
			ip.productoId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindInventarioProductoNombre(idBuscado varchar(50))
		BEGIN
			select
				ip.productoId,
                ip.inventarioProductoCant,
                pr.proveedorId,
                pr.proveedorNombre,
                ip.productoDesc,
                ep.estadoProductoDesc,
                ip.productoPrecio,
                c.categoriaNombre
		from
			InventarioProductos as ip
		inner join EstadoProductos as ep
			on ip.estadoProductoId = ep.estadoProductoId
		inner join Proveedores as pr
			on ip.proveedorId = pr.proveedorId
		inner join categoriaproductos as c
			on ip.categoriaId = c.categoriaId
		where ip.productoDesc = idBuscado
        group by 
         ip.productoId
		order by         
			ip.productoId ASC;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_FindInventarioProveedor(idBuscado varchar(50))
		BEGIN
			select
				ip.productoId,
                ip.inventarioProductoCant,
                pr.proveedorNombre,
                ip.productoDesc,
                ep.estadoProductoDesc,
                ip.productoPrecio,
                c.categoriaNombre
		from
			InventarioProductos as ip
		inner join EstadoProductos as ep
			on ip.estadoProductoId = ep.estadoProductoId
		inner join Proveedores as pr
			on ip.proveedorId = pr.proveedorId
		inner join categoriaproductos as c
			on ip.categoriaId = c.categoriaId
		where pr.proveedorNombre = idBuscado
        group by 
         ip.productoId
		order by         
			ip.productoId ASC;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_ListInventarioProv()
		BEGIN
			select
				ip.productoId,
                ip.inventarioProductoCant,
                pr.proveedorNombre,
                ip.productoDesc,
                ep.estadoProductoDesc,
                ip.productoPrecio,
                c.categoriaNombre
		from
			InventarioProductos as ip
		inner join EstadoProductos as ep
			on ip.estadoProductoId = ep.estadoProductoId
		inner join Proveedores as pr
			on ip.proveedorId = pr.proveedorId
		inner join categoriaproductos as c
			on ip.categoriaId = c.categoriaId
        group by 
			pr.proveedorNombre
		order by         
			p.productoId ASC;
        END $$
DELIMITER ;
-- CATEGORIA

DELIMITER $$
	create procedure Sp_ListCategoriaProducto()
		BEGIN
			select categoriaId, categoriaNombre
				from CategoriaProductos
					order by categoriaId asc;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpAddCategoriaProducto(id varchar(7), nombre varchar(50))
		BEGIN
			insert into CategoriaProductos(categoriaId, categoriaNombre)
				values(id, nombre);
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateCategoriaProducto(idBuscado varchar(7),idNuevo varchar(7), nuevoNombre varchar(50))
		BEGIN
			update CategoriaProductos
				set categoriaId =idNuevo , categoriaNombre = nuevoNombre
					where categoriaId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindCategoriaProducto(idBuscado varchar(7))
		BEGIN
			select categoriaNombre, categoriaId
            from categoriaproductos
				where categoriaId = idBuscado
					order by categoriaId asc;
		END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindCategoriaProductosNombre(nombre varchar(50))
		BEGIN
			select categoriaId, categoriaNombre
            from categoriaproductos
				where categoriaNombre = nombre
					order by categoriaId asc;
		END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteCategoriaProducto(idBuscado varchar(7))
		BEGIN
			delete from CategoriaProductos
				where categoriaId = idBuscado;
        END $$
DELIMITER ;

-- Estado producto

DELIMITER $$
	create procedure Sp_ListEstadoProducto()
		BEGIN
			select estadoProductoId, estadoProductoDesc
				from EstadoProductos
					order by estadoProductoId asc;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_AddEstadoProducto(descripcion varchar(1000))
		BEGIN
			insert into EstadoProductos(estadoProductoDesc)
				values(descripcion);
		END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_UpdateEstadoProducto(idBuscado tinyint(1), nuevaDesc varchar(100))
		BEGIN
			update EstadoProductos
				set estadoProductoDesc = nuevaDesc
					where estadoProductoId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindEstadoProducto(idBuscado tinyint(1))
		BEGIN
			select estadoProductoId, estadoProductoDesc
				from EstadoProductos
					where estadoProductoId = idBuscado
						order by estadoProductoId asc;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindEstadoNombre(nombre varchar(100))
		BEGIN
			select estadoProductoId, estadoProductoDesc
				from EstadoProductos
					where estadoProductoDesc = nombre
						order by estadoProductoId asc;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_EliminarEstadoProducto(idBuscado tinyint(1))
		BEGIN
			delete from EstadoProductos
				where estadoProductoId = idBuscado;
        END $$
DELIMITER ;

-- Proveedores 

DELIMITER $$
	create procedure Sp_ListProveedor()
		BEGIN
			select proveedorId, proveedorNombre, proveedorTelefono, proveedorNit
				from Proveedores
					order by proveedorId asc;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_AddProveedor(proveedores varchar(7),nombre varchar(50), telefono varchar(8), nit varchar(50))
		BEGIN
			insert into Proveedores(proveedorId,proveedorNombre, proveedorTelefono, proveedorNit)
				values (proveedores , nombre , telefono, nit);
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateProveedor(idBuscado varchar(7), nuevoCodigo varchar(7),nuevoNombre varchar(50), nuevoTelefono varchar(8),  nit varchar(20))
		BEGIN
			update Proveedores
				set proveedorId = nuevoCodigo ,proveedorNombre = nuevoNombre, proveedorTelefono = nuevoTelefono, proveedorNit = nit
					where proveedorId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindProveedor(idBuscado varchar(7))
		BEGIN
			select proveedorId, proveedorNombre, proveedorTelefono, proveedorNit
				from Proveedores 
					where proveedorId = idBuscado
						order by proveedorId asc;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_FindBuscarProveedoresNombre(nombre varchar(100))
		BEGIN
			select proveedorId, proveedorNombre, proveedorTelefono,proveedorNit
				from Proveedores 
					where proveedorNombre = nombre
						order by proveedorId asc;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_FindProveedoresPorNombre(nombre varchar(50))
		BEGIN
			select proveedorId, proveedorNombre, proveedorTelefono,proveedorNit
				from Proveedores 
					where proveedorNombre = nombre
						order by proveedorId asc;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_FindProveedoresPorNit(nit varchar(20))
		BEGIN
			select proveedorId, proveedorNombre, proveedorTelefono,proveedorNit
				from Proveedores 
					where proveedorNit = nit
						order by proveedorId asc;
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_DeleteProveedor(idBuscado varchar(7))
		BEGIN
			delete from Proveedores
				where proveedorId = idBuscado;
        END $$
DELIMITER ;


-- Clientes

DELIMITER $$
create procedure Sp_ListCliente()
	BEGIN
		select clienteId, clienteNit, clienteNombre, clienteDireccion
			from clientes
				order by clienteId asc;
    END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_AddCliente(nit varchar(19), nombre varchar(50), direccion varchar(100))
		BEGIN
			insert into Clientes(clienteNit, clienteNombre, clienteDireccion)
				value(nit, nombre, direccion);
        END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE Sp_AddClienteSinDireccion(nit varchar(19), nombre varchar(25))
		BEGIN
			insert into Clientes(clienteNit, clienteNombre)
				value(nit, nombre);
        END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateCliente(idBuscado int(100), nuevoNit varchar(19), nombre varchar(50), direccion varchar(100))
		BEGIN
			update Clientes
				set clienteNit = nuevoNit, clienteNombre = nombre, clienteDireccion = direccion
					where clienteId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure Sp_DeleteClientes(idBuscado int(100))
		BEGIN
			delete from Clientes
				where clienteId = idBuscado;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure Sp_FindClientes(idBuscado int(100))
		BEGIN
			select clienteId, clienteNit, clienteNombre, clienteDireccion
				from Clientes
					where clienteId = idBuscado
						order by clienteId asc;
		END $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_FindClientesNIt(nit varchar(19))
		BEGIN
			select clienteId, clienteNit, clienteNombre, clienteDireccion
				from Clientes
					where clienteNit = nit
						order by clienteId asc;
		END $$
DELIMITER ;


-- Usuarios
DELIMITER $$
	create procedure Sp_ListUsuario()
		BEGIN 
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios, tipousuario 
					where Usuarios.tipoUsuarioId = tipousuario.tipoUsuarioId 
						order by usuarioId ASC;
        END $$
DELIMITER ;


-- CAMBIO #1
DELIMITER $$
	create procedure Sp_ListUsuarioVendedor()
		BEGIN 
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios, tipousuario 
					where Usuarios.tipoUsuarioId = tipousuario.tipoUsuarioId 
					and  (Usuarios.tipoUsuarioId = 2 or Usuarios.tipoUsuarioId = 3)
						order by usuarioId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_ListUsuarioEmpleado()
		BEGIN 
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios, tipousuario 
					where Usuarios.tipoUsuarioId = tipousuario.tipoUsuarioId 
					and  (Usuarios.tipoUsuarioId = 2 or Usuarios.tipoUsuarioId = 3)
						order by usuarioId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_ListUsuarioC(username varchar(20))
		BEGIN 
			select usuarioId, usuarioNombre
				from Usuarios
					where Usuarios.usuarioNombre = username
                    order by usuarioId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_AddUsuario(nombre varchar(30), pass varchar(40), tipoUsuario tinyint(1))
		BEGIN
			insert into Usuarios(usuarioNombre, usuarioPassword,tipoUsuarioId)
				values(nombre, pass,tipousuario);
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_UpdateUsuario(idBuscado int(100), nombre varchar(30), pass varchar(30),tipoUsuario tinyint(1))
		BEGIN
			update Usuarios
				set usuarioNombre = nombre, usuarioPassword = pass, tipoUsuarioId = tipoUsuario
					where usuarioId = idBuscado;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindUsuario(idBuscado int(100))
		BEGIN
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios,tipousuario
					where usuarioId = idBuscado
						and tipousuario.tipoUsuarioId = usuarios.tipoUsuarioId
							order by usuarioId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_FindUsuarioId(usuarioNombre varchar(30))
		BEGIN
			select usuarioId, usuarioNombre, usuarioPassword, tipoUsuario
				from Usuarios,tipousuario
					where usuarioNombre= Usuarios.usuarioNombre 
						and tipousuario.tipoUsuarioId = usuarios.tipoUsuarioId
							order by usuarioId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure Sp_DeleteUsuarios(idBuscado int(100))
		BEGIN
			delete from Usuarios
				where usuarioId = idBuscado;
        END $$
DELIMITER ;


-- procreso de actualizar inventario desde factura - 8/04/2021
DELIMITER $$
	create procedure SpActualizarInventarioProductosFacturacion(idBuscado varchar(7), cant double, estado tinyint(1))
		BEGIN
			update InventarioProductos
				set  estadoProductoId = estado, inventarioproductos.inventarioProductoCant = cant
					where productoId = idBuscado;
        END $$
DELIMITER ;





DELIMITER $$
	create procedure SpBuscarProductosFac(idBuscado varchar(7))
		BEGIN
			select 
				pr.productoId,
                pr.productoDesc,
                p.proveedorNombre,
                p.proveedorId,
                cp.categoriaNombre,
                pr.precioCosto,
                pr.productoPrecio,
                tp.tipoProdDesc,
                ip.inventarioProductoCant
			from 
				Productos as pr
			inner join
				Proveedores as p
			on 
				pr.proveedorId = p.proveedorId
			inner join 
				CategoriaProductos as cp
			on
				pr.categoriaId = cp.categoriaId 
			inner join
				inventarioproductos as ip
			on pr.productoId = ip.productoId
            
			where pr.productoId = idBuscado and ip.estadoProductoId = 1 
			order by pr.productoId ASC;
        END $$
DELIMITER ;


DELIMITER $$
	create procedure SpListarProductosFac()
		BEGIN
			select 
				pr.productoId,
                pr.productoDesc,
                p.proveedorNombre,
                cp.categoriaNombre,
                pr.precioCosto,
                pr.productoPrecio,
                tp.tipoProdDesc,
                ip.inventarioProductoCant
			from 
				Productos as pr
			inner join
				Proveedores as p
			on 
				pr.proveedorId = p.proveedorId
			inner join 
				CategoriaProductos as cp
			on
				pr.categoriaId = cp.categoriaId 
			inner join
				inventarioproductos as ip
			on pr.productoId = ip.productoId
            where ip.inventarioProductoCant > 0
			order by
				pr.productoId ASC;
        END $$
DELIMITER ;


-- CAMBIOS QUE SE HICIERON

DROP PROCEDURE IF EXISTS SpListarBackup;

DELIMITER $$
	create procedure SpListarBackup(userName varchar(30))
		BEGIN
			select fdb.facturaDetalleIdBackup, p.productoId,p.productoDesc, fdb.cantidadBackup , p.productoPrecio ,fdb.totalParcialBackup
				from facturadetallebackup as fdb
							inner join Productos as p
								on fdb.productoIdBackup = p.productoId;
        END $$
DELIMITER ;


-- QUERYS CAMBIO
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
		where pr.proveedorNombre = proveedor
		order by 
			p.productoId ASC;
        END $$
DELIMITER ;



DELIMITER $$
	create procedure Sp_TransferirCotizacion(cotizacion int(5), estado int(5), entrada date, salida date, operador int)
		begin
			insert into produccion(produccionCotizacion,produccionEstado,produccionFechaEntrada,produccionFechaSalida, produccionOperador)
				values(cotizacion, estado, entrada, salida, operador);
        end $$
DELIMITER ;



insert into EstadoProduccion values(1, 'PENDIENTE'),(2, 'PRODUCCION'), (3, 'FINALIZADO'), (4, 'PAUSA')






