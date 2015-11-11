# ************************************************************
# Sequel Pro SQL dump
# Version 4499
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.42)
# Database: SlotMachineSpin
# Generation Time: 2015-11-11 03:59:46 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table Slot_Machine
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Slot_Machine`;

CREATE TABLE `Slot_Machine` (
  `PlayerID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(72) NOT NULL,
  `Credits` int(11) DEFAULT NULL,
  `LifetimeSpins` int(11) DEFAULT NULL,
  `Salt` varchar(128) NOT NULL,
  PRIMARY KEY (`PlayerID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

LOCK TABLES `Slot_Machine` WRITE;
/*!40000 ALTER TABLE `Slot_Machine` DISABLE KEYS */;

INSERT INTO `Slot_Machine` (`PlayerID`, `Name`, `Credits`, `LifetimeSpins`, `Salt`)
VALUES
	(1,'test',519,4,'salt');

/*!40000 ALTER TABLE `Slot_Machine` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
