<?php
	
	require_once "class.php";

	if (isset($_POST["sensor"])):


		$con = new con;
		$res = $con->select();
		echo json_encode($res);


	endif;


 ?>