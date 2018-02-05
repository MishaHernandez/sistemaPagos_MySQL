DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_actualizarPago` $$
CREATE PROCEDURE `sistema`.`sp_actualizarPago` (id char(8), fecha date, observ varchar(20), idalu char(8))
BEGIN
     update pago set fecha = fecha, observacion = observ, idalumno = idalu where idpago = id;
END $$

DELIMITER ;