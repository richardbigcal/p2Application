<?php
require_once 'init.php';

$name = $_POST["name"];
$user_name = $_POST["user_name"];
$user_password = $_POST["user_password"];

$query = "select * from login_info where user_name = '$user_name'";

$result = mysqli_query($connect, $query);

if(mysqli_num_rows($result)>0)
{
	$status = "exist";
}
else 
{
	$query = "INSERT INTO `login_info`(`name`, `user_name`, `user_password`) VALUES('$name','$user_name','$user_password')";
	
	if(mysqli_query($connect,$query))
	{
		$status = "ok";
	} else {
		$status = "error";
	}
}
echo json_encode(array ("response"=>status));

mysqli_close($connect);
?>