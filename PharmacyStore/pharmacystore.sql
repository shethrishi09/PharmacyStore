-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 30, 2024 at 07:05 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.1.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `pharmacystore`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `getMedicine` ()   BEGIN
select * from medicines;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `getPurchase_History` ()   begin
select * from purchase_history;
end$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertMed` (IN `in_name` VARCHAR(220), IN `in_description` VARCHAR(320), IN `in_category` VARCHAR(500), IN `in_price` DOUBLE, IN `in_stock` INT(200), IN `in_manufacturer` VARCHAR(100), IN `in_expire` VARCHAR(120))   begin

insert into medicines(name, description, category, price, stock_quantity,manufacturer, expiration_date) values(in_name, in_description, in_category, in_price, in_stock, in_manufacturer, in_expire);

end$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `medicines`
--

CREATE TABLE `medicines` (
  `id` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `description` text NOT NULL,
  `category` varchar(100) DEFAULT NULL,
  `price` double NOT NULL,
  `stock_quantity` int(11) DEFAULT NULL,
  `manufacturer` varchar(50) DEFAULT NULL,
  `expiration_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicines`
--

INSERT INTO `medicines` (`id`, `name`, `description`, `category`, `price`, `stock_quantity`, `manufacturer`, `expiration_date`) VALUES
(1, 'paracetamol 500mg', 'a medicine used to treat mild to moderate pain', 'analgesics (painkillers)', 17.95, 10, 'Farmson Pharmaceutical Pvt Ltd', '2026-05-26'),
(2, 'Cyanocobalamin 1000mg', 'A nutrient in the vitamin B complex that the body needs in small amounts to function and stay healthy', 'vitamin tablet', 35.12, 0, 'Xi\'an ZB Biotech Co., Ltd(China)', '2025-04-23'),
(3, 'mbson sl', ' a health supplement formulated with mecobalamin, a form of vitamin B12', 'Vitamin supplement(Vitamin B12)', 41.4, 49, 'Unison Pharmaceuticals Pvt. Ltd.', '2026-09-21'),
(4, 'combiflam 400mg', 'reduce pain, fever, and inflammation', 'analgesics (painkillers)', 44.57, 33, ' Sanofi India Ltd.', '2025-05-09'),
(5, 'dolo 650mg', 'reduce fever and treat mild to moderate pain', 'analgesics (painkillers)', 29.71, 20, 'Micro lab LTD.', '2025-08-15'),
(6, 'Amonil-cv-625', 'It is used to treat bacterial infections like, tonsillitis, sinusitis, otitis media, respiratory tract infections, urinary tract infections, boils, abscesses, cellulitis, wound infection, bone infection, and oral cavity infections.', 'antibiotic', 20.3, 10, 'NILRISE PHARMACEUTICALS PVT LTD', '2025-07-05');

-- --------------------------------------------------------

--
-- Table structure for table `purchase_history`
--

CREATE TABLE `purchase_history` (
  `pid` int(11) NOT NULL,
  `user_name` varchar(50) NOT NULL,
  `medicine_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `total_payment` double NOT NULL,
  `purchase_date` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `purchase_history`
--

INSERT INTO `purchase_history` (`pid`, `user_name`, `medicine_id`, `quantity`, `total_payment`, `purchase_date`) VALUES
(1, 'Krish', 1, 2, 35.9, '2024-08-08 16:11:53'),
(2, 'Smit', 2, 1, 35.12, '2024-08-08 16:13:00'),
(3, 'Smit', 1, 5, 89.75, '2024-08-08 16:13:24'),
(4, 'Krish', 1, 10, 179.5, '2024-08-08 16:31:49'),
(5, 'Krish', 2, 3, 105.36, '2024-08-08 18:39:41'),
(6, 'Smit', 1, 5, 89.75, '2024-08-08 18:42:28'),
(7, 'Jeet', 1, 2, 35.9, '2024-08-08 19:02:52'),
(8, 'Krish', 1, 3, 53.85, '2024-08-09 16:29:09'),
(9, 'Kunj', 2, 1, 35.12, '2024-08-09 17:09:18'),
(10, 'Manthan', 2, 1, 35.12, '2024-08-09 17:10:31'),
(11, 'Kunj', 1, 2, 35.9, '2024-08-09 17:12:05'),
(12, 'Krish', 2, 1, 35.12, '2024-08-09 17:13:13'),
(14, 'Jeet', 1, 2, 35.9, '2024-08-09 17:28:53'),
(15, 'Viraj', 1, 1, 17.95, '2024-08-09 17:29:49'),
(16, 'Kaushal', 1, 1, 17.95, '2024-08-09 17:58:01'),
(17, 'Kunjesh', 2, 2, 70.24, '2024-08-09 18:08:26'),
(18, 'Krish', 2, 2, 70.24, '2024-08-09 18:09:28'),
(19, 'Krish', 2, 1, 35.12, '2024-08-09 20:05:20'),
(20, 'Nishit', 1, 2, 35.9, '2024-08-09 20:09:15'),
(21, 'Kaushal', 2, 1, 35.12, '2024-08-20 16:24:45'),
(22, 'Kaushal', 1, 1, 17.95, '2024-08-20 16:24:49'),
(23, 'Harshil', 2, 2, 70.24, '2024-08-20 17:21:36'),
(24, 'Swayam', 1, 1, 17.95, '2024-08-20 17:22:53'),
(25, 'Swayam', 1, 1, 17.95, '2024-08-20 17:23:40'),
(26, 'smit', 1, 4, 71.8, '2024-08-21 11:41:21'),
(27, 'Pratik', 2, 1, 35.12, '2024-08-29 22:35:21'),
(28, 'SmitP', 1, 2, 35.9, '2024-08-29 22:38:32'),
(29, 'Smit', 2, 1, 35.12, '2024-08-29 22:40:51'),
(30, 'Kunj', 2, 1, 35.12, '2024-08-30 12:55:07'),
(31, 'Manthan', 3, 1, 41.4, '2024-08-30 12:56:30'),
(32, 'HSD', 6, 6, 121.80000000000001, '2024-08-30 14:08:06'),
(33, 'RAJ', 1, 3, 53.849999999999994, '2024-08-30 16:19:16'),
(34, 'Mitanshu', 4, 1, 44.57, '2024-08-30 16:20:55'),
(35, 'kunjesh', 2, 9, 316.08, '2024-08-30 20:02:03'),
(36, 'Tirth', 4, 1, 44.57, '2024-08-30 20:27:30'),
(37, 'Krish', 1, 3, 53.85, '2024-08-30 20:45:58'),
(38, 'Krish', 1, 2, 35.9, '2024-08-30 20:52:05');

