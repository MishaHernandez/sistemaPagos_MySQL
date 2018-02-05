DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_eliminarPago` $$
CREATE PROCEDURE `sistema`.`sp_eliminarPago` (id char(8))
BEGIN
     delete from pago where idpago = id;
END $$

DELIMITER ;