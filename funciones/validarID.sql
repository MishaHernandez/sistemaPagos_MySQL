DELIMITER $$

DROP FUNCTION IF EXISTS `sistema`.`func_validar` $$
CREATE FUNCTION `sistema`.`func_validar` (id char(8), tabla varchar(12)) RETURNS INT
BEGIN
     declare num int;
     if tabla = 'alumno' then
       select count(*) into num from alumno where idalumno = id;
     elseif tabla = 'tasa' then
       select count(*) into num from tasa where idtasa = id;
     elseif tabla = 'pago' then
       select count(*) into num from pago where idpago = id;
     end if;
     return num;
END $$

DELIMITER ;