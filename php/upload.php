<?php

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    $id = $_POST['id'];
    $photo = $_POST['photo'];

    $path = "profile_users/$id.jpeg";
    $finalPath = "http://192.168.0.14/p2system/profile_users/".$path;

    require_once 'connect.php';

    $query = "UPDATE users_table SET photo='$finalPath' WHERE id='$id' ";

    if (mysqli_query($connect, $query)) {
        
        if ( file_put_contents( $path, base64_decode($photo) ) ) {
            
            $result['success'] = "1";
            $result['message'] = "success";

            echo json_encode($result);
            mysqli_close($connect);

        }

    }

}

?>