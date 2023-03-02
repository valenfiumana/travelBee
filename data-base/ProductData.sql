-- MySQL dump 10.13  Distrib 8.0.27, for macos11 (x86_64)
--
-- Host: localhost    Database: integrador
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'','https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/categories/viewAll.svg','Ver todo'),(2,'Hermosas cabañas.','https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/categories/cabanas.jpeg','Cabañas'),(3,'Hermosas islas.','https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/categories/islas.jpeg','Islas'),(4,'Hermosos castillos medievales para que puedas sentirte dentro de una película de la Edad Media.','https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/categories/castillos.jpeg','Castillos'),(5,'Hermosos barcos.','https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/categories/barcos.jpeg','Barcos');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `cities`
--

LOCK TABLES `cities` WRITE;
/*!40000 ALTER TABLE `cities` DISABLE KEYS */;
INSERT INTO `cities` VALUES (1,'Estados Unidos','Rockbridge, Ohio'),(2,'Colombia','Cartagena, Bolívar'),(3,'Alemania','Serrig, Rheinlan'),(4,'Italia','Pescara, Abruzzo');
/*!40000 ALTER TABLE `cities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `features`
--

LOCK TABLES `features` WRITE;
/*!40000 ALTER TABLE `features` DISABLE KEYS */;
INSERT INTO `features` VALUES (1,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/1.svg','Aire acondicionado'),(2,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/2.svg','Calefacción'),(3,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/3.svg','Cocina'),(4,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/4.svg','Estacionamiento'),(5,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/5.svg','Wifi'),(6,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/6.svg','Televisión'),(7,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/7.svg','Lavarropas'),(8,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/8.svg','Heladera'),(9,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/9.svg','Acceso a playa'),(10,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/10.svg','Acceso a lago'),(11,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/11.svg','Pet friendly'),(12,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/12.svg','Vista panorámica'),(13,'https://travel-bee-images.s3.us-east-2.amazonaws.com/icons/amenities/13.svg','Cámaras');
/*!40000 ALTER TABLE `features` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,1,'1','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/mainCabin1.webp'),(2,1,'2','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/dfaa8d12-f3cf-48d7-b0bd-b7f923c7789a.webp'),(3,1,'3','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/b1726498-95c5-47dd-829a-f23e8aacd93a.webp'),(4,1,'4','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/91ea4bc6-72b2-49d9-9da3-34c11aed5181.webp'),(5,1,'5','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/4e911a89-23ca-4c53-afbf-6783b998bdbb.webp'),(6,1,'6','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/3f319f71-f769-4616-a60f-389436e1aa44.webp'),(7,1,'7','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/24a05e1f-1b03-43bc-a2d5-c757e8dbdea1.webp'),(8,1,'8','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/cabin1/06e6d8a0-6256-475b-a4e7-b3b4c7934fbb.webp'),(9,2,'1','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/mainIsland1.webp'),(10,2,'2','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/f1f4f807-b77f-4223-98f0-3afaa11520f6.webp'),(11,2,'3','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/9b06d931-4837-46b2-acc3-f5c4d44fc979.webp'),(12,2,'4','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/7eca5837-9d44-4b76-851a-bcb284a9e0f5.webp'),(13,2,'5','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/6d6e7241-7113-4009-9d30-a65dfd9bac8c.webp'),(14,2,'6','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/2d3bdedf-6ffc-4c17-9a3a-a6cb02e2974f.webp'),(15,2,'7','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/21456fac-8f90-4655-a25c-b0a5a1d51481.webp'),(16,2,'8','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/island1/02f03e7a-59e4-40a9-b088-71021603fc66.webp'),(17,3,'1','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/mainCastle1.webp'),(18,3,'2','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/fa23489b-f64f-4860-96ae-5bf18073cb2a.webp'),(19,3,'3','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/cc931754-1705-4db5-a213-c2b9aa1147a8.webp'),(20,3,'4','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/c985d6ac-5b44-4f1f-87ce-c4abfc8bd28b.webp'),(21,3,'5','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/c61cba83-6e88-4df3-b3c2-3471a1756ace.webp'),(22,3,'6','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/ab6f9c48-4946-4d6a-b28f-519d930a8536.webp'),(23,3,'7','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/7312cb52-9cd8-40c5-93e1-4a3f329d6b5c.webp'),(24,3,'8','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/castle1/58a4ee47-5bcc-4ed2-a0fd-1074bc77333d.webp'),(25,4,'1','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/mainBoat1.png'),(26,4,'2','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/db2d66fb-3c14-41bc-b033-7ecab281db02.webp'),(27,4,'3','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/bd2d2f29-a845-474f-ab59-e80c9ecf1257.webp'),(28,4,'4','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/7bd42789-da1c-4d3a-8a61-c489ac289f12.webp'),(29,4,'5','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/4bb73809-cb7c-4931-aa7f-2983c917eb40.webp'),(30,4,'6','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/4b3e1d10-fc63-411a-823f-64536dfded78.webp'),(31,4,'7','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/25eb159f-caa0-4486-9ffd-f9110b969ceb.webp'),(32,4,'8','https://travel-bee-images.s3.us-east-2.amazonaws.com/products/boat1/2158727c-3a38-43bb-bfca-43e3df6729f7.webp');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `policies`
--

LOCK TABLES `policies` WRITE;
/*!40000 ALTER TABLE `policies` DISABLE KEYS */;
/*!40000 ALTER TABLE `policies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'El A-Frame hueco Dunlap es un A-Frame de lujo personalizado completado en 2021. El A-Frame tiene capacidad para 10 personas con 3 dormitorios y un pintoresco loft lleno de ventanas con capacidad para 4 personas.',-82.525258,39.579535,5000,'Cabaña del bosque',2,1),(2,'Disfruta de nuestra exclusiva isla privada ubicada en las Islas del Rosario. El lugar tiene un estilo tropical que combina con el hermoso paisaje. Disfruta de nuestro Oasis.',-75.758898,10.177181,1344,'Isla Mar Azul',3,2),(3,'Fantástico castillo bien conservado en el Moselle con áreas verdes y viñedos en Serrig cerca de Trier en el Moselle. Perfecto para una familia numerosa, un grupo de varias parejas, familias o amigos',6.577559,49.577394,931,'Castillo Moselle',4,3),(4,'Estilo inigualable, comodidad extraordinaria. Dos cabinas dobles con baño privado y ducha para uso exclusivo. Dos cabinas triples con baño privado y ducha para uso exclusivo. Aire acondicionado',14.232678,42.468044,719,'Ketch 80 pies, Lady Q',5,4);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products_have_features`
--

LOCK TABLES `products_have_features` WRITE;
/*!40000 ALTER TABLE `products_have_features` DISABLE KEYS */;
INSERT INTO `products_have_features` VALUES (2,1),(2,3),(2,5),(2,7),(2,8),(2,9),(2,11),(2,12),(3,2),(3,3),(3,4),(3,5),(3,6),(3,7),(3,8),(3,10),(3,11),(3,12),(3,13),(4,2),(4,3),(4,6),(4,8),(4,9),(4,10),(4,12),(1,2),(1,3),(1,5),(1,7),(1,8),(1,10),(1,11),(1,12);
/*!40000 ALTER TABLE `products_have_features` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `products_have_policies`
--

LOCK TABLES `products_have_policies` WRITE;
/*!40000 ALTER TABLE `products_have_policies` DISABLE KEYS */;
/*!40000 ALTER TABLE `products_have_policies` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-10 23:30:15
