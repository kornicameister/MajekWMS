CREATE DATABASE  IF NOT EXISTS `majekwms` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci */;
USE `majekwms`;
-- MySQL dump 10.13  Distrib 5.5.28, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: majekwms
-- ------------------------------------------------------
-- Server version	5.5.28-0ubuntu0.12.10.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `product` (
  `idProduct` int(11) NOT NULL AUTO_INCREMENT,
  `measure_id` int(11) DEFAULT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `pallets` int(11) NOT NULL,
  `price` double NOT NULL,
  `quantity` double NOT NULL,
  `tax` float NOT NULL,
  `description` varchar(250) COLLATE utf8_polish_ci DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idProduct`),
  KEY `fk_product_1` (`measure_id`),
  CONSTRAINT `fk_product_1` FOREIGN KEY (`measure_id`) REFERENCES `measure` (`idMeasure`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientType`
--

DROP TABLE IF EXISTS `clientType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientType` (
  `idClientType` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(15) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`idClientType`,`type`),
  UNIQUE KEY `type` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientType`
--

LOCK TABLES `clientType` WRITE;
/*!40000 ALTER TABLE `clientType` DISABLE KEYS */;
INSERT INTO `clientType` VALUES (1,'recipient','2012-02-02 00:00:00',2),(2,'supplier','2012-12-03 10:39:50',2),(3,'undefined','2012-12-03 10:39:50',2);
/*!40000 ALTER TABLE `clientType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `idCompany` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `longname` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `version` int(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idCompany`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `idAddress` int(11) NOT NULL AUTO_INCREMENT,
  `city_id` int(11) NOT NULL,
  `postcode` varchar(12) COLLATE utf8_polish_ci NOT NULL,
  `street` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `version` int(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idAddress`),
  UNIQUE KEY `postcode` (`postcode`),
  UNIQUE KEY `street` (`street`),
  KEY `fk_address_1` (`city_id`),
  CONSTRAINT `fk_address_1` FOREIGN KEY (`city_id`) REFERENCES `city` (`idCity`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unitType`
--

DROP TABLE IF EXISTS `unitType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unitType` (
  `idUnitType` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `abbreviation` varchar(6) COLLATE utf8_polish_ci NOT NULL,
  `description` varchar(120) COLLATE utf8_polish_ci DEFAULT NULL,
  `parentType` int(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUnitType`,`type`),
  UNIQUE KEY `abbreviation` (`abbreviation`),
  KEY `fk_unitType_1` (`parentType`),
  CONSTRAINT `fk_unitType_1` FOREIGN KEY (`parentType`) REFERENCES `unitType` (`idUnitType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unitType`
--

LOCK TABLES `unitType` WRITE;
/*!40000 ALTER TABLE `unitType` DISABLE KEYS */;
INSERT INTO `unitType` VALUES (8,'Global','g','Global, all is valid in this unit',NULL,'2012-02-02 00:00:00',2),(9,'Metal','gm','Wszystko, co metalowe',8,'2012-02-02 00:00:00',3);
/*!40000 ALTER TABLE `unitType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `idUser` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(16) COLLATE utf8_polish_ci NOT NULL,
  `password` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `login_2` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'wmsadmin','wmsadmin','2012-01-01 00:00:00',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `idInvoice` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` int(11) NOT NULL,
  `invoiceType_id` int(11) NOT NULL,
  `createdDate` datetime NOT NULL,
  `dueDate` datetime NOT NULL,
  `description` varchar(150) COLLATE utf8_polish_ci DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  `refNumber` varchar(255) COLLATE utf8_polish_ci NOT NULL,
  PRIMARY KEY (`idInvoice`),
  UNIQUE KEY `refNumber` (`refNumber`),
  UNIQUE KEY `idInvoice_UNIQUE` (`idInvoice`),
  KEY `fk_invoice_1` (`client_id`),
  KEY `fk_invoice_2` (`invoiceType_id`),
  CONSTRAINT `fk_invoice_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_2` FOREIGN KEY (`invoiceType_id`) REFERENCES `invoiceType` (`idInvoiceType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measure`
--

DROP TABLE IF EXISTS `measure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `measure` (
  `idMeasure` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `abbreviation` varchar(10) COLLATE utf8_polish_ci NOT NULL,
  `version` int(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idMeasure`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `measure`
--

LOCK TABLES `measure` WRITE;
/*!40000 ALTER TABLE `measure` DISABLE KEYS */;
INSERT INTO `measure` VALUES (1,'Kilogram','k',2,'2012-02-02 00:00:00'),(2,'Tona','t',2,'2012-02-02 00:00:00');
/*!40000 ALTER TABLE `measure` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unitProduct`
--

DROP TABLE IF EXISTS `unitProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unitProduct` (
  `unit_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  PRIMARY KEY (`unit_id`,`product_id`),
  KEY `FK36F48ECB2B6BFD30` (`unit_id`),
  KEY `fk_unitProduct_1` (`unit_id`),
  KEY `fk_unitProduct_2` (`product_id`),
  CONSTRAINT `fk_unitProduct_1` FOREIGN KEY (`unit_id`) REFERENCES `unit` (`idUnit`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_unitProduct_2` FOREIGN KEY (`product_id`) REFERENCES `product` (`idProduct`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unitProduct`
--

LOCK TABLES `unitProduct` WRITE;
/*!40000 ALTER TABLE `unitProduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `unitProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit` (
  `idUnit` int(11) NOT NULL AUTO_INCREMENT,
  `warehouse_id` int(11) NOT NULL,
  `unittype_id` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `size` bigint(20) NOT NULL,
  `description` longtext COLLATE utf8_polish_ci,
  `version` int(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idUnit`),
  KEY `fk_unit_1` (`unittype_id`),
  KEY `fk_unit_2` (`warehouse_id`),
  CONSTRAINT `fk_unit_1` FOREIGN KEY (`unittype_id`) REFERENCES `unitType` (`idUnitType`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_unit_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`idWarehouse`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientDetails`
--

DROP TABLE IF EXISTS `clientDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientDetails` (
  `idClient` int(11) NOT NULL,
  `account` varchar(30) COLLATE utf8_polish_ci NOT NULL,
  `fax` varchar(11) COLLATE utf8_polish_ci NOT NULL,
  `nip` varchar(15) COLLATE utf8_polish_ci NOT NULL,
  `phone` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `version` int(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idClient`),
  KEY `fk_clientDetails_1` (`idClient`),
  CONSTRAINT `fk_clientDetails_1` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientDetails`
--

LOCK TABLES `clientDetails` WRITE;
/*!40000 ALTER TABLE `clientDetails` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `idCity` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` date DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`idCity`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (4,'Łowicz','2012-02-02',2),(5,'Warszawa','2012-02-02',3);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoiceType`
--

DROP TABLE IF EXISTS `invoiceType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoiceType` (
  `idInvoiceType` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(10) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`idInvoiceType`,`type`),
  UNIQUE KEY `name` (`type`),
  UNIQUE KEY `name_2` (`type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoiceType`
--

LOCK TABLES `invoiceType` WRITE;
/*!40000 ALTER TABLE `invoiceType` DISABLE KEYS */;
INSERT INTO `invoiceType` VALUES (1,'return','2012-02-02 00:00:00',2),(2,'supply','2020-02-02 00:00:00',3),(3,'receipt','2012-02-02 00:00:00',3);
/*!40000 ALTER TABLE `invoiceType` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoiceProduct`
--

DROP TABLE IF EXISTS `invoiceProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoiceProduct` (
  `invoice_id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `comment` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `price` float NOT NULL,
  `quantity` double NOT NULL,
  `tax` int(11) NOT NULL,
  PRIMARY KEY (`invoice_id`,`product_id`),
  KEY `FK1083A5026CF510E4` (`invoice_id`),
  KEY `fk_invoiceProduct_1` (`product_id`),
  KEY `fk_invoiceProduct_2` (`invoice_id`),
  CONSTRAINT `fk_invoiceProduct_1` FOREIGN KEY (`product_id`) REFERENCES `product` (`idProduct`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_invoiceProduct_2` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`idInvoice`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoiceProduct`
--

LOCK TABLES `invoiceProduct` WRITE;
/*!40000 ALTER TABLE `invoiceProduct` DISABLE KEYS */;
/*!40000 ALTER TABLE `invoiceProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `idClient` int(11) NOT NULL AUTO_INCREMENT,
  `address_id` int(11) NOT NULL,
  `clientType_id` int(11) NOT NULL,
  `name` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `company` varchar(45) COLLATE utf8_polish_ci NOT NULL,
  `description` varchar(200) COLLATE utf8_polish_ci DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`idClient`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `company` (`company`),
  UNIQUE KEY `name_2` (`name`),
  KEY `fk_client_1` (`address_id`),
  KEY `fk_client_2` (`clientType_id`),
  CONSTRAINT `fk_client_2` FOREIGN KEY (`clientType_id`) REFERENCES `clientType` (`idClientType`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_client_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`idAddress`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse` (
  `idWarehouse` int(11) NOT NULL,
  `name` varchar(20) COLLATE utf8_polish_ci NOT NULL,
  `size` int(11) NOT NULL,
  `createdDate` datetime NOT NULL,
  `description` longtext COLLATE utf8_polish_ci,
  `updatedOn` datetime DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`idWarehouse`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`),
  KEY `fk_warehouse_1` (`idWarehouse`),
  CONSTRAINT `fk_warehouse_1` FOREIGN KEY (`idWarehouse`) REFERENCES `company` (`idCompany`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-12-03 17:49:04
