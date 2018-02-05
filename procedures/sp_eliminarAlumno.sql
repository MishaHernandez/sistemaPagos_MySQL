DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_eliminarAlumno` $$
CREATE PROCEDURE `sistema`.`sp_eliminarAlumno` (id char(8))
BEGIN
     delete from alumno where idalumno = id;
END $$

DELIMITER ;