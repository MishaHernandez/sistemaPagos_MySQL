DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_eliminarTasa` $$
CREATE PROCEDURE `sistema`.`sp_eliminarTasa` (id char(3))
BEGIN
     delete from tasa where idtasa = id;
END $$

DELIMITER ;