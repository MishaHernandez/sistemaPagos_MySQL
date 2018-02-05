-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.1.29-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win32
-- HeidiSQL Versión:             9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Volcando estructura de base de datos para sistema
CREATE DATABASE IF NOT EXISTS `sistema` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `sistema`;

-- Volcando estructura para tabla sistema.alumno
CREATE TABLE IF NOT EXISTS `alumno` (
  `idalumno` char(8) NOT NULL,
  `nombres` varchar(20) NOT NULL,
  `apellidos` varchar(40) NOT NULL,
  `direccion` varchar(20) NOT NULL,
  `telefono` int(11) NOT NULL,
  `correo` varchar(20) NOT NULL,
  PRIMARY KEY (`idalumno`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla sistema.alumno: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `alumno` DISABLE KEYS */;
INSERT IGNORE INTO `alumno` (`idalumno`, `nombres`, `apellidos`, `direccion`, `telefono`, `correo`) VALUES
	('12012023', 'Jorge', 'Ramos', 'Jr Tumbes 33', 356254198, 'jorge@gmail.com'),
	('12012048', 'Mijail', 'Hernandez', 'Jr Puno 797', 982635423, 'mijail@gmail.com'),
	('12014009', 'Alberto', 'Quiroga', 'Cusco 221', 536524, 'alberto@hotmail.com'),
	('12014012', 'Julio', 'Castro', 'Ayacucho 231', 355478654, 'julio@hotmail.com');
/*!40000 ALTER TABLE `alumno` ENABLE KEYS */;

-- Volcando estructura para tabla sistema.detallepago
CREATE TABLE IF NOT EXISTS `detallepago` (
  `idtasa` char(3) NOT NULL,
  `idpago` char(8) NOT NULL,
  `monto` int(11) NOT NULL,
  KEY `idtasa` (`idtasa`),
  KEY `idpago` (`idpago`),
  CONSTRAINT `detallepago_ibfk_1` FOREIGN KEY (`idpago`) REFERENCES `pago` (`idpago`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `detallepago_ibfk_2` FOREIGN KEY (`idtasa`) REFERENCES `tasa` (`idtasa`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla sistema.detallepago: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `detallepago` DISABLE KEYS */;
INSERT IGNORE INTO `detallepago` (`idtasa`, `idpago`, `monto`) VALUES
	('1', '2975', 150),
	('2', '1669', 30),
	('1', '1316', 150);
/*!40000 ALTER TABLE `detallepago` ENABLE KEYS */;

-- Volcando estructura para función sistema.func_totalPagos
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `func_totalPagos`(id char(8)) RETURNS int(11)
BEGIN
     declare suma int;
     select sum(monto) into suma from detallepago where idtasa like concat(id,'%');
     return suma;
END//
DELIMITER ;

-- Volcando estructura para función sistema.func_validar
DELIMITER //
CREATE DEFINER=`root`@`localhost` FUNCTION `func_validar`(id char(8), tabla varchar(12)) RETURNS int(11)
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
END//
DELIMITER ;

-- Volcando estructura para tabla sistema.pago
CREATE TABLE IF NOT EXISTS `pago` (
  `idpago` char(8) NOT NULL,
  `fecha` date NOT NULL,
  `observacion` varchar(20) NOT NULL,
  `idalumno` char(8) NOT NULL,
  PRIMARY KEY (`idpago`),
  KEY `idalumno` (`idalumno`),
  CONSTRAINT `pago_ibfk_1` FOREIGN KEY (`idalumno`) REFERENCES `alumno` (`idalumno`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla sistema.pago: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT IGNORE INTO `pago` (`idpago`, `fecha`, `observacion`, `idalumno`) VALUES
	('1316', '2018-02-02', 'pagos', '12014009'),
	('1669', '2018-01-18', 'pagos', '12012023'),
	('2975', '2018-01-20', 'pagos', '12012048');
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;

-- Volcando estructura para tabla sistema.reporte
CREATE TABLE IF NOT EXISTS `reporte` (
  `idestud` char(8) DEFAULT NULL,
  `nombre` varchar(40) DEFAULT NULL,
  `apellidop` varchar(40) DEFAULT NULL,
  `apellidom` varchar(40) DEFAULT NULL,
  `idpago` char(8) DEFAULT NULL,
  `monto` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla sistema.reporte: ~0 rows (aproximadamente)
/*!40000 ALTER TABLE `reporte` DISABLE KEYS */;
/*!40000 ALTER TABLE `reporte` ENABLE KEYS */;

-- Volcando estructura para procedimiento sistema.sp_actualizarAlumno
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarAlumno`(id char(8), nom varchar(20), apel varchar(40), dir varchar(20), tel int(11), correo varchar(20))
BEGIN
     update alumno set nombres = nom, apellidos = apel, direccion = dir, telefono = tel, correo = correo where idalumno = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_actualizarDetalleP
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarDetalleP`(id char(3), idpago char(8), monto int(11))
BEGIN
     update detallepago set idpago = idpago, monto = monto where idtasa = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_actualizarPago
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarPago`(id char(8), fecha date, observ varchar(20), idalu char(8))
BEGIN
     update pago set fecha = fecha, observacion = observ, idalumno = idalu where idpago = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_actualizarTasa
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_actualizarTasa`(id char(3), descrip varchar(20), monto int)
BEGIN
     update tasa set descripcion = descrip, monto = monto where idtasa = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_buscarAlumno
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarAlumno`(letra char(8))
BEGIN
     select * from alumno where idalumno like concat(letra,'%');
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_buscarDetalleP
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarDetalleP`(letra char(3))
BEGIN
     select * from detallepago where idtasa like concat(letra,'%');
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_buscarPago
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarPago`(letra char(8))
BEGIN
     select * from pago where idpago like concat(letra,'%');
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_buscarTasa
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_buscarTasa`(letra char(3))
BEGIN
     select * from tasa where idtasa like concat(letra,'%');
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_eliminarAlumno
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarAlumno`(id char(8))
BEGIN
     delete from alumno where idalumno = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_eliminarDetalleP
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarDetalleP`(id char(3))
BEGIN
     delete from detallepago where idtasa = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_eliminarPago
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarPago`(id char(8))
BEGIN
     delete from pago where idpago = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_eliminarTasa
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_eliminarTasa`(id char(3))
BEGIN
     delete from tasa where idtasa = id;
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_insertarAlumno
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarAlumno`(id char(8), nom varchar(20), apel varchar(40), dir varchar(20), tel int(11), correo varchar(20))
BEGIN
      insert into alumno values(id, nom, apel, dir, tel, correo);
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_insertarDetalleP
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarDetalleP`(id char(3), idpago char(8), monto int(11))
BEGIN
     insert into detallepago values(id, idpago, monto);
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_insertarPago
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarPago`(id char(8), fecha date, observ varchar(20), idalu char(8))
BEGIN
     insert into pago values(id, fecha, observ, idalu);
END//
DELIMITER ;

-- Volcando estructura para procedimiento sistema.sp_insertarTasa
DELIMITER //
CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_insertarTasa`(id char(3), descrip varchar(20), monto int(11))
BEGIN
     insert into tasa values(id, descrip, monto);
END//
DELIMITER ;

-- Volcando estructura para tabla sistema.tasa
CREATE TABLE IF NOT EXISTS `tasa` (
  `idtasa` char(3) NOT NULL,
  `descripcion` varchar(20) NOT NULL,
  `monto` int(11) NOT NULL,
  PRIMARY KEY (`idtasa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Volcando datos para la tabla sistema.tasa: ~3 rows (aproximadamente)
/*!40000 ALTER TABLE `tasa` DISABLE KEYS */;
INSERT IGNORE INTO `tasa` (`idtasa`, `descripcion`, `monto`) VALUES
	('1', 'matricula', 150),
	('2', 'historial', 30),
	('3', 'inscripcion', 50);
/*!40000 ALTER TABLE `tasa` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
