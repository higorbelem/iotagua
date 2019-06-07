<?php
	include_once 'conexao.php';
	
	$sql1 = $dbcon -> query("SELECT * FROM `sensor-nivel` WHERE ID = (SELECT MAX(ID) FROM `sensor-nivel`)");

	if(mysqli_num_rows($sql1) > 0){
        $dados = $sql1->fetch_array();

        echo $dados['valor'];
	}else{
		echo "erro";
    }

?>