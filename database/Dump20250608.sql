-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: clinicamedica
-- ------------------------------------------------------
-- Server version	8.0.42

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `especialidad`
--

DROP TABLE IF EXISTS `especialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `especialidad` (
  `EspecialidadID` int NOT NULL AUTO_INCREMENT,
  `MedicoID` int DEFAULT NULL,
  `Nombre` varchar(100) DEFAULT NULL,
  `Descripcion` text,
  PRIMARY KEY (`EspecialidadID`),
  KEY `fk_medico_especialidad` (`MedicoID`),
  CONSTRAINT `fk_medico_especialidad` FOREIGN KEY (`MedicoID`) REFERENCES `medico` (`MedicoID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `especialidad`
--

LOCK TABLES `especialidad` WRITE;
/*!40000 ALTER TABLE `especialidad` DISABLE KEYS */;
INSERT INTO `especialidad` VALUES (1,1,'Ginecología','Especialidad de Ginecología'),(2,2,'Hepatología','Especialidad de Hepatología'),(3,3,'Neurología','Especialidad de Neurología'),(4,4,'Clínica Médica','Especialidad de Clínica Médica'),(9,NULL,'Cardiología','Especialidad de Cardiología'),(10,NULL,'Oculista','Especialidad de los ojos');
/*!40000 ALTER TABLE `especialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `historiaclinica`
--

DROP TABLE IF EXISTS `historiaclinica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historiaclinica` (
  `HistoriaClinicaID` int NOT NULL,
  `PacienteID` int DEFAULT NULL,
  `MedicoID` int DEFAULT NULL,
  `Fecha` date DEFAULT NULL,
  `Descripcion` text,
  PRIMARY KEY (`HistoriaClinicaID`),
  KEY `fk_historia_medico` (`MedicoID`),
  KEY `historiaclinica_ibfk_1` (`PacienteID`),
  CONSTRAINT `fk_historia_medico` FOREIGN KEY (`MedicoID`) REFERENCES `medico` (`MedicoID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `historiaclinica_ibfk_1` FOREIGN KEY (`PacienteID`) REFERENCES `paciente` (`PacienteID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historiaclinica`
--

LOCK TABLES `historiaclinica` WRITE;
/*!40000 ALTER TABLE `historiaclinica` DISABLE KEYS */;
INSERT INTO `historiaclinica` VALUES (1,1,1,'2025-06-05','Consulta de rutina paciente 1.'),(2,2,2,'2025-06-06','Consulta de rutina paciente 2.'),(3,3,3,'2025-06-07','Consulta de rutina paciente 3.'),(4,4,1,'2025-06-08','Consulta de rutina paciente 4.'),(5,5,2,'2025-06-09','Consulta de rutina paciente 5.');
/*!40000 ALTER TABLE `historiaclinica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `horariomedico`
--

DROP TABLE IF EXISTS `horariomedico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `horariomedico` (
  `MedicoID` int NOT NULL,
  `Dia` enum('Lunes','Martes','Miércoles','Jueves','Viernes','Sábado','Domingo') NOT NULL,
  `HoraInicio` time NOT NULL,
  `HoraFin` time NOT NULL,
  PRIMARY KEY (`MedicoID`,`Dia`),
  CONSTRAINT `fk_horario_medico` FOREIGN KEY (`MedicoID`) REFERENCES `medico` (`MedicoID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `horariomedico`
--

LOCK TABLES `horariomedico` WRITE;
/*!40000 ALTER TABLE `horariomedico` DISABLE KEYS */;
INSERT INTO `horariomedico` VALUES (1,'Martes','08:00:00','14:00:00'),(1,'Jueves','08:00:00','14:00:00'),(1,'Viernes','08:00:00','14:00:00'),(2,'Martes','08:00:00','12:00:00'),(2,'Jueves','08:00:00','14:00:00'),(2,'Viernes','08:00:00','17:00:00'),(3,'Martes','08:00:00','14:00:00'),(3,'Jueves','08:00:00','14:00:00'),(3,'Viernes','08:00:00','14:00:00'),(4,'Miércoles','13:00:00','21:00:00'),(4,'Viernes','13:00:00','21:00:00'),(5,'Lunes','17:00:00','21:00:00'),(5,'Miércoles','17:00:00','21:00:00'),(6,'Martes','07:00:00','13:00:00'),(6,'Jueves','07:00:00','13:00:00'),(7,'Lunes','08:00:00','13:00:00'),(7,'Miércoles','10:00:00','15:00:00'),(7,'Viernes','09:00:00','17:00:00'),(9,'Martes','09:00:00','17:00:00'),(9,'Jueves','09:00:00','17:00:00'),(10,'Lunes','09:00:00','17:00:00'),(10,'Martes','09:00:00','17:00:00'),(11,'Lunes','17:00:00','22:00:00'),(11,'Miércoles','09:00:00','13:00:00'),(12,'Jueves','09:00:00','17:00:00'),(12,'Viernes','09:00:00','17:00:00'),(13,'Lunes','18:00:00','22:00:00'),(13,'Jueves','18:00:00','22:00:00'),(14,'Lunes','09:00:00','17:00:00'),(14,'Martes','09:00:00','17:00:00'),(14,'Jueves','09:00:00','17:00:00'),(14,'Viernes','09:00:00','17:00:00'),(15,'Lunes','08:00:00','13:00:00'),(15,'Martes','08:00:00','13:00:00'),(15,'Jueves','08:00:00','13:00:00'),(15,'Viernes','08:00:00','13:00:00'),(16,'Lunes','11:00:00','17:00:00'),(16,'Miércoles','11:00:00','17:00:00');
/*!40000 ALTER TABLE `horariomedico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medico`
--

DROP TABLE IF EXISTS `medico`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medico` (
  `MedicoID` int NOT NULL AUTO_INCREMENT,
  `DNI` int DEFAULT NULL,
  `Matricula` varchar(50) DEFAULT NULL,
  `DiaLaboral` varchar(100) DEFAULT NULL,
  `HorarioLaboral` varchar(100) DEFAULT NULL,
  `Usuario` varchar(50) DEFAULT NULL,
  `Contrasenia` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`MedicoID`),
  KEY `DNI` (`DNI`),
  CONSTRAINT `medico_ibfk_1` FOREIGN KEY (`DNI`) REFERENCES `persona` (`DNI`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medico`
--

LOCK TABLES `medico` WRITE;
/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` VALUES (1,90247685,'MAT1','Martes, Jueves, Viernes','08:00-14:00','user_doc1','pass1'),(2,71670897,'MAT2','Martes, Jueves, Viernes','08:00-14:00','user_doc2','pass2'),(3,70099880,'MAT3','Martes, Jueves, Viernes','08:00-14:00','user_doc3','pass3'),(4,30587624,'MAT4','Miercoles, Viernes','13:00-21:00','user_doc4','pass4'),(5,35157863,'MAT5','Lunes, Miercoles','17:00-21:00','user_doc5','pass5'),(6,37655987,'MAT6','Martes, Jueves','07:00-13:00','user_doc6','pass6'),(7,40841562,'MAT7','Lunes, Martes','14:00-19:00','user_7','pass7'),(9,36458201,'MAT8','Lunes, Miercoles, Jueves','15:30-20:00','user_8','pass8'),(10,13703810,'MAT59','Martes, Jueves','17:30-21:00','user_59','pass59'),(11,8036091,'MAT50','Luenes, Miercoles, Viernes','8:00-12:30','user50','pass50'),(12,27541258,'MAT95','Martes, Jueves, Viernes','15:00-21:00','user95','pass95'),(13,8036092,'MAT55','Lunes, Jueves','18:00 - 22:00','user_55','pass55'),(14,16554135,'MAT64','Miercoles, Viernes','08:00:00 - 12:00:00','user64','pass64'),(15,16855729,'MAT91','Lunes,Martes,Jueves,Viernes','08:00 - 13:00','user91','pass91'),(16,36521852,'MAT993','Lunes, Miercoles','11:00 - 17:00','user993','pass993');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicoespecialidad`
--

DROP TABLE IF EXISTS `medicoespecialidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicoespecialidad` (
  `MedicoID` int NOT NULL,
  `EspecialidadID` int NOT NULL,
  PRIMARY KEY (`MedicoID`,`EspecialidadID`),
  KEY `EspecialidadID` (`EspecialidadID`),
  CONSTRAINT `medicoespecialidad_ibfk_1` FOREIGN KEY (`MedicoID`) REFERENCES `medico` (`MedicoID`),
  CONSTRAINT `medicoespecialidad_ibfk_2` FOREIGN KEY (`EspecialidadID`) REFERENCES `especialidad` (`EspecialidadID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicoespecialidad`
--

LOCK TABLES `medicoespecialidad` WRITE;
/*!40000 ALTER TABLE `medicoespecialidad` DISABLE KEYS */;
INSERT INTO `medicoespecialidad` VALUES (2,1),(7,1),(1,2),(10,2),(3,3),(13,3),(6,4),(12,4),(16,4),(4,9),(11,9),(15,9),(5,10),(9,10),(14,10);
/*!40000 ALTER TABLE `medicoespecialidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paciente`
--

DROP TABLE IF EXISTS `paciente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paciente` (
  `PacienteID` int NOT NULL AUTO_INCREMENT,
  `DNI` int DEFAULT NULL,
  `ObraSocial` varchar(100) DEFAULT NULL,
  `NumeroAfiliado` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`PacienteID`),
  KEY `paciente_ibfk_1` (`DNI`),
  CONSTRAINT `paciente_ibfk_1` FOREIGN KEY (`DNI`) REFERENCES `persona` (`DNI`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paciente`
--

LOCK TABLES `paciente` WRITE;
/*!40000 ALTER TABLE `paciente` DISABLE KEYS */;
INSERT INTO `paciente` VALUES (1,8028123,'SancorSalud','AFIL-1001'),(2,12759284,'Prensa','AFIL-1002'),(3,5931607,'OSDE','AFIL-1003'),(4,51267442,'Subsidio de salud','AFIL-1004'),(5,46111397,'SancorSalud','AFIL-1005');
/*!40000 ALTER TABLE `paciente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `PagoID` int NOT NULL,
  `TurnoID` int DEFAULT NULL,
  `Cantidad` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`PagoID`),
  KEY `fk_turno` (`TurnoID`),
  CONSTRAINT `fk_turno` FOREIGN KEY (`TurnoID`) REFERENCES `turno` (`TurnoID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pago`
--

LOCK TABLES `pago` WRITE;
/*!40000 ALTER TABLE `pago` DISABLE KEYS */;
INSERT INTO `pago` VALUES (1,NULL,5000.00);
/*!40000 ALTER TABLE `pago` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persona`
--

DROP TABLE IF EXISTS `persona`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persona` (
  `DNI` int NOT NULL,
  `Nombre` varchar(100) DEFAULT NULL,
  `Apellido` varchar(100) DEFAULT NULL,
  `FechaNacimiento` date DEFAULT NULL,
  `Telefono` varchar(20) DEFAULT NULL,
  `Genero` varchar(10) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`DNI`),
  CONSTRAINT `persona_chk_1` CHECK ((`DNI` between 1000000 and 99999999))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persona`
--

LOCK TABLES `persona` WRITE;
/*!40000 ALTER TABLE `persona` DISABLE KEYS */;
INSERT INTO `persona` VALUES (5931607,'María José','Mayol','1982-09-30','+34873989506','Femenino','qvinas@yahoo.com'),(8028123,'Julio','Bonet','1987-02-02','810 68 85 50','Masculino','nicolasmugica@higueras.es'),(8036091,'Agustin','Rodriguez','1950-11-10','381467875','Masculino','agustin@gmail.com'),(8036092,'Agustin','Rodriguez','1955-10-11','3816542587','Femenino','agustina@hotmail.com'),(12345678,'Pablo','Virgolini','1980-01-01','1234567890','Masculino','pablo@example.com'),(12759284,'Selena','Pina','1964-02-17','+34 957 101 220','Femenino','qtapia@yahoo.com'),(13703810,'Ana','Talavera','1959-12-03','3814153504','Femenino','ana_1959@gmail.com'),(16554135,'Silvia','Medina','1964-03-28','3815428724','Femenino','silgar@hotmail.com'),(16855729,'Cesar','Parache','1991-12-01','3865695512','Masculino','cesarp@hotmail.com'),(27541258,'Diana','Elias','1995-01-30','3865495528','Femenino','diana@gmail.com'),(30587624,'Alejandro','Rosales','1980-04-28','547628165','Masculino','alejandror@hotmail.com'),(35157863,'Salome','Garcia','1990-11-30','5468853','Femenino','salo90@hotmail.com'),(36458201,'Marian','Dominguez','1987-09-25','3814153506','Masculino','marian@hotmail.com'),(36521852,'Daniel','Diaz','1993-05-14','38562485','Masculino','danieldiaz@gmail.com'),(37655987,'Anita','Diaz','1993-07-26','54415875','Femenino','anitadiaz@hotmail.com'),(40841562,'Sol','Martinez','1995-08-26','3865467752','Femenino','solmar97@live.com.ar'),(46111397,'Alcides','Fajardo','1999-04-08','+34 735 053 688','Femenino','ana-belen38@elias-corral.es'),(51267442,'Arcelia','Antón','1971-09-22','+34741 74 25 58','Femenino','sauraroldan@hotmail.com'),(70099880,'Daniel','Ferré','1966-12-16','605390757','Masculino','naranjolara@hotmail.com'),(71670897,'Bautista','Torrente','1980-07-30','+34 957 06 95 77','Masculino','jdonaire@gmail.com'),(72722748,'Ramona','Espejo','1984-03-13','657901381','Femenino','valle.castillo@carmona.net'),(78102066,'Baldomero','García','1987-03-08','+34 972 95 82 30','Femenino','mariadelvalle66@hotmail.com'),(90247685,'Rosa','Aroca','1998-03-28','602043360','Masculino','areina@hotmail.com');
/*!40000 ALTER TABLE `persona` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `secretario`
--

DROP TABLE IF EXISTS `secretario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `secretario` (
  `SecretarioID` int NOT NULL,
  `DNI` int DEFAULT NULL,
  `Usuario` varchar(50) DEFAULT NULL,
  `Contrasenia` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`SecretarioID`),
  KEY `DNI` (`DNI`),
  CONSTRAINT `secretario_ibfk_1` FOREIGN KEY (`DNI`) REFERENCES `persona` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `secretario`
--

LOCK TABLES `secretario` WRITE;
/*!40000 ALTER TABLE `secretario` DISABLE KEYS */;
INSERT INTO `secretario` VALUES (1,72722748,'user_sec1','pass1'),(2,78102066,'user_sec2','pass2'),(3,12345678,'pablo','1234');
/*!40000 ALTER TABLE `secretario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `turno`
--

DROP TABLE IF EXISTS `turno`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `turno` (
  `TurnoID` int NOT NULL AUTO_INCREMENT,
  `PacienteID` int DEFAULT NULL,
  `MedicoID` int DEFAULT NULL,
  `SecretarioID` int DEFAULT NULL,
  `PagoID` int DEFAULT NULL,
  `FechaProgramada` date DEFAULT NULL,
  `HoraProgramada` time DEFAULT NULL,
  PRIMARY KEY (`TurnoID`),
  KEY `SecretarioID` (`SecretarioID`),
  KEY `PagoID` (`PagoID`),
  KEY `fk_turno_medico` (`MedicoID`),
  KEY `turno_ibfk_1` (`PacienteID`),
  CONSTRAINT `fk_turno_medico` FOREIGN KEY (`MedicoID`) REFERENCES `medico` (`MedicoID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `turno_ibfk_1` FOREIGN KEY (`PacienteID`) REFERENCES `paciente` (`PacienteID`),
  CONSTRAINT `turno_ibfk_3` FOREIGN KEY (`SecretarioID`) REFERENCES `secretario` (`SecretarioID`),
  CONSTRAINT `turno_ibfk_4` FOREIGN KEY (`PagoID`) REFERENCES `pago` (`PagoID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `turno`
--

LOCK TABLES `turno` WRITE;
/*!40000 ALTER TABLE `turno` DISABLE KEYS */;
INSERT INTO `turno` VALUES (2,2,2,2,NULL,'2025-06-11','10:00:00'),(5,5,2,1,NULL,'2025-06-14','10:00:00'),(6,1,5,NULL,NULL,'2025-06-11','19:00:00'),(7,2,9,NULL,NULL,'2025-06-26','11:30:00'),(10,1,14,3,NULL,'2025-06-19','11:00:00'),(12,1,5,3,NULL,'2025-06-11','10:00:00');
/*!40000 ALTER TABLE `turno` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'clinicamedica'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-08 11:28:26
