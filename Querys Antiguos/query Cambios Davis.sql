

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
