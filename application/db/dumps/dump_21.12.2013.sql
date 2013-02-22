CREATE DATABASE  IF NOT EXISTS `majekwms` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_polish_ci */;
USE `majekwms`;
-- MySQL dump 10.13  Distrib 5.5.29, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: majekwms
-- ------------------------------------------------------
-- Server version	5.5.29-0ubuntu0.12.10.1

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
  `idProduct` INT(11) NOT NULL AUTO_INCREMENT,
  `measure_id` INT(11) DEFAULT NULL,
  `NAME` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `price` DOUBLE NOT NULL,
  `tax` FLOAT NOT NULL,
  `description` VARCHAR(250) COLLATE utf8_polish_ci DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idProduct`),
  KEY `fk_product_1` (`measure_id`),
  CONSTRAINT `fk_product_1` FOREIGN KEY (`measure_id`) REFERENCES `measure` (`idMeasure`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,2,'A1',32,22,'322',0,'2012-12-04 12:18:16'),(2,1,'A2',65,22,'6556',0,'2012-12-04 12:18:34'),(3,2,'P4',5646,22,'llll',0,'2012-12-04 12:19:11'),(4,2,'P5',56,22,'5656',0,'2012-12-04 12:19:39'),(5,1,'P6',10,22,'Testujemy teraz dodanie nowego produktu',0,'2012-12-27 16:30:11'),(6,2,'Wódka',22,22,'222',0,'2012-12-27 16:48:25'),(7,2,'Jebaka',12,22,'Do testów dla dużej faktury',0,'2012-12-27 16:50:25');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `companyWarehouse`
--

DROP TABLE IF EXISTS `companyWarehouse`;
/*!50001 DROP VIEW IF EXISTS `companyWarehouse`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `companyWarehouse` (
  `id` tinyint NOT NULL,
  `companyPrefix` tinyint NOT NULL,
  `companyName` tinyint NOT NULL,
  `warehouseName` tinyint NOT NULL,
  `warehouseCapicity` tinyint NOT NULL,
  `warehouseCreatedAt` tinyint NOT NULL,
  `warehouseDesc` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `clientType`
--

DROP TABLE IF EXISTS `clientType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientType` (
  `idClientType` INT(11) NOT NULL AUTO_INCREMENT,
  `TYPE` VARCHAR(15) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idClientType`,`TYPE`),
  UNIQUE KEY `TYPE` (`TYPE`)
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
  `idCompany` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(20) COLLATE utf8_polish_ci NOT NULL,
  `longname` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `version` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idCompany`),
  UNIQUE KEY `NAME` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (10,'wmsadmin','wmsadmin',0,'2012-12-04 11:50:34');
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `address` (
  `idAddress` INT(11) NOT NULL AUTO_INCREMENT,
  `city_id` INT(11) NOT NULL,
  `postcode` VARCHAR(12) COLLATE utf8_polish_ci NOT NULL,
  `street` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `version` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idAddress`),
  UNIQUE KEY `postcode` (`postcode`),
  UNIQUE KEY `street` (`street`),
  KEY `fk_address_1` (`city_id`),
  CONSTRAINT `fk_address_1` FOREIGN KEY (`city_id`) REFERENCES `city` (`idCity`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (1,16,'dass','da',0,'2012-12-21 22:03:00'),(3,16,'99-566','Gorzelnia 56a',0,'2012-12-22 16:30:36'),(4,17,'99-856','Stars 5b',0,'2012-12-22 16:38:04'),(5,303,'66-666','Sixteen 16a',0,'2012-12-22 17:05:52'),(6,41,'99-666','Sezamkowa 2',0,'2013-01-03 02:47:39'),(7,13,'22-555','Smykalki 23',0,'2013-01-03 02:54:28'),(8,4,'12-555','Krzacka 34a',0,'2013-01-03 03:05:10'),(9,14,'99-855','Kaszanka 85a',0,'2013-01-03 16:09:36');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unitType`
--

DROP TABLE IF EXISTS `unitType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unitType` (
  `idUnitType` INT(11) NOT NULL AUTO_INCREMENT,
  `TYPE` VARCHAR(20) COLLATE utf8_polish_ci NOT NULL,
  `abbreviation` VARCHAR(6) COLLATE utf8_polish_ci NOT NULL,
  `description` VARCHAR(120) COLLATE utf8_polish_ci DEFAULT NULL,
  `parentType` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idUnitType`,`TYPE`),
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

