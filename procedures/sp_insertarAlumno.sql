DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_insertarAlumno` $$
CREATE PROCEDURE `sistema`.`sp_insertarAlumno` (id char(8), nom varchar(20), apel varchar(40), dir varchar(20), tel int(11), correo varchar(20))
BEGIN
      insert into alumno values(id, nom, apel, dir, tel, correo);
END $$

DELIMITER ;