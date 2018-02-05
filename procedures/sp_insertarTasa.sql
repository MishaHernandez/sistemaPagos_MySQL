DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_insertarTasa` $$
CREATE PROCEDURE `sistema`.`sp_insertarTasa` (id char(3), descrip varchar(20), monto int(11))
BEGIN
     insert into tasa values(id, descrip, monto);
END $$

DELIMITER ;