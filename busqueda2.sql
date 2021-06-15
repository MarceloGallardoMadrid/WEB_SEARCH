-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.5.10-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             11.2.0.6213
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando estructura para tabla busqueda2.documento
CREATE TABLE IF NOT EXISTS `documento` (
  `nombre` varchar(45) DEFAULT NULL,
  `words` int(11) DEFAULT NULL,
  `iddoc` int(11) NOT NULL,
  PRIMARY KEY (`iddoc`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla busqueda2.termino
CREATE TABLE IF NOT EXISTS `termino` (
  `nombre` varchar(100) DEFAULT NULL,
  `MaxTf` int(11) DEFAULT NULL,
  `iddoc` int(11) DEFAULT NULL,
  `cantdoc` int(11) DEFAULT NULL,
  `idword` int(11) NOT NULL,
  PRIMARY KEY (`idword`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

-- Volcando estructura para tabla busqueda2.terminoxdocumento
CREATE TABLE IF NOT EXISTS `terminoxdocumento` (
  `idT` int(11) NOT NULL,
  `idD` int(11) NOT NULL,
  `tf` int(11) DEFAULT NULL,
  PRIMARY KEY (`idT`,`idD`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- La exportación de datos fue deseleccionada.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
