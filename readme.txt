Contacto is a project designed for training prupose. Training was organised by GDG Kathmandu with the support of MobileNepal.
The main concern of this project is to teach Android UI design, Design Integration in Android using XML, API design and Development,
Android-API integration.

#Designer : Anjan Shrestha
#API developer: Hem Shrestha
#Android application Developer: Vivek Bhusal

########################################################################################################
To add contact
http://katibajyo.com/api/contacto.php?action=add&username=Hem&c_fname=Hem&c_lname=Shrestha&n_mobile=9843096958&n_home=&n_office=
note:username is used for the person who adds the contact

To delete contact 
http://katibajyo.com/api/contacto.php?action=delete&id=5


To view single contact
http://katibajyo.com/api/contacto.php?action=view&id=1

To view all contact list
http://katibajyo.com/api/contacto.php?action=list

To edit contact 
http://katibajyo.com/api/contacto.php?action=edit&id=26&username=Hem&c_fname=Hem&c_lname=Shrestha&n_mobile=9843096958&n_home=&n_office=

To search Contact
http://katibajyo.com/api/contacto.php?action=search&q=vivek

To view specific username's contact list
http://katibajyo.com/api/contacto.php?action=list&username=Hem

########################################################################################################
Below is the php code used for api development

<?php
	$mycon=mysql_connect("localhost","####","######") or die('{"res":"Database Error"}');
	$mydb=mysql_select_db("contacto") or die('{"res":"Database Error"}');
	$action = (isset($_GET['action'])?($_GET['action']):"list");
	switch($action){
		case "list":
			$sql = "SELECT * FROM `contacts` LIMIT 0, 50"; 
			$myquery = mysql_query($sql) or die('{"res":"Query error"}');
			$json_array = array();
			while($rows = mysql_fetch_array($myquery)){//`username`, `c_fname`, `c_lname`, `n_home`, `n_mobile`, `n_office`, `created`
					$row_array['id'] = $rows['id'];
					$row_array['username'] = $rows['username'];
					$row_array['c_fname'] = $rows['c_fname'];
					$row_array['c_lname'] = $rows['c_lname'];
					$row_array['n_home'] = $rows['n_home'];
					$row_array['n_mobile'] = $rows['n_mobile'];
					$row_array['n_office'] = $rows['n_office'];
					$row_array['created'] = $rows['created'];

					array_push($json_array,$row_array);	
			}
			$json_result['res'] = 'success';
			$json_result['data'] = $json_array;
			echo json_encode($json_result);
			break;
		case "view":
			if(!isset($_GET['id'])){
				die('{"res":"id not specified"}');
			}
			$sql = "SELECT * FROM `contacts` WHERE `id` = ".$_GET['id'] ; 
			$myquery = mysql_query($sql) or die('{"res":"Query error"}');
			$row_array;
			while($rows = mysql_fetch_array($myquery)){//`username`, `c_fname`, `c_lname`, `n_home`, `n_mobile`, `n_office`, `created`
				$row_array['id'] = $rows['id'];
				$row_array['username'] = $rows['username'];
				$row_array['c_fname'] = $rows['c_fname'];
				$row_array['c_lname'] = $rows['c_lname'];
				$row_array['n_home'] = $rows['n_home'];
				$row_array['n_mobile'] = $rows['n_mobile'];
				$row_array['n_office'] = $rows['n_office'];
				$row_array['created'] = $rows['created'];

			}
			$json_result['res'] = 'success';
			$json_result['data'] = $row_array;
			echo json_encode($json_result);
			break;
		case "add":
			$username = (isset($_GET['username'])?($_GET['username']):"hem");
			$c_fname = (isset($_GET['c_fname'])?($_GET['c_fname']):"");
			$c_lname = (isset($_GET['c_lname'])?($_GET['c_lname']):"");
			$n_home = (isset($_GET['n_home'])?($_GET['n_home']):"");
			$n_mobile = (isset($_GET['n_mobile'])?($_GET['n_mobile']):"");
			$n_office = (isset($_GET['n_office'])?($_GET['n_office']):"");
			
			$sql = "INSERT INTO `contacts` (`username`, `c_fname`, `c_lname`, `n_home`, `n_mobile`, `n_office`, `created`) VALUES ('".$username."', '".$c_fname."', '".$c_lname."', '".$n_home."', '".$n_mobile."', '".$n_office."', '".date('Y-m-d h:i:s a', strtotime('now'))."')";
			//echo $sql;
			$myquery=mysql_query($sql);
			if($myquery){
				$json_result['res'] = 'success';
				echo json_encode($json_result);
			}
			else{
				$json_result['res'] = 'error';
				echo json_encode($json_result);
			}
			break;
		case "edit":
		if(!isset($_GET['id'])){
				die('{"res":"id not specified"}');
			}
			$id = $_GET['id'];
			$username = (isset($_GET['username'])?($_GET['username']):"hem");
			$c_fname = (isset($_GET['c_fname'])?($_GET['c_fname']):"");
			$c_lname = (isset($_GET['c_lname'])?($_GET['c_lname']):"");
			$n_home = (isset($_GET['n_home'])?($_GET['n_home']):"");
			$n_mobile = (isset($_GET['n_mobile'])?($_GET['n_mobile']):"");
			$n_office = (isset($_GET['n_office'])?($_GET['n_office']):"");
			$sql = "UPDATE `contacts` SET `username`='".$username."', `c_fname`='".$c_fname."', `c_lname`='".$c_lname."', `n_home`='".$n_home."', `n_mobile`='".$n_mobile."', `n_office`='".$n_office."' WHERE `id`=".$id;
			//echo $sql;
			$myquery=mysql_query($sql);
			if($myquery){
				$json_result['res'] = 'success';
				echo json_encode($json_result);
			}
			else{
				$json_result['res'] = 'error';
				echo json_encode($json_result);
			}
			break;
		case "search":
			$q = (isset($_GET['q']))?($_GET['q']):"";
			$sql = "SELECT * FROM `contacts` WHERE `username` LIKE '%".$q."%' OR `c_fname` LIKE '%".$q."%' OR `c_lname` LIKE '%".$q."%' LIMIT 0, 50";
			//echo $sql;
			$myquery = mysql_query($sql) or die('{"res":"Query error"}');
			$json_array = array();
			while($rows = mysql_fetch_array($myquery)){//`username`, `c_fname`, `c_lname`, `n_home`, `n_mobile`, `n_office`, `created`
					$row_array['id'] = $rows['id'];
					$row_array['username'] = $rows['username'];
					$row_array['c_fname'] = $rows['c_fname'];
					$row_array['c_lname'] = $rows['c_lname'];
					$row_array['n_home'] = $rows['n_home'];
					$row_array['n_mobile'] = $rows['n_mobile'];
					$row_array['n_office'] = $rows['n_office'];
					$row_array['created'] = $rows['created'];

					array_push($json_array,$row_array);	
			}
			$json_result['res'] = 'success';
			$json_result['data'] = $json_array;
			echo json_encode($json_result);
			break;
		case "delete":
			if(!isset($_GET['id'])){
				die('{"res":"id not specified"}');
			}
			$sql = "DELETE FROM `contacts` WHERE `id` = ".$_GET['id'] ; 
			$myquery = mysql_query($sql) or die('{"res":"Query error"}');
			if($myquery){
				$json_result['res'] = 'success';
				echo json_encode($json_result);
			}
			else{
				$json_result['res'] = 'error';
				echo json_encode($json_result);
			}
			break;
		default:
				$json_result['res'] = 'invalid action';
				echo json_encode($json_result);
			break;
	}
?>

##############################################################################################################################

-- phpMyAdmin SQL Dump
-- version 3.4.5
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 28, 2012 at 12:03 PM
-- Server version: 5.5.16
-- PHP Version: 5.3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `contacto`
--

-- --------------------------------------------------------

--
-- Table structure for table `contacts`
--

CREATE TABLE IF NOT EXISTS `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `c_fname` varchar(50) NOT NULL,
  `c_lname` varchar(50) DEFAULT NULL,
  `n_home` varchar(50) DEFAULT NULL,
  `n_mobile` varchar(50) DEFAULT NULL,
  `n_office` varchar(50) DEFAULT NULL,
  `created` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

--
-- Dumping data for table `contacts`
--

INSERT INTO `contacts` (`username`, `c_fname`, `c_lname`, `n_home`, `n_mobile`, `n_office`, `created`) VALUES
('Hem', 'Hem', 'Shrestha', '01234567', '9843096958', '011223344', '2012-12-29 03:59:23 pm');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;