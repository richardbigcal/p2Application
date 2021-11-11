<?php

$localhost = "localhost";
$user_name = "root";
$user_password = "";
$db_name = "p2system";

$conn = mysqli_connect($localhost, $user_name, $user_password, $db_name);

if($connect)
echo "connection success...";
else
echo "connection failed...";






?>