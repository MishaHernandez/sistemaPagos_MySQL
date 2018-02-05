DELIMITER $$

DROP PROCEDURE IF EXISTS `sistema`.`sp_buscarTasa` $$
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarTasa`(letra char(3))
BEGIN
     select * from tasa where idtasa like concat(letra,'%');
END $$

DELIMITER ;