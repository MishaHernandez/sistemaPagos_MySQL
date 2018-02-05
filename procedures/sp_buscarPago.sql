DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_buscarPago` $$
CREATE PROCEDURE `sistema`.`sp_buscarPago`(letra char(8))
BEGIN
     select * from pago where idpago like concat(letra,'%');
END $$

DELIMITER ;