DROP TABLE IF EXISTS `USER`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `USER` (
  `idUser` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(16) COLLATE utf8_polish_ci NOT NULL,
  `password` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idUser`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `login_2` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `USER` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `USER` VALUES (2,'wmsadmin','wmsadmin','2012-01-01 00:00:00',2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoice`
--

DROP TABLE IF EXISTS `invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoice` (
  `idInvoice` INT(11) NOT NULL AUTO_INCREMENT,
  `refNumber` VARCHAR(22) COLLATE utf8_polish_ci NOT NULL,
  `client_id` INT(11) NOT NULL,
  `invoiceType_id` INT(11) NOT NULL,
  `createdDate` datetime NOT NULL,
  `dueDate` datetime NOT NULL,
  `description` VARCHAR(150) COLLATE utf8_polish_ci DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idInvoice`),
  UNIQUE KEY `refNumber` (`refNumber`),
  UNIQUE KEY `idInvoice_UNIQUE` (`idInvoice`),
  KEY `fk_invoice_1` (`client_id`),
  KEY `fk_invoice_2` (`invoiceType_id`),
  CONSTRAINT `fk_invoice_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`idClient`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_invoice_2` FOREIGN KEY (`invoiceType_id`) REFERENCES `invoiceType` (`idInvoiceType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `invoice`
--

LOCK TABLES `invoice` WRITE;
/*!40000 ALTER TABLE `invoice` DISABLE KEYS */;
INSERT INTO `invoice` VALUES (1,'F12/23/2012/8_59[39]',4,2,'2012-12-23 00:00:00','2013-01-06 00:00:00',NULL,'2012-12-23 21:06:10',0),(2,'F12/23/2012/9_35[15]',4,2,'2012-12-23 00:00:00','2013-01-06 00:00:00',NULL,'2012-12-23 21:37:06',0),(19,'F12/27/2012/2_53[26]',3,2,'2012-12-27 00:00:00','2013-01-10 00:00:00',NULL,'2012-12-27 14:53:38',0),(20,'F12/27/2012/3_49[11]',4,2,'2012-12-31 00:00:00','2013-01-14 00:00:00',NULL,'2012-12-27 15:51:26',0),(21,'F12/27/2012/3_54[04]',5,3,'2012-12-27 00:00:00','2013-01-10 00:00:00',NULL,'2012-12-27 15:54:23',0),(22,'F12/27/2012/4_48[30]',3,2,'2012-12-27 00:00:00','2013-01-10 00:00:00',NULL,'2012-12-27 16:48:58',0),(23,'F12/27/2012/5_02[16]',4,2,'2012-12-27 00:00:00','2013-01-10 00:00:00',NULL,'2012-12-27 17:03:49',0),(24,'F12/27/2012/5_08[02]',5,3,'2012-12-27 00:00:00','2013-01-10 00:00:00',NULL,'2012-12-27 17:40:09',0),(25,'F1/3/2013/12_54[24]',3,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 00:54:48',0),(26,'F1/3/2013/1_07[14]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 01:08:00',0),(27,'F1/3/2013/1_10[27]',5,3,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 01:11:01',0),(28,'F1/3/2013/1_52[23]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 01:52:59',0),(30,'F1/3/2013/1_54[25]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 01:54:42',0),(31,'F1/3/2013/1_57[32]',3,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 01:57:50',0),(32,'F1/3/2013/2_01[29]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:02:05',0),(33,'F1/3/2013/2_06[51]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:07:23',0),(34,'F1/3/2013/2_16[10]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:16:29',0),(35,'F1/3/2013/2_18[38]',3,2,'2013-01-03 00:00:00','2013-01-31 00:00:00',NULL,'2013-01-03 02:19:02',0),(36,'F1/3/2013/2_20[28]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:21:13',0),(37,'F1/3/2013/2_25[53]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:26:12',0),(38,'F1/3/2013/2_41[47]',4,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:42:08',0),(39,'F1/3/2013/2_43[40]',3,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:43:55',0),(40,'F1/3/2013/2_47[42]',6,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:47:55',0),(41,'F1/3/2013/2_54[32]',7,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 02:55:04',0),(42,'F1/3/2013/3_03[15]',7,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 03:03:26',0),(44,'F1/3/2013/3_05[14]',8,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 03:08:52',0),(45,'F1/3/2013/3_09[49]',8,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 03:10:19',0),(46,'F1/3/2013/3_11[12]',7,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 03:11:40',0),(47,'F1/3/2013/12_23[21]',7,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 12:23:44',0),(48,'F1/3/2013/12_30[17]',8,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 12:30:35',0),(49,'F1/3/2013/12_41[17]',6,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 12:41:59',0),(50,'F1/3/2013/1_10[02]',3,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 13:10:16',0),(51,'F1/3/2013/1_16[01]',6,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 13:16:13',0),(52,'F1/3/2013/1_19[19]',7,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 13:19:27',0),(53,'F1/3/2013/1_23[50]',6,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 13:23:59',0),(54,'F1/3/2013/4_10[39]',7,2,'2013-01-03 00:00:00','2013-01-17 00:00:00',NULL,'2013-01-03 16:11:22',0),(55,'F2/18/2013/9_02[19]',7,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:02:35',0),(57,'F2/18/2013/9_11[49]',4,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:12:11',0),(58,'F2/18/2013/9_13[02]',8,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:13:18',0),(59,'F2/18/2013/9_15[19]',4,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:15:31',0),(60,'F2/18/2013/9_17[58]',4,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:18:10',0),(61,'F2/18/2013/9_22[05]',6,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:22:32',0),(62,'F2/18/2013/9_28[20]',7,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:28:46',0),(63,'F2/18/2013/9_38[56]',6,2,'2013-02-18 00:00:00','2013-03-04 00:00:00',NULL,'2013-02-18 21:39:06',0),(64,'F2/19/2013/8_48[32]',6,2,'2013-02-19 00:00:00','2013-03-05 00:00:00',NULL,'2013-02-19 20:48:46',0),(65,'F2/19/2013/8_51[19]',8,2,'2013-02-19 00:00:00','2013-03-05 00:00:00',NULL,'2013-02-19 20:51:32',0),(66,'F2/19/2013/8_57[11]',8,2,'2013-02-19 00:00:00','2013-03-05 00:00:00',NULL,'2013-02-19 20:57:24',0),(67,'F2/19/2013/9_25[24]',8,2,'2013-02-19 00:00:00','2013-03-05 00:00:00',NULL,'2013-02-19 21:25:57',0),(68,'F2/19/2013/9_29[04]',8,2,'2013-02-19 00:00:00','2013-03-05 00:00:00',NULL,'2013-02-19 21:29:35',0),(69,'F2/19/2013/9_33[59]',8,2,'2013-02-19 00:00:00','2013-03-05 00:00:00',NULL,'2013-02-19 21:34:17',0);
/*!40000 ALTER TABLE `invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `measure`
--

DROP TABLE IF EXISTS `measure`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `measure` (
  `idMeasure` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `abbreviation` VARCHAR(10) COLLATE utf8_polish_ci NOT NULL,
  `version` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idMeasure`,`NAME`)
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
  `unit_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `pallets` INT(11) DEFAULT '0',
  `version` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
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
INSERT INTO `unitProduct` VALUES (1,1,4,NULL,NULL),(1,2,4,NULL,NULL),(1,6,4,NULL,NULL),(2,4,4,NULL,NULL),(2,5,4,NULL,NULL),(3,1,33,0,'2013-01-03 12:28:19'),(3,3,4,NULL,NULL),(3,5,19,0,'2013-01-03 12:28:19'),(3,7,10,NULL,NULL),(4,2,54,0,'2013-01-03 12:31:16'),(4,5,0,0,'2013-01-03 12:24:45'),(5,1,10,1,'2013-02-18 21:18:48'),(5,2,10,1,'2013-01-03 12:42:00'),(5,3,65,1,'2013-01-03 12:42:00'),(5,4,46,1,'2013-02-19 21:34:21'),(5,5,200,1,'2013-01-03 12:42:00'),(5,7,10,0,'2013-01-03 02:19:41'),(6,4,4,0,'2013-01-03 02:19:41'),(6,5,6,0,'2013-01-03 12:28:19');
/*!40000 ALTER TABLE `unitProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `unit`
--

DROP TABLE IF EXISTS `unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `unit` (
  `idUnit` INT(11) NOT NULL AUTO_INCREMENT,
  `warehouse_id` INT(11) NOT NULL,
  `unittype_id` INT(11) NOT NULL,
  `NAME` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `SIZE` bigint(20) NOT NULL,
  `description` longtext COLLATE utf8_polish_ci,
  `version` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  PRIMARY KEY (`idUnit`),
  KEY `fk_unit_1` (`unittype_id`),
  KEY `fk_unit_2` (`warehouse_id`),
  CONSTRAINT `fk_unit_1` FOREIGN KEY (`unittype_id`) REFERENCES `unitType` (`idUnitType`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_unit_2` FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse` (`idWarehouse`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `unit`
--

LOCK TABLES `unit` WRITE;
/*!40000 ALTER TABLE `unit` DISABLE KEYS */;
INSERT INTO `unit` VALUES (1,10,9,'U3',88,'4646464646',3,'2012-12-04 12:10:05'),(2,10,9,'U2',77,'Tololrorrlorororlrrlor',2,'2012-12-04 12:10:05'),(3,10,9,'U1',66,'Hahaha',2,'2012-12-04 12:10:05'),(4,10,9,'U4',150,'Testowanie 666',0,'2013-01-02 22:51:32'),(5,10,8,'U6',600,'Trrrrr',0,'2013-01-02 22:52:25'),(6,10,8,'U5',10,'Hahaha',0,'2013-01-02 22:52:25'),(7,10,9,'U_66',300,'U_66 for metal thrash...',0,'2013-02-19 21:33:50');
/*!40000 ALTER TABLE `unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clientDetails`
--

DROP TABLE IF EXISTS `clientDetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientDetails` (
  `idClient` INT(11) NOT NULL,
  `account` VARCHAR(33) COLLATE utf8_polish_ci NOT NULL,
  `nip` VARCHAR(15) COLLATE utf8_polish_ci NOT NULL,
  `phone` VARCHAR(15) COLLATE utf8_polish_ci NOT NULL,
  `version` INT(11) DEFAULT NULL,
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
INSERT INTO `clientDetails` VALUES (3,'36-1111-2222-3333-4444-5555-6666','333-222-22-22','+(48)838-12-54',0,'2012-12-22 16:30:36'),(4,'36-5555-9999-8888-6666-7777-5555','999-99-99-666','+(46)838-69-66',0,'2012-12-22 16:38:04'),(5,'36-6564-5265-4666-5656-6546-2365','999-222-11-11','+(48)666-11-00',0,'2012-12-22 17:05:52'),(6,'36-6666-8888-5555-1111-2222-4444','666-666-66-66','+(48)836-65-63',0,'2013-01-03 02:47:39'),(7,'36-8888-1356-1345-1313-1235-1313','888-88-88-888','+(22)666-66-66',0,'2013-01-03 02:54:28'),(8,'36-4694-1348-3158-0000-0000-3365','999-999-99-99','+(63)222-22-99',0,'2013-01-03 03:05:10'),(9,'36-6956-6912-6954-6958-5862-8888','999-55-55-666','+(48)865-95-25',0,'2013-01-03 16:09:36');
/*!40000 ALTER TABLE `clientDetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `city`
--

DROP TABLE IF EXISTS `city`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `city` (
  `idCity` INT(11) NOT NULL AUTO_INCREMENT,
  `NAME` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` DATE DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idCity`),
  UNIQUE KEY `NAME` (`NAME`),
  UNIQUE KEY `name_2` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=326 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `city`
--

LOCK TABLES `city` WRITE;
/*!40000 ALTER TABLE `city` DISABLE KEYS */;
INSERT INTO `city` VALUES (4,'Łowicz','2012-02-02',2),(5,'Warszawa','2012-02-02',3),(6,'Kolhapur','2012-01-01',6),(7,'Port Blair','2012-01-01',6),(8,'Adilabad','2012-01-01',6),(9,'Adoni','2012-01-01',6),(10,'Amadalavalasa','2012-01-01',6),(11,'Amalapuram','2012-01-01',6),(12,'Anakapalle','2012-01-01',6),(13,'Anantapur','2012-01-01',6),(14,'Badepalle','2012-01-01',6),(15,'Banganapalle','2012-01-01',6),(16,'Bapatla','2012-01-01',6),(17,'Bellampalle','2012-01-01',6),(18,'Bethamcherla','2012-01-01',6),(19,'Bhadrachalam','2012-01-01',6),(20,'Bhainsa','2012-01-01',6),(21,'Bheemunipatnam','2012-01-01',6),(22,'Bhimavaram','2012-01-01',6),(23,'Bhongir','2012-01-01',6),(24,'Bobbili','2012-01-01',6),(25,'Bodhan','2012-01-01',6),(26,'Chilakaluripet','2012-01-01',6),(27,'Chirala','2012-01-01',6),(28,'Chittoor','2012-01-01',6),(29,'Cuddapah','2012-01-01',6),(30,'Devarakonda','2012-01-01',6),(31,'Dharmavaram','2012-01-01',6),(32,'Eluru','2012-01-01',6),(33,'Farooqnagar','2012-01-01',6),(34,'Gadwal','2012-01-01',6),(35,'Gooty','2012-01-01',6),(36,'Gudivada','2012-01-01',6),(37,'Gudur','2012-01-01',6),(38,'Guntakal','2012-01-01',6),(39,'Guntur','2012-01-01',6),(40,'Hanuman Junction','2012-01-01',6),(41,'Hindupur','2012-01-01',6),(42,'Hyderabad','2012-01-01',6),(43,'Ichchapuram','2012-01-01',6),(44,'Jaggaiahpet','2012-01-01',6),(45,'Jagtial','2012-01-01',6),(46,'Jammalamadugu','2012-01-01',6),(47,'Jangaon','2012-01-01',6),(48,'Kadapa','2012-01-01',6),(49,'Kadiri','2012-01-01',6),(50,'Kagaznagar','2012-01-01',6),(51,'Kakinada','2012-01-01',6),(52,'Kalyandurg','2012-01-01',6),(53,'Kamareddy','2012-01-01',6),(54,'Kandukur','2012-01-01',6),(55,'Karimnagar','2012-01-01',6),(56,'Kavali','2012-01-01',6),(57,'Khammam','2012-01-01',6),(58,'Koratla','2012-01-01',6),(59,'Kothagudem','2012-01-01',6),(60,'Kothapeta','2012-01-01',6),(61,'Kovvur','2012-01-01',6),(62,'Kurnool','2012-01-01',6),(63,'Kyathampalle','2012-01-01',6),(64,'Macherla','2012-01-01',6),(65,'Machilipatnam','2012-01-01',6),(66,'Madanapalle','2012-01-01',6),(67,'Mahbubnagar','2012-01-01',6),(68,'Mancherial','2012-01-01',6),(69,'Mandamarri','2012-01-01',6),(70,'Mandapeta','2012-01-01',6),(71,'Manuguru','2012-01-01',6),(72,'Markapur','2012-01-01',6),(73,'Medak','2012-01-01',6),(74,'Miryalaguda','2012-01-01',6),(75,'Mogalthur','2012-01-01',6),(76,'Nagari','2012-01-01',6),(77,'Nagarkurnool','2012-01-01',6),(78,'Nandyal','2012-01-01',6),(79,'Narasapur','2012-01-01',6),(80,'Narasaraopet','2012-01-01',6),(81,'Narayanpet','2012-01-01',6),(82,'Narsipatnam','2012-01-01',6),(83,'Nellore','2012-01-01',6),(84,'Nidadavole','2012-01-01',6),(85,'Nirmal','2012-01-01',6),(86,'Nizamabad','2012-01-01',6),(87,'Nuzvid','2012-01-01',6),(88,'Ongole','2012-01-01',6),(89,'Palacole','2012-01-01',6),(90,'Palasa Kasibugga','2012-01-01',6),(91,'Palwancha','2012-01-01',6),(92,'Parvathipuram','2012-01-01',6),(93,'Pedana','2012-01-01',6),(94,'Peddapuram','2012-01-01',6),(95,'Pithapuram','2012-01-01',6),(96,'Pondur','2012-01-01',6),(97,'Ponnur','2012-01-01',6),(98,'Proddatur','2012-01-01',6),(99,'Punganur','2012-01-01',6),(100,'Puttur','2012-01-01',6),(101,'Rajahmundry','2012-01-01',6),(102,'Rajam','2012-01-01',6),(103,'Ramachandrapuram','2012-01-01',6),(104,'Ramagundam','2012-01-01',6),(105,'Rayachoti','2012-01-01',6),(106,'Rayadurg','2012-01-01',6),(107,'Renigunta','2012-01-01',6),(108,'Repalle','2012-01-01',6),(109,'Sadasivpet','2012-01-01',6),(110,'Salur','2012-01-01',6),(111,'Samalkot','2012-01-01',6),(112,'Sangareddy','2012-01-01',6),(113,'Sattenapalle','2012-01-01',6),(114,'Siddipet','2012-01-01',6),(115,'Singapur','2012-01-01',6),(116,'Sircilla','2012-01-01',6),(117,'Srikakulam','2012-01-01',6),(118,'Srikalahasti','2012-01-01',6),(119,'Suryapet','2012-01-01',6),(120,'Tadepalligudem','2012-01-01',6),(121,'Tadpatri','2012-01-01',6),(122,'Tandur','2012-01-01',6),(123,'Tanuku','2012-01-01',6),(124,'Tenali','2012-01-01',6),(125,'Tirupati','2012-01-01',6),(126,'Tuni','2012-01-01',6),(127,'Uravakonda','2012-01-01',6),(128,'Venkatagiri','2012-01-01',6),(129,'Vicarabad','2012-01-01',6),(130,'Vijayawada','2012-01-01',6),(131,'Vinukonda','2012-01-01',6),(132,'Visakhapatnam','2012-01-01',6),(133,'Vizianagaram','2012-01-01',6),(134,'Wanaparthy','2012-01-01',6),(135,'Warangal','2012-01-01',6),(136,'Yellandu','2012-01-01',6),(137,'Yemmiganur','2012-01-01',6),(138,'Yerraguntla','2012-01-01',6),(139,'Zahirabad','2012-01-01',6),(140,'Rajampet','2012-01-01',6),(141,'Along','2012-01-01',6),(142,'Bomdila','2012-01-01',6),(143,'Itanagar','2012-01-01',6),(144,'Naharlagun','2012-01-01',6),(145,'Pasighat','2012-01-01',6),(146,'Abhayapuri','2012-01-01',6),(147,'Amguri','2012-01-01',6),(148,'Anandnagaar','2012-01-01',6),(149,'Barpeta','2012-01-01',6),(150,'Barpeta Road','2012-01-01',6),(151,'Bilasipara','2012-01-01',6),(152,'Bongaigaon','2012-01-01',6),(153,'Dhekiajuli','2012-01-01',6),(154,'Dhubri','2012-01-01',6),(155,'Dibrugarh','2012-01-01',6),(156,'Digboi','2012-01-01',6),(157,'Diphu','2012-01-01',6),(158,'Dispur','2012-01-01',6),(159,'Gauripur','2012-01-01',6),(160,'Goalpara','2012-01-01',6),(161,'Golaghat','2012-01-01',6),(162,'Guwahati','2012-01-01',6),(163,'Haflong','2012-01-01',6),(164,'Hailakandi','2012-01-01',6),(165,'Hojai','2012-01-01',6),(166,'Jorhat','2012-01-01',6),(167,'Karimganj','2012-01-01',6),(168,'Kokrajhar','2012-01-01',6),(169,'Lanka','2012-01-01',6),(170,'Lumding','2012-01-01',6),(171,'Mangaldoi','2012-01-01',6),(172,'Mankachar','2012-01-01',6),(173,'Margherita','2012-01-01',6),(174,'Mariani','2012-01-01',6),(175,'Marigaon','2012-01-01',6),(176,'Nagaon','2012-01-01',6),(177,'Nalbari','2012-01-01',6),(178,'North Lakhimpur','2012-01-01',6),(179,'Rangia','2012-01-01',6),(180,'Sibsagar','2012-01-01',6),(181,'Silapathar','2012-01-01',6),(182,'Silchar','2012-01-01',6),(183,'Tezpur','2012-01-01',6),(184,'Tinsukia','2012-01-01',6),(185,'Amarpur','2012-01-01',6),(186,'Araria','2012-01-01',6),(187,'Areraj','2012-01-01',6),(188,'Arrah','2012-01-01',6),(189,'Asarganj','2012-01-01',6),(190,'Aurangabad','2012-01-01',6),(191,'Bagaha','2012-01-01',6),(192,'Bahadurganj','2012-01-01',6),(193,'Bairgania','2012-01-01',6),(194,'Bakhtiarpur','2012-01-01',6),(195,'Banka','2012-01-01',6),(196,'Banmankhi Bazar','2012-01-01',6),(197,'Barahiya','2012-01-01',6),(198,'Barauli','2012-01-01',6),(199,'Barbigha','2012-01-01',6),(200,'Barh','2012-01-01',6),(201,'Begusarai','2012-01-01',6),(202,'Behea','2012-01-01',6),(203,'Bettiah','2012-01-01',6),(204,'Bhabua','2012-01-01',6),(205,'Bhagalpur','2012-01-01',6),(206,'Bihar Sharif','2012-01-01',6),(207,'Bikramganj','2012-01-01',6),(208,'Bodh Gaya','2012-01-01',6),(209,'Buxar','2012-01-01',6),(210,'Chandan Bara','2012-01-01',6),(211,'Chanpatia','2012-01-01',6),(212,'Chhapra','2012-01-01',6),(213,'Colgong','2012-01-01',6),(214,'Dalsinghsarai','2012-01-01',6),(215,'Darbhanga','2012-01-01',6),(216,'Daudnagar','2012-01-01',6),(217,'Dehri-on-Sone','2012-01-01',6),(218,'Dhaka','2012-01-01',6),(219,'Dighwara','2012-01-01',6),(220,'Dumraon','2012-01-01',6),(221,'Fatwah','2012-01-01',6),(222,'Forbesganj','2012-01-01',6),(223,'Gaya','2012-01-01',6),(224,'Gogri Jamalpur','2012-01-01',6),(225,'Gopalganj','2012-01-01',6),(226,'Hajipur','2012-01-01',6),(227,'Hilsa','2012-01-01',6),(228,'Hisua','2012-01-01',6),(229,'Islampur','2012-01-01',6),(230,'Jagdispur','2012-01-01',6),(231,'Jamalpur','2012-01-01',6),(232,'Jamui','2012-01-01',6),(233,'Jehanabad','2012-01-01',6),(234,'Jhajha','2012-01-01',6),(235,'Jhanjharpur','2012-01-01',6),(236,'Jogabani','2012-01-01',6),(237,'Kanti','2012-01-01',6),(238,'Katihar','2012-01-01',6),(239,'Khagaria','2012-01-01',6),(240,'Kharagpur','2012-01-01',6),(241,'Kishanganj','2012-01-01',6),(242,'Lakhisarai','2012-01-01',6),(243,'Lalganj','2012-01-01',6),(244,'Madhepura','2012-01-01',6),(245,'Madhubani','2012-01-01',6),(246,'Maharajganj','2012-01-01',6),(247,'Mahnar Bazar','2012-01-01',6),(248,'Makhdumpur','2012-01-01',6),(249,'Maner','2012-01-01',6),(250,'Manihari','2012-01-01',6),(251,'Marhaura','2012-01-01',6),(252,'Masaurhi','2012-01-01',6),(253,'Mirganj','2012-01-01',6),(254,'Mokameh','2012-01-01',6),(255,'Motihari','2012-01-01',6),(256,'Motipur','2012-01-01',6),(257,'Munger','2012-01-01',6),(258,'Murliganj','2012-01-01',6),(259,'Muzaffarpur','2012-01-01',6),(260,'Narkatiaganj','2012-01-01',6),(261,'Naugachhia','2012-01-01',6),(262,'Nawada','2012-01-01',6),(263,'Nokha','2012-01-01',6),(264,'Patna','2012-01-01',6),(265,'Piro','2012-01-01',6),(266,'Purnia','2012-01-01',6),(267,'Rafiganj','2012-01-01',6),(268,'Rajgir','2012-01-01',6),(269,'Ramnagar','2012-01-01',6),(270,'Raxaul Bazar','2012-01-01',6),(271,'Revelganj','2012-01-01',6),(272,'Rosera','2012-01-01',6),(273,'Saharsa','2012-01-01',6),(274,'Samastipur','2012-01-01',6),(275,'Sasaram','2012-01-01',6),(276,'Sheikhpura','2012-01-01',6),(277,'Sheohar','2012-01-01',6),(278,'Sherghati','2012-01-01',6),(279,'Silao','2012-01-01',6),(280,'Sitamarhi','2012-01-01',6),(281,'Siwan','2012-01-01',6),(282,'Sonepur','2012-01-01',6),(283,'Sugauli','2012-01-01',6),(284,'Sultanganj','2012-01-01',6),(285,'Supaul','2012-01-01',6),(286,'Warisaliganj','2012-01-01',6),(287,'Ahiwara','2012-01-01',6),(288,'Akaltara','2012-01-01',6),(289,'Ambagarh Chowki','2012-01-01',6),(290,'Ambikapur','2012-01-01',6),(291,'Arang','2012-01-01',6),(292,'Bade Bacheli','2012-01-01',6),(293,'Balod','2012-01-01',6),(294,'Baloda Bazar','2012-01-01',6),(295,'Bemetra','2012-01-01',6),(296,'Bhatapara','2012-01-01',6),(297,'Bilaspur','2012-01-01',6),(298,'Birgaon','2012-01-01',6),(299,'Champa','2012-01-01',6),(300,'Chirmiri','2012-01-01',6),(301,'Dalli-Rajhara','2012-01-01',6),(302,'Dhamtari','2012-01-01',6),(303,'Dipka','2012-01-01',6),(304,'Dongargarh','2012-01-01',6),(305,'Durg-Bhilai Nagar','2012-01-01',6),(306,'Gobranawapara','2012-01-01',6),(307,'Jagdalpur','2012-01-01',6),(308,'Janjgir','2012-01-01',6),(309,'Jashpurnagar','2012-01-01',6),(310,'Kanker','2012-01-01',6),(311,'Kawardha','2012-01-01',6),(312,'Kondagaon','2012-01-01',6),(313,'Korba','2012-01-01',6),(314,'Mahasamund','2012-01-01',6),(315,'Mahendragarh','2012-01-01',6),(316,'Mungeli','2012-01-01',6),(317,'Naila Janjgir','2012-01-01',6),(318,'Raigarh','2012-01-01',6),(319,'Raipur','2012-01-01',6),(320,'Rajnandgaon','2012-01-01',6),(321,'Sakti','2012-01-01',6),(322,'Tilda Newra','2012-01-01',6),(323,'Amli','2012-01-01',6),(324,'Silvassa','2012-01-01',6),(325,'Daman and Diu','2012-01-01',6);
/*!40000 ALTER TABLE `city` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `invoiceType`
--

DROP TABLE IF EXISTS `invoiceType`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `invoiceType` (
  `idInvoiceType` INT(11) NOT NULL AUTO_INCREMENT,
  `TYPE` VARCHAR(10) COLLATE utf8_polish_ci NOT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idInvoiceType`,`TYPE`),
  UNIQUE KEY `NAME` (`TYPE`),
  UNIQUE KEY `name_2` (`TYPE`)
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
  `invoice_id` INT(11) NOT NULL,
  `product_id` INT(11) NOT NULL,
  `comment` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `price` FLOAT NOT NULL,
  `pallets` INT(11) NOT NULL,
  `tax` INT(11) NOT NULL,
  `version` INT(11) DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
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
INSERT INTO `invoiceProduct` VALUES (1,1,'Test 1',54,2,22,1,'2012-02-02 00:00:00'),(2,2,'Test 2',111,45,10,1,'2012-02-02 00:00:00'),(19,1,'12',12,12,22,0,'2012-12-27 14:53:38'),(20,2,'666',6,66,22,0,'2012-12-27 15:51:26'),(21,1,'5',2,20,55,0,'2012-12-27 15:54:23'),(22,6,'',45,10,22,0,'2012-12-27 16:48:58'),(23,1,'20',20,20,22,0,'2012-12-27 17:03:49'),(23,2,'40',40,40,22,0,'2012-12-27 17:03:49'),(23,3,'50',50,50,65,0,'2012-12-27 17:03:49'),(23,4,'6',6,6,22,0,'2012-12-27 17:03:49'),(23,5,'1',1,1,22,0,'2012-12-27 17:03:49'),(23,6,'12',12,12,22,0,'2012-12-27 17:03:49'),(23,7,'25',25,25,22,0,'2012-12-27 17:03:49'),(24,7,'66',0.2,20,22,0,'2012-12-27 17:40:09'),(25,7,'333',23,520,22,0,'2013-01-03 00:54:48'),(26,1,'2',2,2,22,0,'2013-01-03 01:08:19'),(26,4,'66',66,6,22,0,'2013-01-03 01:08:19'),(27,1,'333',3,20,22,0,'2013-01-03 01:11:01'),(27,4,'testowanie',8.9,30,22,0,'2013-01-03 01:11:01'),(27,6,'45',2,100,22,0,'2013-01-03 01:11:01'),(28,3,'333',20,60,22,0,'2013-01-03 01:52:59'),(28,4,'20',20,20,22,0,'2013-01-03 01:52:59'),(28,7,'5646',266,10,22,0,'2013-01-03 01:52:59'),(30,4,'333',87,20,22,0,'2013-01-03 01:54:42'),(31,3,'2',5646,2,22,0,'2013-01-03 01:57:50'),(31,6,'20',200,10,22,0,'2013-01-03 01:57:50'),(32,1,'5',5,5,22,0,'2013-01-03 02:02:05'),(32,2,'5',5,10,60,0,'2013-01-03 02:02:05'),(32,5,'15',15,15,15,0,'2013-01-03 02:02:05'),(33,1,'10',10,10,22,0,'2013-01-03 02:07:23'),(33,2,'20',20,20,22,0,'2013-01-03 02:07:23'),(33,3,'30',30,30,22,0,'2013-01-03 02:07:23'),(34,2,'2',65,2,22,0,'2013-01-03 02:16:30'),(34,4,'5',56,5,22,0,'2013-01-03 02:16:30'),(35,4,'10',56,10,22,0,'2013-01-03 02:19:02'),(35,7,'66',12,66,22,0,'2013-01-03 02:19:02'),(36,1,'10',32,10,22,0,'2013-01-03 02:21:13'),(36,2,'20',65,20,22,0,'2013-01-03 02:21:13'),(36,3,'60',5646,20,22,0,'2013-01-03 02:21:13'),(36,5,'60',10,20,22,0,'2013-01-03 02:21:13'),(37,2,'5',65,5,22,0,'2013-01-03 02:26:12'),(37,5,'60',60,60,22,0,'2013-01-03 02:26:12'),(38,1,'20',20,20,22,0,'2013-01-03 02:42:08'),(38,3,'20',5646,20,22,0,'2013-01-03 02:42:08'),(39,2,'10',65,10,22,0,'2013-01-03 02:43:55'),(39,3,'10',10,10,22,0,'2013-01-03 02:43:55'),(40,3,'10',5646,30,22,0,'2013-01-03 02:47:55'),(41,1,'10',32,10,22,0,'2013-01-03 02:56:00'),(41,2,'6',65,6,22,0,'2013-01-03 02:56:00'),(41,5,'20',10,20,22,0,'2013-01-03 02:55:04'),(42,5,'100',10,100,22,0,'2013-01-03 03:03:26'),(44,1,'10',10,10,22,0,'2013-01-03 03:08:54'),(44,2,'55',555,600,22,0,'2013-01-03 03:08:54'),(44,3,'10',5646,10,22,0,'2013-01-03 03:08:54'),(45,1,'500',500,500,22,0,'2013-01-03 03:10:19'),(45,3,'100',100,100,22,0,'2013-01-03 03:10:19'),(46,1,'50',32,50,22,0,'2013-01-03 03:11:40'),(46,3,'400',5646,400,22,0,'2013-01-03 03:11:40'),(46,4,'50',56,50,22,0,'2013-01-03 03:11:40'),(47,1,'501',32,50,22,0,'2013-01-03 12:23:44'),(47,5,'285',10,25,22,0,'2013-01-03 12:23:44'),(48,2,'574',65,54,22,0,'2013-01-03 12:30:35'),(48,4,'25',56,25,22,0,'2013-01-03 12:30:35'),(49,1,'65',32,50,22,0,'2013-01-03 12:42:00'),(49,2,'10',65,10,22,0,'2013-01-03 12:42:00'),(49,3,'65',5646,65,22,0,'2013-01-03 12:42:00'),(49,5,'200',10,200,22,0,'2013-01-03 12:42:00'),(50,3,'500',5646,100,22,0,'2013-01-03 13:10:16'),(51,3,'666',5646,30,22,0,'2013-01-03 13:16:13'),(52,2,'20',65,20,22,0,'2013-01-03 13:19:27'),(53,2,'52',65,52,22,0,'2013-01-03 13:23:59'),(54,3,'953',5646,65,22,0,'2013-01-03 16:11:22'),(55,3,'333',35,10,22,0,'2013-02-18 21:02:35'),(57,3,'44',44,44,22,0,'2013-02-18 21:12:11'),(57,5,'444',44.1,64,22,0,'2013-02-18 21:12:11'),(58,3,'66',45,65,22,0,'2013-02-18 21:13:18'),(59,5,'66',66,366,22,0,'2013-02-18 21:15:32'),(60,1,'4',0.1,10,22,0,'2013-02-18 21:18:10'),(61,3,'40',40,3,22,0,'2013-02-18 21:22:32'),(61,7,'5',5,5,22,0,'2013-02-18 21:22:32'),(65,2,'10',10,10,22,0,'2013-02-19 20:51:32'),(66,2,'40',40,40,22,0,'2013-02-19 20:57:24');
/*!40000 ALTER TABLE `invoiceProduct` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `idClient` INT(11) NOT NULL AUTO_INCREMENT,
  `address_id` INT(11) NOT NULL,
  `clientType_id` INT(11) NOT NULL,
  `NAME` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `company` VARCHAR(45) COLLATE utf8_polish_ci NOT NULL,
  `description` VARCHAR(200) COLLATE utf8_polish_ci DEFAULT NULL,
  `updatedOn` datetime DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idClient`),
  UNIQUE KEY `NAME` (`NAME`),
  UNIQUE KEY `name_2` (`NAME`),
  KEY `fk_client_1` (`address_id`),
  KEY `fk_client_2` (`clientType_id`),
  CONSTRAINT `fk_client_1` FOREIGN KEY (`address_id`) REFERENCES `address` (`idAddress`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_client_2` FOREIGN KEY (`clientType_id`) REFERENCES `clientType` (`idClientType`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (3,3,2,'Agatom','P.H.U. Agatom','Piotr Trębski','2012-12-22 16:30:36',0),(4,4,2,'Agatom2','P.H.U. Agatom','Piotr Trębski - magazyny na mieście','2012-12-22 16:38:04',0),(5,5,1,'Martom','P.H.U. Martom','This is the six','2012-12-22 17:05:52',0),(6,6,2,'Atheist_1','Atheistic Sands','Some company','2013-01-03 02:47:39',0),(7,7,2,'Sand_33','ZZ Sandy Companies','ZZ-TOP','2013-01-03 02:54:28',0),(8,8,2,'Sand_32','ZZ Sandy Companies','ZZ-Top','2013-01-03 03:05:10',0),(9,9,1,'Test2','Test5','Jakiś opis','2013-01-03 16:09:36',0);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouse`
--

DROP TABLE IF EXISTS `warehouse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouse` (
  `idWarehouse` INT(11) NOT NULL,
  `NAME` VARCHAR(20) COLLATE utf8_polish_ci NOT NULL,
  `SIZE` INT(11) NOT NULL,
  `createdDate` datetime NOT NULL,
  `description` longtext COLLATE utf8_polish_ci,
  `updatedOn` datetime DEFAULT NULL,
  `version` INT(11) DEFAULT NULL,
  PRIMARY KEY (`idWarehouse`),
  UNIQUE KEY `NAME` (`NAME`),
  UNIQUE KEY `name_2` (`NAME`),
  KEY `fk_warehouse_1` (`idWarehouse`),
  CONSTRAINT `fk_warehouse_1` FOREIGN KEY (`idWarehouse`) REFERENCES `company` (`idCompany`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_polish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouse`
--

LOCK TABLES `warehouse` WRITE;
/*!40000 ALTER TABLE `warehouse` DISABLE KEYS */;
INSERT INTO `warehouse` VALUES (10,'wmsadmin',1300,'2012-12-04 00:00:00','wmsadmin','2012-12-04 11:50:34',0);
/*!40000 ALTER TABLE `warehouse` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `companyWarehouse`
--

/*!50001 DROP TABLE IF EXISTS `companyWarehouse`*/;
/*!50001 DROP VIEW IF EXISTS `companyWarehouse`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8 */;
/*!50001 SET character_set_results     = utf8 */;
/*!50001 SET collation_connection      = utf8_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `companyWarehouse` AS select `c`.`idCompany` AS `id`,`c`.`name` AS `companyPrefix`,`c`.`longname` AS `companyName`,`w`.`name` AS `warehouseName`,`w`.`size` AS `warehouseCapicity`,`w`.`createdDate` AS `warehouseCreatedAt`,`w`.`description` AS `warehouseDesc` from (`company` `c` join `warehouse` `w` on((`c`.`idCompany` = `w`.`idWarehouse`))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-02-21  6:25:39
