<?php

///// di tapos

    class Db0perations{
		
        private $con;
		
        function _construct (){
             require_once dirname(_FILE_).'/connect.php';
			 
            $db = new connect();
			
            $this->con = $db->connect ();
			}
	 
	 
        /*CRUD -> C -> CREATE */
		
		
        public function createUser($username, $pass, $email){
			
            $password = md5($pass);
			
            $stmt = $this->con->prepare("INSERT INTO `users (`id`, `username`, `password`,email) VALUES (NULL, ?, ?, ?);");
			
            $stmt->bind_param("sss",$username, $password, $email);
			
            if($stmt->execute()){
                 return true;
             }else{
				 
                 return false;
				 
        private function ||
    }
		}
		
		
		
		
	}
		

























?>