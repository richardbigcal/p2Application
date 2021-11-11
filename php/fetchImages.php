<?php
$servername="localhost";
$mysql_user="root";
$mysql_pass="";
$dbname="p2system";
$connect = mysqli_connect($servername, $mysql_user, $mysql_pass, $dbname);
	


        if($_SERVER['REQUEST_METHOD'] =='POST')
            {
    
     
			$result = array();
			$result['data'] = array();
			$select= "SELECT * FROM imagedata";
			$responce = mysqli_query($connect,$select);
	
            
			while($row = mysqli_fetch_array($responce))
			{
		
		    $index['id']      = $row['0'];
		    $index['image']   = $row['1'];
				
			array_push($result['data'], $index);
			
				
			}
			$result["success"]="1";
		    echo json_encode($result);
			mysqli_close($connect);
	


            }	
?>