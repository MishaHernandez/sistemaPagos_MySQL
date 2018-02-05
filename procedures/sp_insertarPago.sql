DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_insertarPago` $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarPago`(id char(8), fecha date, observ varchar(20), idalu char(8))
BEGIN
     insert into pago values(id, fecha, observ, idalu);
END $$

DELIMITER ;