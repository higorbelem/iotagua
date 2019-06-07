<?php
	include_once 'conexao.php';
	
    $valor = $_POST['valor'];

	$sql1 = $dbcon -> query("INSERT INTO `sensor-nivel` (`valor`) VALUES ($valor)");

	if(mysqli_num_rows($sql1) > 0){
        echo "ok";
	}else{
		echo "erro";
   }
?>