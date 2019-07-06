<?php
	include_once 'conexao.php';

	$valor = $_POST['valor'];
    $id_sensor = $_POST['id-sensor'];

	$sql1 = $dbcon -> query("INSERT INTO `medicao` (`valor`,`id-sensor`) VALUES ($valor,$id_sensor)");

	if(mysqli_num_rows($sql1) > 0){
        echo "ok";
	}else{
		echo "erro";
   }
?>