<?php

require_once 'connect.php';

if($connect){
	echo("Application ");
	
}else{
	echo("connection not success");
}
if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$latitude=$_POST['latitude'];
	$longitude=$_POST['longitude'];
	$altitude=$_POST['altitude'];
	$accuracy=$_POST['accuracy'];
	$speed = $_POST['speed'];
	$address=$_POST['address'];
	
	
	
	$query="INSERT INTO `geotagging`(`latitude`, `longitude`, `altitude`, `accuracy`, `speed`, `address`) VALUES ('$latitude','$longitude','$altitude','$accuracy','$speed','$address')";
	if(mysqli_query($connect,$query)){
		echo("success!");
	}else{
		echo("error");
	}
}





?>