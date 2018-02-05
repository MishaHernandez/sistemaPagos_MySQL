DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_buscarDetalleP` $$
CREATE PROCEDURE `sistema`.`sp_buscarDetalleP`(letra char(3))
BEGIN
     select * from detallepago where idtasa like concat(letra,'%');
END $$

DELIMITER ;