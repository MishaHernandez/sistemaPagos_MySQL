DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_actualizarDetalleP` $$
CREATE PROCEDURE `sistema`.`sp_actualizarDetalleP` (id char(3), idpago char(8), monto int(11))
BEGIN
     update detallepago set idpago = idpago, monto = monto where idtasa = id;
END $$

DELIMITER ;