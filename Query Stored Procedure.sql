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
	create procedure Sp_UpdateCamposEspeciales(idBuscado int(5),nombre varchar(150), precio double)
		begin
			update CamposEspeciales
				set campoNombre  = nombre,
					campoPrecio = precio
				where campoId = idBuscado;
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
		update TipoCliente 
			set tipoClienteDesc =  tipoDesc,
				tipoClienteDescuento = tipoDescuento
			where tipoClienteId = idBuscado;
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
				from TipoClientea as tc;
        end $$
DELIMITER ;

-- Cotizacion 
DELIMITER $$
	create procedure Sp_AddCotizacion(cliente int(5), tipoCliente int(5), mesajero int(5), img varchar(50), fecha date, cantidad double, referencia varchar(100), producto varchar(7), tipoPrecio varchar(50), alto double, ancho double, largo double, facVenta int(5), observaicion varchar(50), descuento double, precioU double, total double, camposEspeciales int(5))
		begin
			insert into Cotizacion(cotizacionCliente, cotizacionTipoClienteId, cotizacionMensajero, cotizacionImg, cotizacionFecha, cotizacionCantida, cotizacionModeloRef, cotizacionProducto, cotizacionTipoPrecio, cotizacionAlto, cotizacionAncho, cotizacionLargo, cotizacionFacVenta, cotizacionDesc, cotizacionDescuento, cotizacionPrecioU, cotizacionTotal, cotizacionCamposEspeciales )
				values(cliente,tipoCliente, mesajero, img, fecha, cantidad, referencia, producto, tipoPrecio, alto, ancho, largo, facVenta, observaicion, descuento, precioU, total, camposEspeciales);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateCotizacion(idBuscado int(5),cliente int(5), tipoCliente int(5), mesajero int(5), img varchar(50), fecha date, cantidad double, referencia varchar(100), producto varchar(7), tipoPrecio varchar(50), alto double, ancho double, largo double, facVenta int(5), observaicion varchar(50), descuento double, precioU double, total double, camposEspeciales int(5))
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
                cotizacionCamposEspeciales = camposEspeciales 
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

DELIMITER $$
	create procedure Sp_ListarCotizaciones()
		begin 
			select c.cotizacionId, c.cotizacionImg , c.cotizacionFecha , c.cotizacionCantida , c.cotizacionModeloRef, c.cotizacionTipoPrecio , c.cotizacionAlto , c.cotizacionAncho , c.cotizacionLargo, c.cotizacionDesc , c.cotizacionDescuento , c.cotizacionPrecioU , c.cotizacionTotal,
				   cl.clienteNombre,
                   tc.tipoClienteDesc,
                   u.usuarioNombre,
                   ip.productoDesc,
                   fv.campoNombre, fv.campoPrecio
				from Cotizacion as c
				inner join Clientes as cl
					on c.cotizacionCliente = cl.clienteId
				inner join TipoCliente as tc
					on c.cotizacionTipoClienteId = tc.tipoClienteId
				inner join Usuarios as u
					on c.cotizacionMensajero = u.usuarioId
				inner join InventarioProductos as ip
					on c.cotizacionProducto = ip.productoId
				inner join FactorVenta as fv
					on c.cotizacionFacVenta = fv.factorVentaId
				inner join CamposEspeciales as ce
					on c.cotizacionCamposEspeciales = fv.campoNombre;
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
	create procedure Sp_AddEstadoProduccion(descript varchar(20))
		begin
			insert into EstadoProduccion(estadoProduccionDesc)
				values (descript);
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


-- Produccion
DELIMITER $$
	create procedure Sp_AddProduccion(cotizacion int(5), estado int(5), operador int(5), entrada date, salida date, restantes int)
		begin 
			insert into Produccion(produccionCotizacion, produccionEstado, produccionOperador, produccionFechaEntrada, produccionFechaSalida, produccionDiasRestantes)
            values(cotizacion, estado, operador, entrada, salida, restantes);
        end $$
DELIMITER ;

DELIMITER $$	
	create procedure Sp_UpdateProduccion(idBuscado int(5),estado int(5),entrada date, salida date, restantes int)
		begin
			update Produccion
				set 
					produccionEstado = estado,
                    produccionDiasRestantes = restantes,
                    produccionFechaEntrada = entrada,
                    produccionFechaSalida = salida
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
			select p.produccionId, p.produccionFechaEntrada, p.produccionFechaSalida, p.produccionDiasRestantes,
				c.cotizacionId, c.cotizacionDesc ,
                e.estadoProduccionDesc,
                u. usuarioNombre
				from Produccion as p
				inner join Cotizacion as c
					on p.produccionCotizacion = cotizacionId
				inner join Estadoproduccion as e
					on p.produccionEstado = e.estadoProduccionId
				inner join Usuarios as u
					on p.produccionOperador = usuarioId;
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






















