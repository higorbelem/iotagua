<?php 


$host = "localhost";
$dbusername = "root";
$dbpassword = "";
$dbname = "mateus";

// Create connection
$con = new mysqli ($host, $dbusername, $dbpassword, $dbname);

if(!$con){

  die("Connection failed: ".mysqli_connect_error());

}


?>