<?php
$servername="localhost";
$mysql_user="root";
$mysql_pass="";
$dbname="p2system";
$connect = mysqli_connect($servername, $mysql_user, $mysql_pass, $dbname);
if($connect){
	echo("Application ");
	
}else{
	echo("connection not success");
}
if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$firstname=$_POST['firstname'];
	$gender=$_POST['gender'];
	$email=$_POST['email'];
	$date=$_POST['date'];
	$link = $_POST['link'];
	$time=$_POST['time'];
	$phone=$_POST['phone'];
	
	
	
	
	$query="INSERT INTO `schedule`(`firstname`,`gender`, `email`, `date`,`link`,`time`, `phone`) VALUES  ('$firstname','$gender','$email','$date','$link','$time','$phone')";
	if(mysqli_query($connect,$query)){
		echo("success!");
	}else{
		echo("error");
	}
}



?>