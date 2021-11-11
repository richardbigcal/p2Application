<?php
$servername="localhost";
$mysql_user="root";
$mysql_pass="";
$dbname="p2system";
$connect =mysqli_connect($servername, $mysql_user, $mysql_pass, $dbname);
if($connect){
	echo("Application");
	
}else{
	echo("connection not success");
}
if($_SERVER['REQUEST_METHOD']=='POST'){
	
	$date=$_POST['date'];
	$name=$_POST['name'];
	$age=$_POST['age'];
	$add=$_POST['address'];
	$phone=$_POST['phone'];
	$email=$_POST['email'];
	$occupation=$_POST['occupation'];
	$status=$_POST['status'];
	$alternate_contact=$_POST['alternate_contact'];
	$relationship=$_POST['relationship'];
	$mobile=$_POST['mobile'];
	

	$Q1=$_POST['Q1'];
	$Q2=$_POST['Q2'];
	$Q3=$_POST['Q3'];
	$Q4=$_POST['Q4'];
	$Q5=$_POST['Q5'];
	$Q6=$_POST['Q6'];
	$Q7=$_POST['Q7'];
	$Q8=$_POST['Q8'];
	$Q9=$_POST['Q9'];
	$Q10=$_POST['Q10'];
	$Q11=$_POST['Q11'];
	$Q12=$_POST['Q12'];
	$Q13=$_POST['Q13'];
	$Q14=$_POST['Q14'];
	$Q15=$_POST['Q15'];
	$Q16=$_POST['Q16'];
	$Q17=$_POST['Q17'];
	
	$query="INSERT INTO `personal_info`(`date`,`name`, `age`, `address`, `phone`, `email`, `occupation`,`status`, `alternate_contact`, `relationship`, `mobile`, `Q1`, `Q2`, `Q3`, `Q4`, `Q5`, `Q6`, `Q7`, `Q8`, `Q9`, `Q10`, `Q11`, `Q12`, `Q13`, `Q14`, `Q15`, `Q16`, `Q17`) VALUES ('$date','$name','$age','$add',$phone,'$email','$occupation','$status','$alternate_contact','$relationship','$mobile','$Q1','$Q2','$Q3','$Q4','$Q5','$Q6','$Q7','$Q8','$Q9','$Q10','$Q11','$Q12','$Q13','$Q14','$Q15','$Q16','$Q17')";
	if(mysqli_query($connect,$query)){
		echo("success!");
	}else{
		echo("error in registration");
	}
}else{
	echo("error in request method");
}



?>