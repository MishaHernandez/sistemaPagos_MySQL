DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_actualizarTasa` $$
CREATE PROCEDURE `sistema`.`sp_actualizarTasa` (id char(3), descrip varchar(20), monto int)
BEGIN
     update tasa set descripcion = descrip, monto = monto where idtasa = id;
END $$

DELIMITER ;