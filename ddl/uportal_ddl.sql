-- MySQL dump 10.13  Distrib 5.6.35, for Win64 (x86_64)
--
-- Host: localhost    Database: uportal
-- ------------------------------------------------------
-- Server version	5.6.35-log

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
-- Table structure for table `aaa`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `aaa` (
  `id` varchar(40) NOT NULL,
  `autoIndex` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`autoIndex`)
) ENGINE=InnoDB AUTO_INCREMENT=9932 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bak_budget`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bak_budget` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `budgetDepartment` varchar(200) DEFAULT NULL,
  `parentId` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `signer` varchar(40) NOT NULL,
  `locationId` varchar(40) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  `year` varchar(40) NOT NULL,
  `beginDate` varchar(40) NOT NULL,
  `endDate` varchar(40) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `carryStatus` int(11) NOT NULL,
  `total` double NOT NULL,
  `sail` double NOT NULL,
  `orgProfit` double NOT NULL,
  `realProfit` double NOT NULL,
  `outSave` double NOT NULL,
  `outMoney` double NOT NULL,
  `inMoney` double NOT NULL,
  `realMonery` double NOT NULL,
  `description` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bak_budgetitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bak_budgetitem` (
  `id` varchar(40) NOT NULL,
  `feeItemId` varchar(200) NOT NULL,
  `budgetId` varchar(40) NOT NULL,
  `locationId` varchar(40) NOT NULL,
  `budget` double NOT NULL,
  `carryStatus` int(11) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `realMonery` double NOT NULL,
  `useMonery` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bak_princi`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bak_princi` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `level` int(11) NOT NULL,
  `status` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bak_staffer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bak_staffer` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `locationId` varchar(50) DEFAULT NULL,
  `industryId` varchar(50) DEFAULT NULL,
  `industryId2` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `black` int(11) DEFAULT '0',
  `graduateDate` varchar(40) DEFAULT NULL,
  `specialty` varchar(200) DEFAULT NULL,
  `graduate` varchar(200) DEFAULT NULL,
  `graduateSchool` varchar(200) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `postId` varchar(40) DEFAULT NULL,
  `principalshipId` varchar(40) DEFAULT NULL,
  `nation` varchar(40) DEFAULT NULL,
  `city` varchar(40) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `visage` varchar(40) DEFAULT NULL,
  `idCard` varchar(100) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `handphone` varchar(40) DEFAULT NULL,
  `subphone` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `picPath` varchar(300) DEFAULT NULL,
  `idiograph` varchar(300) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `examType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `lever` int(11) DEFAULT '1',
  `pwkey` varchar(1000) DEFAULT NULL,
  `credit` double DEFAULT '0',
  `industryId3` varchar(50) DEFAULT NULL,
  `otype` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bak_vs_sta_pri`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bak_vs_sta_pri` (
  `id` int(11) NOT NULL DEFAULT '0',
  `stafferId` varchar(40) NOT NULL,
  `principalshipId` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bbb`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bbb` (
  `id` varchar(40) NOT NULL,
  `autoIndex` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bom`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bom` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `productId` varchar(40) DEFAULT NULL,
  `subProductId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bs`
--


--
-- Table structure for table `bumenduizhaobiao`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bumenduizhaobiao` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `daima` varchar(255) DEFAULT NULL,
  `bumen` varchar(255) DEFAULT NULL,
  `gongsi` varchar(255) DEFAULT NULL,
  `shiyebu` varchar(255) DEFAULT NULL,
  `xitong` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bumenduizhaobiao1`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bumenduizhaobiao1` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `daima` varchar(255) DEFAULT NULL,
  `bumen` varchar(255) DEFAULT NULL,
  `gongsi` varchar(255) DEFAULT NULL,
  `shiyebu` varchar(255) DEFAULT NULL,
  `xitong` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bumenduizhaobiao2`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bumenduizhaobiao2` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `kehudaima` varchar(255) DEFAULT NULL,
  `kehu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `bumenduizhaobiao3`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bumenduizhaobiao3` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `daima` varchar(255) DEFAULT NULL,
  `bumen` varchar(255) DEFAULT NULL,
  `gongsi` varchar(255) DEFAULT NULL,
  `shiyebu` varchar(255) DEFAULT NULL,
  `xitong` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chanpinbm`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chanpinbm` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `mingcheng` varchar(255) DEFAULT NULL,
  `bieming` varchar(255) DEFAULT NULL,
  `bianma` varchar(255) DEFAULT NULL,
  `xilie` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chanpinbm2`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chanpinbm2` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `mingcheng` varchar(255) DEFAULT NULL,
  `bieming` varchar(255) DEFAULT NULL,
  `bianma` varchar(255) DEFAULT NULL,
  `xilie` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copy_of_t_center_base`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_base` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outid` varchar(255) NOT NULL DEFAULT '',
  `productid` varchar(255) NOT NULL DEFAULT '',
  `productname` varchar(255) DEFAULT NULL,
  `ibmoney` double DEFAULT NULL,
  `motivationmoney` double DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=414 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copy_of_t_center_cusprosailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_cusprosailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` varchar(255) NOT NULL DEFAULT '',
  `customerid` varchar(255) DEFAULT '',
  `customername` varchar(255) DEFAULT '',
  `sh` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `productname` varchar(255) DEFAULT NULL,
  `productid` varchar(255) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `value` double(12,2) DEFAULT NULL,
  `ibmoney` double(12,2) DEFAULT NULL,
  `jlmoney` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=921352 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copy_of_t_center_customersailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_customersailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` varchar(255) NOT NULL DEFAULT '',
  `customerid` varchar(255) DEFAULT '',
  `customername` varchar(255) DEFAULT '',
  `sh` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `value` double(12,2) DEFAULT NULL,
  `ibmoney` double(12,2) DEFAULT NULL,
  `jlmoney` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=812839 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copy_of_t_center_out`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_out` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `fullid` varchar(255) DEFAULT NULL,
  `emergency` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=66664607 DEFAULT CHARSET=utf8 COMMENT='1';
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`127.0.0.1`*/ /*!50003 TRIGGER t_center_outemergency

AFTER INSERT ON copy_of_t_center_out

FOR EACH ROW

BEGIN
update t_center_package set emergency =1 where id=(select distinct max(packageid) from t_center_package_item where outid=new.fullid);
update t_center_out set emergency =1 where fullid=new.fullid;  
update t_center_out set emergency =1 where fullid=(SELECT distinct OANO FROM t_center_olbase WHERE OUTID=NEW.fullid);  
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `copy_of_t_center_outtransp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_outtransp` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `fullid` varchar(255) DEFAULT NULL,
  `transportno` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`127.0.0.1`*/ /*!50003 TRIGGER t_center_outtransp

AFTER insert ON copy_of_t_center_outtransp

FOR EACH ROW

BEGIN
update t_center_out set transportno =new.transportno where fullid=NEW.fullid;  
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `copy_of_t_center_productsailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_productsailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` date DEFAULT '0000-00-00',
  `productname` varchar(300) DEFAULT NULL,
  `productid` varchar(20) DEFAULT NULL,
  `amount` varchar(10) NOT NULL DEFAULT '',
  `value` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `zs` double(12,2) DEFAULT NULL,
  `jl` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=333778 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copy_of_t_center_staffersailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_staffersailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` date NOT NULL DEFAULT '0000-00-00',
  `staffer` varchar(30) DEFAULT NULL,
  `stafferid` varchar(10) DEFAULT NULL,
  `syb` varchar(255) DEFAULT NULL,
  `dq` varchar(255) DEFAULT NULL,
  `bm` varchar(255) DEFAULT NULL,
  `value` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `zs` double(12,2) DEFAULT NULL,
  `jl` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=348672 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `copy_of_t_center_staprosailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `copy_of_t_center_staprosailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` date NOT NULL DEFAULT '0000-00-00',
  `staffer` varchar(30) DEFAULT NULL,
  `stafferid` varchar(10) DEFAULT NULL,
  `syb` varchar(255) DEFAULT NULL,
  `dq` varchar(255) DEFAULT NULL,
  `bm` varchar(255) DEFAULT NULL,
  `productname` varchar(255) DEFAULT NULL,
  `productid` varchar(255) DEFAULT NULL,
  `amount` varchar(10) NOT NULL DEFAULT '',
  `value` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `zs` double(12,2) DEFAULT NULL,
  `jl` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=706561 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `cust_apply_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cust_apply_bak` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `selltype` int(11) DEFAULT '0',
  `protype` int(11) DEFAULT '0',
  `newtype` int(11) DEFAULT '0',
  `qqtype` int(11) DEFAULT '0',
  `rtype` int(11) DEFAULT '0',
  `createrId` varchar(50) DEFAULT NULL,
  `formtype` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `company` varchar(1000) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `fax` varchar(1000) DEFAULT NULL,
  `mail` varchar(1000) DEFAULT NULL,
  `postcode` varchar(40) DEFAULT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `accounts` varchar(100) DEFAULT NULL,
  `dutycode` varchar(100) DEFAULT NULL,
  `flowcom` varchar(400) DEFAULT NULL,
  `opr` int(11) DEFAULT '0',
  `reson` varchar(400) DEFAULT NULL,
  `oprReson` varchar(400) DEFAULT NULL,
  `updaterId` varchar(50) DEFAULT NULL,
  `loginTime` varchar(50) DEFAULT NULL,
  `createTime` varchar(50) DEFAULT NULL,
  `beginConnectTime` varchar(50) DEFAULT NULL,
  `mtype` int(11) DEFAULT NULL,
  `htype` int(11) DEFAULT NULL,
  `blog` int(11) DEFAULT NULL,
  `card` int(11) DEFAULT NULL,
  `lever` int(11) DEFAULT '1',
  `hlocal` varchar(200) DEFAULT NULL,
  `assignPer1` double DEFAULT '0',
  `assignPer2` double DEFAULT '0',
  `assignPer3` double DEFAULT '0',
  `assignPer4` double DEFAULT '0',
  `post` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `msn` varchar(200) DEFAULT NULL,
  `web` varchar(500) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL,
  `hasNew` int(11) DEFAULT '0',
  `cdepartement` varchar(200) DEFAULT NULL,
  `creditLevelId` varchar(40) DEFAULT '90000000000000000001',
  `creditVal` double DEFAULT '30',
  `creditUpdateTime` int(11) DEFAULT '0',
  `reserve1` varchar(1200) DEFAULT NULL,
  `reserve2` varchar(1200) DEFAULT NULL,
  `reserve3` varchar(1200) DEFAULT NULL,
  `reserve4` varchar(1200) DEFAULT NULL,
  `reserve5` varchar(1200) DEFAULT NULL,
  `reserve6` varchar(1200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(200) DEFAULT NULL,
  `selltype1` int(11) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `customer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer` (
  `ID` int(11) NOT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `fax` varchar(1000) DEFAULT NULL,
  `mail` varchar(1000) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `msn` varchar(200) DEFAULT NULL,
  `web` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `exportdata`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `exportdata` (
  `id` int(11) DEFAULT NULL,
  `c1` int(20) DEFAULT NULL,
  `c2` int(20) DEFAULT NULL,
  `c3` int(20) DEFAULT NULL,
  `c4` varchar(255) DEFAULT NULL,
  `c5` varchar(255) DEFAULT NULL,
  `c6` varchar(255) DEFAULT NULL,
  `c7` varchar(255) DEFAULT NULL,
  `d1` varchar(255) DEFAULT NULL,
  `d2` varchar(255) DEFAULT NULL,
  `d3` varchar(255) DEFAULT NULL,
  `d4` varchar(255) DEFAULT NULL,
  `d5` varchar(255) DEFAULT NULL,
  `d6` varchar(255) DEFAULT NULL,
  `d7` varchar(255) DEFAULT NULL,
  `d8` varchar(255) DEFAULT NULL,
  `d9` varchar(255) DEFAULT NULL,
  `d10` varchar(255) DEFAULT NULL,
  `d11` varchar(255) DEFAULT NULL,
  `d12` varchar(255) DEFAULT NULL,
  `d13` varchar(255) DEFAULT NULL,
  `d14` varchar(255) DEFAULT NULL,
  `d15` varchar(255) DEFAULT NULL,
  `d16` varchar(255) DEFAULT NULL,
  `d17` varchar(255) DEFAULT NULL,
  `d18` varchar(255) DEFAULT NULL,
  `d19` varchar(255) DEFAULT NULL,
  `d20` varchar(255) DEFAULT NULL,
  `d21` varchar(255) DEFAULT NULL,
  `d22` varchar(255) DEFAULT NULL,
  `d23` varchar(255) DEFAULT NULL,
  `d24` varchar(255) DEFAULT NULL,
  `d25` varchar(255) DEFAULT NULL,
  `d26` varchar(255) DEFAULT NULL,
  `d27` varchar(255) DEFAULT NULL,
  `d28` varchar(255) DEFAULT NULL,
  `d29` varchar(255) DEFAULT NULL,
  `d30` varchar(255) DEFAULT NULL,
  `d31` varchar(255) DEFAULT NULL,
  `d32` varchar(255) DEFAULT NULL,
  `d33` varchar(255) DEFAULT NULL,
  `d34` varchar(255) DEFAULT NULL,
  `d35` varchar(255) DEFAULT NULL,
  `d36` varchar(255) DEFAULT NULL,
  `d37` varchar(255) DEFAULT NULL,
  `d38` varchar(255) DEFAULT NULL,
  `d39` varchar(255) DEFAULT NULL,
  `d40` varchar(255) DEFAULT NULL,
  `d41` varchar(255) DEFAULT NULL,
  `d42` varchar(255) DEFAULT NULL,
  `d43` varchar(255) DEFAULT NULL,
  `d44` varchar(255) DEFAULT NULL,
  `d45` varchar(255) DEFAULT NULL,
  `d46` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `fhfs`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `fhfs` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `shiyebu` varchar(255) DEFAULT NULL,
  `fahuofangshi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `financemonth_bef`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `financemonth_bef` (
  `id` int(11) NOT NULL DEFAULT '0',
  `type` int(11) DEFAULT NULL,
  `itemIndex` int(11) DEFAULT NULL,
  `itemName` varchar(200) DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  `monthKey` varchar(8) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(4) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `lastOpr` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `importdata`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `importdata` (
  `id` int(11) DEFAULT NULL,
  `c1` int(20) DEFAULT NULL,
  `c2` int(20) DEFAULT NULL,
  `c3` int(20) DEFAULT NULL,
  `c4` varchar(255) DEFAULT NULL,
  `c5` varchar(255) DEFAULT NULL,
  `c6` varchar(255) DEFAULT NULL,
  `c7` varchar(255) DEFAULT NULL,
  `d1` varchar(255) DEFAULT NULL,
  `d2` varchar(255) DEFAULT NULL,
  `d3` varchar(255) DEFAULT NULL,
  `d4` varchar(255) DEFAULT NULL,
  `d5` varchar(255) DEFAULT NULL,
  `d6` varchar(255) DEFAULT NULL,
  `d7` varchar(255) DEFAULT NULL,
  `d8` varchar(255) DEFAULT NULL,
  `d9` varchar(255) DEFAULT NULL,
  `d10` varchar(255) DEFAULT NULL,
  `d11` varchar(255) DEFAULT NULL,
  `d12` varchar(255) DEFAULT NULL,
  `d13` varchar(255) DEFAULT NULL,
  `d14` varchar(255) DEFAULT NULL,
  `d15` varchar(255) DEFAULT NULL,
  `d16` varchar(255) DEFAULT NULL,
  `d17` varchar(255) DEFAULT NULL,
  `d18` varchar(255) DEFAULT NULL,
  `d19` varchar(255) DEFAULT NULL,
  `d20` varchar(255) DEFAULT NULL,
  `d21` varchar(255) DEFAULT NULL,
  `d22` varchar(255) DEFAULT NULL,
  `d23` varchar(255) DEFAULT NULL,
  `d24` varchar(255) DEFAULT NULL,
  `d25` varchar(255) DEFAULT NULL,
  `d26` varchar(255) DEFAULT NULL,
  `d27` varchar(255) DEFAULT NULL,
  `d28` varchar(255) DEFAULT NULL,
  `d29` varchar(255) DEFAULT NULL,
  `d30` varchar(255) DEFAULT NULL,
  `d31` varchar(255) DEFAULT NULL,
  `d32` varchar(255) DEFAULT NULL,
  `d33` varchar(255) DEFAULT NULL,
  `d34` varchar(255) DEFAULT NULL,
  `d35` varchar(255) DEFAULT NULL,
  `d36` varchar(255) DEFAULT NULL,
  `d37` varchar(255) DEFAULT NULL,
  `d38` varchar(255) DEFAULT NULL,
  `d39` varchar(255) DEFAULT NULL,
  `d40` varchar(255) DEFAULT NULL,
  `d41` varchar(255) DEFAULT NULL,
  `d42` varchar(255) DEFAULT NULL,
  `d43` varchar(255) DEFAULT NULL,
  `d44` varchar(255) DEFAULT NULL,
  `d45` varchar(255) DEFAULT NULL,
  `d46` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `leibie`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leibie` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `typeid` varchar(40) DEFAULT NULL,
  `outtypeid` varchar(40) DEFAULT NULL,
  `mingcheng` varchar(100) DEFAULT NULL,
  `leibie` varchar(40) DEFAULT NULL,
  `leibie2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `ind_tid` (`typeid`),
  KEY `ind_oid` (`outtypeid`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `oastaffer_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `oastaffer_bak` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `locationId` varchar(50) DEFAULT NULL,
  `industryId` varchar(50) DEFAULT NULL,
  `industryId2` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `black` int(11) DEFAULT '0',
  `graduateDate` varchar(40) DEFAULT NULL,
  `specialty` varchar(200) DEFAULT NULL,
  `graduate` varchar(200) DEFAULT NULL,
  `graduateSchool` varchar(200) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `postId` varchar(40) DEFAULT NULL,
  `principalshipId` varchar(40) DEFAULT NULL,
  `nation` varchar(40) DEFAULT NULL,
  `city` varchar(40) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `visage` varchar(40) DEFAULT NULL,
  `idCard` varchar(100) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `handphone` varchar(40) DEFAULT NULL,
  `subphone` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `picPath` varchar(300) DEFAULT NULL,
  `idiograph` varchar(300) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `examType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `lever` int(11) DEFAULT '1',
  `pwkey` varchar(1000) DEFAULT NULL,
  `credit` double DEFAULT '0',
  `industryId3` varchar(50) DEFAULT NULL,
  `otype` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roleautht`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roleautht` (
  `roleid` varchar(40) NOT NULL,
  `authid` varchar(40) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sale_tmp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sale_tmp` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(200) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `inputPrice` double DEFAULT NULL,
  `pprice` double DEFAULT NULL,
  `iprice` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=320 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `sjduizhaobiao`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sjduizhaobiao` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `pinming` varchar(255) DEFAULT NULL,
  `productid` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `xiuzhengpinming` varchar(255) DEFAULT NULL,
  `gongzuoshi` varchar(255) DEFAULT NULL,
  `tichengbili` varchar(255) DEFAULT NULL,
  `shangshishijian` varchar(20) DEFAULT NULL,
  `chanpinleibie` varchar(255) DEFAULT NULL,
  `bili` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=333 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_bakfinance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_bakfinance` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `updateFlag` int(11) DEFAULT '0',
  `dutyId` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refOut` varchar(40) DEFAULT NULL,
  `refBill` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `inmoney` bigint(20) DEFAULT '0',
  `outmoney` bigint(20) DEFAULT '0',
  `checks` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `financeDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `locks` int(11) DEFAULT '0',
  `monthIndex` int(11) DEFAULT '0',
  `refChecks` varchar(1200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_banksail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_banksail` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `outTime` varchar(30) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(60) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(100) DEFAULT NULL,
  `midincome` double DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_batchId` (`batchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_accesslog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_accesslog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30953 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_address`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_address` (
  `id` varchar(40) NOT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL,
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_address_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_address_his` (
  `id` varchar(40) NOT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL,
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `telephone` varchar(30) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `updator` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `batch` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  KEY `idx_customerid` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_agentconfig`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_agentconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) NOT NULL DEFAULT '',
  `agentINS` varchar(40) NOT NULL DEFAULT '',
  `serverIP` varchar(40) NOT NULL,
  `localIP` varchar(40) DEFAULT '0',
  `SK` varchar(200) DEFAULT '0',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_agreement`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_agreement` (
  `id` varchar(20) NOT NULL,
  `agreementName` varchar(200) NOT NULL,
  `agreementCode` varchar(50) NOT NULL DEFAULT '',
  `lineCode` varchar(50) DEFAULT NULL,
  `signDate` varchar(200) DEFAULT NULL,
  `agreementMoney` varchar(20) DEFAULT NULL,
  `agreementType` int(20) DEFAULT NULL,
  `agreementProperty` varchar(40) DEFAULT NULL,
  `party_a` varchar(200) DEFAULT NULL,
  `party_b` varchar(200) DEFAULT NULL,
  `agreementStatus` int(20) DEFAULT NULL,
  `agreementStage` varchar(50) DEFAULT NULL,
  `agreementText` varchar(200) DEFAULT NULL,
  `refProject` varchar(100) DEFAULT NULL,
  `beforeAgreement` varchar(100) DEFAULT NULL,
  `afterAgreement` varchar(100) DEFAULT NULL,
  `finishiProcess` varchar(100) DEFAULT NULL,
  `agreementDesc` varchar(300) DEFAULT NULL,
  `changeLog` varchar(300) DEFAULT NULL,
  `applyTime` varchar(50) DEFAULT NULL,
  `applyer` varchar(50) DEFAULT NULL,
  `processid` varchar(50) DEFAULT NULL,
  `firstparty` varchar(50) DEFAULT NULL,
  `secondparty` varchar(50) DEFAULT NULL,
  `flowKey` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`agreementCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_alarm`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_alarm` (
  `ID` varchar(40) NOT NULL,
  `refId` varchar(40) NOT NULL,
  `refType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `logTime` varchar(40) NOT NULL,
  `alarmContent` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_logTime` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_allprofit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_allprofit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) DEFAULT NULL,
  `moneys` varchar(40) DEFAULT NULL,
  `lastModified` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=474 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_appbase`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_appbase` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `orderNo` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productCode` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `picPath` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_orderNo` (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_appdist`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_appdist` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `orderNo` varchar(40) DEFAULT NULL,
  `province` varchar(30) DEFAULT NULL,
  `city` varchar(30) DEFAULT NULL,
  `fullAddress` varchar(300) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  `receiverMobile` varchar(40) DEFAULT NULL,
  `carryType` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_appinvoice`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_appinvoice` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `orderNo` varchar(40) DEFAULT NULL,
  `invoiceHead` varchar(100) DEFAULT NULL,
  `invoiceName` varchar(100) DEFAULT NULL,
  `invoiceMoney` double DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idx_orderNo` (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_appout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_appout` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `orderNo` varchar(40) DEFAULT NULL,
  `outTime` varchar(40) DEFAULT NULL,
  `outDate` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `userId` varchar(40) DEFAULT NULL,
  `activity` varchar(50) DEFAULT NULL,
  `sale` double DEFAULT NULL,
  `pay` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `payAccount` varchar(200) DEFAULT NULL,
  `payStatus` int(11) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `ostatus` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idx_orderNo` (`orderNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_approvelog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_approvelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullId` varchar(40) DEFAULT NULL,
  `actor` varchar(20) DEFAULT NULL,
  `oprMode` int(11) DEFAULT '0',
  `oprAmount` int(11) DEFAULT '0',
  `preStatus` int(11) DEFAULT '0',
  `afterStatus` int(11) DEFAULT '0',
  `logTime` varchar(20) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `actorId` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `out_fullId` (`fullId`)
) ENGINE=InnoDB AUTO_INCREMENT=6143346 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_appuser`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_appuser` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `loginName` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `mobile` varchar(40) DEFAULT NULL,
  `verifyCode` varchar(40) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `addressId` varchar(40) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `fullAddress` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_appuser_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_appuser_apply` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `loginName` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `applyId` varchar(40) DEFAULT NULL,
  `approveId` varchar(40) DEFAULT NULL,
  `mobile` varchar(40) DEFAULT NULL,
  `verifyCode` varchar(40) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `addressId` varchar(40) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `fullAddress` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_area`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_area` (
  `id` varchar(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `parentId` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_assess_broken`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_assess_broken` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=5955 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_assess_broken_detail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_assess_broken_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=1018 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_attachment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_attachment` (
  `ID` varchar(40) NOT NULL,
  `mailId` varchar(40) NOT NULL,
  `name` varchar(400) NOT NULL,
  `path` varchar(400) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_M` (`mailId`),
  KEY `INX_S` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_auth`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_auth` (
  `id` varchar(40) NOT NULL,
  `PARENTID` varchar(40) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `LEVEL` int(11) DEFAULT '0',
  `bottomFlag` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_auto_approve`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_auto_approve` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullId` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_autopaylog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_autopaylog` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(40) DEFAULT NULL,
  `outBalanceId` varchar(40) DEFAULT NULL,
  `outType` int(11) DEFAULT NULL,
  `billId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=78352 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_autopaymoni`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_autopaymoni` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_backpayapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_backpayapply` (
  `ID` varchar(40) NOT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `billId` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `backPay` double DEFAULT '0',
  `changePayment` double DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `refIds` varchar(1200) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_backprepay_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_backprepay_apply` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `name` varchar(40) DEFAULT NULL,
  `flowKey` varchar(50) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `stype` int(11) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `total` bigint(20) DEFAULT NULL,
  `backMoney` bigint(20) DEFAULT NULL,
  `ownMoney` bigint(20) DEFAULT NULL,
  `receiver` varchar(30) DEFAULT NULL,
  `receiveBank` varchar(100) DEFAULT NULL,
  `receiveAccount` varchar(40) DEFAULT NULL,
  `billId` varchar(40) DEFAULT NULL,
  `paymentId` varchar(40) DEFAULT NULL,
  `customerAccount` varchar(40) DEFAULT NULL,
  `bankId` varchar(40) DEFAULT NULL,
  `bankName` varchar(100) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `newBillId` varchar(40) DEFAULT NULL COMMENT '拆分出的billId,for return',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_bank`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_bank` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `DUTYID` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `description` varchar(1200) DEFAULT NULL,
  `total` double DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `bankNo` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INX_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_bank_balance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_bank_balance` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bankId` varchar(200) NOT NULL,
  `statDate` varchar(200) NOT NULL,
  `balance` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=88491 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_bankbu_level`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_bankbu_level` (
  `id` varchar(40) NOT NULL,
  `name` varchar(40) NOT NULL,
  `provincename` varchar(255) DEFAULT NULL,
  `provinceManagerId` varchar(40) DEFAULT NULL,
  `provinceManager` varchar(40) DEFAULT NULL,
  `regionalname` varchar(255) DEFAULT NULL,
  `regionalManagerId` varchar(40) DEFAULT NULL,
  `regionalManager` varchar(40) DEFAULT NULL,
  `dqname` varchar(255) DEFAULT NULL,
  `regionalDirectorId` varchar(40) DEFAULT NULL,
  `regionalDirector` varchar(40) DEFAULT NULL,
  `manager` varchar(25) DEFAULT NULL,
  `managerid` varchar(25) DEFAULT NULL,
  `ywbname` varchar(40) DEFAULT NULL,
  `changer` varchar(25) DEFAULT NULL,
  `changetime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_bankconfigforship`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_bankconfigforship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `bank` varchar(200) NOT NULL,
  `city` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_base`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_base` (
  `id` int(11) NOT NULL,
  `inway` int(11) DEFAULT '0',
  `productId` varchar(40) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `costPrice` double DEFAULT '0',
  `costPriceKey` varchar(40) DEFAULT NULL,
  `value` double DEFAULT '0',
  `owner` varchar(40) DEFAULT NULL,
  `ownerName` varchar(200) DEFAULT NULL,
  `locationId` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `showName` varchar(200) DEFAULT NULL,
  `showId` varchar(40) DEFAULT NULL,
  `outId` varchar(50) DEFAULT NULL,
  `storageId` varchar(40) DEFAULT NULL,
  `depotpartName` varchar(200) DEFAULT NULL,
  `depotpartId` varchar(40) DEFAULT NULL,
  `inputPrice` double DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `invoiceMoney` double DEFAULT '0',
  `pprice` double DEFAULT '0',
  `iprice` double DEFAULT '0',
  `deliverType` int(11) DEFAULT '-1',
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `profit` double DEFAULT '0',
  `profitRatio` double DEFAULT '0',
  `oldGoods` int(11) DEFAULT NULL,
  `taxrate` double DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `inputRate` double DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `ibMoney` double DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  `profitmoney` double(8,2) DEFAULT NULL COMMENT '2',
  `grossProfit` double DEFAULT NULL,
  `checkgrossProfit` double(6,2) DEFAULT NULL,
  `cash` double(7,2) DEFAULT NULL,
  `ibMoney2` double DEFAULT '0',
  `motivationMoney2` double DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_BASE` (`outId`,`productId`,`costPriceKey`,`owner`,`depotpartId`),
  KEY `INX_OUTID` (`outId`),
  KEY `INX_SHOWID` (`showId`),
  KEY `INX_PRODUCTID` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_basebalance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_basebalance` (
  `id` varchar(40) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `baseId` varchar(40) NOT NULL,
  `outId` varchar(40) NOT NULL,
  `amount` int(11) DEFAULT '0',
  `sailPrice` double DEFAULT '0',
  `invoiceMoney` double DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INX_BASEID` (`baseId`),
  KEY `INX_OUTID` (`outId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_baserepaire`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_baserepaire` (
  `id` varchar(40) NOT NULL DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `baseId` varchar(40) DEFAULT NULL,
  `productId` varchar(200) NOT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `value` double DEFAULT '0',
  `description` varchar(200) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `showName` varchar(200) DEFAULT NULL,
  `showId` varchar(40) DEFAULT NULL,
  `inputPrice` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_refid` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_batchapprove`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_batchapprove` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `applyId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `action` varchar(10) DEFAULT NULL,
  `reason` varchar(200) DEFAULT NULL,
  `ret` int(11) DEFAULT NULL,
  `result` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_batchId` (`batchId`)
) ENGINE=InnoDB AUTO_INCREMENT=1092421 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_batchret_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_batchret_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `operator` varchar(20) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_outId` (`outId`)
) ENGINE=InnoDB AUTO_INCREMENT=92610 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_batchswatch`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_batchswatch` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `action` varchar(10) DEFAULT NULL,
  `ret` int(11) DEFAULT NULL,
  `result` varchar(200) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `baseId` varchar(40) DEFAULT NULL,
  `dirDeport` varchar(40) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `transportNo` varchar(100) DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `idx_batchid` (`batchId`)
) ENGINE=InnoDB AUTO_INCREMENT=103607 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black` (
  `id` varchar(40) NOT NULL,
  `logDate` varchar(20) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `allMoneys` double DEFAULT NULL,
  `credit` int(11) DEFAULT NULL,
  `entryDate` varchar(20) DEFAULT NULL,
  `removeDate` varchar(20) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black_h`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black_h` (
  `id` varchar(40) NOT NULL,
  `backupDate` varchar(30) DEFAULT NULL,
  `logDate` varchar(20) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `allMoneys` double DEFAULT NULL,
  `credit` int(11) DEFAULT NULL,
  `entryDate` varchar(20) DEFAULT NULL,
  `removeDate` varchar(20) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  KEY `idx_stafferId` (`stafferId`),
  KEY `idx_backupdate` (`backupDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black_out`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black_out` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `customerName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_refIdOutId` (`refId`,`type`,`outId`)
) ENGINE=InnoDB AUTO_INCREMENT=100339378 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black_out_detail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black_out_detail` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(40) DEFAULT NULL,
  `outBalanceId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `costprice` double DEFAULT NULL,
  `baseId` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_outid` (`outId`)
) ENGINE=InnoDB AUTO_INCREMENT=14395271 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black_out_detail_h`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black_out_detail_h` (
  `Id` int(11) NOT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `outBalanceId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `costprice` double DEFAULT NULL,
  `backupDate` varchar(20) DEFAULT NULL,
  KEY `idx_id` (`Id`),
  KEY `idx_outId` (`outId`),
  KEY `idx_backupdate` (`backupDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black_out_h`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black_out_h` (
  `id` int(11) NOT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `customerName` varchar(100) DEFAULT NULL,
  `backupDate` varchar(30) DEFAULT NULL COMMENT '备份时间',
  KEY `idx_refIdOutId` (`refId`,`type`,`outId`),
  KEY `id` (`id`),
  KEY `idx_backupdate` (`backupDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black_out_tmp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black_out_tmp` (
  `id` int(11) NOT NULL DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `customerName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_black_tmp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_black_tmp` (
  `id` varchar(40) NOT NULL,
  `logDate` varchar(20) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `days` int(11) DEFAULT NULL,
  `allMoneys` double DEFAULT NULL,
  `credit` int(11) DEFAULT NULL,
  `entryDate` varchar(20) DEFAULT NULL,
  `removeDate` varchar(20) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_blackrule`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_blackrule` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `productType` varchar(400) DEFAULT NULL,
  `industryId` varchar(400) DEFAULT NULL,
  `departmentId` varchar(400) DEFAULT NULL,
  `outId` varchar(400) DEFAULT NULL,
  `beginOutTime` varchar(20) DEFAULT NULL,
  `endOutTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_blackrule_product`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_blackrule_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) NOT NULL,
  `productId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_blackrule_staffer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_blackrule_staffer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) NOT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4022 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_branch_relation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_branch_relation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` varchar(40) NOT NULL,
  `subBranchName` varchar(200) NOT NULL,
  `branchName` varchar(200) DEFAULT NULL,
  `subBranchMail` varchar(200) DEFAULT NULL,
  `branchMail` varchar(200) DEFAULT NULL,
  `sendMailFlag` int(11) DEFAULT '0',
  `copyToBranchFlag` int(11) DEFAULT '0',
  `logTime` varchar(255) DEFAULT '',
  `operator` varchar(255) DEFAULT '',
  `channel` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `t_center_branch_relation_subBranchName_branchName_channel_index` (`subBranchName`,`branchName`,`channel`)
) ENGINE=InnoDB AUTO_INCREMENT=931 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_budget`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_budget` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `budgetDepartment` varchar(200) DEFAULT NULL,
  `parentId` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `signer` varchar(40) NOT NULL,
  `locationId` varchar(40) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  `year` varchar(40) NOT NULL,
  `beginDate` varchar(40) NOT NULL,
  `endDate` varchar(40) NOT NULL,
  `type` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `carryStatus` int(11) NOT NULL,
  `total` double NOT NULL,
  `sail` double NOT NULL,
  `orgProfit` double NOT NULL,
  `realProfit` double NOT NULL,
  `outSave` double NOT NULL,
  `outMoney` double NOT NULL,
  `inMoney` double NOT NULL,
  `realMonery` double NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_B` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_budget_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_budget_apply` (
  `id` varchar(40) NOT NULL,
  `budgetId` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  `type` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_budgetitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_budgetitem` (
  `id` varchar(40) NOT NULL,
  `feeItemId` varchar(200) NOT NULL,
  `budgetId` varchar(40) NOT NULL,
  `locationId` varchar(40) NOT NULL,
  `budget` double NOT NULL,
  `carryStatus` int(11) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `realMonery` double NOT NULL,
  `useMonery` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `I_B` (`budgetId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_budgetlog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_budgetlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) DEFAULT NULL,
  `fromType` int(11) DEFAULT '0',
  `userType` int(11) DEFAULT '0',
  `billId` varchar(40) DEFAULT NULL,
  `billIds` varchar(1000) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refSubId` varchar(40) DEFAULT NULL,
  `budgetId` varchar(40) DEFAULT NULL,
  `budgetItemId` varchar(40) DEFAULT NULL,
  `budgetId0` varchar(40) DEFAULT NULL,
  `budgetItemId0` varchar(40) DEFAULT NULL,
  `budgetId1` varchar(40) DEFAULT NULL,
  `budgetItemId1` varchar(40) DEFAULT NULL,
  `budgetId2` varchar(40) DEFAULT NULL,
  `budgetItemId2` varchar(40) DEFAULT NULL,
  `feeItemId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `logTime` varchar(20) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `monery` bigint(20) NOT NULL,
  `log` varchar(1000) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `bakmonery` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `I_F` (`budgetId`),
  KEY `I_FI` (`budgetItemId`),
  KEY `I_FI0` (`budgetItemId0`),
  KEY `I_FI1` (`budgetItemId1`),
  KEY `I_FI2` (`budgetItemId2`),
  KEY `I_refId` (`refId`),
  KEY `I_refSubId` (`refSubId`),
  KEY `I_stafferId` (`stafferId`),
  KEY `I_logTime` (`logTime`)
) ENGINE=InnoDB AUTO_INCREMENT=208045 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_budgetlogtmp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_budgetlogtmp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `budgetLogId` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `budgetId` varchar(40) DEFAULT NULL,
  `budgetItemId` varchar(40) DEFAULT NULL,
  `monery` bigint(20) NOT NULL,
  `status` int(11) DEFAULT '0',
  `budgetId0` varchar(40) DEFAULT NULL,
  `budgetItemId0` varchar(40) DEFAULT NULL,
  `budgetId1` varchar(40) DEFAULT NULL,
  `budgetItemId1` varchar(40) DEFAULT NULL,
  `budgetId2` varchar(40) DEFAULT NULL,
  `budgetItemId2` varchar(40) DEFAULT NULL,
  `feeItemId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_refid` (`refId`)
) ENGINE=InnoDB AUTO_INCREMENT=4685 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_cgxd`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_cgxd` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `pm` varchar(255) NOT NULL DEFAULT '',
  `amount` int(11) NOT NULL DEFAULT '0',
  `dw` varchar(255) DEFAULT NULL,
  `xqrq` date NOT NULL DEFAULT '0000-00-00',
  `xqbm` varchar(255) NOT NULL DEFAULT '',
  `xqr` varchar(255) NOT NULL DEFAULT '',
  `creattime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status` int(11) NOT NULL DEFAULT '0',
  `cgspr` varchar(255) DEFAULT '',
  `cgspsj` datetime DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `xqbz` varchar(255) DEFAULT NULL,
  `yjdhrq` date DEFAULT '0000-00-00',
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=237 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_check`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_check` (
  `ID` varchar(40) NOT NULL,
  `checks` varchar(200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `unitId` varchar(40) DEFAULT NULL,
  `refId` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  KEY `INX_id` (`ID`),
  KEY `INX_stafferId` (`stafferId`),
  KEY `INX_refId` (`refId`),
  KEY `INX_logTime` (`logTime`),
  KEY `IX_unitId` (`unitId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_checkgrossprofit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_checkgrossprofit` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `basid` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `outid` varchar(255) DEFAULT NULL,
  `productid` varchar(255) DEFAULT NULL,
  `productname` varchar(255) DEFAULT NULL,
  `amount` varchar(255) DEFAULT NULL,
  `costprice` varchar(255) DEFAULT NULL,
  `checkgrossprofit` varchar(255) DEFAULT NULL,
  `approver` varchar(255) DEFAULT NULL,
  `approvername` varchar(255) DEFAULT NULL,
  `changetime` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`127.0.0.1`*/ /*!50003 TRIGGER t_center_checkgrossprofit

AFTER INSERT ON t_center_checkgrossprofit

FOR EACH ROW

BEGIN

update t_center_base set checkgrossprofit=new.checkgrossprofit where id=new.basid;
update t_center_out set changetime=new.changetime where fullid=NEW.outid;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `t_center_checkin`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_checkin` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(255) DEFAULT NULL,
  `workplace` varchar(255) DEFAULT NULL,
  `changetime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_checkpackage`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_checkpackage` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `packageid` varchar(255) DEFAULT NULL,
  `checkuser` varchar(255) DEFAULT NULL,
  `checkstatus` varchar(255) DEFAULT NULL,
  `workplace` varchar(255) DEFAULT NULL,
  `packageamount` varchar(255) DEFAULT NULL,
  `checktime` varchar(255) DEFAULT NULL,
  `begintime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `packageid` (`packageid`)
) ENGINE=InnoDB AUTO_INCREMENT=8653 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_citic_branch`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_citic_branch` (
  `id` varchar(40) NOT NULL,
  `stafferId` varchar(40) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_citic_order`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_citic_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mailId` varchar(100) NOT NULL,
  `status` int(11) DEFAULT '0',
  `customerId` varchar(40) NOT NULL,
  `customerName` varchar(40) NOT NULL,
  `idCard` varchar(20) NOT NULL,
  `dealDate` varchar(40) DEFAULT NULL,
  `pickupDate` varchar(40) DEFAULT NULL,
  `pickupFlag` int(11) DEFAULT NULL,
  `tellerId` varchar(40) DEFAULT NULL,
  `pickupNode` varchar(40) DEFAULT NULL,
  `pickupAddress` varchar(200) DEFAULT NULL,
  `branchId` varchar(40) DEFAULT NULL,
  `branchName` varchar(40) NOT NULL,
  `secondBranch` varchar(40) NOT NULL,
  `comunicationBranch` varchar(40) NOT NULL,
  `comunicatonBranchName` varchar(40) NOT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productCode` varchar(40) NOT NULL,
  `enterpriseProductCode` varchar(40) DEFAULT NULL,
  `productName` varchar(100) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `productWeight` double DEFAULT '0',
  `value` double NOT NULL DEFAULT '0',
  `fee` double NOT NULL DEFAULT '0',
  `arriveDate` varchar(40) DEFAULT NULL,
  `orderOrShow` varchar(40) DEFAULT NULL,
  `spotFlag` int(40) DEFAULT NULL,
  `citicNo` varchar(40) NOT NULL,
  `invoiceNature` varchar(40) NOT NULL,
  `invoiceHead` varchar(40) NOT NULL,
  `invoiceCondition` varchar(40) NOT NULL,
  `managerId` varchar(40) NOT NULL,
  `manager` varchar(40) NOT NULL,
  `originator` varchar(40) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `provinceName` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `city` varchar(40) NOT NULL,
  `address` varchar(40) NOT NULL,
  `receiver` varchar(40) NOT NULL,
  `receiverMobile` varchar(40) NOT NULL,
  `handPhone` varchar(40) DEFAULT NULL,
  `weight` double DEFAULT '0',
  `goldPrice` double DEFAULT '0',
  `materialType` varchar(40) DEFAULT NULL,
  `productType` varchar(40) DEFAULT NULL,
  `pickupType` varchar(40) DEFAULT NULL,
  `teller` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `citicOrderDate` varchar(40) DEFAULT NULL,
  `enterpriseName` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_ALL` (`citicNo`,`productName`)
) ENGINE=InnoDB AUTO_INCREMENT=44035 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_city`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_city` (
  `id` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_commission`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_commission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(40) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `shouldCommission` double DEFAULT NULL,
  `realCommission` double DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `fee` double DEFAULT NULL,
  `lastDeduction` double DEFAULT NULL,
  `receiveCost` double DEFAULT NULL,
  `KPIDeduction` double DEFAULT NULL,
  `turnNextMonthDeduction` double DEFAULT NULL,
  `YearKPIMoney` double DEFAULT NULL,
  `broken` double DEFAULT NULL,
  `baddebt` double DEFAULT NULL,
  `financeFee` double DEFAULT NULL,
  `insteadPay` double DEFAULT NULL,
  `handFee` double DEFAULT NULL,
  `befFreeze` double DEFAULT NULL,
  `curFreeze` double DEFAULT NULL,
  `finalCommission` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferId` (`stafferId`),
  KEY `idx_parentid` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=2345 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_commission_freeze`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_commission_freeze` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentid` varchar(40) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `freezeRate` double DEFAULT NULL,
  `freezeMoney` double DEFAULT NULL,
  `creditMoney` double DEFAULT NULL,
  `creditAmout` double DEFAULT NULL,
  `creditRate` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferId` (`stafferId`),
  KEY `idx_parentid` (`parentid`)
) ENGINE=InnoDB AUTO_INCREMENT=173 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_commission_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_commission_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `opr` varchar(50) DEFAULT NULL,
  `lastModified` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_commission_month`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_commission_month` (
  `id` varchar(40) NOT NULL DEFAULT '0',
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `beginTime` varchar(40) DEFAULT NULL,
  `endTime` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_compose`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_compose` (
  `id` varchar(40) NOT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `productId` varchar(20) DEFAULT NULL,
  `deportId` varchar(20) DEFAULT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `relationId` varchar(20) DEFAULT NULL,
  `refId` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `amount` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `checks` varchar(200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `goldPrice` double DEFAULT NULL,
  `silverPrice` double DEFAULT NULL,
  `parentId` varchar(40) DEFAULT NULL,
  `hybrid` int(11) DEFAULT '0',
  `tag` varchar(30) DEFAULT NULL,
  `description` varchar(1024) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `INX_STAFFERID` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_compose_fee`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_compose_fee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(40) DEFAULT NULL,
  `feeItemId` varchar(20) DEFAULT NULL,
  `price` double DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INX_PARENTID` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=3968 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_compose_feedefined`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_compose_feedefined` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `taxId` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_compose_item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_compose_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(20) DEFAULT NULL,
  `productId` varchar(20) DEFAULT NULL,
  `deportId` varchar(20) DEFAULT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `relationId` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  `inputRate` double DEFAULT '0',
  `stype` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INX_PARENTID` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=10089201 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_credit_curcore`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_credit_curcore` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(40) DEFAULT NULL,
  `maxBusiness` double DEFAULT NULL,
  `oldMaxBusiness` double DEFAULT NULL,
  `sumTotal` double DEFAULT NULL,
  `oldSumTotal` double DEFAULT NULL,
  `year` int(11) DEFAULT '2009',
  `oldYear` int(11) DEFAULT '2009',
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_INX_CID` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=22534 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_credit_item01`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_credit_item01` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `per` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_credit_item02`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_credit_item02` (
  `id` varchar(40) NOT NULL,
  `pid` varchar(40) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `unit` varchar(200) DEFAULT NULL,
  `sub` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `face` int(11) DEFAULT '0',
  `per` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_credit_item03`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_credit_item03` (
  `id` varchar(40) NOT NULL,
  `pid` varchar(40) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `unit` varchar(200) DEFAULT NULL,
  `indexPos` int(11) DEFAULT NULL,
  `per` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_credit_level`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_credit_level` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `min` double DEFAULT NULL,
  `max` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_curfee`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_curfee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=5380 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_curfee_detail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_curfee_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=43407 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_curmoney`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_curmoney` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `badDebts` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=6304 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_curmoney_detail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_curmoney_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `outtype` int(11) DEFAULT NULL,
  `promValue` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `badDebts` double DEFAULT NULL,
  `refOutFullId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=50031 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_curprofit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_curprofit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=5380 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_cus_check`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_cus_check` (
  `id` varchar(40) NOT NULL,
  `applyerId` varchar(40) DEFAULT NULL,
  `checkerId` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `approverId` varchar(40) DEFAULT NULL,
  `beginTime` varchar(40) DEFAULT NULL,
  `endTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `retInit` int(11) DEFAULT '0',
  `retOK` int(11) DEFAULT '0',
  `retError` int(11) DEFAULT '0',
  `logTime` varchar(20) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_F` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_cus_checkitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_cus_checkitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `customerCode` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `ret` int(11) DEFAULT '0',
  `logTime` varchar(20) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_F` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=298044 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_cusassignaly`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_cusassignaly` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userId` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `locationId` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `I_C` (`customerId`),
  KEY `I_S` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_cusprosailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_cusprosailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` varchar(255) NOT NULL DEFAULT '',
  `customerid` varchar(255) DEFAULT '',
  `customername` varchar(255) DEFAULT '',
  `sh` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `productname` varchar(255) DEFAULT NULL,
  `productid` varchar(255) DEFAULT NULL,
  `amount` varchar(10) DEFAULT NULL,
  `value` double(12,2) DEFAULT NULL,
  `ibmoney` double(12,2) DEFAULT NULL,
  `jlmoney` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=978839 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_custapprove`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_custapprove` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `customerId` varchar(40) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `applyType` int(255) DEFAULT NULL,
  `sellType` int(11) DEFAULT NULL,
  `protype` int(11) DEFAULT NULL,
  `protype2` int(11) DEFAULT NULL,
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) DEFAULT NULL,
  `fromType` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `opr` int(11) DEFAULT NULL,
  `applyId` varchar(40) DEFAULT NULL,
  `oprReson` varchar(200) DEFAULT NULL,
  `reson` varchar(200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idx_name` (`name`),
  UNIQUE KEY `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_apply` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `selltype` int(11) DEFAULT '0',
  `protype` int(11) DEFAULT '0',
  `newtype` int(11) DEFAULT '0',
  `qqtype` int(11) DEFAULT '0',
  `rtype` int(11) DEFAULT '0',
  `createrId` varchar(50) DEFAULT NULL,
  `formtype` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `company` varchar(1000) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `fax` varchar(1000) DEFAULT NULL,
  `mail` varchar(1000) DEFAULT NULL,
  `postcode` varchar(40) DEFAULT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `accounts` varchar(100) DEFAULT NULL,
  `dutycode` varchar(100) DEFAULT NULL,
  `flowcom` varchar(400) DEFAULT NULL,
  `opr` int(11) DEFAULT '0',
  `reson` varchar(400) DEFAULT NULL,
  `oprReson` varchar(400) DEFAULT NULL,
  `updaterId` varchar(50) DEFAULT NULL,
  `loginTime` varchar(50) DEFAULT NULL,
  `createTime` varchar(50) DEFAULT NULL,
  `beginConnectTime` varchar(50) DEFAULT NULL,
  `mtype` int(11) DEFAULT NULL,
  `htype` int(11) DEFAULT NULL,
  `blog` int(11) DEFAULT NULL,
  `card` int(11) DEFAULT NULL,
  `lever` int(11) DEFAULT '1',
  `hlocal` varchar(200) DEFAULT NULL,
  `assignPer1` double DEFAULT '0',
  `assignPer2` double DEFAULT '0',
  `assignPer3` double DEFAULT '0',
  `assignPer4` double DEFAULT '0',
  `post` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `msn` varchar(200) DEFAULT NULL,
  `web` varchar(500) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL,
  `hasNew` int(11) DEFAULT '0',
  `cdepartement` varchar(200) DEFAULT NULL,
  `creditLevelId` varchar(40) DEFAULT '90000000000000000001',
  `creditVal` double DEFAULT '30',
  `creditUpdateTime` int(11) DEFAULT '0',
  `reserve1` varchar(1200) DEFAULT NULL,
  `reserve2` varchar(1200) DEFAULT NULL,
  `reserve3` varchar(1200) DEFAULT NULL,
  `reserve4` varchar(1200) DEFAULT NULL,
  `reserve5` varchar(1200) DEFAULT NULL,
  `reserve6` varchar(1200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(200) DEFAULT NULL,
  `selltype1` int(11) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  `bankClass` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INX_NAME` (`name`),
  UNIQUE KEY `IX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_bank`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_bank` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `customerId` varchar(400) DEFAULT NULL,
  `accountName` varchar(200) DEFAULT NULL,
  `accountNO` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=12211 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_corporation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_corporation` (
  `id` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `licenseNo` varchar(100) NOT NULL,
  `registryMoney` double NOT NULL,
  `registryAddress` varchar(400) NOT NULL,
  `businessAddress` varchar(400) NOT NULL,
  `assets` double NOT NULL,
  `establishDate` varchar(10) NOT NULL,
  `lastYearSales` double NOT NULL,
  `thisYearSales` double NOT NULL,
  `employeeAmount` int(11) NOT NULL,
  `sellerAmount` int(11) DEFAULT NULL,
  `saleArea` varchar(100) DEFAULT NULL,
  `agent` varchar(100) DEFAULT NULL,
  `agentArea` varchar(100) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_NAME` (`name`),
  UNIQUE KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_corporation_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_corporation_apply` (
  `id` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `licenseNo` varchar(100) NOT NULL,
  `registryMoney` double NOT NULL,
  `registryAddress` varchar(400) NOT NULL,
  `businessAddress` varchar(400) NOT NULL,
  `assets` double NOT NULL,
  `establishDate` varchar(10) NOT NULL,
  `lastYearSales` double NOT NULL,
  `thisYearSales` double NOT NULL,
  `employeeAmount` int(11) NOT NULL,
  `sellerAmount` int(11) DEFAULT NULL,
  `saleArea` varchar(100) DEFAULT NULL,
  `agent` varchar(100) DEFAULT NULL,
  `agentArea` varchar(100) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_NAME` (`name`),
  UNIQUE KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_corporation_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_corporation_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `forward` int(11) DEFAULT NULL,
  `customerId` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `licenseNo` varchar(100) NOT NULL,
  `registryMoney` double NOT NULL,
  `registryAddress` varchar(400) NOT NULL,
  `businessAddress` varchar(400) NOT NULL,
  `assets` double NOT NULL,
  `establishDate` varchar(10) NOT NULL,
  `lastYearSales` double NOT NULL,
  `thisYearSales` double NOT NULL,
  `employeeAmount` int(11) NOT NULL,
  `sellerAmount` int(11) DEFAULT NULL,
  `saleArea` varchar(100) DEFAULT NULL,
  `agent` varchar(100) DEFAULT NULL,
  `agentArea` varchar(100) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=157 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_depart`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_depart` (
  `id` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `people` int(11) DEFAULT NULL,
  `departType` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_NAME` (`name`),
  UNIQUE KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_depart_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_depart_apply` (
  `code` varchar(20) DEFAULT NULL,
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `people` int(11) DEFAULT NULL,
  `departType` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_NAME` (`name`),
  UNIQUE KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_depart_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_depart_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `forward` int(11) DEFAULT NULL,
  `customerId` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `people` int(11) DEFAULT NULL,
  `departType` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_his` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `CUSTOMERID` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `selltype` int(11) DEFAULT '0',
  `protype` int(11) DEFAULT '0',
  `newtype` int(11) DEFAULT '0',
  `qqtype` int(11) DEFAULT '0',
  `rtype` int(11) DEFAULT '0',
  `createrId` varchar(50) DEFAULT NULL,
  `formtype` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `address` varchar(1000) DEFAULT NULL,
  `company` varchar(1000) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `fax` varchar(1000) DEFAULT NULL,
  `mail` varchar(1000) DEFAULT NULL,
  `postcode` varchar(40) DEFAULT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `accounts` varchar(100) DEFAULT NULL,
  `dutycode` varchar(100) DEFAULT NULL,
  `flowcom` varchar(400) DEFAULT NULL,
  `updaterId` varchar(50) DEFAULT NULL,
  `loginTime` varchar(50) DEFAULT NULL,
  `createTime` varchar(50) DEFAULT NULL,
  `beginConnectTime` varchar(50) DEFAULT NULL,
  `mtype` int(11) DEFAULT NULL,
  `htype` int(11) DEFAULT NULL,
  `blog` int(11) DEFAULT NULL,
  `card` int(11) DEFAULT NULL,
  `lever` int(11) DEFAULT '1',
  `hlocal` varchar(200) DEFAULT NULL,
  `assignPer1` double DEFAULT '0',
  `assignPer2` double DEFAULT '0',
  `assignPer3` double DEFAULT '0',
  `assignPer4` double DEFAULT '0',
  `post` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `msn` varchar(200) DEFAULT NULL,
  `web` varchar(500) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL,
  `checkStatus` int(11) NOT NULL DEFAULT '0',
  `hasNew` int(11) DEFAULT '0',
  `cdepartement` varchar(200) DEFAULT NULL,
  `creditLevelId` varchar(40) DEFAULT '90000000000000000001',
  `creditVal` double DEFAULT '30',
  `creditUpdateTime` int(11) DEFAULT '0',
  `reserve1` varchar(1200) DEFAULT NULL,
  `reserve2` varchar(1200) DEFAULT NULL,
  `reserve3` varchar(1200) DEFAULT NULL,
  `reserve4` varchar(1200) DEFAULT NULL,
  `reserve5` varchar(1200) DEFAULT NULL,
  `reserve6` varchar(1200) DEFAULT NULL,
  `checks` varchar(200) DEFAULT NULL,
  `selltype1` int(11) DEFAULT NULL,
  `bankClass` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_CID` (`CUSTOMERID`)
) ENGINE=InnoDB AUTO_INCREMENT=61487 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_individual`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_individual` (
  `id` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `sex` int(11) NOT NULL,
  `personal` int(11) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `qq` varchar(1000) DEFAULT NULL,
  `weibo` varchar(1000) DEFAULT NULL,
  `weixin` varchar(1000) DEFAULT NULL,
  `duty` int(11) DEFAULT NULL,
  `reportTo` varchar(40) DEFAULT NULL,
  `interest` varchar(100) DEFAULT NULL,
  `relationship` int(1) DEFAULT '-1',
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_individual_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_individual_apply` (
  `id` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `sex` int(11) NOT NULL,
  `personal` int(11) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `qq` varchar(1000) DEFAULT NULL,
  `weibo` varchar(1000) DEFAULT NULL,
  `weixin` varchar(1000) DEFAULT NULL,
  `duty` int(11) DEFAULT NULL,
  `reportTo` varchar(40) DEFAULT NULL,
  `interest` varchar(100) DEFAULT NULL,
  `relationship` int(1) DEFAULT '-1',
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_NAME` (`name`),
  UNIQUE KEY `IDX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_individual_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_individual_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `forward` int(11) DEFAULT NULL,
  `customerId` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `sex` int(11) NOT NULL,
  `personal` int(11) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `qq` varchar(1000) DEFAULT NULL,
  `weibo` varchar(1000) DEFAULT NULL,
  `weixin` varchar(1000) DEFAULT NULL,
  `duty` int(11) DEFAULT NULL,
  `reportTo` varchar(40) DEFAULT NULL,
  `interest` varchar(100) DEFAULT NULL,
  `relationship` int(1) DEFAULT '-1',
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_log` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `oldName` varchar(200) DEFAULT NULL,
  `newName` varchar(200) DEFAULT NULL,
  `opr` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_main`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_main` (
  `id` varchar(40) NOT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `simpleName` varchar(100) NOT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `sellType` int(11) NOT NULL DEFAULT '0',
  `protype` int(11) NOT NULL DEFAULT '0',
  `protype2` int(11) NOT NULL DEFAULT '0',
  `qqtype` int(11) DEFAULT NULL,
  `rtype` int(11) NOT NULL DEFAULT '0',
  `fromType` int(11) NOT NULL,
  `introducer` varchar(40) DEFAULT NULL,
  `industry` int(11) NOT NULL,
  `historySales` int(11) DEFAULT NULL,
  `salesAmount` double DEFAULT NULL,
  `creditLevelId` varchar(40) DEFAULT '90000000000000000001',
  `creditVal` double DEFAULT '30',
  `creditUpdateTime` int(11) DEFAULT '0',
  `contactTimes` int(11) DEFAULT NULL,
  `lastContactTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `ostatus` int(11) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `createTime` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `hasNew` int(11) DEFAULT '0',
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(200) DEFAULT NULL,
  `refCorpCustId` varchar(40) DEFAULT NULL,
  `refDepartCustId` varchar(40) DEFAULT NULL,
  `formerCust` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `IDX_CODE` (`code`),
  KEY `idx_cityid` (`cityId`),
  KEY `idx_logtime` (`logTime`),
  KEY `idx_createtime` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_now_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_now_bak` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `selltype` int(11) DEFAULT '0',
  `protype` int(11) DEFAULT '0',
  `newtype` int(11) DEFAULT '0',
  `qqtype` int(11) DEFAULT '0',
  `rtype` int(11) DEFAULT '0',
  `createrId` varchar(50) DEFAULT NULL,
  `formtype` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `company` varchar(1000) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `fax` varchar(1000) DEFAULT NULL,
  `mail` varchar(1000) DEFAULT NULL,
  `postcode` varchar(40) DEFAULT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `accounts` varchar(100) DEFAULT NULL,
  `dutycode` varchar(100) DEFAULT NULL,
  `flowcom` varchar(400) DEFAULT NULL,
  `loginTime` varchar(50) DEFAULT NULL,
  `createTime` varchar(50) DEFAULT NULL,
  `beginConnectTime` varchar(50) DEFAULT NULL,
  `mtype` int(11) DEFAULT NULL,
  `htype` int(11) DEFAULT NULL,
  `blog` int(11) DEFAULT NULL,
  `card` int(11) DEFAULT NULL,
  `lever` int(11) DEFAULT '1',
  `hlocal` varchar(200) DEFAULT NULL,
  `assignPer1` double DEFAULT '0',
  `assignPer2` double DEFAULT '0',
  `assignPer3` double DEFAULT '0',
  `assignPer4` double DEFAULT '0',
  `post` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `msn` varchar(200) DEFAULT NULL,
  `web` varchar(500) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL,
  `hasNew` int(11) DEFAULT '0',
  `cdepartement` varchar(200) DEFAULT NULL,
  `creditLevelId` varchar(40) DEFAULT '90000000000000000001',
  `creditVal` double DEFAULT '30',
  `creditUpdateTime` int(11) DEFAULT '0',
  `reserve1` varchar(1200) DEFAULT NULL,
  `reserve2` varchar(1200) DEFAULT NULL,
  `reserve3` varchar(1200) DEFAULT NULL,
  `reserve4` varchar(1200) DEFAULT NULL,
  `reserve5` varchar(1200) DEFAULT NULL,
  `reserve6` varchar(1200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(200) DEFAULT NULL,
  `selltype1` int(11) DEFAULT NULL,
  `bankClass` int(11) DEFAULT NULL,
  `ostatus` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customer_now_old`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customer_now_old` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `selltype` int(11) DEFAULT '0',
  `protype` int(11) DEFAULT '0',
  `newtype` int(11) DEFAULT '0',
  `qqtype` int(11) DEFAULT '0',
  `rtype` int(11) DEFAULT '0',
  `createrId` varchar(50) DEFAULT NULL,
  `formtype` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `company` varchar(1000) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `fax` varchar(1000) DEFAULT NULL,
  `mail` varchar(1000) DEFAULT NULL,
  `postcode` varchar(40) DEFAULT NULL,
  `bank` varchar(100) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `accounts` varchar(100) DEFAULT NULL,
  `dutycode` varchar(100) DEFAULT NULL,
  `flowcom` varchar(400) DEFAULT NULL,
  `loginTime` varchar(50) DEFAULT NULL,
  `createTime` varchar(50) DEFAULT NULL,
  `beginConnectTime` varchar(50) DEFAULT NULL,
  `mtype` int(11) DEFAULT NULL,
  `htype` int(11) DEFAULT NULL,
  `blog` int(11) DEFAULT NULL,
  `card` int(11) DEFAULT NULL,
  `lever` int(11) DEFAULT '1',
  `hlocal` varchar(200) DEFAULT NULL,
  `assignPer1` double DEFAULT '0',
  `assignPer2` double DEFAULT '0',
  `assignPer3` double DEFAULT '0',
  `assignPer4` double DEFAULT '0',
  `post` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `msn` varchar(200) DEFAULT NULL,
  `web` varchar(500) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL,
  `hasNew` int(11) DEFAULT '0',
  `cdepartement` varchar(200) DEFAULT NULL,
  `creditLevelId` varchar(40) DEFAULT '90000000000000000001',
  `creditVal` double DEFAULT '30',
  `creditUpdateTime` int(11) DEFAULT '0',
  `reserve1` varchar(1200) DEFAULT NULL,
  `reserve2` varchar(1200) DEFAULT NULL,
  `reserve3` varchar(1200) DEFAULT NULL,
  `reserve4` varchar(1200) DEFAULT NULL,
  `reserve5` varchar(1200) DEFAULT NULL,
  `reserve6` varchar(1200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(200) DEFAULT NULL,
  `selltype1` int(11) DEFAULT NULL,
  `bankClass` int(11) DEFAULT NULL,
  `ostatus` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INX_NAME` (`name`),
  UNIQUE KEY `IX_CODE` (`code`),
  KEY `IX_CITY` (`cityId`),
  KEY `INX_LOGINTIME` (`loginTime`),
  KEY `INX_CREATETIME` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_customersailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_customersailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` varchar(255) NOT NULL DEFAULT '',
  `customerid` varchar(255) DEFAULT '',
  `customername` varchar(255) DEFAULT '',
  `sh` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `value` double(12,2) DEFAULT NULL,
  `ibmoney` double(12,2) DEFAULT NULL,
  `jlmoney` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=945019 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_decompose`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_decompose` (
  `id` varchar(40) NOT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `productId` varchar(20) DEFAULT NULL,
  `deportId` varchar(20) DEFAULT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `amount` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INX_STAFFERID` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_depot`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_depot` (
  `id` varchar(40) NOT NULL,
  `type` int(11) DEFAULT '0',
  `name` varchar(200) NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `industryId2` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_depotpart`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_depotpart` (
  `id` varchar(40) NOT NULL,
  `type` int(11) DEFAULT '0',
  `name` varchar(200) NOT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_distbase`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_distbase` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `baseId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=531596 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_distribution`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_distribution` (
  `id` varchar(40) NOT NULL DEFAULT '0',
  `outId` varchar(40) DEFAULT NULL,
  `refAddId` varchar(40) DEFAULT NULL,
  `shipping` int(11) DEFAULT NULL,
  `transport1` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL,
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `areaId` varchar(40) DEFAULT NULL,
  `printCount` int(11) DEFAULT '0',
  `outboundDate` varchar(20) DEFAULT NULL COMMENT '发货日期',
  PRIMARY KEY (`id`),
  KEY `idx_outid` (`outId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_dutyentity`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_dutyentity` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `icp` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `dues` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `showType` int(11) DEFAULT '0',
  `invoicer` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_enum`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_enum` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(40) NOT NULL,
  `keyss` varchar(40) NOT NULL,
  `val` varchar(200) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_ENUM` (`type`,`keyss`)
) ENGINE=InnoDB AUTO_INCREMENT=349 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_enumdefine`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_enumdefine` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `cnname` varchar(200) NOT NULL,
  `ref` varchar(1000) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_express`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_express` (
  `id` int(11) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `name2` varchar(64) DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_external_assess`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_external_assess` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `KPI` double DEFAULT NULL,
  `ratio` double DEFAULT NULL,
  `cost` double DEFAULT NULL,
  `yearKPI` double DEFAULT NULL,
  `shouldCommission` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_external_stafferyear`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_external_stafferyear` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `years` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=232 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_feedback`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_feedback` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `customerId` varchar(200) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `outCount` int(11) DEFAULT NULL,
  `productCount` int(11) DEFAULT NULL,
  `moneys` double DEFAULT NULL,
  `noPayMoneys` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `bear` varchar(40) DEFAULT NULL,
  `bearName` varchar(200) DEFAULT NULL,
  `refVisitId` varchar(40) DEFAULT NULL,
  `forecastDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `mailAddress` varchar(50) DEFAULT NULL,
  `statsStar` varchar(40) DEFAULT NULL,
  `statsEnd` varchar(40) DEFAULT NULL,
  `industryIdName` varchar(100) DEFAULT NULL,
  `hadpay` double DEFAULT NULL,
  `pstatus` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_feedback_check`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_feedback_check` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `taskId` varchar(40) DEFAULT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `statTime` varchar(40) DEFAULT NULL,
  `contact` varchar(100) DEFAULT NULL,
  `contactPhone` varchar(100) DEFAULT NULL,
  `ifPromiseReplyCheck` int(11) DEFAULT NULL,
  `planReplyDate` varchar(40) DEFAULT NULL,
  `ifReceiveConfirmFax` int(11) DEFAULT NULL,
  `checkResult` int(11) DEFAULT NULL,
  `caller` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `stafferName` varchar(100) DEFAULT NULL,
  `statsStar` varchar(40) DEFAULT NULL,
  `statsEnd` varchar(40) DEFAULT NULL,
  `industryIdName` varchar(100) DEFAULT NULL,
  `ifSendConfirmFax` int(11) DEFAULT NULL,
  `checkProcess` int(11) DEFAULT NULL,
  `exceptionProcesser` varchar(40) DEFAULT NULL,
  `exceptionProcesserName` varchar(40) DEFAULT NULL,
  `exceptionStatus` int(11) DEFAULT NULL,
  `actualExceptionReason` int(11) DEFAULT NULL,
  `resolve` int(11) DEFAULT NULL,
  `resolveText` varchar(300) DEFAULT NULL,
  `exceptRef` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_taskid` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_feedback_detail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_feedback_detail` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `hasBack` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `backMoney` double DEFAULT NULL,
  `pay` int(11) DEFAULT NULL,
  `noPayAmount` int(11) DEFAULT NULL,
  `noPayMoneys` double DEFAULT NULL,
  `settleNoPayAmount` int(11) DEFAULT NULL,
  `settleNoPayMoney` double DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_taskid` (`taskId`),
  KEY `idx_taskproduct` (`taskId`,`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=97748 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_feedback_visit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_feedback_visit` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `taskId` varchar(40) DEFAULT NULL,
  `taskType` int(11) DEFAULT NULL,
  `caller` varchar(40) DEFAULT NULL,
  `callTime` varchar(40) DEFAULT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `exceptionProcesser` varchar(40) DEFAULT NULL,
  `exceptionProcesserName` varchar(40) DEFAULT NULL,
  `exceptionStatus` int(11) DEFAULT NULL,
  `ifHasContact` varchar(255) DEFAULT NULL,
  `contact` varchar(100) DEFAULT NULL,
  `contactPhone` varchar(100) DEFAULT NULL,
  `planReplyDate` varchar(40) DEFAULT NULL,
  `actualExceptionReason` int(11) DEFAULT NULL,
  `resolve` int(11) DEFAULT NULL,
  `resolveText` varchar(300) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `stafferName` varchar(100) DEFAULT NULL,
  `exceptRef` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_taskid` (`taskId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_feedback_visititem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_feedback_visititem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `receive` int(11) DEFAULT NULL,
  `receiptTime` varchar(40) DEFAULT NULL,
  `ifHasException` int(11) DEFAULT NULL,
  `exceptionAmount` int(11) DEFAULT NULL,
  `exceptionType` int(11) DEFAULT NULL,
  `exceptionText` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_refid` (`refId`)
) ENGINE=InnoDB AUTO_INCREMENT=33656 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_feeitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_feeitem` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `taxId` varchar(40) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `taxId2` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_finance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_finance` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `updateFlag` int(11) DEFAULT '0',
  `dutyId` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refOut` varchar(40) DEFAULT NULL,
  `refBill` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `inmoney` bigint(20) DEFAULT '0',
  `outmoney` bigint(20) DEFAULT '0',
  `checks` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `financeDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `locks` int(11) DEFAULT '0',
  `monthIndex` int(11) DEFAULT '0',
  `refChecks` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_DUTYID` (`dutyId`),
  KEY `IX_refId` (`refId`),
  KEY `IX_refOut` (`refOut`),
  KEY `IX_refBill` (`refBill`),
  KEY `IX_refStock` (`refStock`),
  KEY `IX_financeDate` (`financeDate`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_finance_fee_detail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_finance_fee_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `outid` varchar(40) DEFAULT NULL,
  `outValue` double DEFAULT NULL,
  `outBackValue` double DEFAULT NULL,
  `otherBuyValue` double DEFAULT NULL,
  `money` double DEFAULT NULL,
  `ratio` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `used` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferId` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=12006 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_finance_fee_result`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_finance_fee_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=6304 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_finance_fee_stats`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_finance_fee_stats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=12870 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_finance_tag`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_finance_tag` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(40) DEFAULT NULL,
  `typeName` varchar(50) DEFAULT NULL,
  `fullId` varchar(40) DEFAULT NULL,
  `tag` varchar(20) DEFAULT NULL,
  `statsTime` varchar(30) DEFAULT NULL,
  `mtype` int(11) DEFAULT '-1',
  `insId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=840136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_finance_tag1`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_finance_tag1` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(40) DEFAULT NULL,
  `typeName` varchar(50) DEFAULT NULL,
  `fullId` varchar(40) DEFAULT NULL,
  `tag` varchar(20) DEFAULT NULL,
  `statsTime` varchar(30) DEFAULT NULL,
  `mtype` int(11) DEFAULT '-1',
  `insId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1902 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_finance_temp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_finance_temp` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `locks` int(11) DEFAULT '0',
  `updateFlag` int(11) DEFAULT '0',
  `dutyId` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refOut` varchar(40) DEFAULT NULL,
  `refBill` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `inmoney` bigint(20) DEFAULT '0',
  `outmoney` bigint(20) DEFAULT '0',
  `checks` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `financeDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `monthIndex` int(11) DEFAULT '0',
  `refChecks` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_DUTYID` (`dutyId`),
  KEY `IX_refId` (`refId`),
  KEY `IX_refOut` (`refOut`),
  KEY `IX_refBill` (`refBill`),
  KEY `IX_refStock` (`refStock`),
  KEY `IX_financeDate` (`financeDate`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financeitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financeitem` (
  `ID` varchar(40) NOT NULL,
  `pid` varchar(40) NOT NULL,
  `pareId` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `forward` int(11) DEFAULT '0',
  `taxId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `inmoney` bigint(20) DEFAULT '0',
  `outmoney` bigint(20) DEFAULT '0',
  `unitType` int(11) DEFAULT '0',
  `unitId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `depotId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `duty2Id` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refOut` varchar(40) DEFAULT NULL,
  `refBill` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `financeDate` varchar(40) DEFAULT NULL,
  `taxId0` varchar(40) DEFAULT NULL,
  `taxId1` varchar(40) DEFAULT NULL,
  `taxId2` varchar(40) DEFAULT NULL,
  `taxId3` varchar(40) DEFAULT NULL,
  `taxId4` varchar(40) DEFAULT NULL,
  `taxId5` varchar(40) DEFAULT NULL,
  `taxId6` varchar(40) DEFAULT NULL,
  `taxId7` varchar(40) DEFAULT NULL,
  `taxId8` varchar(40) DEFAULT NULL,
  `productAmountIn` int(11) DEFAULT '0',
  `productAmountOut` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `IX_pid` (`pid`),
  KEY `IX_taxId` (`taxId`),
  KEY `IX_taxId0` (`taxId0`),
  KEY `IX_taxId1` (`taxId1`),
  KEY `IX_taxId2` (`taxId2`),
  KEY `IX_taxId3` (`taxId3`),
  KEY `IX_taxId4` (`taxId4`),
  KEY `IX_refId` (`refId`),
  KEY `IX_refOut` (`refOut`),
  KEY `IX_refBill` (`refBill`),
  KEY `IX_refStock` (`refStock`),
  KEY `IX_financeDate` (`financeDate`),
  KEY `IX_departmentId` (`departmentId`),
  KEY `IX_LOGTIME` (`logTime`),
  KEY `IX_taxId5` (`taxId5`),
  KEY `INX_taxId6` (`taxId6`),
  KEY `INX_taxId7` (`taxId7`),
  KEY `INX_taxId8` (`taxId8`),
  KEY `IX_SFALL` (`stafferId`),
  KEY `IX_UFALL` (`unitId`),
  KEY `IX_TFALL0` (`taxId0`),
  KEY `IX_TFALL` (`taxId`),
  KEY `IX_DFALL` (`dutyId`),
  KEY `IX_TFALL1` (`taxId1`),
  KEY `IX_TFALL2` (`taxId2`),
  KEY `IX_TFALL3` (`taxId3`),
  KEY `IX_TFALL4` (`taxId4`),
  KEY `IX_productid` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financeitem_temp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financeitem_temp` (
  `ID` varchar(40) NOT NULL,
  `pid` varchar(40) NOT NULL,
  `pareId` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `forward` int(11) DEFAULT '0',
  `taxId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `inmoney` bigint(20) DEFAULT '0',
  `outmoney` bigint(20) DEFAULT '0',
  `unitType` int(11) DEFAULT '0',
  `unitId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `depotId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productAmountIn` int(11) DEFAULT '0',
  `productAmountOut` int(11) DEFAULT '0',
  `duty2Id` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refOut` varchar(40) DEFAULT NULL,
  `refBill` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `financeDate` varchar(40) DEFAULT NULL,
  `taxId0` varchar(40) DEFAULT NULL,
  `taxId1` varchar(40) DEFAULT NULL,
  `taxId2` varchar(40) DEFAULT NULL,
  `taxId3` varchar(40) DEFAULT NULL,
  `taxId4` varchar(40) DEFAULT NULL,
  `taxId5` varchar(40) DEFAULT NULL,
  `taxId6` varchar(40) DEFAULT NULL,
  `taxId7` varchar(40) DEFAULT NULL,
  `taxId8` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_pid` (`pid`),
  KEY `IX_taxId` (`taxId`),
  KEY `IX_unitId` (`unitId`),
  KEY `IX_DUTYID` (`dutyId`),
  KEY `IX_taxId0` (`taxId0`),
  KEY `IX_taxId1` (`taxId1`),
  KEY `IX_taxId2` (`taxId2`),
  KEY `IX_taxId3` (`taxId3`),
  KEY `IX_taxId4` (`taxId4`),
  KEY `IX_taxId5` (`taxId5`),
  KEY `IX_taxId6` (`taxId6`),
  KEY `IX_taxId7` (`taxId7`),
  KEY `IX_taxId8` (`taxId8`),
  KEY `IX_refId` (`refId`),
  KEY `IX_refOut` (`refOut`),
  KEY `IX_refBill` (`refBill`),
  KEY `IX_refStock` (`refStock`),
  KEY `IX_financeDate` (`financeDate`),
  KEY `IX_departmentId` (`departmentId`),
  KEY `IX_stafferId` (`stafferId`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financemonth`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financemonth` (
  `ID` varchar(40) NOT NULL,
  `taxId` varchar(100) DEFAULT NULL,
  `inmoneyTotal` bigint(20) DEFAULT '0',
  `outmoneyTotal` bigint(20) DEFAULT '0',
  `lastTotal` bigint(20) DEFAULT '0',
  `inmoneyAllTotal` bigint(20) DEFAULT '0',
  `outmoneyAllTotal` bigint(20) DEFAULT '0',
  `lastAllTotal` bigint(20) DEFAULT '0',
  `monthKey` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `taxId0` varchar(40) DEFAULT NULL,
  `taxId1` varchar(40) DEFAULT NULL,
  `taxId2` varchar(40) DEFAULT NULL,
  `taxId3` varchar(40) DEFAULT NULL,
  `taxId4` varchar(40) DEFAULT NULL,
  `taxId5` varchar(40) DEFAULT NULL,
  `taxId6` varchar(40) DEFAULT NULL,
  `taxId7` varchar(40) DEFAULT NULL,
  `taxId8` varchar(40) DEFAULT NULL,
  `monthTurnTotal` bigint(20) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_monthKey` (`monthKey`,`taxId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financemonth_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financemonth_bak` (
  `ID` varchar(40) NOT NULL,
  `taxId` varchar(100) DEFAULT NULL,
  `inmoneyTotal` bigint(20) DEFAULT '0',
  `outmoneyTotal` bigint(20) DEFAULT '0',
  `lastTotal` bigint(20) DEFAULT '0',
  `inmoneyAllTotal` bigint(20) DEFAULT '0',
  `outmoneyAllTotal` bigint(20) DEFAULT '0',
  `lastAllTotal` bigint(20) DEFAULT '0',
  `monthKey` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `taxId0` varchar(40) DEFAULT NULL,
  `taxId1` varchar(40) DEFAULT NULL,
  `taxId2` varchar(40) DEFAULT NULL,
  `taxId3` varchar(40) DEFAULT NULL,
  `taxId4` varchar(40) DEFAULT NULL,
  `taxId5` varchar(40) DEFAULT NULL,
  `taxId6` varchar(40) DEFAULT NULL,
  `taxId7` varchar(40) DEFAULT NULL,
  `taxId8` varchar(40) DEFAULT NULL,
  `monthTurnTotal` bigint(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financemonth_bef`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financemonth_bef` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT NULL,
  `itemIndex` int(11) DEFAULT NULL,
  `itemName` varchar(200) DEFAULT NULL,
  `money` bigint(20) DEFAULT NULL,
  `monthKey` varchar(8) DEFAULT NULL,
  `year` varchar(4) DEFAULT NULL,
  `month` varchar(4) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `lastOpr` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financerefer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financerefer` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `other` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financerep`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financerep` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT '0',
  `rmethod` int(11) DEFAULT '0',
  `itemIndex` int(11) DEFAULT '0',
  `itemName` varchar(200) DEFAULT NULL,
  `itemPName` varchar(200) DEFAULT NULL,
  `expr` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_monthKey` (`type`,`itemIndex`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financeshow`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financeshow` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `taxId` varchar(40) DEFAULT NULL,
  `taxName` varchar(100) DEFAULT NULL,
  `showBeginAllmoney` varchar(50) DEFAULT NULL,
  `beginAllmoney` bigint(20) DEFAULT NULL,
  `showCurrInmoney` varchar(50) DEFAULT NULL,
  `currInmoney` bigint(20) DEFAULT NULL,
  `showAllInmoney` varchar(50) DEFAULT NULL,
  `allInmoney` bigint(20) DEFAULT NULL,
  `showCurrOutmoney` varchar(50) DEFAULT NULL,
  `currOutmoney` bigint(20) DEFAULT NULL,
  `showAllOutmoney` varchar(50) DEFAULT NULL,
  `allOutmoney` bigint(20) DEFAULT NULL,
  `showLastmoney` varchar(50) DEFAULT NULL,
  `lastmoney` bigint(20) DEFAULT NULL,
  `forwardName` varchar(10) DEFAULT NULL,
  `unitName` varchar(100) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=869 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financeturn`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financeturn` (
  `ID` varchar(40) NOT NULL,
  `status` int(11) DEFAULT '0',
  `amount` int(11) DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `startTime` varchar(40) DEFAULT NULL,
  `endTime` varchar(40) DEFAULT NULL,
  `monthKey` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_monthKey` (`monthKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_financeturn_rollback`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_financeturn_rollback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `monthkey` varchar(20) DEFAULT NULL,
  `logtime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_flowlog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_flowlog` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `outid` varchar(30) NOT NULL DEFAULT '',
  `stafferid` varchar(20) NOT NULL DEFAULT '',
  `type` varchar(2) NOT NULL DEFAULT '',
  `approver` varchar(20) NOT NULL DEFAULT '',
  `prestatus` varchar(2) NOT NULL DEFAULT '',
  `status` varchar(2) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `changetime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=18632 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_frflow`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_frflow` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `lcid` varchar(30) NOT NULL DEFAULT '',
  `outid` varchar(30) DEFAULT '',
  `stafferid` varchar(20) NOT NULL DEFAULT '',
  `type` varchar(2) NOT NULL DEFAULT '',
  `yctype` varchar(255) DEFAULT NULL,
  `approver` varchar(20) NOT NULL DEFAULT '',
  `status` varchar(2) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT '',
  `createtime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `changetime` varchar(255) NOT NULL DEFAULT '',
  `fj` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `outid` (`outid`)
) ENGINE=InnoDB AUTO_INCREMENT=12800 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_frflowstatus`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_frflowstatus` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `status` int(2) NOT NULL DEFAULT '0',
  `statusname` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=100 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_frflowtype`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_frflowtype` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(2) NOT NULL DEFAULT '0',
  `typename` varchar(20) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_fruser`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_fruser` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `name` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `bmid` int(2) NOT NULL DEFAULT '0',
  `preid` int(11) DEFAULT '0',
  `bmname` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `position` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `mail` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1089 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_goldsilverprice`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_goldsilverprice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gold` double DEFAULT NULL,
  `Silver` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=608 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_group`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_group` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `stafferId` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_gsout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_gsout` (
  `id` varchar(40) NOT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `gtotal` int(11) DEFAULT '0',
  `stotal` int(11) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `outTime` varchar(20) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INX_STAFFERID` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_gsout_item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_gsout_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(20) DEFAULT NULL,
  `productId` varchar(20) DEFAULT NULL,
  `deportId` varchar(20) DEFAULT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `goldWeight` int(11) DEFAULT NULL,
  `silverWeight` int(11) DEFAULT NULL,
  `goldPrice` double DEFAULT NULL,
  `silverPrice` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INX_PARENTID` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=2441 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_inbill`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_inbill` (
  `id` varchar(40) NOT NULL,
  `type` int(11) DEFAULT '0',
  `ulock` int(11) DEFAULT '0',
  `bankId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `outBalanceId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `moneys` double DEFAULT '0',
  `customerId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `ownerId` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `destBankId` varchar(40) DEFAULT NULL,
  `refBillId` varchar(40) DEFAULT NULL,
  `paymentId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(4000) DEFAULT NULL,
  `checks` varchar(1200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `srcMoneys` double DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `freeze` int(11) DEFAULT '0',
  `dutyId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_BANK` (`bankId`),
  KEY `IX_customerId` (`customerId`),
  KEY `IX_outId` (`outId`),
  KEY `IX_ownerId` (`ownerId`),
  KEY `IX_logTime` (`logTime`),
  KEY `IX_paymentId` (`paymentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_insbindout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_insbindout` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `invoiceStorageId` varchar(40) DEFAULT NULL,
  `fullId` varchar(40) DEFAULT NULL,
  `outtype` int(11) DEFAULT NULL,
  `confirmMoney` double DEFAULT NULL,
  `providerId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_instorageid` (`invoiceStorageId`),
  KEY `idx_fullId` (`fullId`)
) ENGINE=InnoDB AUTO_INCREMENT=4437 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_insimport_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_insimport_log` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `batchId` varchar(40) DEFAULT NULL,
  `message` varchar(300) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_batchId` (`batchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_instancetemplate`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_instancetemplate` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(400) NOT NULL,
  `INSTANCEId` varchar(40) NOT NULL,
  `TEMPLATEID` varchar(40) NOT NULL,
  `readonly` int(11) DEFAULT '0',
  `flowId` varchar(40) NOT NULL,
  `path` varchar(400) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `INSTANCEId` (`INSTANCEId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoice`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoice` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) NOT NULL,
  `forward` int(11) NOT NULL,
  `counteract` int(11) NOT NULL,
  `val` double NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoice_import`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoice_import` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `type` varchar(30) NOT NULL DEFAULT '',
  `invoiceMoney` double DEFAULT NULL,
  `invoiceNum` varchar(200) DEFAULT NULL,
  `invoiceId` varchar(50) DEFAULT NULL,
  `invoiceHead` varchar(100) DEFAULT NULL,
  `invoiceDate` varchar(30) DEFAULT NULL,
  `refInsId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `shipping` int(11) DEFAULT NULL COMMENT '配送方式',
  `transport1` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL COMMENT '省',
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL COMMENT '详细地址',
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL COMMENT '区',
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `description` varchar(1200) DEFAULT NULL COMMENT '备注',
  `addrType` int(11) DEFAULT '0' COMMENT '地址类型',
  `invoiceFollowOut` varchar(40) DEFAULT NULL,
  `virtualInvoiceNum` varchar(100) DEFAULT '',
  `productId` varchar(100) DEFAULT '',
  `productName` varchar(100) DEFAULT '',
  `amount` int(11) DEFAULT '0',
  `kppm` varchar(255) DEFAULT NULL,
  `splitFlag` int(11) DEFAULT '0',
  `zzsInfo` varchar(1024) DEFAULT NULL,
  `status` varchar(25) NOT NULL DEFAULT '',
  `approver` varchar(255) DEFAULT NULL,
  `spmc` varchar(255) DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `idx_batchid` (`batchId`)
) ENGINE=InnoDB AUTO_INCREMENT=1021 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoiceins`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoiceins` (
  `id` varchar(40) NOT NULL,
  `invoiceId` varchar(40) NOT NULL,
  `dutyId` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `unit` varchar(200) NOT NULL,
  `status` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `reveive` varchar(200) NOT NULL,
  `moneys` double DEFAULT '0',
  `invoiceDate` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `processer` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `refIds` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  `vtype` int(11) DEFAULT '0',
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(1200) DEFAULT NULL,
  `checkrefId` varchar(200) DEFAULT NULL,
  `stype` int(11) DEFAULT '0',
  `headType` int(11) DEFAULT NULL,
  `headContent` varchar(1200) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  `payConfirmStatus` int(11) DEFAULT NULL,
  `invoiceConfirmStatus` int(11) DEFAULT NULL,
  `otype` int(11) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `insAmount` int(11) DEFAULT '0',
  `fillType` int(1) DEFAULT '0' COMMENT '配送地址填写方式',
  `shipping` int(11) DEFAULT NULL COMMENT '配送方式',
  `transport1` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL COMMENT '省',
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL COMMENT '详细地址',
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL COMMENT '区',
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `invoiceFollowOut` varchar(40) DEFAULT NULL,
  `packaged` int(11) DEFAULT '0',
  `zzsInfo` varchar(1024) DEFAULT NULL,
  `emergency` int(11) DEFAULT '0',
  `spmc` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `INX_customerId` (`customerId`),
  KEY `INX_stafferId` (`stafferId`),
  KEY `INX_logTime` (`logTime`),
  KEY `idx_dutyId` (`dutyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoiceins_exception`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoiceins_exception` (
  `id` varchar(40) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `exception` varchar(400) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_parentid` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoiceins_import`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoiceins_import` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `invoiceMoney` double DEFAULT NULL,
  `invoiceNum` varchar(200) DEFAULT NULL,
  `invoiceId` varchar(50) DEFAULT NULL,
  `invoiceHead` varchar(100) DEFAULT NULL,
  `invoiceDate` varchar(30) DEFAULT NULL,
  `refInsId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `shipping` int(11) DEFAULT NULL COMMENT '配送方式',
  `transport1` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL COMMENT '省',
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL COMMENT '详细地址',
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL COMMENT '区',
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `description` varchar(1200) DEFAULT NULL COMMENT '备注',
  `addrType` int(11) DEFAULT '0' COMMENT '地址类型',
  `invoiceFollowOut` varchar(40) DEFAULT NULL,
  `virtualInvoiceNum` varchar(100) DEFAULT '',
  `productId` varchar(100) DEFAULT '',
  `productName` varchar(100) DEFAULT '',
  `amount` int(11) DEFAULT '0',
  `splitFlag` int(11) DEFAULT '0',
  `zzsInfo` varchar(1024) DEFAULT NULL,
  `spmc` varchar(255) DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `idx_batchid` (`batchId`)
) ENGINE=InnoDB AUTO_INCREMENT=216941 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoiceins_item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoiceins_item` (
  `id` varchar(40) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `showId` varchar(40) NOT NULL,
  `showName` varchar(200) NOT NULL,
  `special` varchar(200) DEFAULT NULL,
  `unit` varchar(200) DEFAULT NULL,
  `amount` int(11) NOT NULL,
  `price` double DEFAULT '0',
  `moneys` double DEFAULT '0',
  `outId` varchar(40) DEFAULT NULL,
  `baseId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `costPrice` double DEFAULT NULL,
  `logTime` varchar(128) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `IX_PARENTID` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoiceinstag`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoiceinstag` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `insId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1982 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoicelineproject`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoicelineproject` (
  `id` varchar(40) NOT NULL,
  `refid` varchar(40) NOT NULL,
  `invoiceType` varchar(50) DEFAULT NULL,
  `invoiceMoney` varchar(100) DEFAULT NULL,
  `invoiceTime` varchar(50) DEFAULT NULL,
  `finishiDays1` varchar(200) DEFAULT NULL,
  `invocurrentTask` varchar(100) DEFAULT NULL,
  `beforeTask2` varchar(200) DEFAULT NULL,
  `afterTask2` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_refid` (`refid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_invoicestorage`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_invoicestorage` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `invoiceHead` varchar(60) DEFAULT NULL,
  `invoiceCompany` varchar(100) DEFAULT NULL,
  `moneys` double DEFAULT NULL,
  `hasConfirmMoneys` double DEFAULT NULL,
  `invoiceId` varchar(40) DEFAULT NULL,
  `invoiceNumber` varchar(50) DEFAULT NULL,
  `invoiceDate` varchar(20) DEFAULT NULL,
  `oprName` varchar(30) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `noTaxMoney` double DEFAULT '0',
  `taxMoney` double DEFAULT '0',
  `oprDate` varchar(30) DEFAULT NULL,
  `providerId` varchar(40) DEFAULT NULL,
  `providerName` varchar(100) DEFAULT NULL COMMENT '客户/供应商',
  PRIMARY KEY (`Id`),
  KEY `idx_stafferid` (`stafferId`),
  KEY `idx_invoiceId` (`invoiceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_lock`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_lock` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(20) DEFAULT NULL,
  `fkId` varchar(40) DEFAULT NULL,
  `module` varchar(20) DEFAULT NULL,
  `operation` varchar(20) DEFAULT NULL,
  `locationId` varchar(20) DEFAULT NULL,
  `logTime` varchar(20) DEFAULT NULL,
  `log` varchar(1000) DEFAULT NULL,
  `position` varchar(400) DEFAULT NULL,
  `posid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_F` (`fkId`)
) ENGINE=InnoDB AUTO_INCREMENT=28079 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_log_cchange`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_log_cchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(20) DEFAULT NULL,
  `stafferName` varchar(200) DEFAULT NULL,
  `customerId` varchar(20) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `customerCode` varchar(40) DEFAULT NULL,
  `operation` int(11) DEFAULT '0',
  `logTime` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_F` (`logTime`)
) ENGINE=InnoDB AUTO_INCREMENT=190826 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_log_credit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_log_credit` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) DEFAULT NULL,
  `cid` varchar(40) DEFAULT NULL,
  `locationId` varchar(20) DEFAULT NULL,
  `logTime` varchar(20) DEFAULT NULL,
  `log` varchar(1000) DEFAULT NULL,
  `val` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_CID` (`cid`),
  KEY `IX_TIME` (`logTime`)
) ENGINE=InnoDB AUTO_INCREMENT=47917493 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_log_curout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_log_curout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `outsId` varchar(40) DEFAULT NULL,
  `delay` int(11) DEFAULT NULL,
  `logTime` varchar(20) DEFAULT NULL,
  `val` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_CID` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=246238 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_mail`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_mail` (
  `ID` varchar(40) NOT NULL,
  `title` varchar(1000) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `senderId` varchar(40) NOT NULL,
  `senderName` varchar(200) NOT NULL,
  `reveiveId` varchar(40) NOT NULL,
  `reveiveIds` varchar(1000) NOT NULL,
  `reveiveNames` varchar(1000) NOT NULL,
  `reveiveIds2` varchar(1000) DEFAULT NULL,
  `reveiveNames2` varchar(1000) DEFAULT NULL,
  `reveiveIds3` varchar(1000) DEFAULT NULL,
  `reveiveNames3` varchar(1000) DEFAULT NULL,
  `href` varchar(1000) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `feeback` int(11) DEFAULT '0',
  `attachment` int(11) DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_R` (`reveiveId`),
  KEY `INX_S` (`senderId`),
  KEY `INX_L` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_mail2`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_mail2` (
  `ID` varchar(40) NOT NULL,
  `title` varchar(1000) DEFAULT NULL,
  `content` varchar(1000) DEFAULT NULL,
  `senderId` varchar(40) NOT NULL,
  `senderName` varchar(200) NOT NULL,
  `reveiveId` varchar(40) NOT NULL,
  `reveiveIds` varchar(1000) NOT NULL,
  `reveiveNames` varchar(1000) NOT NULL,
  `reveiveIds2` varchar(1000) DEFAULT NULL,
  `reveiveNames2` varchar(1000) DEFAULT NULL,
  `reveiveIds3` varchar(1000) DEFAULT NULL,
  `reveiveNames3` varchar(1000) DEFAULT NULL,
  `href` varchar(1000) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `feeback` int(11) DEFAULT '0',
  `attachment` int(11) DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_R` (`reveiveId`),
  KEY `INX_S` (`senderId`),
  KEY `INX_L` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_midfinance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_midfinance` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `updateFlag` int(11) DEFAULT '0',
  `dutyId` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refOut` varchar(40) DEFAULT NULL,
  `refBill` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `inmoney` bigint(20) DEFAULT '0',
  `outmoney` bigint(20) DEFAULT '0',
  `checks` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `financeDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `locks` int(11) DEFAULT '0',
  `monthIndex` int(11) DEFAULT '0',
  `refChecks` varchar(1200) DEFAULT NULL,
  KEY `idx_refout` (`refOut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_midfinanceitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_midfinanceitem` (
  `ID` varchar(40) NOT NULL,
  `pid` varchar(40) NOT NULL,
  `pareId` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `forward` int(11) DEFAULT '0',
  `taxId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `inmoney` bigint(20) DEFAULT '0',
  `outmoney` bigint(20) DEFAULT '0',
  `unitType` int(11) DEFAULT '0',
  `unitId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `depotId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `duty2Id` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refOut` varchar(40) DEFAULT NULL,
  `refBill` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `financeDate` varchar(40) DEFAULT NULL,
  `taxId0` varchar(40) DEFAULT NULL,
  `taxId1` varchar(40) DEFAULT NULL,
  `taxId2` varchar(40) DEFAULT NULL,
  `taxId3` varchar(40) DEFAULT NULL,
  `taxId4` varchar(40) DEFAULT NULL,
  `taxId5` varchar(40) DEFAULT NULL,
  `taxId6` varchar(40) DEFAULT NULL,
  `taxId7` varchar(40) DEFAULT NULL,
  `taxId8` varchar(40) DEFAULT NULL,
  `productAmountIn` int(11) DEFAULT '0',
  `productAmountOut` int(11) DEFAULT '0',
  KEY `idx_refout` (`refOut`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaattachment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaattachment` (
  `ID` varchar(40) NOT NULL,
  `refId` varchar(40) NOT NULL,
  `name` varchar(400) NOT NULL,
  `path` varchar(400) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_M` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oadepartment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oadepartment` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowbelongs`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowbelongs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `instanceId` varchar(40) DEFAULT NULL,
  `flowId` varchar(40) DEFAULT NULL,
  `tokenId` varchar(1000) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `createId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `stafferId` (`stafferId`),
  KEY `logTime` (`logTime`),
  KEY `INX_CREATEID` (`createId`)
) ENGINE=InnoDB AUTO_INCREMENT=150136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowconsign`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowconsign` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `instanceId` varchar(40) DEFAULT NULL,
  `flowId` varchar(40) DEFAULT NULL,
  `tokenId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `instanceId` (`instanceId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowdefine`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowdefine` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `mode` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `description` varchar(1000) DEFAULT NULL,
  `parentType` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowdefinetoken`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowdefinetoken` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(40) DEFAULT NULL,
  `flowId` varchar(40) DEFAULT NULL,
  `mode` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `pluginType` int(11) DEFAULT '0',
  `orders` int(11) DEFAULT NULL,
  `preOrders` int(11) DEFAULT NULL,
  `nextOrders` int(11) DEFAULT NULL,
  `begining` bit(1) DEFAULT NULL,
  `ending` bit(1) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `subFlowId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowinstance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowinstance` (
  `id` varchar(40) NOT NULL,
  `flowId` varchar(40) DEFAULT NULL,
  `currentTokenId` varchar(40) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `endTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `attachment` varchar(255) DEFAULT NULL,
  `fileName` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `liminal` double DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `parentId` varchar(40) DEFAULT NULL,
  `parentTokenId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `flowId` (`flowId`),
  KEY `stafferId` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowinstanceview`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowinstanceview` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `viewer` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `flowId` varchar(40) DEFAULT NULL,
  `instanceId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `createId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INX_NAME` (`viewer`),
  KEY `logTime` (`logTime`),
  KEY `IX_createId` (`createId`)
) ENGINE=InnoDB AUTO_INCREMENT=239900 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowlog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `instanceId` varchar(40) DEFAULT NULL,
  `flowId` varchar(40) DEFAULT NULL,
  `tokenId` varchar(40) DEFAULT NULL,
  `nextTokenId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `oprMode` int(11) DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `nextStafferId` varchar(40) DEFAULT NULL,
  `opinion` varchar(1000) DEFAULT NULL,
  `createId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `instanceId` (`instanceId`),
  KEY `INX_CREATEID` (`createId`)
) ENGINE=InnoDB AUTO_INCREMENT=181349 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oaflowviewer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oaflowviewer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flowId` varchar(40) DEFAULT NULL,
  `processer` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=209 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oalocation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oalocation` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(200) NOT NULL,
  `parentId` varchar(40) DEFAULT '0',
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oamenuitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oamenuitem` (
  `id` varchar(40) NOT NULL,
  `MENUITEMNAME` varchar(200) NOT NULL,
  `URL` varchar(255) NOT NULL,
  `PARENTID` varchar(40) NOT NULL,
  `BottomFlag` int(11) DEFAULT '0',
  `AUTH` varchar(1000) DEFAULT NULL,
  `indexPos` int(11) DEFAULT '99',
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oamenuitem_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oamenuitem_bak` (
  `id` varchar(40) NOT NULL,
  `MENUITEMNAME` varchar(200) NOT NULL,
  `URL` varchar(255) NOT NULL,
  `PARENTID` varchar(40) NOT NULL,
  `BottomFlag` int(11) DEFAULT '0',
  `AUTH` varchar(1000) DEFAULT NULL,
  `indexPos` int(11) DEFAULT '99',
  `description` varchar(1200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oarole`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oarole` (
  `id` int(11) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `visible` int(11) NOT NULL,
  `locationId` varchar(40) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oastaffer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oastaffer` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `locationId` varchar(50) DEFAULT NULL,
  `industryId` varchar(50) DEFAULT NULL,
  `industryId2` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `black` int(11) DEFAULT '0',
  `graduateDate` varchar(40) DEFAULT NULL,
  `specialty` varchar(200) DEFAULT NULL,
  `graduate` varchar(200) DEFAULT NULL,
  `graduateSchool` varchar(200) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `postId` varchar(40) DEFAULT NULL,
  `principalshipId` varchar(40) DEFAULT NULL,
  `nation` varchar(40) DEFAULT NULL,
  `city` varchar(40) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `visage` varchar(40) DEFAULT NULL,
  `idCard` varchar(100) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `handphone` varchar(40) DEFAULT NULL,
  `subphone` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `picPath` varchar(300) DEFAULT NULL,
  `idiograph` varchar(300) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `examType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `lever` int(11) DEFAULT '1',
  `pwkey` varchar(1000) DEFAULT NULL,
  `credit` double DEFAULT '0',
  `industryId3` varchar(50) DEFAULT NULL,
  `otype` int(11) DEFAULT '0',
  `zw` varchar(255) DEFAULT NULL,
  `zzzt` varchar(255) DEFAULT NULL,
  `lzsj` date DEFAULT NULL,
  `outsj` date DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_STAFFER_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oastaffer_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oastaffer_bak` (
  `id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(40) DEFAULT NULL,
  `locationId` varchar(50) DEFAULT NULL,
  `industryId` varchar(50) DEFAULT NULL,
  `industryId2` varchar(50) DEFAULT NULL,
  `sex` int(11) DEFAULT '0',
  `black` int(11) DEFAULT '0',
  `graduateDate` varchar(40) DEFAULT NULL,
  `specialty` varchar(200) DEFAULT NULL,
  `graduate` varchar(200) DEFAULT NULL,
  `graduateSchool` varchar(200) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `postId` varchar(40) DEFAULT NULL,
  `principalshipId` varchar(40) DEFAULT NULL,
  `nation` varchar(40) DEFAULT NULL,
  `city` varchar(40) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `visage` varchar(40) DEFAULT NULL,
  `idCard` varchar(100) DEFAULT NULL,
  `birthday` varchar(40) DEFAULT NULL,
  `handphone` varchar(40) DEFAULT NULL,
  `subphone` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `picPath` varchar(300) DEFAULT NULL,
  `idiograph` varchar(300) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `examType` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `lever` int(11) DEFAULT '1',
  `pwkey` varchar(1000) DEFAULT NULL,
  `credit` double DEFAULT '0',
  `industryId3` varchar(50) DEFAULT NULL,
  `otype` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_oauser`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_oauser` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `roleId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `fail` int(11) DEFAULT '0',
  `loginTime` varchar(50) DEFAULT NULL,
  `stafferId` varchar(50) DEFAULT NULL,
  `locationId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_NAME` (`name`),
  KEY `IX_ROLE` (`roleId`)
) ENGINE=InnoDB AUTO_INCREMENT=2995 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_olbase`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_olbase` (
  `Id` bigint(20) NOT NULL AUTO_INCREMENT,
  `outid` varchar(30) NOT NULL DEFAULT '',
  `oano` varchar(30) DEFAULT NULL,
  `productcode` varchar(20) NOT NULL DEFAULT '',
  `productname` varchar(70) NOT NULL DEFAULT '',
  `amount` varchar(10) NOT NULL DEFAULT '',
  `price` double(10,2) DEFAULT '0.00' COMMENT '1',
  `ibmoney` double(10,2) DEFAULT '0.00',
  `motivationmoney` double(10,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `depot` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=616410 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_olout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_olout` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `olfullid` varchar(100) NOT NULL DEFAULT '',
  `status` varchar(2) NOT NULL DEFAULT '',
  `type` varchar(2) NOT NULL DEFAULT '',
  `stafferid` varchar(15) DEFAULT NULL,
  `staffername` varchar(255) DEFAULT NULL,
  `customerid` varchar(20) NOT NULL DEFAULT '',
  `customername` varchar(100) NOT NULL DEFAULT '',
  `express` varchar(10) NOT NULL DEFAULT '',
  `expresscompany` varchar(2) NOT NULL DEFAULT '',
  `expresspay` varchar(2) DEFAULT '',
  `provinceid` varchar(8) DEFAULT NULL,
  `cityid` varchar(8) DEFAULT '',
  `address` varchar(255) DEFAULT NULL,
  `receiver` varchar(20) DEFAULT NULL,
  `telephone` varchar(40) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `emergency` varchar(1) DEFAULT NULL,
  `pricestatus` varchar(2) NOT NULL DEFAULT '',
  `ibmotstatus` varchar(2) NOT NULL DEFAULT '',
  `fj` varchar(255) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '0000-00-00 00:00:00',
  `presentFlag` varchar(2) DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=20168544 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_olpay`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_olpay` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `lcid` varchar(255) NOT NULL DEFAULT '',
  `customername` varchar(255) NOT NULL DEFAULT '',
  `route` varchar(25) NOT NULL DEFAULT '',
  `frombank` varchar(255) NOT NULL DEFAULT '',
  `account` varchar(255) NOT NULL DEFAULT '',
  `fromname` varchar(255) NOT NULL DEFAULT '',
  `money` double(10,2) NOT NULL DEFAULT '0.00',
  `fromtime` date NOT NULL DEFAULT '0000-00-00',
  `yyaccount` varchar(255) NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `fj` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=MyISAM AUTO_INCREMENT=187 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_org`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_org` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `subId` varchar(40) NOT NULL,
  `PARENTID` varchar(40) NOT NULL DEFAULT '0',
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=773 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_out`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_out` (
  `id` int(11) NOT NULL DEFAULT '0',
  `fullId` varchar(200) NOT NULL,
  `flowId` varchar(50) DEFAULT NULL,
  `outTime` varchar(20) DEFAULT NULL,
  `managerTime` varchar(20) DEFAULT NULL,
  `outType` int(11) DEFAULT '0',
  `channel` varchar(10) DEFAULT NULL,
  `locationID` int(11) DEFAULT '0',
  `location` varchar(200) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `industryId2` varchar(40) DEFAULT NULL,
  `department` varchar(200) DEFAULT NULL,
  `customerId` varchar(200) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `total` double DEFAULT '0',
  `hadPay` double DEFAULT '0',
  `badDebts` double DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `hasInvoice` int(11) DEFAULT '0',
  `invoiceStatus` int(11) DEFAULT '0',
  `invoiceId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `mark` int(11) DEFAULT '0',
  `pay` int(11) DEFAULT '0',
  `inway` int(11) DEFAULT '0',
  `tempType` int(11) DEFAULT '0',
  `customerName` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `stafferName` varchar(200) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `checks` varchar(2000) DEFAULT NULL,
  `reday` varchar(200) DEFAULT NULL,
  `arriveDate` varchar(200) DEFAULT NULL,
  `redate` varchar(200) DEFAULT NULL,
  `depotpartId` varchar(200) DEFAULT NULL,
  `destinationId` varchar(200) DEFAULT NULL,
  `tranNo` varchar(200) DEFAULT NULL,
  `refOutFullId` varchar(200) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `managerId` varchar(40) DEFAULT NULL,
  `reserve1` int(11) DEFAULT '0',
  `reserve2` int(11) DEFAULT '0',
  `reserve3` int(11) DEFAULT '0',
  `reserve4` varchar(200) DEFAULT NULL,
  `reserve5` varchar(200) DEFAULT NULL,
  `reserve6` varchar(200) DEFAULT NULL,
  `reserve7` varchar(1000) DEFAULT NULL,
  `reserve8` varchar(1000) DEFAULT NULL,
  `reserve9` varchar(1000) DEFAULT NULL,
  `invoiceMoney` double DEFAULT '0',
  `curcredit` double DEFAULT '0',
  `staffcredit` double DEFAULT '0',
  `managercredit` double DEFAULT '0',
  `checkStatus` int(11) DEFAULT '0',
  `badDebtsCheckStatus` int(11) DEFAULT '0',
  `changeTime` varchar(40) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  `sailType` varchar(40) DEFAULT NULL,
  `productType` varchar(40) DEFAULT NULL,
  `ratio` varchar(40) DEFAULT NULL,
  `vtype` int(11) DEFAULT '0',
  `vtypeFullId` varchar(200) DEFAULT NULL,
  `pmtype` int(11) DEFAULT '0',
  `industryId3` varchar(40) DEFAULT NULL,
  `payTime` varchar(20) DEFAULT NULL,
  `forceBuyType` int(11) DEFAULT NULL,
  `eventId` varchar(40) DEFAULT NULL,
  `refBindOutId` varchar(400) DEFAULT NULL,
  `promValue` double DEFAULT NULL,
  `promStatus` int(11) DEFAULT NULL,
  `lastModified` varchar(40) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `guarantor` varchar(40) DEFAULT NULL,
  `feedBackVisit` int(11) DEFAULT '0',
  `feedBackCheck` int(11) DEFAULT '0',
  `taxTotal` double DEFAULT NULL,
  `hasRebate` int(11) DEFAULT NULL,
  `piDutyId` varchar(40) DEFAULT '',
  `piMtype` int(11) DEFAULT '-1',
  `piType` int(11) DEFAULT '-1',
  `piStatus` int(11) DEFAULT '-1',
  `hasConfirm` int(11) DEFAULT '0',
  `hasConfirmInsMoney` double DEFAULT '0',
  `emergency` int(11) DEFAULT '0' COMMENT '紧急',
  `presentFlag` int(11) DEFAULT '0' COMMENT '赠送类型',
  `podate` varchar(40) DEFAULT NULL,
  `ibFlag` int(11) DEFAULT '-1',
  `motivationFlag` int(11) DEFAULT '-1',
  `remoteAllocate` int(11) DEFAULT '0',
  `reason` varchar(100) DEFAULT '',
  `transportNo` varchar(100) DEFAULT '',
  `swbz` varchar(255) DEFAULT NULL,
  `outbackStatus` varchar(255) DEFAULT '',
  `customerCreated` int(11) DEFAULT '0',
  `flowTime` varchar(255) DEFAULT '',
  `ibApplyId` varchar(255) DEFAULT '',
  `motivationApplyId` varchar(255) DEFAULT '',
  `refGiftId` varchar(32) DEFAULT '',
  `cashflag` int(2) DEFAULT NULL,
  `profigflag` int(2) DEFAULT NULL,
  `ibFlag2` int(11) DEFAULT '0',
  `ibApplyId2` varchar(255) DEFAULT '',
  `motivationFlag2` int(11) DEFAULT '0',
  `motivationApplyId2` varchar(255) DEFAULT '',
  PRIMARY KEY (`fullId`),
  KEY `INX_OUTTIME` (`outTime`),
  KEY `INX_STAFFERNAME` (`stafferName`),
  KEY `INX_LOCATIONID` (`locationID`),
  KEY `INX_CID` (`customerId`),
  KEY `IX_SID` (`stafferId`),
  KEY `INX_INDUSTRYID` (`industryId`),
  KEY `INX_INDUSTRYID2` (`industryId2`),
  KEY `INX_MANAGERID` (`managerId`),
  KEY `IX_refOutFullId` (`refOutFullId`),
  KEY `INX_ID` (`id`),
  KEY `IX_arriveDate` (`arriveDate`),
  KEY `INX_LASTMODIFIED` (`lastModified`),
  KEY `INX_FULLID` (`fullId`),
  KEY `INX_MGTIME` (`managerTime`),
  KEY `t_center_out_type_index` (`type`),
  KEY `t_center_out_status_index` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_out_auditrule`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_out_auditrule` (
  `id` varchar(40) NOT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `sailType` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_out_auditrule_item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_out_auditrule_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `managerType` int(11) DEFAULT NULL,
  `productType` int(11) DEFAULT NULL,
  `materiaType` int(11) DEFAULT NULL,
  `currencyType` int(11) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `payCondition` int(11) DEFAULT NULL,
  `returnCondition` int(11) DEFAULT NULL,
  `productPeriod` int(11) DEFAULT NULL,
  `ratioDown` int(11) DEFAULT NULL,
  `ratioUp` int(11) DEFAULT NULL,
  `profitPeriod` int(11) DEFAULT NULL,
  `LTSailPrice` int(11) DEFAULT NULL,
  `diffRatio` double DEFAULT NULL,
  `minRatio` double DEFAULT NULL,
  `accountPeriod` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_out_import`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_out_import` (
  `id` int(11) NOT NULL DEFAULT '0',
  `fullId` int(11) NOT NULL AUTO_INCREMENT,
  `batchId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `customerId` varchar(20) DEFAULT NULL,
  `branchName` varchar(200) NOT NULL,
  `secondBranch` varchar(200) DEFAULT NULL,
  `comunicationBranch` varchar(200) DEFAULT NULL,
  `comunicatonBranchName` varchar(200) DEFAULT NULL,
  `productId` varchar(200) DEFAULT NULL,
  `productCode` varchar(200) DEFAULT '0',
  `productName` varchar(200) DEFAULT '0',
  `amount` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `style` varchar(40) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `midValue` double DEFAULT NULL,
  `arriveDate` varchar(40) DEFAULT NULL,
  `storageType` int(11) DEFAULT '0',
  `citicNo` varchar(100) DEFAULT '0',
  `invoiceNature` int(11) DEFAULT '0',
  `invoiceHead` varchar(200) DEFAULT '0',
  `invoiceCondition` varchar(200) DEFAULT '0',
  `bindNo` varchar(200) DEFAULT '0',
  `invoiceType` int(11) DEFAULT '0',
  `invoiceName` varchar(200) DEFAULT NULL,
  `invoiceMoney` double DEFAULT NULL,
  `provinceId` varchar(200) DEFAULT '0',
  `cityId` varchar(200) DEFAULT '0',
  `address` varchar(200) DEFAULT '0',
  `receiver` varchar(20) DEFAULT '0',
  `handPhone` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `OANo` varchar(40) DEFAULT NULL,
  `reason` varchar(200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `firstName` varchar(30) DEFAULT NULL,
  `citicOrderDate` varchar(40) DEFAULT NULL,
  `transport1` int(11) DEFAULT NULL,
  `itype` int(11) DEFAULT '0',
  `outType` int(11) DEFAULT '-1',
  `depotId` varchar(40) DEFAULT NULL,
  `depotpartId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `shipping` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `expressPay` int(11) DEFAULT NULL,
  `transportPay` int(11) DEFAULT NULL,
  `preUse` int(11) DEFAULT '0',
  `reday` int(11) DEFAULT '0',
  `presentFlag` int(11) DEFAULT '0',
  `ibMoney` double DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  `customerName` varchar(200) DEFAULT '',
  `nbyhNo` varchar(200) DEFAULT '',
  `result` varchar(100) DEFAULT '',
  `importFromMail` int(11) DEFAULT '0',
  `lhwd` varchar(255) DEFAULT '',
  `telephone` varchar(255) DEFAULT '',
  `direct` int(11) DEFAULT '0',
  `channel` varchar(10) DEFAULT '',
  `cash` double DEFAULT '0',
  `grossProfit` double DEFAULT '0',
  `ibMoney2` double DEFAULT '0',
  `motivationMoney2` double DEFAULT '0',
  PRIMARY KEY (`fullId`),
  KEY `idx_batchid` (`batchId`),
  KEY `idx_oano` (`OANo`)
) ENGINE=InnoDB AUTO_INCREMENT=433897 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_out_import_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_out_import_his` (
  `id` int(11) NOT NULL DEFAULT '0',
  `fullId` int(11) NOT NULL DEFAULT '0',
  `batchId` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `customerId` varchar(20) DEFAULT NULL,
  `branchName` varchar(200) NOT NULL,
  `secondBranch` varchar(200) DEFAULT NULL,
  `comunicationBranch` varchar(200) DEFAULT NULL,
  `comunicatonBranchName` varchar(200) DEFAULT NULL,
  `productId` varchar(200) DEFAULT NULL,
  `productCode` varchar(200) DEFAULT '0',
  `productName` varchar(200) DEFAULT '0',
  `amount` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `style` varchar(40) DEFAULT NULL,
  `value` double DEFAULT NULL,
  `midValue` double DEFAULT NULL,
  `arriveDate` varchar(40) DEFAULT NULL,
  `storageType` int(11) DEFAULT '0',
  `citicNo` varchar(40) DEFAULT '0',
  `invoiceNature` int(11) DEFAULT '0',
  `invoiceHead` varchar(200) DEFAULT '0',
  `invoiceCondition` varchar(200) DEFAULT '0',
  `bindNo` varchar(200) DEFAULT '0',
  `invoiceType` int(11) DEFAULT '0',
  `invoiceName` varchar(200) DEFAULT NULL,
  `invoiceMoney` double DEFAULT NULL,
  `provinceId` varchar(200) DEFAULT '0',
  `cityId` varchar(200) DEFAULT '0',
  `address` varchar(200) DEFAULT '0',
  `receiver` varchar(20) DEFAULT '0',
  `handPhone` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `OANo` varchar(40) DEFAULT NULL,
  `reason` varchar(200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `firstName` varchar(30) DEFAULT NULL,
  `citicOrderDate` varchar(40) DEFAULT NULL,
  `transport1` varchar(10) DEFAULT NULL,
  `itype` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_out_paychanged`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_out_paychanged` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_out_result`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_out_result` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `citicNo` varchar(100) DEFAULT '0',
  `OANo` varchar(40) DEFAULT '0',
  `processResult` varchar(200) DEFAULT '0',
  `citicAmount` int(11) DEFAULT '0',
  `citicMoney` double DEFAULT '0',
  `OAamount` int(11) DEFAULT '0',
  `OAmoney` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_oano` (`OANo`)
) ENGINE=InnoDB AUTO_INCREMENT=423210 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outback`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outback` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `expressCompany` varchar(20) DEFAULT NULL,
  `transportNo` varchar(40) DEFAULT NULL,
  `fromProvince` varchar(20) DEFAULT NULL,
  `fromCity` varchar(20) DEFAULT NULL,
  `fromAddress` varchar(255) DEFAULT NULL,
  `fromer` varchar(20) DEFAULT NULL,
  `fromMobile` varchar(20) DEFAULT NULL,
  `toProvince` varchar(20) DEFAULT NULL,
  `toCity` varchar(20) DEFAULT NULL,
  `toAddress` varchar(255) DEFAULT NULL,
  `tos` varchar(20) DEFAULT NULL,
  `toMobile` varchar(20) DEFAULT NULL,
  `goods` int(11) DEFAULT NULL,
  `receiverId` varchar(20) DEFAULT NULL,
  `receiver` varchar(30) DEFAULT NULL,
  `receiverDate` varchar(30) DEFAULT NULL,
  `claimer` varchar(30) DEFAULT NULL,
  `claimTime` varchar(40) DEFAULT NULL,
  `checker` varchar(30) DEFAULT NULL,
  `checkTime` varchar(40) DEFAULT NULL,
  `checkReason` varchar(255) DEFAULT NULL,
  `stocker` varchar(30) DEFAULT NULL,
  `stockTime` varchar(40) DEFAULT NULL,
  `stafferId` varchar(30) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `refOutId` varchar(255) DEFAULT NULL,
  `description` varchar(1024) DEFAULT NULL,
  `note` varchar(1024) DEFAULT '',
  `handoverReason` varchar(100) DEFAULT '',
  `handoverChecker` varchar(16) DEFAULT '',
  `handoverCheckTime` varchar(100) DEFAULT '',
  `confirmChecker` varchar(16) DEFAULT '',
  `confirmCheckTime` varchar(100) DEFAULT '',
  `createtime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outback_check`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outback_check` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outbackid` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `outid` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `reoano` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `status` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `customername` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `productid` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `productname` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `amount` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `invoicenum` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `description` varchar(255) COLLATE utf8_bin DEFAULT '',
  `changetime` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '',
  `depot` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `checkbz` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `costprice` double NOT NULL DEFAULT '0',
  `outtype` int(1) DEFAULT NULL,
  `outcustomerid` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `outreceiver` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3132 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outback_item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outback_item` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outbackid` varchar(255) NOT NULL DEFAULT '',
  `outid` varchar(255) NOT NULL DEFAULT '',
  `reoano` varchar(255) DEFAULT NULL,
  `status` varchar(2) NOT NULL DEFAULT '0',
  `type` int(11) NOT NULL DEFAULT '0',
  `customername` varchar(255) NOT NULL DEFAULT '',
  `productid` varchar(255) NOT NULL DEFAULT '',
  `productname` varchar(255) NOT NULL DEFAULT '',
  `amount` varchar(255) NOT NULL DEFAULT '',
  `invoicenum` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT '',
  `changetime` varchar(255) NOT NULL DEFAULT '',
  `depot` varchar(255) DEFAULT NULL,
  `checkbz` varchar(255) DEFAULT NULL,
  `costprice` double NOT NULL DEFAULT '0',
  `outtype` int(1) DEFAULT NULL,
  `outcustomerid` varchar(255) DEFAULT NULL,
  `outreceiver` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3812 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outback_item_new`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outback_item_new` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outbackid` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `status` varchar(2) NOT NULL DEFAULT '0',
  `type` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `staffer` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `customername` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `productid` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `productname` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `amount` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `changetime` varchar(255) NOT NULL DEFAULT '',
  `creatername` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2970 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outbackmx`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outbackmx` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `transportno` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `sh` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `city` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `fromer` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `frommobile` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `goods` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `status` varchar(20) CHARACTER SET utf8 DEFAULT NULL,
  `expresscompany` varchar(11) CHARACTER SET utf8 NOT NULL DEFAULT '0',
  `customername` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `staffer` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `productname` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `amount` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `description` varchar(255) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  `checkbz` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `monitorpoint` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outbalance`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outbalance` (
  `id` varchar(40) NOT NULL,
  `outId` varchar(40) NOT NULL,
  `total` double DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `pay` int(11) DEFAULT '0',
  `payMoney` double DEFAULT '0',
  `invoiceStatus` int(11) DEFAULT '0',
  `invoiceMoney` double DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `customerId` varchar(200) DEFAULT NULL,
  `dirDepot` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `reason` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `checks` varchar(200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  `refOutBalanceId` varchar(40) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `hasRebate` int(11) DEFAULT NULL,
  `piDutyId` varchar(40) DEFAULT '',
  `piMtype` int(11) DEFAULT '-1',
  `piType` int(11) DEFAULT '-1',
  `piStatus` int(11) DEFAULT '-1',
  `hasConfirm` int(11) DEFAULT '0',
  `hasConfirmInsMoney` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INX_OUTID` (`outId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outbatchprice`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outbatchprice` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `beginDate` varchar(30) DEFAULT NULL,
  `endDate` varchar(30) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outbill`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outbill` (
  `id` varchar(40) NOT NULL,
  `type` int(11) DEFAULT '0',
  `payType` int(11) DEFAULT '0',
  `ulock` int(11) DEFAULT '0',
  `bankId` varchar(40) DEFAULT NULL,
  `stockId` varchar(40) DEFAULT NULL,
  `stockItemId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `moneys` double DEFAULT '0',
  `provideId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `invoiceId` varchar(40) DEFAULT NULL,
  `ownerId` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `destBankId` varchar(40) DEFAULT NULL,
  `refBillId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `checks` varchar(1200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `srcMoneys` double DEFAULT '0',
  `updateId` int(11) DEFAULT '0',
  `createType` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IX_BANK` (`bankId`),
  KEY `IX_provideId` (`provideId`),
  KEY `IX_stockId` (`stockId`),
  KEY `IX_stockItemId` (`stockItemId`),
  KEY `IX_ownerId` (`ownerId`),
  KEY `IX_logTime` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outimport_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outimport_log` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `batchId` varchar(40) DEFAULT NULL,
  `message` varchar(300) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_batchId` (`batchId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outproduct`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outproduct` (
  `fullId` varchar(40) NOT NULL,
  `currentStatus` int(11) DEFAULT '0',
  `reprotType` int(11) DEFAULT '0',
  `promitType` int(11) DEFAULT '0',
  `transport` varchar(20) DEFAULT NULL,
  `applys` varchar(200) DEFAULT NULL,
  `arriveDate` varchar(40) DEFAULT NULL,
  `checker` varchar(40) DEFAULT NULL,
  `packager` varchar(40) DEFAULT NULL,
  `packageTime` varchar(40) DEFAULT NULL,
  `packageAmount` varchar(40) DEFAULT NULL,
  `packageWeight` varchar(40) DEFAULT NULL,
  `visitTime` varchar(40) DEFAULT NULL,
  `arriveTime` varchar(40) DEFAULT NULL,
  `preparer` varchar(40) DEFAULT NULL,
  `mathine` varchar(40) DEFAULT NULL,
  `transportFee` varchar(40) DEFAULT NULL,
  `transportNo` varchar(200) DEFAULT NULL,
  `gid` varchar(40) NOT NULL,
  `reveiver` varchar(200) DEFAULT NULL,
  `sendPlace` varchar(200) DEFAULT NULL,
  `distId` varchar(40) DEFAULT NULL,
  `prints` int(11) DEFAULT NULL,
  `passDate` varchar(40) DEFAULT NULL,
  `addrType` int(11) DEFAULT NULL,
  `sfReceiveDate` varchar(64) DEFAULT '',
  `shipping` int(11) DEFAULT '0',
  `pay` int(11) DEFAULT '-1',
  PRIMARY KEY (`gid`),
  KEY `INX_ARRIVEDATE` (`arriveDate`),
  KEY `IX_fullId` (`fullId`),
  KEY `idx_distId` (`distId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outrepaire`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outrepaire` (
  `id` varchar(40) NOT NULL DEFAULT '0',
  `outId` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `invoiceId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(200) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(200) DEFAULT NULL,
  `reday` int(11) DEFAULT NULL,
  `redate` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `reason` varchar(200) DEFAULT NULL,
  `logTime` varchar(20) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `newOutId` varchar(40) DEFAULT NULL,
  `retOutId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_outid` (`outId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_outunique`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_outunique` (
  `id` varchar(40) NOT NULL,
  `ref` varchar(200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_package`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_package` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `customerId` varchar(40) DEFAULT NULL,
  `shipping` int(11) DEFAULT NULL,
  `transport1` int(11) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `expressPay` int(11) DEFAULT '-1',
  `amount` int(11) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `stafferName` varchar(100) DEFAULT NULL,
  `industryName` varchar(100) DEFAULT NULL,
  `departName` varchar(100) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `pickupId` varchar(40) DEFAULT NULL,
  `index_pos` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `transportPay` int(11) DEFAULT NULL,
  `productCount` int(11) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `shipTime` varchar(40) DEFAULT NULL,
  `cityId` varchar(20) DEFAULT '',
  `emergency` int(11) DEFAULT '0' COMMENT '紧急',
  `sendMailFlag` int(11) DEFAULT '1',
  `billsTime` varchar(40) DEFAULT '',
  `insFollowOut` int(11) DEFAULT '1',
  `sfReceiveDate` varchar(64) DEFAULT '',
  `transportNo` varchar(40) DEFAULT '',
  `sendMailFlagNbyh` int(11) DEFAULT '-1',
  `sendMailFlagSails` int(11) DEFAULT '-1',
  `zsFollowOut` int(11) DEFAULT '1',
  `pickupTime` varchar(255) DEFAULT '',
  `printTime` varchar(255) DEFAULT '',
  `telephone` varchar(255) DEFAULT '',
  `direct` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `idx_union` (`shipping`,`transport1`,`receiver`,`mobile`,`expressPay`,`transport2`,`transportPay`,`locationId`),
  KEY `idx_pickup` (`pickupId`,`index_pos`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_package_item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_package_item` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `packageId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `baseId` varchar(40) DEFAULT NULL,
  `productId` varchar(500) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `outTime` varchar(30) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `printText` varchar(2000) DEFAULT NULL,
  `emergency` int(11) DEFAULT '0' COMMENT '紧急',
  PRIMARY KEY (`Id`),
  KEY `idx_packageid` (`packageId`),
  KEY `outId` (`outId`)
) ENGINE=InnoDB AUTO_INCREMENT=1101390 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_packageflow`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_packageflow` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `pickupid` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_packagelog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_packagelog` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `packageid` varchar(255) DEFAULT NULL,
  `user` varchar(255) DEFAULT NULL,
  `prestatus` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `checkstatus` varchar(255) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=282 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_partlineproject`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_partlineproject` (
  `id` varchar(40) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `projectproid` varchar(40) DEFAULT NULL,
  `partdetail` varchar(200) DEFAULT NULL,
  `partcount` int(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_parentId` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_payapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_payapply` (
  `ID` varchar(40) NOT NULL,
  `paymentId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `badMoney` double DEFAULT '0',
  `moneys` double DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `approve` varchar(1200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `vtype` int(11) DEFAULT '0',
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(1200) DEFAULT NULL,
  `checkrefId` varchar(200) DEFAULT NULL,
  `oriCustomerId` varchar(40) DEFAULT NULL,
  `oriStafferId` varchar(40) DEFAULT NULL,
  `oriBillId` varchar(40) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_paymentId` (`paymentId`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_paylineproject`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_paylineproject` (
  `id` varchar(40) NOT NULL,
  `refId` varchar(40) NOT NULL,
  `paytype` varchar(50) DEFAULT NULL,
  `paymoney` varchar(200) DEFAULT NULL,
  `paytime` varchar(50) DEFAULT NULL,
  `finishdays` int(20) DEFAULT NULL,
  `beforetask` varchar(100) DEFAULT NULL,
  `aftertask` varchar(100) DEFAULT NULL,
  `paycurrentTask1` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_refId` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_payment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_payment` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(400) DEFAULT '',
  `fromer` varchar(200) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `bankId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `batchId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `useall` int(11) DEFAULT '0',
  `money` double DEFAULT '0',
  `handling` double DEFAULT '0',
  `useMoney` double DEFAULT '0',
  `receiveTime` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `destStafferId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `checks1` varchar(200) DEFAULT NULL,
  `checks2` varchar(200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `checks3` varchar(200) DEFAULT NULL,
  `bakmoney` double DEFAULT '0',
  `fromerNo` varchar(40) DEFAULT NULL,
  `ctype` int(11) DEFAULT '0',
  `posTerminalNumber` varchar(100) DEFAULT '',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_ALL` (`refId`,`bankId`),
  KEY `IX_BANKID` (`bankId`),
  KEY `IX_BATCHID` (`batchId`),
  KEY `IX_STAFFERID` (`stafferId`),
  KEY `IX_customerId` (`customerId`),
  KEY `IX_receiveTime` (`receiveTime`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_paytag`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_paytag` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=433759 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_pf_order`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_pf_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mailId` varchar(100) NOT NULL,
  `status` int(11) DEFAULT '0',
  `dealDate` varchar(40) DEFAULT NULL,
  `dealTime` varchar(40) DEFAULT NULL,
  `branchName` varchar(40) NOT NULL,
  `comunicatonBranchName` varchar(40) NOT NULL,
  `productCode` varchar(40) NOT NULL,
  `productName` varchar(100) NOT NULL,
  `amount` int(11) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `value` double NOT NULL DEFAULT '0',
  `arrivalDate` varchar(40) DEFAULT '',
  `citicNo` varchar(40) NOT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `method` varchar(40) DEFAULT NULL,
  `pos` varchar(40) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `shippingOrg` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_ALL` (`citicNo`,`productName`)
) ENGINE=InnoDB AUTO_INCREMENT=2771 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_placeandinway`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_placeandinway` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) DEFAULT '0',
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(150) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `industryName` varchar(50) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=416 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_plan`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_plan` (
  `ID` int(11) NOT NULL,
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `carryType` int(11) DEFAULT '0',
  `orderIndex` int(11) DEFAULT '0',
  `fkId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `beginTime` varchar(40) DEFAULT NULL,
  `endTime` varchar(40) DEFAULT NULL,
  `carryTime` varchar(40) DEFAULT NULL,
  `realCarryTime` varchar(40) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_TIME` (`carryTime`),
  KEY `INX_ETIME` (`endTime`),
  KEY `INX_BTIME` (`beginTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_post`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_post` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_postcredit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_postcredit` (
  `id` varchar(40) NOT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `invoiceID` varchar(40) DEFAULT NULL,
  `credit` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_preconsign`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_preconsign` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(40) DEFAULT NULL,
  `logTime` varchar(255) DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_preinvoice`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_preinvoice` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `name` varchar(40) DEFAULT NULL,
  `flowKey` varchar(50) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `stype` int(11) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `invoiceType` int(11) DEFAULT NULL,
  `total` bigint(20) DEFAULT NULL,
  `invoiceMoney` bigint(20) DEFAULT NULL,
  `invoiceName` varchar(200) DEFAULT NULL,
  `otype` int(11) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `planOutTime` varchar(40) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `shipping` int(11) DEFAULT '0',
  `transport1` int(11) DEFAULT '0',
  `transport2` int(11) DEFAULT '0',
  `expressPay` int(11) DEFAULT '0',
  `transportPay` int(11) DEFAULT '0',
  `provinceId` varchar(20) DEFAULT '',
  `cityId` varchar(20) DEFAULT '',
  `invoiceNumber` varchar(60) DEFAULT '',
  `address` varchar(60) DEFAULT '',
  `receiver` varchar(20) DEFAULT '',
  `mobile` varchar(20) DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_presentflag`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_presentflag` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `type` varchar(3) DEFAULT NULL,
  `name` varchar(20) DEFAULT NULL,
  `pretype` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_price_change`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_price_change` (
  `ID` varchar(40) NOT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `checks` varchar(200) DEFAULT NULL,
  `checkStatus` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `description` varchar(1200) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `INX_STAFFERID` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_price_change_nitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_price_change_nitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(20) DEFAULT NULL,
  `relationId` varchar(20) DEFAULT NULL,
  `productId` varchar(20) DEFAULT NULL,
  `deportId` varchar(20) DEFAULT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `refId` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INX_PARENTID` (`parentId`),
  KEY `INX_RELATIONID` (`relationId`),
  KEY `IX_REFID` (`refId`)
) ENGINE=InnoDB AUTO_INCREMENT=11665 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_price_change_oitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_price_change_oitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(20) DEFAULT NULL,
  `relationId` varchar(20) DEFAULT NULL,
  `productId` varchar(20) DEFAULT NULL,
  `deportId` varchar(20) DEFAULT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `refId` varchar(20) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `UIX_REFID` (`refId`),
  KEY `INX_PARENTID` (`parentId`),
  KEY `INX_RELATIONID` (`relationId`)
) ENGINE=InnoDB AUTO_INCREMENT=10620 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_price_config`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_price_config` (
  `id` varchar(40) NOT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `isWave` int(11) DEFAULT NULL,
  `province` varchar(30) DEFAULT '',
  `gsPriceUp` double DEFAULT NULL,
  `gsPriceDown` double DEFAULT NULL,
  `beginDate` varchar(30) DEFAULT NULL,
  `endDate` varchar(30) DEFAULT NULL,
  `industryId` varchar(400) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `minPrice` double DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `ftype` int(11) DEFAULT '0',
  `goldPriceFactor` double DEFAULT '1',
  `silverPriceFactor` double DEFAULT '1',
  `pprice` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_price_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_price_his` (
  `ID` varchar(40) NOT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `price` double DEFAULT '0',
  `logTime` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  KEY `IX_PRODUCTID` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_priceask`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_priceask` (
  `id` varchar(40) NOT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `endTime` varchar(40) DEFAULT NULL,
  `refStock` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `processTime` varchar(40) DEFAULT NULL,
  `instancy` int(11) DEFAULT '0',
  `userId` varchar(200) DEFAULT NULL,
  `locationId` varchar(200) DEFAULT NULL,
  `puserId` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `overTime` int(11) DEFAULT '0',
  `description` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `src` int(11) DEFAULT '0',
  `srcamount` int(11) DEFAULT '0',
  `productType` int(11) DEFAULT '0',
  `saveType` int(11) DEFAULT '0',
  `parentAsk` varchar(40) DEFAULT NULL,
  `askDate` varchar(40) DEFAULT NULL,
  `amountStatus` int(11) DEFAULT '0',
  `stockMode` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `logTime` (`logTime`),
  KEY `processTime` (`processTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_priceaskprovider`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_priceaskprovider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `askId` varchar(40) DEFAULT NULL,
  `providerId` int(11) DEFAULT NULL,
  `productId` int(11) DEFAULT NULL,
  `hasAmount` int(11) DEFAULT '0',
  `price` double DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `userId` varchar(40) DEFAULT NULL,
  `supportAmount` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `srcType` int(11) DEFAULT '0',
  `goldPrice` double DEFAULT NULL,
  `silverPrice` double DEFAULT NULL,
  `handleFee` double DEFAULT NULL,
  `amount` double(10,2) DEFAULT NULL,
  `outTime` varchar(40) DEFAULT NULL,
  `isWrapper` int(11) DEFAULT NULL,
  `flow` varchar(40) DEFAULT NULL,
  `gap` int(11) DEFAULT NULL,
  `unitPrice` double DEFAULT NULL,
  `provideConfirmDate` varchar(30) DEFAULT NULL,
  `confirmSendDate` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `askId` (`askId`)
) ENGINE=InnoDB AUTO_INCREMENT=214312 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_principalship`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_principalship` (
  `ID` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `level` int(11) NOT NULL,
  `status` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_product`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_product` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `outname` varchar(255) DEFAULT NULL,
  `wlname` varchar(255) DEFAULT NULL,
  `fullspell` varchar(1200) DEFAULT NULL,
  `shortspell` varchar(200) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `abstractType` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `refId` varchar(50) DEFAULT NULL,
  `ptype` int(11) DEFAULT '0',
  `ctype` int(11) DEFAULT '0',
  `typeName` varchar(200) DEFAULT NULL,
  `specification` varchar(400) DEFAULT NULL,
  `model` varchar(400) DEFAULT NULL,
  `amountUnit` varchar(40) DEFAULT NULL,
  `weightUnit` varchar(40) DEFAULT NULL,
  `cubageUnit` varchar(40) DEFAULT NULL,
  `version` varchar(200) DEFAULT NULL,
  `design` varchar(200) DEFAULT NULL,
  `stockType` int(11) DEFAULT '0',
  `materielSource` varchar(200) DEFAULT NULL,
  `storeUnit` varchar(200) DEFAULT NULL,
  `abc` varchar(200) DEFAULT NULL,
  `batchModal` varchar(200) DEFAULT NULL,
  `checkDays` int(11) DEFAULT '0',
  `maxStoreDays` int(11) DEFAULT '0',
  `safeStoreDays` int(11) DEFAULT '0',
  `makeDays` int(11) DEFAULT '0',
  `flowDays` int(11) DEFAULT '0',
  `minAmount` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `assembleFlag` varchar(200) DEFAULT NULL,
  `lastOrderDate` varchar(40) DEFAULT NULL,
  `consumeInDay` int(11) DEFAULT '0',
  `orderAmount` int(11) DEFAULT '0',
  `mainProvider` varchar(40) DEFAULT NULL,
  `assistantProvider1` varchar(40) DEFAULT NULL,
  `assistantProvider2` varchar(40) DEFAULT NULL,
  `assistantProvider3` varchar(40) DEFAULT NULL,
  `assistantProvider4` varchar(40) DEFAULT NULL,
  `sailType` int(11) DEFAULT '0',
  `adjustPrice` int(11) DEFAULT '0',
  `financeType` varchar(40) DEFAULT NULL,
  `dutyType` varchar(40) DEFAULT NULL,
  `cost` double DEFAULT '0',
  `planCost` double DEFAULT '0',
  `batchPrice` double DEFAULT '0',
  `sailPrice` double DEFAULT '0',
  `checkFlag` varchar(200) DEFAULT NULL,
  `checkType` int(11) DEFAULT '0',
  `checkStandard` varchar(200) DEFAULT NULL,
  `picPath` varchar(255) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `reserve1` varchar(200) DEFAULT NULL,
  `reserve2` varchar(200) DEFAULT NULL,
  `reserve3` varchar(200) DEFAULT NULL,
  `reserve4` varchar(200) DEFAULT NULL,
  `reserve5` varchar(200) DEFAULT NULL,
  `reserve6` varchar(200) DEFAULT NULL,
  `reserve7` varchar(255) DEFAULT NULL,
  `reserve8` varchar(128) DEFAULT '',
  `reserve9` varchar(128) DEFAULT '',
  `reserve10` varchar(255) DEFAULT '',
  `midName` varchar(10) DEFAULT NULL,
  `refProductId` varchar(40) DEFAULT NULL,
  `inputInvoice` varchar(40) DEFAULT NULL,
  `sailInvoice` varchar(40) DEFAULT NULL,
  `productAmount` int(11) DEFAULT '-1',
  `packageAmount` int(11) DEFAULT '-1',
  `certificateAmount` int(11) DEFAULT '-1',
  `productWeight` double DEFAULT '0',
  `wlsx` varchar(20) DEFAULT NULL,
  `wlzhgx` varchar(20) DEFAULT NULL,
  `policy` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_NAME` (`name`),
  UNIQUE KEY `INX_CODE` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_product_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_product_bak` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `fullspell` varchar(1200) DEFAULT NULL,
  `shortspell` varchar(200) DEFAULT NULL,
  `code` varchar(50) DEFAULT NULL,
  `abstractType` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `refId` varchar(50) DEFAULT NULL,
  `ptype` int(11) DEFAULT '0',
  `ctype` int(11) DEFAULT '0',
  `typeName` varchar(200) DEFAULT NULL,
  `specification` varchar(400) DEFAULT NULL,
  `model` varchar(400) DEFAULT NULL,
  `amountUnit` varchar(40) DEFAULT NULL,
  `weightUnit` varchar(40) DEFAULT NULL,
  `cubageUnit` varchar(40) DEFAULT NULL,
  `version` varchar(200) DEFAULT NULL,
  `design` varchar(200) DEFAULT NULL,
  `stockType` int(11) DEFAULT '0',
  `materielSource` varchar(200) DEFAULT NULL,
  `storeUnit` varchar(200) DEFAULT NULL,
  `abc` varchar(200) DEFAULT NULL,
  `batchModal` varchar(200) DEFAULT NULL,
  `checkDays` int(11) DEFAULT '0',
  `maxStoreDays` int(11) DEFAULT '0',
  `safeStoreDays` int(11) DEFAULT '0',
  `makeDays` int(11) DEFAULT '0',
  `flowDays` int(11) DEFAULT '0',
  `minAmount` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `assembleFlag` varchar(200) DEFAULT NULL,
  `lastOrderDate` varchar(40) DEFAULT NULL,
  `consumeInDay` int(11) DEFAULT '0',
  `orderAmount` int(11) DEFAULT '0',
  `mainProvider` varchar(40) DEFAULT NULL,
  `assistantProvider1` varchar(40) DEFAULT NULL,
  `assistantProvider2` varchar(40) DEFAULT NULL,
  `assistantProvider3` varchar(40) DEFAULT NULL,
  `assistantProvider4` varchar(40) DEFAULT NULL,
  `sailType` int(11) DEFAULT '0',
  `adjustPrice` int(11) DEFAULT '0',
  `financeType` varchar(40) DEFAULT NULL,
  `dutyType` varchar(40) DEFAULT NULL,
  `cost` double DEFAULT '0',
  `planCost` double DEFAULT '0',
  `batchPrice` double DEFAULT '0',
  `sailPrice` double DEFAULT '0',
  `checkFlag` varchar(200) DEFAULT NULL,
  `checkType` int(11) DEFAULT '0',
  `checkStandard` varchar(200) DEFAULT NULL,
  `picPath` varchar(255) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `createrId` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `reserve1` varchar(200) DEFAULT NULL,
  `reserve2` varchar(200) DEFAULT NULL,
  `reserve3` varchar(200) DEFAULT NULL,
  `reserve4` varchar(200) DEFAULT NULL,
  `reserve5` varchar(200) DEFAULT NULL,
  `reserve6` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_product_exchange`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_product_exchange` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `srcProductId` varchar(200) NOT NULL,
  `srcAmount` int(11) DEFAULT NULL,
  `destProductId` varchar(200) NOT NULL,
  `destAmount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uc_ProductAmount` (`srcProductId`,`srcAmount`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_product_import`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_product_import` (
  `id` varchar(40) NOT NULL,
  `bank` varchar(40) NOT NULL,
  `branchName` varchar(255) DEFAULT NULL,
  `customerName` varchar(255) DEFAULT NULL,
  `channel` varchar(20) DEFAULT NULL,
  `bankProductCode` varchar(40) NOT NULL,
  `name` varchar(80) NOT NULL DEFAULT '',
  `code` varchar(40) NOT NULL,
  `bankProductBarcode` varchar(40) NOT NULL,
  `bankProductName` varchar(40) NOT NULL,
  `properties` varchar(100) NOT NULL,
  `weight` varchar(40) DEFAULT '',
  `material` varchar(40) DEFAULT NULL,
  `retailPrice` double DEFAULT '0',
  `costPrice` double DEFAULT '0',
  `ibMoney` double DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  `grossProfit` double DEFAULT '0',
  `buyBack` varchar(6) DEFAULT '-1',
  `onMarketDate` varchar(40) DEFAULT '',
  `offlineDate` varchar(40) DEFAULT '',
  `branchRange` varchar(40) DEFAULT '',
  `taxRate` varchar(40) NOT NULL,
  `invoiceType` varchar(40) NOT NULL,
  `invoiceContent` varchar(256) NOT NULL,
  `cash` double(7,2) DEFAULT NULL,
  `discription` varchar(255) DEFAULT '',
  `updateTime` varchar(64) DEFAULT '',
  `operator` varchar(10) DEFAULT '',
  `firstname` varchar(32) DEFAULT '',
  `ibMoney2` double DEFAULT '0',
  `motivationMoney2` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `bankProductCode` (`bankProductCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_product_vs_bank`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_product_vs_bank` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(100) NOT NULL,
  `citicProductName` varchar(200) DEFAULT NULL,
  `zhProductName` varchar(200) DEFAULT NULL,
  `pufaProductName` varchar(200) DEFAULT NULL,
  `zjProductName` varchar(200) DEFAULT NULL,
  `gznsProductName` varchar(200) DEFAULT NULL,
  `jnnsProductName` varchar(200) DEFAULT NULL,
  `jtProductName` varchar(200) DEFAULT NULL,
  `reserve1` varchar(200) DEFAULT NULL,
  `reserve2` varchar(200) DEFAULT NULL,
  `reserve3` varchar(200) DEFAULT NULL,
  `reserve4` varchar(200) DEFAULT NULL,
  `reserve5` varchar(200) DEFAULT NULL,
  `reserve6` varchar(200) DEFAULT NULL,
  `reserve7` varchar(200) DEFAULT NULL,
  `reserve8` varchar(200) DEFAULT NULL,
  `reserve9` varchar(200) DEFAULT NULL,
  `reserve10` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_productbom`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_productbom` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` varchar(40) DEFAULT NULL,
  `subProductId` varchar(40) DEFAULT NULL,
  `bomamount` int(4) DEFAULT NULL,
  `attritionrate` int(2) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_productid` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=16789678 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_productchangerecord`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_productchangerecord` (
  `id` varchar(40) NOT NULL,
  `flowid` varchar(40) NOT NULL,
  `oldStore` varchar(40) DEFAULT NULL,
  `newStore` varchar(40) DEFAULT NULL,
  `productid` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `moveTime` varchar(40) DEFAULT NULL,
  `reason` varchar(300) DEFAULT NULL,
  `moveStaffer` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_productlineproject`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_productlineproject` (
  `id` varchar(40) NOT NULL,
  `refid` varchar(40) NOT NULL,
  `projectpro` varchar(40) DEFAULT NULL,
  `procount` int(20) DEFAULT NULL,
  `prounitprice` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_refid` (`refid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_productnumber`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_productnumber` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PRODUCTID` varchar(50) DEFAULT NULL,
  `productName` varchar(255) DEFAULT NULL,
  `num` int(11) NOT NULL DEFAULT '0',
  `LOCATIONID` varchar(50) DEFAULT NULL,
  `code` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `UNIT` (`LOCATIONID`,`PRODUCTID`),
  KEY `INX_PRODUCTID` (`PRODUCTID`)
) ENGINE=InnoDB AUTO_INCREMENT=200563 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_productsailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_productsailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` date DEFAULT '0000-00-00',
  `productname` varchar(300) DEFAULT NULL,
  `productid` varchar(20) DEFAULT NULL,
  `amount` varchar(10) NOT NULL DEFAULT '',
  `value` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `zs` double(12,2) DEFAULT NULL,
  `jl` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=255158 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_productsequence`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_productsequence` (
  `head` varchar(4) NOT NULL DEFAULT '',
  `Id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`head`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_producttmcode`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_producttmcode` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `tmcode` varchar(10) NOT NULL DEFAULT '',
  `gg` varchar(15) NOT NULL DEFAULT '',
  `name` varchar(50) NOT NULL DEFAULT '',
  `cz` varchar(30) NOT NULL DEFAULT '',
  `sx` varchar(10) DEFAULT NULL,
  `gys` varchar(10) NOT NULL DEFAULT '',
  `wd` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=103 DEFAULT CHARSET=utf8 COMMENT='条码对应表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_producttypevscustomer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_producttypevscustomer` (
  `productTypeId` int(11) NOT NULL,
  `customerId` int(11) NOT NULL,
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`productTypeId`,`customerId`),
  UNIQUE KEY `IX_ID` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4598 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_project`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_project` (
  `id` varchar(50) NOT NULL,
  `processid` varchar(50) DEFAULT NULL,
  `flowkey` varchar(50) DEFAULT NULL,
  `projectName` varchar(200) NOT NULL,
  `projectCode` varchar(50) NOT NULL,
  `projectType` varchar(20) DEFAULT NULL,
  `creater` varchar(50) DEFAULT NULL,
  `customerId` varchar(100) DEFAULT NULL,
  `projectstatus` int(50) DEFAULT NULL,
  `projectStage` varchar(50) DEFAULT NULL,
  `predictSucRate` varchar(50) DEFAULT NULL,
  `applyTime` varchar(50) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `changelog` varchar(2000) DEFAULT NULL,
  PRIMARY KEY (`projectCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_projectapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_projectapply` (
  `id` varchar(40) NOT NULL,
  `applyId` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `applyerId` varchar(40) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `logTime` varchar(40) NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_applyId` (`applyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_projectapprove`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_projectapprove` (
  `id` varchar(40) NOT NULL,
  `applyId` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `flowKey` varchar(200) NOT NULL,
  `applyerId` varchar(40) NOT NULL,
  `approverId` varchar(40) NOT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `pool` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `total` bigint(20) DEFAULT '0',
  `logTime` varchar(40) NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `stype` int(11) DEFAULT '0',
  `payType` int(11) DEFAULT '0',
  `checkTotal` bigint(20) DEFAULT '0',
  `flag` int(11) DEFAULT NULL,
  `mode` int(11) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_applyId` (`applyId`),
  KEY `I_approverId` (`approverId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_projectflow`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_projectflow` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `flowKey` varchar(200) NOT NULL,
  `tokenName` varchar(200) DEFAULT NULL,
  `nextPlugin` varchar(200) DEFAULT NULL,
  `currentStatus` int(11) NOT NULL DEFAULT '0',
  `nextStatus` int(11) NOT NULL DEFAULT '0',
  `reject` int(11) NOT NULL DEFAULT '0',
  `rejectToPre` int(11) NOT NULL DEFAULT '0',
  `begining` int(11) NOT NULL DEFAULT '0',
  `ending` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_ALL1` (`flowKey`,`currentStatus`),
  UNIQUE KEY `U_ALL2` (`flowKey`,`nextStatus`),
  KEY `I_flowKey` (`flowKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_projecthandlehis`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_projecthandlehis` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `refId` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `applyId` varchar(40) NOT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_STAFFERID` (`stafferId`),
  KEY `I_refId` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_promotion`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_promotion` (
  `id` varchar(30) NOT NULL,
  `name` varchar(100) NOT NULL,
  `minAmount` int(11) DEFAULT NULL,
  `maxAmount` int(11) DEFAULT NULL,
  `minMoney` double DEFAULT NULL,
  `maxMoney` double DEFAULT NULL,
  `isAddUp` int(11) DEFAULT NULL,
  `rebateType` int(11) DEFAULT NULL,
  `giftBag` int(11) DEFAULT NULL,
  `rebateMoney` double DEFAULT NULL,
  `rebateRate` int(11) DEFAULT NULL,
  `maxRebateMoney` double DEFAULT NULL,
  `isInvoice` int(11) DEFAULT NULL,
  `payType` int(11) DEFAULT NULL,
  `industryId` varchar(400) DEFAULT NULL,
  `cType` int(11) DEFAULT NULL,
  `isBlack` int(11) DEFAULT NULL,
  `refTime` int(11) DEFAULT NULL,
  `payTime` int(11) DEFAULT NULL,
  `beginDate` varchar(40) DEFAULT NULL,
  `endDate` varchar(40) DEFAULT NULL,
  `inValid` int(11) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  `updater` varchar(20) DEFAULT NULL,
  `reserve1` varchar(40) DEFAULT NULL,
  `reserve2` varchar(40) DEFAULT NULL,
  `reserve3` varchar(40) DEFAULT NULL,
  `reserve4` varchar(40) DEFAULT NULL,
  `reserve5` varchar(40) DEFAULT NULL,
  `custCredit` int(11) DEFAULT NULL,
  `busiCredit` int(11) DEFAULT NULL,
  `isReturn` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_promotionitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_promotionitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refid` varchar(30) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `sailType` int(11) DEFAULT NULL,
  `productType` int(11) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_refid` (`refid`)
) ENGINE=InnoDB AUTO_INCREMENT=1143 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_provide`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_provide` (
  `ID` int(11) NOT NULL,
  `BAKID` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `htype` int(11) DEFAULT '0',
  `location` varchar(40) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `tel` varchar(200) DEFAULT NULL,
  `fax` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `bank` varchar(200) DEFAULT NULL,
  `accounts` varchar(200) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `dues` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INX_CODE` (`code`),
  UNIQUE KEY `INX_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_provide_bak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_provide_bak` (
  `ID` int(11) NOT NULL DEFAULT '0',
  `BAKID` int(11) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `code` varchar(200) NOT NULL DEFAULT '',
  `type` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `htype` int(11) DEFAULT '0',
  `connector` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `tel` varchar(200) DEFAULT NULL,
  `fax` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `bank` varchar(200) DEFAULT NULL,
  `accounts` varchar(200) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_provide_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_provide_his` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `checkStatus` int(11) NOT NULL DEFAULT '0',
  `PROVIDERID` int(11) NOT NULL,
  `BAKID` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `code` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `htype` int(11) DEFAULT '0',
  `location` varchar(40) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `tel` varchar(200) DEFAULT NULL,
  `fax` varchar(200) DEFAULT NULL,
  `qq` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `bank` varchar(200) DEFAULT NULL,
  `accounts` varchar(200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `dues` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=1070 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_provideuser`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_provideuser` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `pwkey` varchar(400) DEFAULT NULL,
  `roleId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `fail` int(11) DEFAULT '0',
  `loginTime` varchar(50) DEFAULT NULL,
  `provideId` varchar(50) DEFAULT NULL,
  `locationId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `INX_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_province`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_province` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_rebate`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_rebate` (
  `Id` varchar(40) NOT NULL DEFAULT '0',
  `name` varchar(40) DEFAULT NULL,
  `flowKey` varchar(50) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `stype` int(11) DEFAULT NULL,
  `beginDate` varchar(20) DEFAULT NULL,
  `atype` int(11) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `outIds` varchar(2000) DEFAULT NULL,
  `total` bigint(20) DEFAULT NULL,
  `lastMoney` bigint(20) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_receivetask`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_receivetask` (
  `ID` varchar(40) NOT NULL,
  `message` varchar(1200) NOT NULL,
  `sender` varchar(40) NOT NULL,
  `exNumber` varchar(40) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_redate_rule`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_redate_rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `leftDays` int(11) DEFAULT NULL,
  `rightDays` int(11) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `blackType` int(11) DEFAULT NULL,
  `logicOperation` int(11) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_replenishment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_replenishment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` varchar(40) DEFAULT '0',
  `productName` varchar(200) DEFAULT NULL,
  `depotpartId` varchar(40) DEFAULT '0',
  `depotpartName` varchar(255) DEFAULT NULL,
  `costPrice` double DEFAULT '0',
  `costPriceKey` varchar(40) DEFAULT NULL,
  `owner` varchar(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_pdo` (`productId`,`depotpartId`,`owner`)
) ENGINE=InnoDB AUTO_INCREMENT=7424 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_roleauth`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_roleauth` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLEID` varchar(40) NOT NULL,
  `AUTHID` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `INX_ROLEID` (`ROLEID`),
  KEY `IX_AUTHID` (`AUTHID`)
) ENGINE=InnoDB AUTO_INCREMENT=434817 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_sailconf`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_sailconf` (
  `id` varchar(40) NOT NULL,
  `sailType` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `productId` varchar(40) NOT NULL,
  `industryId` varchar(40) NOT NULL,
  `pratio` double DEFAULT '0',
  `iratio` double DEFAULT '0',
  `beginDate` varchar(40) DEFAULT NULL,
  `endDate` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `productType` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `INX_productId` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_sailconfig`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_sailconfig` (
  `id` varchar(40) NOT NULL,
  `pareId` varchar(40) DEFAULT NULL,
  `showId` varchar(40) DEFAULT NULL,
  `sailType` int(11) DEFAULT '0',
  `productType` int(11) DEFAULT '0',
  `finType0` int(11) DEFAULT '0',
  `finType1` int(11) DEFAULT '0',
  `finType2` int(11) DEFAULT '0',
  `finType3` int(11) DEFAULT '0',
  `finType4` int(11) DEFAULT '0',
  `finType5` int(11) DEFAULT '0',
  `ratio0` int(11) DEFAULT '0',
  `ratio1` int(11) DEFAULT '0',
  `ratio2` int(11) DEFAULT '0',
  `ratio3` int(11) DEFAULT '0',
  `ratio4` int(11) DEFAULT '0',
  `ratio5` int(11) DEFAULT '0',
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_CONFIG` (`showId`,`sailType`,`productType`),
  KEY `INX_PAREID` (`pareId`),
  KEY `INX_SHOWIDD` (`showId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_sailtag_config`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_sailtag_config` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `changeTime` int(11) DEFAULT NULL,
  `oldgood` int(11) DEFAULT NULL,
  `rate` double DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `flag` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_sailtranapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_sailtranapply` (
  `id` varchar(40) NOT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `oldStafferId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_schedule_job`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_schedule_job` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createTime` timestamp NULL DEFAULT NULL,
  `updateTime` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `jobName` varchar(255) DEFAULT NULL,
  `jobGroup` varchar(255) DEFAULT NULL,
  `jobStatus` varchar(255) DEFAULT NULL,
  `cronExpression` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `beanClass` varchar(255) DEFAULT NULL,
  `isConcurrent` varchar(255) DEFAULT NULL COMMENT '0',
  `springId` varchar(255) DEFAULT NULL,
  `methodName` varchar(255) NOT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_group` (`jobName`,`jobGroup`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_schedule_job_log`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_schedule_job_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jobId` varchar(20) NOT NULL,
  `jobName` varchar(64) DEFAULT NULL,
  `jobGroup` varchar(64) DEFAULT NULL,
  `fireTime` varchar(20) DEFAULT NULL,
  `nextFireTime` varchar(20) DEFAULT NULL,
  `refireCount` int(11) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=650073 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_sequence`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_sequence` (
  `id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_shortmessage`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_shortmessage` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `batchId` varchar(40) DEFAULT NULL,
  `msgId` varchar(200) DEFAULT NULL,
  `smType` int(11) DEFAULT NULL,
  `mobile` varchar(2000) DEFAULT NULL,
  `content` varchar(200) DEFAULT NULL,
  `stype` int(11) DEFAULT NULL,
  `smode` int(11) DEFAULT NULL,
  `custAndStaff` varchar(2000) DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `result` int(11) DEFAULT NULL,
  `ret` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_show`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_show` (
  `ID` varchar(40) NOT NULL,
  `NAME` varchar(40) NOT NULL,
  `DUTYID` varchar(40) NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_smstatus`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_smstatus` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `msgId` varchar(200) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `retsult` int(11) DEFAULT NULL,
  `ret` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5332 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_smtask`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_smtask` (
  `ID` varchar(40) NOT NULL,
  `fk` varchar(40) NOT NULL,
  `handId` varchar(40) NOT NULL,
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `fktoken` varchar(400) NOT NULL,
  `message` varchar(400) NOT NULL,
  `receiver` varchar(40) NOT NULL,
  `receiveMsg` varchar(1200) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  `sendTime` varchar(40) NOT NULL,
  `endTime` varchar(40) NOT NULL,
  `sendLog` varchar(1000) NOT NULL,
  `menuReceives` varchar(1200) NOT NULL,
  `fail` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_smtaskhis`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_smtaskhis` (
  `ID` varchar(40) NOT NULL,
  `fk` varchar(40) NOT NULL,
  `handId` varchar(40) NOT NULL,
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `fktoken` varchar(400) NOT NULL,
  `message` varchar(400) NOT NULL,
  `receiver` varchar(40) NOT NULL,
  `receiveMsg` varchar(1200) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  `sendTime` varchar(40) NOT NULL,
  `endTime` varchar(40) NOT NULL,
  `sendLog` varchar(1000) NOT NULL,
  `menuReceives` varchar(1200) NOT NULL,
  `fail` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stafferlineproject`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stafferlineproject` (
  `id` varchar(40) NOT NULL,
  `refid` varchar(40) NOT NULL,
  `staffer` varchar(40) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_refid` (`refid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_staffersailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_staffersailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` date NOT NULL DEFAULT '0000-00-00',
  `staffer` varchar(30) DEFAULT NULL,
  `stafferid` varchar(10) DEFAULT NULL,
  `syb` varchar(255) DEFAULT NULL,
  `dq` varchar(255) DEFAULT NULL,
  `bm` varchar(255) DEFAULT NULL,
  `value` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `zs` double(12,2) DEFAULT NULL,
  `jl` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=281569 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_staprosailbak`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_staprosailbak` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `podate` date NOT NULL DEFAULT '0000-00-00',
  `staffer` varchar(30) DEFAULT NULL,
  `stafferid` varchar(10) DEFAULT NULL,
  `syb` varchar(255) DEFAULT NULL,
  `dq` varchar(255) DEFAULT NULL,
  `bm` varchar(255) DEFAULT NULL,
  `productname` varchar(255) DEFAULT NULL,
  `productid` varchar(255) DEFAULT NULL,
  `amount` varchar(10) NOT NULL DEFAULT '',
  `value` double(12,2) DEFAULT NULL,
  `ml` double(12,2) DEFAULT NULL,
  `zs` double(12,2) DEFAULT NULL,
  `jl` double(12,2) DEFAULT NULL,
  `changetime` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=831668 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_statbank`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_statbank` (
  `ID` varchar(40) NOT NULL,
  `bankId` varchar(40) NOT NULL,
  `timeKey` varchar(40) DEFAULT NULL,
  `total` double DEFAULT '0',
  `description` varchar(1200) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_NAME` (`bankId`,`timeKey`),
  KEY `IX_bankId` (`bankId`),
  KEY `IX_timeKey` (`timeKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_statsrank`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_statsrank` (
  `Id` varchar(11) NOT NULL DEFAULT '',
  `batchId` varchar(40) DEFAULT NULL,
  `rank` int(11) DEFAULT NULL,
  `customerId` varchar(39) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `outTime` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `humanSort` int(11) DEFAULT NULL,
  `baseOutTime` int(11) DEFAULT NULL,
  `enoughStock` int(11) DEFAULT NULL,
  `hasFinish` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_statsrankout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_statsrankout` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=7896 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stock`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stock` (
  `id` varchar(40) NOT NULL,
  `userId` int(11) DEFAULT NULL,
  `stafferId` int(11) DEFAULT NULL,
  `locationId` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `pay` int(11) DEFAULT '0',
  `exceptStatus` int(11) DEFAULT '0',
  `owerId` varchar(40) DEFAULT NULL,
  `needTime` varchar(40) DEFAULT NULL,
  `willDate` int(11) DEFAULT '0',
  `nearlyPayDate` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) NOT NULL,
  `flow` varchar(200) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `consign` varchar(1200) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `stype` int(11) DEFAULT '0',
  `stockType` int(11) DEFAULT '0',
  `invoice` int(11) DEFAULT '0',
  `invoiceType` varchar(40) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `mode` int(11) DEFAULT '0',
  `checkStatus` int(11) DEFAULT '0',
  `checks` varchar(200) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  `ptype` int(11) DEFAULT '0',
  `target` varchar(300) DEFAULT NULL,
  `extraStatus` int(11) DEFAULT '0' COMMENT '采购拿货前确认',
  PRIMARY KEY (`id`),
  KEY `logTime` (`logTime`),
  KEY `userId` (`userId`),
  KEY `IX_stafferId` (`stafferId`),
  KEY `IX_industryId` (`industryId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stock_work`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stock_work` (
  `Id` varchar(40) NOT NULL DEFAULT '',
  `status` int(11) DEFAULT NULL COMMENT '跟单状态',
  `workDate` varchar(30) DEFAULT NULL COMMENT '跟单时间',
  `logTime` varchar(30) DEFAULT NULL COMMENT '操作时间',
  `target` varchar(200) DEFAULT NULL COMMENT '采购目标',
  `stafferId` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `stockId` varchar(40) DEFAULT NULL,
  `stockItemId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `provideId` varchar(40) DEFAULT NULL,
  `provideName` varchar(60) DEFAULT NULL,
  `needTime` varchar(30) DEFAULT NULL,
  `newSendDate` varchar(30) DEFAULT NULL COMMENT '最新确认发货时间',
  `provideConfirmDate` varchar(30) DEFAULT NULL COMMENT '供应商确认时间',
  `confirmSendDate` varchar(30) DEFAULT NULL COMMENT '确认发货时间',
  `connector` varchar(40) DEFAULT NULL COMMENT '供应商联系人',
  `way` int(11) DEFAULT NULL COMMENT '跟单途径',
  `followPlan` int(11) DEFAULT NULL COMMENT '是否按计划继续',
  `deliveryDate` int(11) DEFAULT NULL COMMENT '交期',
  `technology` int(11) DEFAULT NULL COMMENT '工艺',
  `pay` int(11) DEFAULT NULL COMMENT '付款方式',
  `sendDate` varchar(30) DEFAULT NULL COMMENT '发货时间',
  `isNew` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_stock` (`stockId`,`stockItemId`),
  KEY `idx_stockId` (`stockId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stock_workcount`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stock_workcount` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `stockId` varchar(40) DEFAULT NULL,
  `stockItemId` varchar(40) DEFAULT NULL,
  `count` int(11) DEFAULT '0' COMMENT '跟单次数',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=900 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stockitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stockitem` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stockId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `refOutId` varchar(200) DEFAULT NULL,
  `hasRef` int(11) DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `providerId` varchar(40) DEFAULT NULL,
  `showId` varchar(40) DEFAULT NULL,
  `depotpartId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `fechProduct` int(11) DEFAULT '0',
  `pay` int(11) DEFAULT '0',
  `price` double DEFAULT NULL,
  `prePrice` double DEFAULT NULL,
  `sailPrice` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `nearlyPayDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `productNum` int(11) DEFAULT '0',
  `netAskId` varchar(40) DEFAULT NULL,
  `priceAskProviderId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `invoiceType` varchar(40) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  `extraStatus` int(11) DEFAULT '0',
  `totalWarehouseNum` int(11) DEFAULT '0',
  `deliveryDate` varchar(200) DEFAULT '',
  `arrivalDate` varchar(200) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `IX_PPI` (`priceAskProviderId`),
  KEY `IX_STOCKID` (`stockId`),
  KEY `IX_PRODUCTID` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=131311 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stockitemarrial`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stockitemarrial` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stockId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `totalWarehouseNum` int(11) DEFAULT NULL,
  `refOutId` varchar(40) DEFAULT NULL,
  `hasRef` int(11) DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `providerId` varchar(40) DEFAULT NULL,
  `showId` varchar(40) DEFAULT NULL,
  `depotpartId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `fechProduct` int(11) DEFAULT '0',
  `pay` int(11) DEFAULT '0',
  `price` double DEFAULT NULL,
  `prePrice` double DEFAULT NULL,
  `sailPrice` double DEFAULT NULL,
  `total` double DEFAULT NULL,
  `nearlyPayDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `productNum` int(11) DEFAULT '0',
  `netAskId` varchar(40) DEFAULT NULL,
  `priceAskProviderId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `invoiceType` varchar(40) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  `extraStatus` int(11) DEFAULT '0',
  `deliveryDate` varchar(200) NOT NULL,
  `arrivalDate` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5856 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stockpayapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stockpayapply` (
  `id` varchar(40) NOT NULL,
  `status` int(11) DEFAULT '0',
  `invoiceId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `provideId` varchar(40) DEFAULT NULL,
  `stockId` varchar(40) DEFAULT NULL,
  `stockItemId` varchar(40) DEFAULT NULL,
  `inBillId` varchar(600) DEFAULT NULL,
  `moneys` double DEFAULT '0',
  `payDate` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `goldPrice` double DEFAULT NULL,
  `silverPrice` double DEFAULT NULL,
  `realMoneys` double DEFAULT NULL,
  `isFinal` int(11) DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `hasConfirm` int(11) DEFAULT '0',
  `hasConfirmInsMoney` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `IX_PROVIDEID` (`provideId`),
  KEY `IX_stockId` (`stockId`),
  KEY `IX_STOCKITEMID` (`stockItemId`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_stockpreapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_stockpreapply` (
  `id` varchar(40) NOT NULL,
  `status` int(11) DEFAULT '0',
  `invoiceId` varchar(40) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `providerId` varchar(40) DEFAULT NULL,
  `inBillId` varchar(600) DEFAULT NULL,
  `moneys` double DEFAULT '0',
  `payDate` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `realMoneys` double DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_PROVIDEID` (`providerId`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_storage`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_storage` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `depotpartId` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INX_DEPOTPARTID` (`depotpartId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_storage_capply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_storage_capply` (
  `ID` varchar(40) NOT NULL,
  `storageRelationId` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `applyer` varchar(40) DEFAULT NULL,
  `reveiver` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IX_REVEIVER` (`reveiver`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_storagelog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_storagelog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serializeId` varchar(40) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `productId` varchar(20) DEFAULT NULL,
  `preAmount` int(11) DEFAULT '0',
  `afterAmount` int(11) DEFAULT '0',
  `preAmount1` int(11) DEFAULT '0',
  `afterAmount1` int(11) DEFAULT '0',
  `preAmount11` int(11) DEFAULT '0',
  `afterAmount11` int(11) DEFAULT '0',
  `preAmount2` int(11) DEFAULT '0',
  `afterAmount2` int(11) DEFAULT '0',
  `preAmount22` int(11) DEFAULT '0',
  `afterAmount22` int(11) DEFAULT '0',
  `changeAmount` int(11) DEFAULT '0',
  `logTime` varchar(20) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `price` double DEFAULT '0',
  `user` varchar(40) DEFAULT NULL,
  `priceKey` varchar(50) DEFAULT NULL,
  `owner` varchar(40) DEFAULT NULL,
  `refId` varchar(100) NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_ALL` (`refId`,`owner`,`productId`,`priceKey`,`depotpartId`),
  KEY `INX_productId` (`productId`),
  KEY `IX_LOGTIME` (`logTime`)
) ENGINE=InnoDB AUTO_INCREMENT=1848374 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_storageralation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_storageralation` (
  `id` varchar(40) NOT NULL,
  `depotpartId` varchar(20) DEFAULT NULL,
  `storageId` varchar(20) DEFAULT NULL,
  `productId` varchar(20) DEFAULT NULL,
  `locationId` varchar(20) DEFAULT NULL,
  `priceKey` varchar(200) NOT NULL DEFAULT '',
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `lastPrice` double DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `inputRate` double DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_ALL` (`depotpartId`,`productId`,`priceKey`,`stafferId`),
  KEY `INX_STORAGE` (`storageId`),
  KEY `INX_productId` (`productId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_storagesnapshot`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_storagesnapshot` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `sdate` varchar(20) DEFAULT NULL,
  `productCode` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `industryName` varchar(100) DEFAULT NULL,
  `depotName` varchar(100) DEFAULT NULL,
  `depotpartName` varchar(100) DEFAULT NULL,
  `depotpartProp` varchar(30) DEFAULT NULL,
  `storageName` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_date` (`sdate`)
) ENGINE=InnoDB AUTO_INCREMENT=4904517 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_swatchstats`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_swatchstats` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `totalMoney` double DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_stafferid` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_swatchstatsitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_swatchstatsitem` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_refid` (`refId`)
) ENGINE=InnoDB AUTO_INCREMENT=2792924 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_sysconfig`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_sysconfig` (
  `id` int(11) NOT NULL,
  `CONFIG` varchar(200) NOT NULL,
  `VALUE` varchar(255) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_task`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_task` (
  `id` varchar(50) NOT NULL DEFAULT '0',
  `flowKey` varchar(100) DEFAULT NULL,
  `taskName` varchar(200) NOT NULL,
  `taskCode` varchar(50) NOT NULL DEFAULT '',
  `taskType` int(20) DEFAULT NULL,
  `dutyStaffer` varchar(100) DEFAULT NULL,
  `partaker` varchar(200) DEFAULT NULL,
  `planFinishTime` varchar(200) DEFAULT NULL,
  `realFinishiTime` varchar(200) DEFAULT NULL,
  `transType` varchar(50) DEFAULT NULL,
  `transObj` varchar(50) DEFAULT NULL,
  `transObjCount` int(11) DEFAULT NULL,
  `receiver` varchar(100) DEFAULT NULL,
  `beforeTask` varchar(100) DEFAULT NULL,
  `afterTask` varchar(100) DEFAULT NULL,
  `taskStatus` int(20) DEFAULT NULL,
  `taskStage` varchar(50) DEFAULT NULL,
  `filePath` varchar(200) DEFAULT NULL,
  `refid` varchar(100) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `changeLog` varchar(200) DEFAULT NULL,
  `applyTime` varchar(50) DEFAULT NULL,
  `processid` varchar(50) DEFAULT NULL,
  `applyer` varchar(50) DEFAULT NULL,
  `addinfo` varchar(2000) DEFAULT NULL,
  `emergencyType` int(11) DEFAULT '0' COMMENT '紧急类型',
  `finishTime` varchar(2) DEFAULT NULL COMMENT '任务完成时间  小时',
  `finishMin` varchar(2) DEFAULT NULL COMMENT '任务完成时间 分',
  `creator` varchar(20) DEFAULT NULL COMMENT '创建人',
  `creatorName` varchar(30) DEFAULT NULL COMMENT '创建人名',
  PRIMARY KEY (`id`,`taskCode`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_taskplan`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_taskplan` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `taskId` varchar(40) DEFAULT NULL COMMENT '关联任务ID',
  `title` varchar(500) DEFAULT NULL,
  `content` varchar(2000) DEFAULT NULL,
  `tos` varchar(2000) DEFAULT NULL,
  `planTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tax`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tax` (
  `ID` varchar(40) NOT NULL,
  `parentId` varchar(40) NOT NULL DEFAULT '0',
  `parentId0` varchar(40) DEFAULT NULL,
  `parentId1` varchar(40) DEFAULT NULL,
  `parentId2` varchar(40) DEFAULT NULL,
  `parentId3` varchar(40) DEFAULT NULL,
  `parentId4` varchar(40) DEFAULT NULL,
  `parentId5` varchar(40) DEFAULT NULL,
  `parentId6` varchar(40) DEFAULT NULL,
  `parentId7` varchar(40) DEFAULT NULL,
  `parentId8` varchar(40) DEFAULT NULL,
  `code` varchar(40) NOT NULL,
  `ptype` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `bname` varchar(255) DEFAULT NULL,
  `refId` varchar(40) DEFAULT NULL,
  `refType` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `bottomFlag` int(11) DEFAULT '0',
  `level` int(11) DEFAULT '0',
  `forward` int(11) DEFAULT '0',
  `unit` int(11) DEFAULT '0',
  `department` int(11) DEFAULT '0',
  `staffer` int(11) DEFAULT '0',
  `depot` int(11) DEFAULT '0',
  `product` int(11) DEFAULT '0',
  `duty` int(11) DEFAULT '0',
  `checkStaffer` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_code` (`code`),
  KEY `IX_PARENTID` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_taxtype`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_taxtype` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_NAME` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpapply` (
  `id` varchar(40) NOT NULL,
  `applyId` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `flowKey` varchar(200) NOT NULL,
  `applyerId` varchar(40) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `total` bigint(20) DEFAULT '0',
  `departmentId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `stype` int(11) DEFAULT '0',
  `payType` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `I_applyId` (`applyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpapprove`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpapprove` (
  `id` varchar(40) NOT NULL,
  `applyId` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `flowKey` varchar(200) NOT NULL,
  `applyerId` varchar(40) NOT NULL,
  `approverId` varchar(40) NOT NULL,
  `departmentId` varchar(40) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `pool` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `total` bigint(20) DEFAULT '0',
  `logTime` varchar(40) NOT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `stype` int(11) DEFAULT '0',
  `payType` int(11) DEFAULT '0',
  `checkTotal` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `I_applyId` (`applyId`),
  KEY `I_approverId` (`approverId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpexpense`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpexpense` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `flowKey` varchar(200) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `borrowStafferId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `ticikCount` int(11) NOT NULL DEFAULT '0',
  `payType` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) NOT NULL,
  `srcCity` varchar(40) DEFAULT NULL,
  `destCity` varchar(40) DEFAULT NULL,
  `airplaneCharges` bigint(20) DEFAULT '0',
  `trainCharges` bigint(20) DEFAULT '0',
  `busCharges` bigint(20) DEFAULT '0',
  `hotelCharges` bigint(20) DEFAULT '0',
  `entertainCharges` bigint(20) DEFAULT '0',
  `allowanceCharges` bigint(20) DEFAULT '0',
  `other1Charges` bigint(20) DEFAULT '0',
  `other2Charges` bigint(20) DEFAULT '0',
  `beginDate` varchar(40) DEFAULT NULL,
  `endDate` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `total` bigint(20) DEFAULT '0',
  `borrowTotal` bigint(20) DEFAULT '0',
  `lastMoney` bigint(20) DEFAULT '0',
  `refMoney` bigint(20) DEFAULT '0',
  `unitName` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `customerNames` varchar(1200) DEFAULT NULL,
  `aroundNames` varchar(200) DEFAULT NULL,
  `companyStafferNames` varchar(200) DEFAULT NULL,
  `stype` int(11) DEFAULT '0',
  `specialType` int(11) DEFAULT '0',
  `dutyId` varchar(40) DEFAULT NULL,
  `compliance` varchar(2) DEFAULT NULL,
  `marketingFlag` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `I_stafferId` (`stafferId`),
  KEY `I_BORROWSTAFFERID` (`borrowStafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpflow`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpflow` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `flowKey` varchar(200) NOT NULL,
  `tokenName` varchar(200) DEFAULT NULL,
  `nextPlugin` varchar(50) DEFAULT NULL,
  `currentStatus` int(11) NOT NULL DEFAULT '0',
  `nextStatus` int(11) NOT NULL DEFAULT '0',
  `singeAll` int(11) NOT NULL DEFAULT '0',
  `reject` int(11) NOT NULL DEFAULT '0',
  `rejectToPre` int(11) NOT NULL DEFAULT '0',
  `begining` int(11) NOT NULL DEFAULT '0',
  `ending` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_ALL1` (`flowKey`,`currentStatus`),
  UNIQUE KEY `U_ALL2` (`flowKey`,`nextStatus`),
  KEY `I_flowKey` (`flowKey`)
) ENGINE=InnoDB AUTO_INCREMENT=869 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcphandlehis`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcphandlehis` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `refId` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `applyId` varchar(40) NOT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_STAFFERID` (`stafferId`),
  KEY `I_refId` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpib`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpib` (
  `id` varchar(40) NOT NULL,
  `refId` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `customerName` varchar(200) DEFAULT NULL,
  `fullId` varchar(10240) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `ibMoney` double DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  `ibMoney2` double DEFAULT '0',
  `motivationMoney2` double DEFAULT '0',
  `logTime` varchar(64) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `I_refId` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpibreport`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpibreport` (
  `id` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `ibMoneyTotal` double DEFAULT '0',
  `motivationMoneyTotal` double DEFAULT '0',
  `logTime` varchar(255) DEFAULT '',
  `ibMoneyTotal2` double DEFAULT '0',
  `motivationMoneyTotal2` double DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpibreport_item`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpibreport_item` (
  `id` varchar(40) NOT NULL,
  `refId` varchar(200) NOT NULL,
  `type` int(11) DEFAULT '0',
  `customerName` varchar(200) DEFAULT NULL,
  `fullId` varchar(200) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `ibMoney` double DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  `price` double DEFAULT '0',
  `productId` varchar(255) DEFAULT '',
  `ibMoney2` double DEFAULT '0',
  `motivationMoney2` double DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `I_refId` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpprepayment`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpprepayment` (
  `id` varchar(40) NOT NULL,
  `refId` varchar(40) NOT NULL,
  `refItemId` varchar(40) NOT NULL,
  `budgetId` varchar(40) NOT NULL,
  `budgetItemId` varchar(40) NOT NULL,
  `feeItem` varchar(40) NOT NULL,
  `departmentId` varchar(40) NOT NULL,
  `moneys` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `I_refId` (`refId`),
  KEY `I_feeItem` (`feeItem`),
  KEY `I_budgetItemId` (`budgetItemId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tcpshare`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tcpshare` (
  `id` varchar(40) NOT NULL,
  `refId` varchar(40) NOT NULL,
  `budgetId` varchar(40) NOT NULL,
  `departmentId` varchar(40) NOT NULL,
  `approverId` varchar(40) NOT NULL,
  `ratio` int(11) DEFAULT '0',
  `realMonery` bigint(20) DEFAULT '0',
  `bearId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_refId` (`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tempbase`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tempbase` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `baseId` varchar(40) DEFAULT NULL,
  `refOutId` varchar(40) DEFAULT NULL,
  `ibMoney` double DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  `matches` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=145 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tempconsign`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tempconsign` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(1024) DEFAULT NULL,
  `insId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=827 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_templatefile`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_templatefile` (
  `ID` varchar(40) NOT NULL,
  `name` varchar(400) NOT NULL,
  `fileName` varchar(255) NOT NULL,
  `path` varchar(400) NOT NULL,
  `logTime` varchar(40) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_tempout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_tempout` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4403 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_translineproject`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_translineproject` (
  `id` varchar(40) NOT NULL,
  `refid` varchar(40) NOT NULL,
  `transType` varchar(50) DEFAULT NULL,
  `transobj` varchar(50) DEFAULT NULL,
  `transobjCount` varchar(50) DEFAULT NULL,
  `transTime` varchar(50) DEFAULT NULL,
  `transDays` varchar(40) DEFAULT NULL,
  `currentTask` varchar(100) DEFAULT NULL,
  `receiver` varchar(50) DEFAULT NULL,
  `beforeTask1` varchar(200) DEFAULT NULL,
  `afterTask1` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_refid` (`refid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_transno`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_transno` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `ckno` varchar(255) DEFAULT NULL,
  `express` varchar(255) DEFAULT NULL,
  `expresscompany` varchar(255) DEFAULT NULL,
  `transportno` varchar(255) DEFAULT NULL,
  `sftime` datetime DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8068 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_transport`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_transport` (
  `id` int(11) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `parent` int(11) DEFAULT '0',
  `company` varchar(200) DEFAULT NULL,
  `conphone` varchar(40) DEFAULT NULL,
  `area` varchar(1200) DEFAULT NULL,
  `outTime` varchar(200) DEFAULT NULL,
  `apiCode` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_travelapply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_travelapply` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) NOT NULL,
  `flowKey` varchar(200) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  `borrowStafferId` varchar(40) DEFAULT NULL,
  `departmentId` varchar(40) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  `status` int(11) NOT NULL DEFAULT '0',
  `borrow` int(11) NOT NULL DEFAULT '0',
  `feedback` int(11) NOT NULL DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) NOT NULL,
  `srcCity` varchar(40) DEFAULT NULL,
  `destCity` varchar(40) DEFAULT NULL,
  `airplaneCharges` bigint(20) DEFAULT '0',
  `trainCharges` bigint(20) DEFAULT '0',
  `busCharges` bigint(20) DEFAULT '0',
  `hotelCharges` bigint(20) DEFAULT '0',
  `entertainCharges` bigint(20) DEFAULT '0',
  `allowanceCharges` bigint(20) DEFAULT '0',
  `other1Charges` bigint(20) DEFAULT '0',
  `other2Charges` bigint(20) DEFAULT '0',
  `beginDate` varchar(40) DEFAULT NULL,
  `endDate` varchar(40) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `total` bigint(20) DEFAULT '0',
  `borrowTotal` bigint(20) DEFAULT '0',
  `unitName` varchar(200) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `customerNames` varchar(1200) DEFAULT NULL,
  `aroundNames` varchar(200) DEFAULT NULL,
  `companyStafferNames` varchar(200) DEFAULT NULL,
  `stype` int(11) DEFAULT '0',
  `dutyId` varchar(40) DEFAULT NULL,
  `compliance` varchar(2) DEFAULT NULL,
  `oldNumber` varchar(40) DEFAULT NULL,
  `purposeType` int(11) DEFAULT NULL,
  `vocationType` int(11) DEFAULT NULL,
  `qingJiapurpose` varchar(500) DEFAULT NULL,
  `importFlag` int(11) DEFAULT '-1',
  `ibType` int(11) DEFAULT '-1',
  `marketingFlag` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `I_stafferId` (`stafferId`),
  KEY `I_BORROWSTAFFERID` (`borrowStafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_travelapplyitem`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_travelapplyitem` (
  `id` varchar(40) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `beginDate` varchar(40) DEFAULT NULL,
  `endDate` varchar(40) DEFAULT NULL,
  `feeItemId` varchar(40) NOT NULL,
  `purpose` varchar(1200) DEFAULT NULL,
  `moneys` bigint(20) DEFAULT '0',
  `description` varchar(1200) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `prices` bigint(20) DEFAULT '0',
  `checkPrices` bigint(20) DEFAULT '0',
  `feeStafferId` varchar(20) DEFAULT NULL,
  `budgetId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `I_parentId` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_travelapplypay`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_travelapplypay` (
  `id` varchar(40) NOT NULL,
  `parentId` varchar(40) NOT NULL,
  `receiveType` int(11) NOT NULL DEFAULT '0',
  `bankName` varchar(200) DEFAULT NULL,
  `userName` varchar(200) DEFAULT NULL,
  `bankNo` varchar(200) DEFAULT NULL,
  `description` varchar(1200) DEFAULT NULL,
  `cdescription` varchar(1200) DEFAULT NULL,
  `moneys` bigint(20) DEFAULT '0',
  `cmoneys` bigint(20) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `I_parentId` (`parentId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_turn_deduction`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_turn_deduction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(40) DEFAULT NULL,
  `month` varchar(10) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_month` (`month`),
  KEY `idx_stafferid` (`stafferId`),
  KEY `idx_parentid` (`parentId`)
) ENGINE=InnoDB AUTO_INCREMENT=728 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_twbase`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_twbase` (
  `id` int(11) NOT NULL,
  `inway` int(11) DEFAULT '0',
  `productId` varchar(40) DEFAULT NULL,
  `unit` varchar(10) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  `price` double DEFAULT '0',
  `costPrice` double DEFAULT '0',
  `costPriceKey` varchar(40) DEFAULT NULL,
  `value` double DEFAULT '0',
  `owner` varchar(40) DEFAULT NULL,
  `ownerName` varchar(200) DEFAULT NULL,
  `locationId` varchar(200) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `showName` varchar(200) DEFAULT NULL,
  `showId` varchar(40) DEFAULT NULL,
  `outId` varchar(50) DEFAULT NULL,
  `storageId` varchar(40) DEFAULT NULL,
  `depotpartName` varchar(200) DEFAULT NULL,
  `depotpartId` varchar(40) DEFAULT NULL,
  `inputPrice` double DEFAULT '0',
  `mtype` int(11) DEFAULT '0',
  `invoiceMoney` double DEFAULT '0',
  `pprice` double DEFAULT '0',
  `iprice` double DEFAULT '0',
  `deliverType` int(11) DEFAULT '-1',
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `profit` double DEFAULT '0',
  `profitRatio` double DEFAULT '0',
  `oldGoods` int(11) DEFAULT NULL,
  `taxrate` double DEFAULT NULL,
  `tax` double DEFAULT NULL,
  `inputRate` double DEFAULT '0',
  `refId` varchar(40) DEFAULT NULL,
  `ibMoney` double DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  `profitmoney` double(8,2) DEFAULT NULL COMMENT '2',
  `grossProfit` double DEFAULT NULL,
  `checkgrossProfit` double(6,2) DEFAULT NULL,
  `cash` double(8,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UN_BASE` (`outId`,`productId`,`costPriceKey`,`owner`,`depotpartId`),
  KEY `INX_OUTID` (`outId`),
  KEY `INX_PRODUCTID` (`productId`),
  KEY `INX_SHOWID` (`showId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_twdistribution`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_twdistribution` (
  `id` varchar(40) NOT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `refAddId` varchar(40) DEFAULT NULL,
  `shipping` int(11) DEFAULT NULL,
  `transport1` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL,
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `areaId` varchar(40) DEFAULT NULL,
  `printCount` int(11) DEFAULT '0',
  `outboundDate` varchar(20) DEFAULT NULL COMMENT '发货日期',
  PRIMARY KEY (`id`),
  KEY `idx_outid` (`outId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_twout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_twout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fullId` varchar(200) NOT NULL,
  `flowId` varchar(50) DEFAULT NULL,
  `outTime` varchar(20) DEFAULT NULL,
  `managerTime` varchar(20) DEFAULT NULL,
  `outType` int(11) DEFAULT '0',
  `channel` varchar(10) DEFAULT NULL,
  `locationID` int(11) DEFAULT '0',
  `location` varchar(200) DEFAULT NULL,
  `industryId` varchar(40) DEFAULT NULL,
  `industryId2` varchar(40) DEFAULT NULL,
  `department` varchar(200) DEFAULT NULL,
  `customerId` varchar(200) DEFAULT NULL,
  `description` varchar(2000) DEFAULT NULL,
  `total` double DEFAULT '0',
  `hadPay` double DEFAULT '0',
  `badDebts` double DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `type` int(11) DEFAULT '0',
  `hasInvoice` int(11) DEFAULT '0',
  `invoiceStatus` int(11) DEFAULT '0',
  `invoiceId` varchar(40) DEFAULT NULL,
  `dutyId` varchar(40) DEFAULT NULL,
  `mark` int(11) DEFAULT '0',
  `pay` int(11) DEFAULT '0',
  `inway` int(11) DEFAULT '0',
  `tempType` int(11) DEFAULT '0',
  `customerName` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `stafferName` varchar(200) DEFAULT NULL,
  `connector` varchar(200) DEFAULT NULL,
  `checks` varchar(2000) DEFAULT NULL,
  `reday` varchar(200) DEFAULT NULL,
  `arriveDate` varchar(200) DEFAULT NULL,
  `redate` varchar(200) DEFAULT NULL,
  `depotpartId` varchar(200) DEFAULT NULL,
  `destinationId` varchar(200) DEFAULT NULL,
  `tranNo` varchar(200) DEFAULT NULL,
  `refOutFullId` varchar(200) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  `managerId` varchar(40) DEFAULT NULL,
  `reserve1` int(11) DEFAULT '0',
  `reserve2` int(11) DEFAULT '0',
  `reserve3` int(11) DEFAULT '0',
  `reserve4` varchar(200) DEFAULT NULL,
  `reserve5` varchar(200) DEFAULT NULL,
  `reserve6` varchar(200) DEFAULT NULL,
  `reserve7` varchar(1000) DEFAULT NULL,
  `reserve8` varchar(1000) DEFAULT NULL,
  `reserve9` varchar(1000) DEFAULT NULL,
  `invoiceMoney` double DEFAULT '0',
  `curcredit` double DEFAULT '0',
  `staffcredit` double DEFAULT '0',
  `managercredit` double DEFAULT '0',
  `checkStatus` int(11) DEFAULT '0',
  `badDebtsCheckStatus` int(11) DEFAULT '0',
  `changeTime` varchar(40) DEFAULT NULL,
  `mtype` int(11) DEFAULT '0',
  `sailType` varchar(40) DEFAULT NULL,
  `productType` varchar(40) DEFAULT NULL,
  `ratio` varchar(40) DEFAULT NULL,
  `vtype` int(11) DEFAULT '0',
  `vtypeFullId` varchar(200) DEFAULT NULL,
  `pmtype` int(11) DEFAULT '0',
  `industryId3` varchar(40) DEFAULT NULL,
  `payTime` varchar(20) DEFAULT NULL,
  `forceBuyType` int(11) DEFAULT NULL,
  `eventId` varchar(40) DEFAULT NULL,
  `refBindOutId` varchar(400) DEFAULT NULL,
  `promValue` double DEFAULT NULL,
  `promStatus` int(11) DEFAULT NULL,
  `lastModified` varchar(40) DEFAULT NULL,
  `operator` varchar(40) DEFAULT NULL,
  `operatorName` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `guarantor` varchar(40) DEFAULT NULL,
  `feedBackVisit` int(11) DEFAULT '0',
  `feedBackCheck` int(11) DEFAULT '0',
  `taxTotal` double DEFAULT NULL,
  `hasRebate` int(11) DEFAULT NULL,
  `piDutyId` varchar(40) DEFAULT '',
  `piMtype` int(11) DEFAULT '-1',
  `piType` int(11) DEFAULT '-1',
  `piStatus` int(11) DEFAULT '-1',
  `hasConfirm` int(11) DEFAULT '0',
  `hasConfirmInsMoney` double DEFAULT '0',
  `emergency` int(11) DEFAULT '0' COMMENT '紧急',
  `presentFlag` int(11) DEFAULT '0' COMMENT '赠送类型',
  `podate` varchar(40) DEFAULT NULL,
  `ibFlag` int(11) DEFAULT '-1',
  `motivationFlag` int(11) DEFAULT '-1',
  `remoteAllocate` int(11) DEFAULT '0',
  `reason` varchar(100) DEFAULT '',
  `transportNo` varchar(100) DEFAULT '',
  `swbz` varchar(255) DEFAULT NULL,
  `outbackStatus` varchar(255) DEFAULT '',
  `customerCreated` int(11) DEFAULT '0',
  `flowTime` varchar(255) DEFAULT '',
  `ibApplyId` varchar(255) DEFAULT '',
  `motivationApplyId` varchar(255) DEFAULT '',
  `refGiftId` varchar(32) DEFAULT '',
  `profigflag` int(2) DEFAULT NULL,
  `cashflag` int(2) DEFAULT NULL,
  PRIMARY KEY (`fullId`,`id`),
  KEY `flowTime` (`flowTime`),
  KEY `INX_CID` (`customerId`),
  KEY `INX_FULLID` (`fullId`),
  KEY `INX_ID` (`id`),
  KEY `INX_INDUSTRYID` (`industryId`),
  KEY `INX_INDUSTRYID2` (`industryId2`),
  KEY `INX_LASTMODIFIED` (`lastModified`),
  KEY `INX_LOCATIONID` (`locationID`),
  KEY `INX_MANAGERID` (`managerId`),
  KEY `INX_MGTIME` (`managerTime`),
  KEY `INX_OUTTIME` (`outTime`),
  KEY `INX_STAFFERNAME` (`stafferName`),
  KEY `INX_vtypeFullId` (`vtypeFullId`),
  KEY `IX_arriveDate` (`arriveDate`),
  KEY `IX_refOutFullId` (`refOutFullId`),
  KEY `IX_SID` (`stafferId`),
  KEY `podate` (`podate`)
) ENGINE=InnoDB AUTO_INCREMENT=548175364 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_unit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_unit` (
  `id` varchar(40) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idx_code` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_user`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_user` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT '0',
  `status` int(11) DEFAULT '0',
  `fail` int(11) DEFAULT '0',
  `loginTime` varchar(50) DEFAULT NULL,
  `stafferName` varchar(50) DEFAULT NULL,
  `LOCATIONID` varchar(50) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `wy` (`name`),
  KEY `INX_NAME` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=100359 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_appout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_appout` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `outId` varchar(40) DEFAULT NULL,
  `appOutId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idx_outid` (`outId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_appusercust`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_appusercust` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `appUserId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  UNIQUE KEY `idx_appUserId` (`appUserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_citic_product`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_citic_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productCode` varchar(40) DEFAULT '0',
  `productName` varchar(200) DEFAULT NULL,
  `citicProductCode` varchar(40) DEFAULT NULL,
  `citicProductName` varchar(200) DEFAULT NULL,
  `firstName` varchar(30) DEFAULT 'N/A',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_pcf` (`productCode`,`citicProductCode`,`firstName`)
) ENGINE=InnoDB AUTO_INCREMENT=2977 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_citicstaff`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_citicstaff` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `customerId` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=214 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_curcre`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_curcre` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(40) DEFAULT NULL,
  `itemId` varchar(40) DEFAULT NULL,
  `pitemId` varchar(40) DEFAULT NULL,
  `ptype` int(11) DEFAULT '0',
  `valueId` varchar(40) DEFAULT NULL,
  `val` double DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `log` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_INX_CIDPITEMID` (`cid`,`itemId`),
  KEY `INX_CID` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=367331 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_curcre_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_curcre_apply` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `cid` varchar(40) DEFAULT NULL,
  `itemId` varchar(40) DEFAULT NULL,
  `pitemId` varchar(40) DEFAULT NULL,
  `ptype` int(11) DEFAULT '0',
  `valueId` varchar(40) DEFAULT NULL,
  `val` double DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `log` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `U_INX_CIDPITEMID` (`cid`,`itemId`),
  KEY `INX_CID` (`cid`)
) ENGINE=InnoDB AUTO_INCREMENT=114261 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custaddr`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custaddr` (
  `id` varchar(40) NOT NULL DEFAULT '',
  `customerId` varchar(40) NOT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(400) DEFAULT NULL,
  `fullAddress` varchar(500) DEFAULT NULL,
  `contact` varchar(30) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `atype` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `shipping` int(11) DEFAULT '-1',
  `transport1` int(11) DEFAULT '0',
  `transport2` int(11) DEFAULT '0',
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custaddr_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custaddr_apply` (
  `id` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(400) DEFAULT NULL,
  `fullAddress` varchar(500) DEFAULT NULL,
  `contact` varchar(30) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `atype` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custaddr_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custaddr_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentid` varchar(40) DEFAULT NULL,
  `batchId` varchar(40) DEFAULT NULL,
  `forward` int(11) DEFAULT NULL,
  `customerId` varchar(40) NOT NULL,
  `provinceId` varchar(40) DEFAULT NULL,
  `cityId` varchar(40) DEFAULT NULL,
  `areaId` varchar(40) DEFAULT NULL,
  `address` varchar(400) DEFAULT NULL,
  `fullAddress` varchar(500) DEFAULT NULL,
  `contact` varchar(30) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL,
  `atype` int(11) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_parentid` (`parentid`),
  KEY `idx_batchId` (`batchId`),
  KEY `idx_customerid` (`customerId`)
) ENGINE=InnoDB AUTO_INCREMENT=481 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custbusi`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custbusi` (
  `id` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `custAccountType` int(11) DEFAULT NULL,
  `custAccountBank` varchar(200) DEFAULT NULL,
  `custAccountName` varchar(200) DEFAULT NULL,
  `custAccount` varchar(50) DEFAULT NULL,
  `myAccountType` int(11) DEFAULT NULL,
  `myAccountBank` varchar(200) DEFAULT NULL,
  `myAccountName` varchar(200) DEFAULT NULL,
  `myAccount` varchar(50) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custbusi_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custbusi_apply` (
  `id` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `custAccountType` int(11) DEFAULT NULL,
  `custAccountBank` varchar(200) DEFAULT NULL,
  `custAccountName` varchar(200) DEFAULT NULL,
  `custAccount` varchar(50) DEFAULT NULL,
  `myAccountType` int(11) DEFAULT NULL,
  `myAccountBank` varchar(200) DEFAULT NULL,
  `myAccountName` varchar(200) DEFAULT NULL,
  `myAccount` varchar(50) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custbusi_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custbusi_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentid` varchar(40) DEFAULT NULL,
  `batchId` varchar(40) DEFAULT NULL,
  `forward` int(11) DEFAULT NULL,
  `customerId` varchar(40) NOT NULL,
  `custAccountType` int(11) DEFAULT NULL,
  `custAccountBank` varchar(200) DEFAULT NULL,
  `custAccountName` varchar(200) DEFAULT NULL,
  `custAccount` varchar(50) DEFAULT NULL,
  `myAccountType` int(11) DEFAULT NULL,
  `myAccountBank` varchar(200) DEFAULT NULL,
  `myAccountName` varchar(200) DEFAULT NULL,
  `myAccount` varchar(50) DEFAULT NULL,
  `valid` int(11) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_parentid` (`parentid`),
  KEY `idx_batchid` (`batchId`),
  KEY `idx_customerid` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custcont`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custcont` (
  `id` varchar(40) NOT NULL DEFAULT '',
  `customerId` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sex` int(11) NOT NULL,
  `personal` int(11) DEFAULT NULL,
  `age` int(11) DEFAULT '0',
  `birthday` varchar(20) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `qq` varchar(1000) DEFAULT NULL,
  `weibo` varchar(1000) DEFAULT NULL,
  `weixin` varchar(1000) DEFAULT NULL,
  `duty` int(11) DEFAULT NULL,
  `reportTo` varchar(40) DEFAULT NULL,
  `interest` varchar(100) DEFAULT NULL,
  `relationship` int(10) DEFAULT '-1',
  `contactTimes` int(11) DEFAULT NULL,
  `lastContactTime` varchar(30) DEFAULT NULL,
  `valid` int(11) DEFAULT '0',
  `description` varchar(500) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custcont_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custcont_apply` (
  `id` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sex` int(11) NOT NULL,
  `personal` int(11) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `qq` varchar(1000) DEFAULT NULL,
  `weibo` varchar(1000) DEFAULT NULL,
  `weixin` varchar(1000) DEFAULT NULL,
  `duty` int(11) DEFAULT NULL,
  `reportTo` varchar(40) DEFAULT NULL,
  `interest` varchar(100) DEFAULT NULL,
  `relationship` int(10) DEFAULT '-1',
  `contactTimes` int(11) DEFAULT NULL,
  `lastContactTime` varchar(30) DEFAULT NULL,
  `valid` int(11) DEFAULT '0',
  `description` varchar(500) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_custcont_his`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_custcont_his` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentid` varchar(40) DEFAULT NULL,
  `batchId` varchar(40) DEFAULT NULL,
  `forward` int(11) DEFAULT NULL,
  `customerId` varchar(40) NOT NULL,
  `name` varchar(100) NOT NULL,
  `sex` int(11) NOT NULL,
  `personal` int(11) DEFAULT NULL,
  `age` int(11) NOT NULL,
  `birthday` varchar(20) DEFAULT NULL,
  `handphone` varchar(1000) DEFAULT NULL,
  `tel` varchar(1000) DEFAULT NULL,
  `email` varchar(1000) DEFAULT NULL,
  `qq` varchar(1000) DEFAULT NULL,
  `weibo` varchar(1000) DEFAULT NULL,
  `weixin` varchar(1000) DEFAULT NULL,
  `duty` int(11) DEFAULT NULL,
  `reportTo` varchar(40) DEFAULT NULL,
  `interest` varchar(100) DEFAULT NULL,
  `relationship` int(10) DEFAULT '-1',
  `contactTimes` int(11) DEFAULT NULL,
  `lastContactTime` varchar(30) DEFAULT NULL,
  `valid` int(11) DEFAULT '0',
  `description` varchar(500) DEFAULT NULL,
  `reserve1` varchar(50) DEFAULT NULL,
  `reserve2` varchar(50) DEFAULT NULL,
  `reserve3` varchar(50) DEFAULT NULL,
  `reserve4` varchar(50) DEFAULT NULL,
  `reserve5` varchar(50) DEFAULT NULL,
  `reserve6` varchar(50) DEFAULT NULL,
  `reserve7` varchar(50) DEFAULT NULL,
  `reserve8` varchar(50) DEFAULT NULL,
  `reserve9` varchar(50) DEFAULT NULL,
  `reserve10` varchar(50) DEFAULT NULL,
  `role` int(11) DEFAULT NULL,
  `updateId` varchar(40) DEFAULT NULL,
  `updateTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_customerId` (`customerId`),
  KEY `idx_parentid` (`parentid`),
  KEY `idx_batchId` (`batchId`)
) ENGINE=InnoDB AUTO_INCREMENT=146 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_deststacus`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_deststacus` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `STAFFERID` varchar(40) NOT NULL,
  `destStafferId` varchar(40) DEFAULT NULL,
  `CUSTOMERID` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `idx_deststaff` (`destStafferId`)
) ENGINE=InnoDB AUTO_INCREMENT=112524 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_dutyinv`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_dutyinv` (
  `id` int(11) NOT NULL,
  `dutyType` int(11) DEFAULT NULL,
  `invoiceID` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_fbout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_fbout` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `taskId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `changeTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_taskid` (`taskId`)
) ENGINE=InnoDB AUTO_INCREMENT=52434 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_flow_template`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_flow_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `FLOWID` varchar(40) DEFAULT NULL,
  `TEMPLATEID` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=240 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_formercust`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_formercust` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `currCustId` varchar(40) DEFAULT NULL,
  `formerCustId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_gift`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_gift` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` varchar(40) DEFAULT NULL,
  `giftProductId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `bank` varchar(200) DEFAULT '',
  `sailAmount` int(11) DEFAULT '0',
  `activity` varchar(200) DEFAULT '',
  `beginDate` varchar(100) DEFAULT '',
  `endDate` varchar(100) DEFAULT '',
  `description` varchar(200) DEFAULT '',
  `industryName` varchar(100) DEFAULT '',
  `industryName2` varchar(100) DEFAULT '',
  `industryName3` varchar(100) DEFAULT '',
  `city` varchar(100) DEFAULT '',
  `stafferName` varchar(100) DEFAULT '',
  `province` varchar(100) DEFAULT '',
  `giftProductId2` varchar(100) DEFAULT '',
  `amount2` int(11) DEFAULT '0',
  `giftProductId3` varchar(100) DEFAULT '',
  `amount3` int(11) DEFAULT '0',
  `excludeBank` varchar(100) DEFAULT '',
  `excludeIndustryName` varchar(100) DEFAULT '',
  `excludeIndustryName2` varchar(100) DEFAULT '',
  `excludeIndustryName3` varchar(100) DEFAULT '',
  `excludeCity` varchar(100) DEFAULT '',
  `excludeProvince` varchar(100) DEFAULT '',
  `excludeStafferName` varchar(100) DEFAULT '',
  `companyShare` int(11) DEFAULT '0',
  `stafferShare` int(11) DEFAULT '0',
  `brancheName` varchar(100) DEFAULT '',
  `excludeBrancheName` varchar(100) DEFAULT '',
  `customerName` varchar(100) DEFAULT '',
  `excludeCustomerName` varchar(100) DEFAULT '',
  `channel` varchar(10) DEFAULT '',
  `excludeChannel` varchar(10) DEFAULT '',
  `branchName` varchar(100) DEFAULT '',
  `excludeBranchName` varchar(100) DEFAULT '',
  `createtime` varchar(100) DEFAULT '',
  `creator` varchar(40) DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=399 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_groupsta`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_groupsta` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `groupId` varchar(40) NOT NULL,
  `stafferId` varchar(40) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IX_U` (`groupId`,`stafferId`),
  KEY `INX_G` (`groupId`)
) ENGINE=InnoDB AUTO_INCREMENT=30584 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_insout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_insout` (
  `id` varchar(40) NOT NULL,
  `insId` varchar(40) NOT NULL,
  `outId` varchar(40) NOT NULL,
  `outBalanceId` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT '0',
  `moneys` double DEFAULT '0',
  `baseId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `IX_INSID` (`insId`),
  KEY `IX_OUTID` (`outId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_insproduct`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_insproduct` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `insId` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `moneys` double DEFAULT NULL,
  `showName` varchar(50) DEFAULT NULL,
  `amount` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `idx_insId` (`insId`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_invoicenum`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_invoicenum` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `insId` varchar(40) DEFAULT NULL,
  `invoiceNum` varchar(50) DEFAULT NULL,
  `moneys` double DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `idx_insid` (`insId`)
) ENGINE=InnoDB AUTO_INCREMENT=290905 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_locationcity`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_locationcity` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `locationId` varchar(40) NOT NULL,
  `provinceID` varchar(40) NOT NULL,
  `cityID` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_LVSC` (`locationId`,`cityID`)
) ENGINE=InnoDB AUTO_INCREMENT=3692 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_outpay`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_outpay` (
  `id` varchar(40) NOT NULL,
  `parentId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `outBalanceId` varchar(40) DEFAULT NULL,
  `paymentId` varchar(40) DEFAULT NULL,
  `billId` varchar(40) DEFAULT NULL,
  `moneys` double DEFAULT '0',
  `stafferId` varchar(40) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `INX_PARENTID` (`parentId`),
  KEY `INX_OUTID` (`outId`),
  KEY `INX_OUTBALANCEID` (`outBalanceId`),
  KEY `INX_PAYID` (`paymentId`),
  KEY `INX_BILLID` (`billId`),
  KEY `INX_LOGTIME` (`logTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_packcust`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_packcust` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `packageId` varchar(40) DEFAULT NULL,
  `customerId` varchar(40) DEFAULT NULL,
  `customerName` varchar(100) DEFAULT NULL,
  `indexPos` int(11) DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `idx_packageid` (`packageId`)
) ENGINE=InnoDB AUTO_INCREMENT=23110284 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_preinvoiceout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_preinvoiceout` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` varchar(40) DEFAULT NULL,
  `outId` varchar(40) DEFAULT NULL,
  `outBalanceId` varchar(40) DEFAULT NULL,
  `money` double DEFAULT NULL,
  `invoiceMoney` double DEFAULT NULL,
  `mayInvoiceMoney` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=821 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_product`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vproductId` varchar(40) NOT NULL,
  `sproductId` varchar(40) NOT NULL,
  `createrId` varchar(40) NOT NULL,
  `amount` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_ALL` (`vproductId`,`sproductId`),
  KEY `INX_VPRODUCTID` (`vproductId`)
) ENGINE=InnoDB AUTO_INCREMENT=711 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_productlocation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_productlocation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productId` varchar(40) NOT NULL,
  `locationId` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_ALL` (`productId`,`locationId`),
  KEY `INX_PRODUCTID` (`productId`)
) ENGINE=InnoDB AUTO_INCREMENT=19755 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_sta_ind`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_sta_ind` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) NOT NULL,
  `industryId` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_ALL` (`stafferId`,`industryId`),
  KEY `INX_stafferId` (`stafferId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_sta_pri`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_sta_pri` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `stafferId` varchar(40) NOT NULL,
  `principalshipId` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `INX_stafferId` (`stafferId`),
  KEY `INX_principalshipId` (`principalshipId`)
) ENGINE=InnoDB AUTO_INCREMENT=24714 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_sta_tra`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_sta_tra` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `destId` varchar(40) NOT NULL,
  `srcId` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `U_ALL` (`srcId`),
  KEY `INX_destId` (`destId`)
) ENGINE=InnoDB AUTO_INCREMENT=1263 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_stacus`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_stacus` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `STAFFERID` int(11) NOT NULL,
  `CUSTOMERID` int(11) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `idx_cust` (`CUSTOMERID`)
) ENGINE=InnoDB AUTO_INCREMENT=3386618 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_stockpaycomp`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_stockpaycomp` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `composeId` varchar(40) DEFAULT NULL,
  `stockPayApplyId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_composeid` (`composeId`)
) ENGINE=InnoDB AUTO_INCREMENT=8387 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_stockprepay`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_stockprepay` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `payApplyId` varchar(40) DEFAULT NULL,
  `prePayId` varchar(40) DEFAULT NULL,
  `moneys` double DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `idx_payApplyId` (`payApplyId`)
) ENGINE=InnoDB AUTO_INCREMENT=567 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_token_operation`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_token_operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flowId` varchar(40) DEFAULT NULL,
  `TOKENID` varchar(40) DEFAULT NULL,
  `pass` int(11) DEFAULT '0',
  `reject` int(11) DEFAULT '0',
  `rejectAll` int(11) DEFAULT '0',
  `ends` int(11) DEFAULT '0',
  `exends` int(11) DEFAULT '0',
  `liminal` double DEFAULT '0',
  `rejectParent` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `TOKENID` (`TOKENID`)
) ENGINE=InnoDB AUTO_INCREMENT=1209 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_token_processer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_token_processer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flowId` varchar(40) DEFAULT NULL,
  `TOKENID` varchar(40) DEFAULT NULL,
  `processer` varchar(40) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1211 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_vs_token_template`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_vs_token_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `flowId` varchar(40) DEFAULT NULL,
  `TOKENID` varchar(40) DEFAULT NULL,
  `TEMPLATEID` varchar(40) DEFAULT NULL,
  `viewTEMPLATE` int(11) DEFAULT '0',
  `editTEMPLATE` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1171 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_zjrcbase`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_zjrcbase` (
  `Id` varchar(20) NOT NULL DEFAULT '',
  `outId` varchar(40) DEFAULT NULL,
  `oaNo` varchar(40) DEFAULT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productName` varchar(200) DEFAULT NULL,
  `zjrcProductId` varchar(40) DEFAULT NULL,
  `zjrcProductName` varchar(200) DEFAULT NULL,
  `locationId` varchar(40) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  `depotpartId` varchar(40) DEFAULT NULL,
  `depotpartName` varchar(100) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `costPrice` double DEFAULT NULL,
  `midRevenue` double DEFAULT NULL,
  `value` double DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `pstatus` int(11) DEFAULT '0',
  `motivationMoney` double DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_zjrcout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_zjrcout` (
  `Id` varchar(20) NOT NULL DEFAULT '',
  `fullId` varchar(40) NOT NULL DEFAULT '',
  `outTime` varchar(20) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `customerId` varchar(20) DEFAULT NULL,
  `customerName` varchar(200) DEFAULT NULL,
  `location` varchar(40) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `outType` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL,
  `cityId` varchar(20) DEFAULT NULL,
  `areaId` varchar(20) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `receiver` varchar(20) DEFAULT NULL,
  `handPhone` varchar(20) DEFAULT NULL,
  `shipDescription` varchar(100) DEFAULT NULL,
  `invoiceHead` varchar(50) DEFAULT NULL,
  `invoiceDetail` varchar(200) DEFAULT NULL,
  `invoiceDescription` varchar(100) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `tranNo` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fullId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_zjrcproduct`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_zjrcproduct` (
  `Id` varchar(30) NOT NULL DEFAULT '',
  `productId` varchar(20) DEFAULT NULL,
  `zjrProductName` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `costPrice` double DEFAULT NULL,
  `midRevenue` double DEFAULT NULL,
  `stafferId` varchar(20) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `motivationMoney` double DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_zjrpdist`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_zjrpdist` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `zjrcOutId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `logTime` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2488 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_zs_order`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_zs_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sn` varchar(40) DEFAULT '',
  `mailId` varchar(100) NOT NULL,
  `status` int(11) DEFAULT '0',
  `orderStatus` varchar(40) DEFAULT '',
  `dealDate` varchar(40) DEFAULT NULL,
  `dealTime` varchar(40) DEFAULT NULL,
  `account` varchar(40) DEFAULT NULL,
  `providerId` varchar(100) DEFAULT NULL,
  `pickupNode` varchar(40) DEFAULT NULL,
  `branchName` varchar(40) NOT NULL,
  `comunicatonBranchName` varchar(40) NOT NULL,
  `storageControlType` varchar(40) DEFAULT '',
  `productCode` varchar(40) NOT NULL,
  `productName` varchar(100) NOT NULL,
  `productSpec` varchar(100) DEFAULT '',
  `amount` int(11) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `value` double NOT NULL DEFAULT '0',
  `fee` double NOT NULL DEFAULT '0',
  `citicNo` varchar(40) NOT NULL,
  `invoiceNature` varchar(40) NOT NULL,
  `invoiceHead` varchar(40) NOT NULL,
  `invoiceCondition` varchar(40) NOT NULL,
  `materialType` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_ALL` (`citicNo`,`productName`)
) ENGINE=InnoDB AUTO_INCREMENT=4927 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_center_zy_order`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_center_zy_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mailId` varchar(100) NOT NULL,
  `status` int(11) DEFAULT '0',
  `innerCustomerId` varchar(40) NOT NULL,
  `customerId` varchar(40) NOT NULL,
  `customerName` varchar(40) NOT NULL,
  `customerType` varchar(20) NOT NULL,
  `customerGroup` varchar(20) NOT NULL,
  `idType` varchar(20) NOT NULL,
  `idCard` varchar(20) NOT NULL,
  `proxyIdType` varchar(20) DEFAULT NULL,
  `proxyIdCard` varchar(20) DEFAULT NULL,
  `bankAccount` varchar(40) NOT NULL,
  `channel` varchar(40) NOT NULL,
  `originalChannel` varchar(40) DEFAULT NULL,
  `terminal` varchar(40) DEFAULT NULL,
  `dealDate` varchar(40) NOT NULL,
  `dealTime` varchar(40) NOT NULL,
  `dealCode` varchar(40) NOT NULL,
  `comunicatonBranchName` varchar(100) NOT NULL,
  `originalDealAgent` varchar(40) DEFAULT NULL,
  `tellerId` varchar(40) DEFAULT NULL,
  `sendMainframeId` varchar(40) NOT NULL,
  `mainframeCheckDate` varchar(40) NOT NULL,
  `mainframeTradeCode` varchar(40) NOT NULL,
  `mainframeDate` varchar(40) DEFAULT NULL,
  `mainframeId` varchar(40) NOT NULL,
  `returnCode` varchar(20) NOT NULL,
  `returnMessage` varchar(20) NOT NULL,
  `tradeStatus` varchar(20) NOT NULL,
  `acceptMethod` varchar(20) DEFAULT NULL,
  `corporateAccount` varchar(40) NOT NULL,
  `finished` int(11) NOT NULL DEFAULT '0',
  `exceptional` int(11) DEFAULT '0',
  `appointmentOfArrival` int(11) DEFAULT '0',
  `pickupNode` varchar(40) DEFAULT NULL,
  `specCode` varchar(40) NOT NULL,
  `specName` varchar(40) NOT NULL,
  `spec` double DEFAULT '0',
  `businessType` varchar(40) DEFAULT NULL,
  `associateDate` varchar(40) DEFAULT NULL,
  `associateId` varchar(40) DEFAULT NULL,
  `citicNo` varchar(40) NOT NULL,
  `channelSerialNumber` varchar(40) NOT NULL,
  `productId` varchar(40) DEFAULT NULL,
  `productCode` varchar(40) NOT NULL,
  `productName` varchar(100) NOT NULL,
  `amount` int(11) NOT NULL,
  `buyUnit` int(11) NOT NULL,
  `price` double NOT NULL DEFAULT '0',
  `value` double NOT NULL DEFAULT '0',
  `fee` double NOT NULL DEFAULT '0',
  `arriveDate` varchar(40) DEFAULT NULL,
  `invoiceHead` varchar(40) NOT NULL,
  `financialStatus` varchar(40) DEFAULT NULL,
  `currency` varchar(40) NOT NULL,
  `manager` varchar(40) NOT NULL,
  `paymentMethod` varchar(40) DEFAULT NULL,
  `remainAmount` int(11) DEFAULT '0',
  `storageCost` double DEFAULT '0',
  `discountRate` double DEFAULT '0',
  `productType` varchar(40) DEFAULT NULL,
  `pickupType` varchar(40) DEFAULT NULL,
  `teller` varchar(40) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `enterpriseCode` varchar(40) NOT NULL,
  `enterpriseName` varchar(40) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `INX_ALL` (`citicNo`,`productName`)
) ENGINE=InnoDB AUTO_INCREMENT=1121 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_estimateprofit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_estimateprofit` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(255) DEFAULT NULL,
  `profit` double DEFAULT NULL,
  `logTime` varchar(30) DEFAULT NULL,
  `stafferName` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_hg_estimate`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_hg_estimate` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `estimateid` varchar(50) DEFAULT NULL COMMENT '预估流程单号',
  `category` varchar(50) DEFAULT NULL COMMENT '品类',
  `yeard` varchar(50) DEFAULT NULL COMMENT '年份',
  `standard` varchar(20) DEFAULT NULL COMMENT '规格',
  `conditiond` varchar(20) DEFAULT NULL COMMENT '实物品相',
  `packaged` varchar(40) DEFAULT NULL COMMENT '包装品相',
  `certificate` varchar(40) DEFAULT NULL COMMENT '证书品相',
  `amount` varchar(255) DEFAULT NULL COMMENT '数量',
  `price` varchar(30) DEFAULT NULL COMMENT '价格',
  `createtime` datetime DEFAULT NULL COMMENT '生成时间',
  `creater` varchar(20) DEFAULT NULL COMMENT '创建者',
  `discription` varchar(255) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_hg_list`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_hg_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transportno` varchar(255) DEFAULT NULL COMMENT '顺丰快递单号',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `county` varchar(255) DEFAULT NULL COMMENT '区县',
  `address` varchar(255) DEFAULT NULL COMMENT '地址',
  `fromer` varchar(255) DEFAULT NULL COMMENT '寄件人',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `discription` varchar(255) DEFAULT NULL COMMENT '备注',
  `estimateid` varchar(50) DEFAULT NULL COMMENT '预估流程单号',
  `productdis` varchar(255) DEFAULT NULL COMMENT '回购商品信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=50 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_hg_listlog`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_hg_listlog` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `backid` varchar(255) DEFAULT NULL,
  `estimateid` varchar(255) DEFAULT NULL COMMENT '预估流程单号',
  `transportno` varchar(255) DEFAULT NULL COMMENT '顺丰快递单号',
  `express` varchar(20) DEFAULT NULL,
  `fromer` varchar(255) DEFAULT NULL COMMENT '寄件人',
  `mobile` varchar(255) DEFAULT NULL COMMENT '手机号',
  `province` varchar(255) DEFAULT NULL COMMENT '省',
  `city` varchar(255) DEFAULT NULL COMMENT '市',
  `address` varchar(255) DEFAULT NULL COMMENT '区县',
  `productdis` varchar(255) DEFAULT NULL COMMENT '回购商品信息',
  `discription` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL COMMENT '回购状态',
  `fromtime` datetime DEFAULT NULL COMMENT '寄件时间',
  `receiver` varchar(255) DEFAULT NULL COMMENT '收件人',
  `receivetime` datetime DEFAULT NULL COMMENT '收件时间',
  `packager` varchar(255) DEFAULT NULL,
  `packagetime` datetime DEFAULT NULL COMMENT '拆包时间',
  `determiner` varchar(255) DEFAULT NULL,
  `determinedes` varchar(255) DEFAULT NULL COMMENT '鉴定信息',
  `determinetime` datetime DEFAULT NULL COMMENT '鉴定时间',
  `pricer` varchar(255) DEFAULT NULL,
  `pricetime` datetime DEFAULT NULL,
  `payer` varchar(255) DEFAULT NULL,
  `paytime` datetime DEFAULT NULL COMMENT '打款时间',
  `sendtransportno` varchar(255) DEFAULT NULL COMMENT '回寄单号',
  `sendtime` datetime DEFAULT NULL COMMENT '回寄时间',
  `sender` varchar(25) DEFAULT NULL,
  `usertime` datetime DEFAULT NULL COMMENT '回寄收货时间',
  `creater` varchar(255) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `fj` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_hg_priceconfig`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_hg_priceconfig` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(255) DEFAULT NULL COMMENT '品类',
  `year` varchar(40) DEFAULT NULL COMMENT '年份',
  `standard` varchar(255) DEFAULT NULL COMMENT '规格',
  `condition1` varchar(255) DEFAULT NULL COMMENT '实物品相',
  `packaged` varchar(255) DEFAULT NULL COMMENT '包装品相',
  `certificate` varchar(255) DEFAULT NULL COMMENT '证书品相',
  `price` varchar(30) DEFAULT NULL COMMENT '价格',
  `createtime` datetime DEFAULT NULL COMMENT '时间',
  `creater` varchar(255) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_product_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_product_apply` (
  `id` varchar(40) NOT NULL,
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(400) DEFAULT NULL,
  `fullspell` varchar(1200) DEFAULT NULL,
  `shortspell` varchar(200) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `materiaType` int(11) DEFAULT NULL,
  `channelType` int(11) DEFAULT NULL,
  `managerType` int(11) DEFAULT NULL,
  `cultrueType` int(11) DEFAULT NULL,
  `discountRate` int(11) DEFAULT NULL,
  `priceRange` int(11) DEFAULT NULL,
  `salePeriod` int(11) DEFAULT NULL,
  `saleTarget` int(11) DEFAULT NULL,
  `nature` int(11) DEFAULT NULL,
  `productManagerId` varchar(40) DEFAULT NULL,
  `currencyType` int(11) DEFAULT NULL,
  `secondhandGoods` int(11) DEFAULT NULL,
  `style` int(11) DEFAULT NULL,
  `country` int(11) DEFAULT NULL,
  `commissionBeginDate` varchar(40) DEFAULT NULL,
  `commissionEndDate` varchar(40) DEFAULT NULL,
  `industryId` varchar(400) DEFAULT NULL,
  `oprId` varchar(40) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `logTime` varchar(40) DEFAULT NULL,
  `description` varchar(400) DEFAULT NULL,
  `gold` double DEFAULT '0',
  `silver` double DEFAULT '0',
  `firstName` varchar(10) DEFAULT NULL,
  `midName` varchar(10) DEFAULT NULL,
  `fullName` varchar(200) DEFAULT NULL,
  `refProductId` varchar(40) DEFAULT NULL,
  `dutyType` varchar(40) DEFAULT NULL COMMENT '增值税类型',
  `inputInvoice` varchar(40) DEFAULT NULL COMMENT '进项发票',
  `sailInvoice` varchar(40) DEFAULT NULL,
  `className` varchar(100) DEFAULT '',
  `productAmount` int(11) DEFAULT '-1',
  `packageAmount` int(11) DEFAULT '-1',
  `certificateAmount` int(11) DEFAULT '-1',
  `productWeight` double DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_product_vs_staffer`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_product_vs_staffer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `stafferRole` varchar(50) DEFAULT NULL,
  `commissionRatio` int(11) DEFAULT NULL,
  `stafferId` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1171 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `t_subproduct_apply`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_subproduct_apply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refId` varchar(40) DEFAULT NULL,
  `subProductId` varchar(40) DEFAULT NULL,
  `subProductAmount` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=789 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_cm`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_cm` (
  `id` varchar(40) DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `name` varchar(200) NOT NULL DEFAULT '',
  `creditlevel` varchar(40) DEFAULT NULL,
  `creditvalue` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_cus`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_cus` (
  `name` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_dist`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_dist` (
  `id` varchar(40) NOT NULL DEFAULT '0',
  `outId` varchar(40) DEFAULT NULL,
  `refAddId` varchar(40) DEFAULT NULL,
  `shipping` int(11) DEFAULT NULL,
  `transport1` int(11) DEFAULT NULL,
  `transport2` int(11) DEFAULT NULL,
  `provinceId` varchar(20) DEFAULT NULL,
  `cityId` varchar(20) DEFAULT NULL,
  `address` varchar(1000) DEFAULT NULL,
  `receiver` varchar(40) DEFAULT NULL,
  `mobile` varchar(50) DEFAULT NULL,
  `telephone` varchar(50) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `expressPay` int(11) DEFAULT '-1',
  `transportPay` int(11) DEFAULT '-1',
  `areaId` varchar(40) DEFAULT NULL,
  `printCount` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_oi`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_oi` (
  `oano` varchar(40) DEFAULT NULL,
  `midvalue` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_oo`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_oo` (
  `outid` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_oo1`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_oo1` (
  `outid` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_pout`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_pout` (
  `code` varchar(50) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `type` varchar(3) DEFAULT NULL,
  `sailtype` varchar(2) DEFAULT NULL,
  `mtype` varchar(2) DEFAULT NULL,
  `oldgoods` varchar(3) DEFAULT NULL,
  `stockType` int(11) DEFAULT '0',
  `checkDays` int(11) DEFAULT '0',
  `maxStoreDays` int(11) DEFAULT '0',
  `safeStoreDays` int(11) DEFAULT '0',
  `flowDays` int(11) DEFAULT '0',
  `minAmount` int(11) DEFAULT '0',
  `ctype` varchar(2) DEFAULT NULL,
  `产品阶段` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_pz`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_pz` (
  `id` varchar(40) NOT NULL,
  `financedate` varchar(40) DEFAULT NULL,
  `logtime` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tmp_staff`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tmp_staff` (
  `name` varchar(200) NOT NULL,
  `depart1` varchar(400) DEFAULT NULL,
  `depart2` varchar(400) DEFAULT NULL,
  `depart3` varchar(400) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan10`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan10` (
  `fullid` tinyint(4) NOT NULL,
  `customername` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan11`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan11` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan12`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan12` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan13`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan13` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan14`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan14` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan15`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan15` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan16`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan16` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan2`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan2` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan21`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan21` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan3`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan3` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan4`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan4` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan41`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan41` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan5`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan5` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan6`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan6` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan61`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan61` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan611`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan611` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan7`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan7` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan8`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan8` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL,
  `leibie` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan9`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan9` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tuidan91`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tuidan91` (
  `fullId` tinyint(4) NOT NULL,
  `outtime` tinyint(4) NOT NULL,
  `changetime` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `v_center_check`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `v_center_check` (
  `id` tinyint(4) NOT NULL,
  `checks` tinyint(4) NOT NULL,
  `checkStatus` tinyint(4) NOT NULL,
  `logTime` tinyint(4) NOT NULL,
  `stafferId` tinyint(4) NOT NULL,
  `unitId` tinyint(4) NOT NULL,
  `refId` tinyint(4) NOT NULL,
  `type` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `v_center_unit`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `v_center_unit` (
  `ID` tinyint(4) NOT NULL,
  `NAME` tinyint(4) NOT NULL,
  `code` tinyint(4) NOT NULL,
  `type` tinyint(4) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yhpmdz`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yhpmdz` (
  `Id` varchar(40) NOT NULL,
  `yh` varchar(40) DEFAULT NULL,
  `yhpm` varchar(50) DEFAULT NULL,
  `oapm` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiao4`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiao4` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nian` varchar(255) DEFAULT NULL,
  `yue` varchar(255) DEFAULT NULL,
  `jidu` varchar(255) DEFAULT NULL,
  `wupinmingcheng` varchar(255) DEFAULT NULL,
  `anjinhuoqudao` varchar(255) DEFAULT NULL,
  `ancaizhi` varchar(255) DEFAULT NULL,
  `shiyebu` varchar(255) DEFAULT NULL,
  `shuliang` double DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `chengben` double DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3302 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiao5`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiao5` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `riqi` date DEFAULT NULL,
  `nian` varchar(255) DEFAULT NULL,
  `yue` varchar(255) DEFAULT NULL,
  `jidu` varchar(255) DEFAULT NULL,
  `kehudaima` varchar(255) DEFAULT NULL,
  `danweimingcheng` varchar(255) DEFAULT NULL,
  `wupindaima` varchar(255) DEFAULT NULL,
  `wupinmingcheng` varchar(255) DEFAULT NULL,
  `bumen` varchar(255) DEFAULT NULL,
  `gongsi` varchar(255) DEFAULT NULL,
  `zhiyuan` varchar(255) DEFAULT NULL,
  `shuliang` double(12,2) DEFAULT NULL,
  `jine` double(12,2) DEFAULT NULL,
  `pihao` double(12,2) DEFAULT NULL,
  `chengben` double(12,2) DEFAULT NULL,
  `maoli` double(12,2) DEFAULT NULL,
  `sheng` varchar(255) DEFAULT NULL,
  `shi` varchar(255) DEFAULT NULL,
  `leibie` varchar(255) DEFAULT NULL,
  `anjinhuoqudao` varchar(255) DEFAULT NULL,
  `anzhuti` varchar(255) DEFAULT NULL,
  `anchangjia` varchar(255) DEFAULT NULL,
  `ancaizhi` varchar(255) DEFAULT NULL,
  `chengshifenji` varchar(255) DEFAULT NULL,
  `quanzhong` varchar(255) DEFAULT NULL,
  `gongzuoquyu` varchar(255) DEFAULT NULL,
  `xulie` varchar(255) DEFAULT NULL,
  `zaizhinianfen` varchar(255) DEFAULT NULL,
  `zaizhizhuangtai` varchar(255) DEFAULT NULL,
  `rusiyuefen` varchar(255) DEFAULT NULL,
  `shiyebu` varchar(255) DEFAULT NULL,
  `gonghao` varchar(255) DEFAULT NULL,
  `zhiwei` varchar(255) DEFAULT NULL,
  `zhuanzhengshijian` varchar(255) DEFAULT NULL,
  `xuhao` varchar(255) DEFAULT NULL,
  `danhao` varchar(255) DEFAULT NULL,
  `shifouzhuanzheng` varchar(255) DEFAULT NULL,
  `kehuleixing` varchar(255) DEFAULT NULL,
  `top20pinming` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1919 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiao6`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiao6` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `riqi` date DEFAULT NULL,
  `nian` varchar(255) DEFAULT NULL,
  `yue` varchar(255) DEFAULT NULL,
  `jidu` varchar(255) DEFAULT NULL,
  `kehudaima` varchar(255) DEFAULT NULL,
  `danweimingcheng` varchar(255) DEFAULT NULL,
  `wupindaima` varchar(255) DEFAULT NULL,
  `wupinmingcheng` varchar(255) DEFAULT NULL,
  `bumen` varchar(255) DEFAULT NULL,
  `gongsi` varchar(255) DEFAULT NULL,
  `zhiyuan` varchar(255) DEFAULT NULL,
  `shuliang` double(12,2) DEFAULT NULL,
  `jine` double(12,2) DEFAULT NULL,
  `pihao` double(12,2) DEFAULT NULL,
  `chengben` double(12,2) DEFAULT NULL,
  `maoli` double(12,2) DEFAULT NULL,
  `sheng` varchar(255) DEFAULT NULL,
  `shi` varchar(255) DEFAULT NULL,
  `leibie` varchar(255) DEFAULT NULL,
  `anjinhuoqudao` varchar(255) DEFAULT NULL,
  `anzhuti` varchar(255) DEFAULT NULL,
  `anchangjia` varchar(255) DEFAULT NULL,
  `ancaizhi` varchar(255) DEFAULT NULL,
  `chengshifenji` varchar(255) DEFAULT NULL,
  `quanzhong` varchar(255) DEFAULT NULL,
  `gongzuoquyu` varchar(255) DEFAULT NULL,
  `xulie` varchar(255) DEFAULT NULL,
  `zaizhinianfen` varchar(255) DEFAULT NULL,
  `zaizhizhuangtai` varchar(255) DEFAULT NULL,
  `rusiyuefen` varchar(255) DEFAULT NULL,
  `shiyebu` varchar(255) DEFAULT NULL,
  `gonghao` varchar(255) DEFAULT NULL,
  `zhiwei` varchar(255) DEFAULT NULL,
  `zhuanzhengshijian` varchar(255) DEFAULT NULL,
  `xuhao` varchar(255) DEFAULT NULL,
  `danhao` varchar(255) DEFAULT NULL,
  `shifouzhuanzheng` varchar(255) DEFAULT NULL,
  `kehuleixing` varchar(255) DEFAULT NULL,
  `top20pinming` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiaost`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiaost` (
  `shiyebu` varchar(40) DEFAULT NULL,
  `gongsi` varchar(50) DEFAULT NULL,
  `bumen` varchar(50) DEFAULT NULL,
  `zaizhinianfen` varchar(40) DEFAULT NULL,
  `zaizhizhuangtai` varchar(40) DEFAULT NULL,
  `xulie` varchar(40) DEFAULT NULL,
  `zhiwei` varchar(40) DEFAULT NULL,
  `nian` int(4) DEFAULT NULL,
  `yue` int(2) DEFAULT NULL,
  `jidu` varchar(1) DEFAULT NULL,
  `zhiyuandaima` varchar(40) DEFAULT NULL,
  `zhiyuan` varchar(200) DEFAULT NULL,
  `shuliang` decimal(33,0) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `yuanshichengben` double DEFAULT NULL,
  `yuanshimaoli` double DEFAULT NULL,
  `chengben` double DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  `sybchengben` double DEFAULT NULL,
  `sybmaoli` double DEFAULT NULL,
  `zbchengben` double DEFAULT NULL,
  `zbmaoli` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiaost2`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiaost2` (
  `shiyebu` varchar(40) DEFAULT NULL,
  `gongsi` varchar(50) DEFAULT NULL,
  `bumen` varchar(50) DEFAULT NULL,
  `zaizhinianfen` varchar(40) DEFAULT NULL,
  `zaizhizhuangtai` varchar(40) DEFAULT NULL,
  `xulie` varchar(40) DEFAULT NULL,
  `zhiwei` varchar(40) DEFAULT NULL,
  `nian` int(4) DEFAULT NULL,
  `yue` int(2) DEFAULT NULL,
  `jidu` varchar(1) DEFAULT NULL,
  `zhiyuandaima` varchar(40) DEFAULT NULL,
  `zhiyuan` varchar(200) DEFAULT NULL,
  `shuliang` decimal(33,0) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `yuanshichengben` double DEFAULT NULL,
  `yuanshimaoli` double DEFAULT NULL,
  `chengben` double DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  `sybchengben` double DEFAULT NULL,
  `sybmaoli` double DEFAULT NULL,
  `zbchengben` double DEFAULT NULL,
  `zbmaoli` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiaost4`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiaost4` (
  `shiyebu` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `gongsi` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `bumen` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `zaizhinianfen` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zaizhizhuangtai` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `xulie` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zhiwei` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `nian` int(4) DEFAULT NULL,
  `yue` int(2) DEFAULT NULL,
  `jidu` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `zhiyuandaima` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zhiyuan` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `shuliang` decimal(33,0) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `yuanshichengben` double DEFAULT NULL,
  `yuanshimaoli` double DEFAULT NULL,
  `chengben` double DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  `sybchengben` double DEFAULT NULL,
  `sybmaoli` double DEFAULT NULL,
  `zbchengben` double DEFAULT NULL,
  `zbmaoli` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiaost5`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiaost5` (
  `shiyebu` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `gongsi` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `bumen` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `zaizhinianfen` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zaizhizhuangtai` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `xulie` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zhiwei` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `nian` int(4) DEFAULT NULL,
  `yue` int(2) DEFAULT NULL,
  `jidu` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `zhiyuandaima` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zhiyuan` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `shuliang` decimal(33,0) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `yuanshichengben` double DEFAULT NULL,
  `yuanshimaoli` double DEFAULT NULL,
  `chengben` double DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  `sybchengben` double DEFAULT NULL,
  `sybmaoli` double DEFAULT NULL,
  `zbchengben` double DEFAULT NULL,
  `zbmaoli` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yuanbiaost6`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yuanbiaost6` (
  `shiyebu` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `gongsi` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `bumen` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `zaizhinianfen` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zaizhizhuangtai` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `xulie` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zhiwei` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `nian` int(4) DEFAULT NULL,
  `yue` int(2) DEFAULT NULL,
  `jidu` varchar(1) CHARACTER SET utf8 DEFAULT NULL,
  `zhiyuandaima` varchar(40) CHARACTER SET utf8 DEFAULT NULL,
  `zhiyuan` varchar(200) CHARACTER SET utf8 DEFAULT NULL,
  `shuliang` decimal(33,0) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `yuanshichengben` double DEFAULT NULL,
  `yuanshimaoli` double DEFAULT NULL,
  `chengben` double DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  `sybchengben` double DEFAULT NULL,
  `sybmaoli` double DEFAULT NULL,
  `zbchengben` double DEFAULT NULL,
  `zbmaoli` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `yusuanduizhaobiao`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `yusuanduizhaobiao` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `daima` varchar(255) DEFAULT NULL,
  `gongsi` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zhibiao`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zhibiao` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `shiyebu` varchar(255) DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  `feiyong` double DEFAULT NULL,
  `jingli` double DEFAULT NULL,
  `yue` varchar(255) DEFAULT NULL,
  `jidu` varchar(255) DEFAULT NULL,
  `nian` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zhiyuanduizhaobiao`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zhiyuanduizhaobiao` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `daima` varchar(40) DEFAULT NULL,
  `xingming` varchar(200) DEFAULT NULL,
  `bumen` varchar(50) DEFAULT NULL,
  `gongsi` varchar(50) DEFAULT NULL,
  `leibie` varchar(40) DEFAULT NULL,
  `zaizhinianfen` varchar(40) DEFAULT NULL,
  `zaizhizhuangtai` varchar(40) DEFAULT NULL,
  `rusishijian` varchar(40) DEFAULT NULL,
  `gongzuoquyu` varchar(40) DEFAULT NULL,
  `xulie` varchar(40) DEFAULT NULL,
  `shiyebu` varchar(40) DEFAULT NULL,
  `gonghao` varchar(40) DEFAULT NULL,
  `zhiwei` varchar(40) DEFAULT NULL,
  `zhuanzhengshijian` varchar(40) DEFAULT NULL,
  `shifouzhuanzheng` varchar(40) DEFAULT NULL,
  `lizhishijian` varchar(40) DEFAULT NULL,
  `zhongxin` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `ind_daima` (`daima`)
) ENGINE=InnoDB AUTO_INCREMENT=806 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zhongshou`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zhongshou` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL,
  `jine` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10170271 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zhongxinzl`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zhongxinzl` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `1jifenhang` varchar(255) DEFAULT NULL,
  `2jifenhang` varchar(255) DEFAULT NULL,
  `zhihang` varchar(255) DEFAULT NULL,
  `xiuzhengzhihang` varchar(255) DEFAULT NULL,
  `shengshi` varchar(255) DEFAULT NULL,
  `xiaoshourenyuan` varchar(255) DEFAULT NULL,
  `quyu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=1110 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zhongxinzl2`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zhongxinzl2` (
  `Id` int(11) NOT NULL DEFAULT '0',
  `1jifenhang` varchar(255) DEFAULT NULL,
  `2jifenhang` varchar(255) DEFAULT NULL,
  `zhihang` varchar(255) DEFAULT NULL,
  `xiuzhengzhihang` varchar(255) DEFAULT NULL,
  `shengshi` varchar(255) DEFAULT NULL,
  `xiaoshourenyuan` varchar(255) DEFAULT NULL,
  `quyu` varchar(255) DEFAULT NULL,
  `bieming` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zhongxinzl3`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zhongxinzl3` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `1jifenhang` varchar(255) DEFAULT NULL,
  `2jifenhang` varchar(255) DEFAULT NULL,
  `zhihang` varchar(255) DEFAULT NULL,
  `xiuzhengzhihang` varchar(255) DEFAULT NULL,
  `shengshi` varchar(255) DEFAULT NULL,
  `xiaoshourenyuan` varchar(255) DEFAULT NULL,
  `quyu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=403 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zsduizhaobiao`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zsduizhaobiao` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `zhiyuan1` varchar(255) DEFAULT NULL,
  `zhiyuan2` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zsrbb`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zsrbb` (
  `xiaoshouriqi` date DEFAULT NULL,
  `wangdianmingcheng` varchar(255) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `shangpinmingcheng` varchar(255) DEFAULT NULL,
  `shuliang` double DEFAULT NULL,
  `zhongshoudanjia` varchar(255) DEFAULT NULL,
  `1jifenhang` varchar(255) DEFAULT NULL,
  `2jifenhang` varchar(255) DEFAULT NULL,
  `zhihang` varchar(255) DEFAULT NULL,
  `shengshi` varchar(255) DEFAULT NULL,
  `xiaoshourenyuan` varchar(255) DEFAULT NULL,
  `quyu` varchar(255) DEFAULT NULL,
  `quanzhong` varchar(255) DEFAULT NULL,
  `maoli` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zsrbb2`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zsrbb2` (
  `xiaoshouriqi` date DEFAULT NULL,
  `wangdianmingcheng` varchar(255) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `shangpinmingcheng` varchar(255) DEFAULT NULL,
  `shuliang` double DEFAULT NULL,
  `zhongshoudanjia` varchar(255) DEFAULT NULL,
  `1jifenhang` varchar(255) DEFAULT NULL,
  `2jifenhang` varchar(255) DEFAULT NULL,
  `zhihang` varchar(255) DEFAULT NULL,
  `shengshi` varchar(255) DEFAULT NULL,
  `xiaoshourenyuan` varchar(255) DEFAULT NULL,
  `quyu` varchar(255) DEFAULT NULL,
  `bieming` varchar(255) DEFAULT NULL,
  `quanzhong` varchar(255) DEFAULT NULL,
  `maoli` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zsrbb3`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zsrbb3` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `yue` varchar(255) DEFAULT NULL,
  `xiaoshourenyuan` varchar(255) DEFAULT NULL,
  `shengshi` varchar(255) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `maoli` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zsrbb4`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zsrbb4` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `yue` varchar(255) DEFAULT NULL,
  `1jifenhang` varchar(255) DEFAULT NULL,
  `zhiyuan` varchar(255) DEFAULT NULL,
  `quanzhong` varchar(255) DEFAULT NULL,
  `jine` varchar(255) DEFAULT NULL,
  `maoli` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zsrbb5`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zsrbb5` (
  `xiaoshouriqi` date DEFAULT NULL,
  `wangdianmingcheng` varchar(255) DEFAULT NULL,
  `jine` double DEFAULT NULL,
  `shangpinmingcheng` varchar(255) DEFAULT NULL,
  `shuliang` double DEFAULT NULL,
  `zhongshoudanjia` varchar(255) DEFAULT NULL,
  `1jifenhang` varchar(255) DEFAULT NULL,
  `2jifenhang` varchar(255) DEFAULT NULL,
  `zhihang` varchar(255) DEFAULT NULL,
  `shengshi` varchar(255) DEFAULT NULL,
  `xiaoshourenyuan` varchar(255) DEFAULT NULL,
  `quyu` varchar(255) DEFAULT NULL,
  `quanzhong` varchar(255) DEFAULT NULL,
  `maoli` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zx1`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zx1` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `quyu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zx4`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zx4` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `1jifenhang` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zx5`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zx5` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `shengshi` varchar(255) DEFAULT NULL,
  `quyu` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zx6`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zx6` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `shengshi` varchar(255) DEFAULT NULL,
  `1jifenhang` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `zx7`
--

/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `zx7` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `zhiyuan` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-14 10:42:47
