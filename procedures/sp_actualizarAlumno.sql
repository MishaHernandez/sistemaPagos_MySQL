DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_actualizarAlumno` $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarAlumno`(id char(8), nom varchar(20), apel varchar(40), dir varchar(20), tel int(11), correo varchar(20))
BEGIN
     update alumno set nombres = nom, apellidos = apel, direccion = dir, telefono = tel, correo = correo where idalumno = id;
END $$

DELIMITER ;