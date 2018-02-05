DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_buscarAlumno` $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarAlumno`(letra char(8))
BEGIN
     select * from alumno where idalumno like concat(letra,'%');
END $$

DELIMITER ;