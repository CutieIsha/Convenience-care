-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: cc_care
-- ------------------------------------------------------
-- Server version	8.0.39

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
-- Table structure for table `patient_health_records`
--

DROP TABLE IF EXISTS `patient_health_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_health_records` (
  `id` int NOT NULL AUTO_INCREMENT,
  `patient_email` varchar(255) DEFAULT NULL,
  `health_condition` varchar(255) DEFAULT NULL,
  `treatment` varchar(255) DEFAULT NULL,
  `medication` varchar(255) DEFAULT NULL,
  `dosage` varchar(50) DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `end_date` date DEFAULT NULL,
  `follow_up_date` date DEFAULT NULL,
  `doctor_name` varchar(100) DEFAULT NULL,
  `clinic_name` varchar(100) DEFAULT NULL,
  `allergies` varchar(255) DEFAULT NULL,
  `notes` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `patient_email` (`patient_email`),
  CONSTRAINT `patient_health_records_ibfk_1` FOREIGN KEY (`patient_email`) REFERENCES `cc_patients` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_health_records`
--

LOCK TABLES `patient_health_records` WRITE;
/*!40000 ALTER TABLE `patient_health_records` DISABLE KEYS */;
INSERT INTO `patient_health_records` VALUES (8,'minakumari@gmail.com','Hypertension','Follow proper diet','Crocin','5mg daily','2024-09-26','2024-10-26','2024-11-01','Dr. Anil Kumar','Sunshine Health Clinic','Penicillin','Patient advised to monitor blood pressure regularly.','2024-09-27 16:56:45','2024-09-27 16:56:45'),(10,'srinivas@gmail.com','Hypertension','Follow proper diet','Crocin','5mg daily','2024-09-26','2024-10-26','2024-11-01','Dr. Anil Kumar','Sunshine Health Clinic','Penicillin','Patient advised to monitor blood pressure regularly.','2024-09-28 03:54:57','2024-09-28 03:54:57'),(11,'srinivas@gmail.com','Hypertension','Follow proper diet','Aspirin','5mg daily','2024-10-26','2024-11-25','2024-12-01','Dr. Anil Kumar','Sunshine Health Clinic','Penicillin','Patient advised to monitor blood pressure regularly.','2024-10-02 14:42:11','2024-10-02 14:42:11');
/*!40000 ALTER TABLE `patient_health_records` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-06 21:18:40
