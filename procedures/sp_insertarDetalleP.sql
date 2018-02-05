DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_insertarDetalleP` $$
CREATE PROCEDURE `sistema`.`sp_insertarDetalleP` (id char(3), idpago char(8), monto int(11))
BEGIN
     insert into detallepago values(id, idpago, monto);
END $$

DELIMITER ;