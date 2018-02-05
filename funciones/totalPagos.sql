DELIMITER $$

DROP FUNCTION IF EXISTS `sistema`.`func_totalPagos` $$
CREATE FUNCTION `sistema`.`func_totalPagos` (id char(8)) RETURNS INT
BEGIN
     declare suma int;
     select sum(monto) into suma from detallepago where idtasa like concat(id,'%');
     return suma;
END $$

DELIMITER ;