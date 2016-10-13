-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Host: .
-- Generation Time: Oct 12, 2016 at 11:20 PM
-- Server version: 10.1.17-MariaDB
-- PHP Version: 7.0.11


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `goods_manager`
--
DROP DATABASE IF EXISTS `goods_manager`;
CREATE DATABASE `goods_manager`;
USE `goods_manager`;

-- --------------------------------------------------------

--
-- Table structure for table `goods`
--

DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `number` int(10) UNSIGNED NOT NULL,
  `name` varchar(150) NOT NULL,
  `division` varchar(10) NOT NULL,
  `broken` tinyint(1) NOT NULL DEFAULT '0',
  `codename` varchar(150) NOT NULL,
  `content` text NOT NULL,
  `takeType` varchar(3) NOT NULL,
  `takePeriod` text NOT NULL,
  `image` varchar(150) NOT NULL
);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `goods`
--
ALTER TABLE `goods`
  ADD PRIMARY KEY (`number`),
  ADD KEY `division` (`division`),
  ADD KEY `broken` (`broken`),
  ADD KEY `takeType` (`takeType`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `goods`
--
ALTER TABLE `goods`
  MODIFY `number` int(10) UNSIGNED NOT NULL AUTO_INCREMENT;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
