<?php


require_once 'connect.php';

	if ($_SERVER['REQUEST_METHOD'] =='POST'){
		
	$query="SELECT * FROM users_table WHERE (name='$name' or email='$email');";

    $response = mysqli_query($connect,$query);

    if (mysqli_num_rows($response) > 0) {
        
        $row = mysqli_fetch_assoc($response);
        if($email==isset($row['email']))
        {
            	echo "email already exists";
        }
		if($name==isset($row['name']))
		{
			echo "Username already exists";
		}
		}else{
			
			
 
     mysqli_close($connect);
	
//do your insert code here or do something (run your code)
}
}

?>