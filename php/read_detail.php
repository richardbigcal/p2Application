<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
    
    $id = $_POST['id'];

    require_once 'connect.php';

    $query = "SELECT * FROM users_table WHERE id='$id' ";

    $response = mysqli_query($connect, $query);

    $result = array();
    $result['read'] = array();

    if( mysqli_num_rows($response) === 1 ) {
        
        if ($row = mysqli_fetch_assoc($response)) {
 
            $h['name']        = $row['name'] ;
            $h['email']       = $row['email'] ;
			$h['phone']       = $row['phone'] ;
			$h['address']     = $row['address'];
			 
			 
             $h['image']      = $row['photo']; 
 
             array_push($result["read"], $h);
 
             $result["success"] = "1";
             echo json_encode($result);
        }
 
   }
 
 }else {
 
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
 
     mysqli_close($connect);
 }
 
 ?>