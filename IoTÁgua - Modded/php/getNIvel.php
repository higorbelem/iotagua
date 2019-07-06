<?php
	include_once 'conexao.php';
	
	$sql1 = $dbcon -> query("SELECT `valor` FROM `medicao` WHERE `id` = (SELECT MAX(`id`) FROM `medicao`)");

	if(mysqli_num_rows($sql1) > 0){
        $dados = $sql1->fetch_array();

        echo $dados['valor'];
	}else{
		echo "erro";
    }

?>