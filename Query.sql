-- QUERYS TABLE 
DELIMITER $$
	create procedure Sp_AddFactorVenta( facVentaDesc varchar(50), descuento double)
		begin
			insert into FactorVenta(factorVentaDesc, factorVentaDescuento)
				values(facVentaDesc,descuento);
        end $$
DELIMITER ;

DELIMITER $$
	create procedure Sp_UpdateFactorVenta(facVentaDesc varchar(50), descuento double)
		begin
			update FactorVenta
				set factorVentaDesc = facVentaDesc,
					factorVentaDescuento = descuento;
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

-- 