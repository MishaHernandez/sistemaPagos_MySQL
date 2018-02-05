DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_eliminarDetalleP` $$
CREATE PROCEDURE `sistema`.`sp_eliminarDetalleP` (id char(3))
BEGIN
     delete from detallepago where idtasa = id;
END $$

DELIMITER ;