-- --------------------------------------------------------

--
-- Table structure for table `sellers`
--

CREATE TABLE `sellers` (
  `sid` int(11) NOT NULL,
  `sname` varchar(255) DEFAULT NULL,
  `susername` varchar(255) DEFAULT NULL,
  `spassword` varchar(255) DEFAULT NULL,
  `semail` varchar(255) DEFAULT NULL,
  `sphone_number` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `sellers`
--

INSERT INTO `sellers` (`sid`, `sname`, `susername`, `spassword`, `semail`, `sphone_number`) VALUES
(1, 'LifeCarePharma', 'LifeCare', '1234', 'Life@gmail.com', '12321211211'),
(2, 'Cipla PVT LTD.', 'Cipla', 'Cipla@121', 'Cipla_1@gmail.com', '9988776655'),
(3, 'ApplePharma', 'AppleP', '1234', 'Apple1@gmail.com', '8897897778'),
(4, 'Apolo', 'ApoloPharma', '12341', 'ApoloP@gmail.com', '9988776655'),
(5, 'ApoloPharma', 'Apolo', '12341', 'Pharma@gmail.com', '9988776655'),
(6, 'HealthCraft LTD.', 'HealthCraft', '9898', 'health@gmail.com', '6355453345'),
(7, 'MEDMEX', 'Medmex', '4532', 'care@gmail.com', '9534736746');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `u_id` int(11) NOT NULL,
  `u_name` varchar(255) DEFAULT NULL,
  `u_age` int(11) DEFAULT NULL,
  `u_gender` varchar(10) DEFAULT NULL,
  `u_phone_number` varchar(15) DEFAULT NULL,
  `u_email` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`u_id`, `u_name`, `u_age`, `u_gender`, `u_phone_number`, `u_email`) VALUES
(1, 'KrishRami', 20, 'M', '9988776655', 'LJKU@gmail.com'),
(2, 'SmitPandya', 21, 'MALE', '9986755654', 'Pandya@gmail.com'),
(3, 'PanchalSmit', 19, 'MALE', '9988996655', 'SmitP@gmail.com'),
(4, 'KunjPanara', 20, 'MALE', '9089879786', 'Kunj@gmail.com'),
(5, 'PratikDemo', 20, 'MALE', '1234567891', 'P@gmail.com'),
(6, 'PratikDemo', 21, 'MALE', '1234567890', 'P@gmail.com'),
(7, 'Demo', 12, 'MALE', '9876543210', 'Demo@gmail.com'),
(8, 'TirthS', 13, 'MALE', '9922334455', 'Solanki@gmail.com'),
(10, 'RV', 18, 'MALE', '9866755434', 'Vaghasiya@gmail.com');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `medicines`
--
ALTER TABLE `medicines`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `purchase_history`
--
ALTER TABLE `purchase_history`
  ADD PRIMARY KEY (`pid`),
  ADD KEY `t1` (`medicine_id`);

--
-- Indexes for table `sellers`
--
ALTER TABLE `sellers`
  ADD PRIMARY KEY (`sid`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`u_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `medicines`
--
ALTER TABLE `medicines`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `purchase_history`
--
ALTER TABLE `purchase_history`
  MODIFY `pid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=39;

--
-- AUTO_INCREMENT for table `sellers`
--
ALTER TABLE `sellers`
  MODIFY `sid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `u_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `purchase_history`
--
ALTER TABLE `purchase_history`
  ADD CONSTRAINT `t1` FOREIGN KEY (`medicine_id`) REFERENCES `medicines` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
