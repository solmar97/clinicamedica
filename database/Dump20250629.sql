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
-- Table structure for table `historiaclinica`
--

DROP TABLE IF EXISTS `historiaclinica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historiaclinica` (
  `HistoriaClinicaID` int NOT NULL AUTO_INCREMENT,
  `PacienteID` int DEFAULT NULL,
  `MedicoID` int DEFAULT NULL,
  `Fecha` date NOT NULL,
  `Descripcion` text,
  `Atendido` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`HistoriaClinicaID`),
  UNIQUE KEY `uq_historia_paciente_fecha` (`PacienteID`,`Fecha`),
  KEY `fk_historia_medico` (`MedicoID`),
  CONSTRAINT `fk_historia_medico` FOREIGN KEY (`MedicoID`) REFERENCES `medico` (`MedicoID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_historia_paciente` FOREIGN KEY (`PacienteID`) REFERENCES `paciente` (`PacienteID`),
  CONSTRAINT `historiaclinica_ibfk_1` FOREIGN KEY (`PacienteID`) REFERENCES `paciente` (`PacienteID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `pago`
--

DROP TABLE IF EXISTS `pago`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pago` (
  `PagoID` int NOT NULL AUTO_INCREMENT,
  `TurnoID` int DEFAULT NULL,
  `Cantidad` decimal(10,2) DEFAULT NULL,
  `PacienteID` int DEFAULT NULL,
  `Monto` decimal(10,2) DEFAULT NULL,
  `FechaPago` date DEFAULT NULL,
  `DescuentoAplicado` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`PagoID`),
  KEY `fk_pago_turno` (`TurnoID`),
  CONSTRAINT `fk_pago_turno` FOREIGN KEY (`TurnoID`) REFERENCES `turno` (`TurnoID`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

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
  `Atendido` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`TurnoID`),
  KEY `SecretarioID` (`SecretarioID`),
  KEY `PagoID` (`PagoID`),
  KEY `fk_turno_medico` (`MedicoID`),
  KEY `turno_ibfk_1` (`PacienteID`),
  CONSTRAINT `fk_turno_medico` FOREIGN KEY (`MedicoID`) REFERENCES `medico` (`MedicoID`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `turno_ibfk_1` FOREIGN KEY (`PacienteID`) REFERENCES `paciente` (`PacienteID`),
  CONSTRAINT `turno_ibfk_3` FOREIGN KEY (`SecretarioID`) REFERENCES `secretario` (`SecretarioID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-29 21:00:18

