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

