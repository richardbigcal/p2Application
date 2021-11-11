<?php
$servername="localhost";
$mysql_user="root";
$mysql_pass="";
$dbname="p2system";
$connect = mysqli_connect($servername, $mysql_user, $mysql_pass, $dbname);
if($connect){
	echo("Upload successful ");
}
if($_SERVER['REQUEST_METHOD']=='POST')
{
	if(isset($_POST['image'])); 
			
	$firstname = $_POST['firstname'];
	$lastname = $_POST['lastname'];
	$report = $_POST['report'];
	$phone = $_POST['phone'];
	$email = $_POST['email'];
	$breed = $_POST['breed'];
	$pet_description = $_POST['pet_description'];	
	$gender = $_POST['gender'];
	
	
	$target_dir = "Images/";
	$image = $_POST['image'];
	$imageStore = rand()."_".time().".jpeg";
	$target_dir = $target_dir."/".$imageStore;
	file_put_contents($target_dir, base64_decode($image));

	$select= "INSERT INTO `imagedata`(`firstname`, `lastname`, `email`, `phone`,`report`, `breed`,`gender`, `pet_description`,`image`) VALUES('$firstname','$lastname','$email','$phone','$report','$breed','$gender','$pet_description','$imageStore')";
	if(mysqli_query($connect,$select)){
		echo("success!");
	}
	
	
	
	
}



?>