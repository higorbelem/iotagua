<?php
    include_once 'conexao.php';
    
    $id_sistema = $_POST['id-sistema'];
	
	$sql1 = $dbcon -> query("SELECT * FROM `sensor` WHERE `id-sistema` = $id_sistema");

	if(mysqli_num_rows($sql1) > 0){
        echo "[";
        while($dados = $sql1->fetch_array()){
            echo "{";

            echo "\"id\":\"";
            echo $dados['id'];
            echo "\",";

            echo "\"nome\":\"";
            echo $dados['nome'];
            echo "\",";

            echo "\"dataInicioOperacoes\":\"";
            echo $dados['data-inicio-operacoes'];
            echo "\",";

            echo "\"local\":\"";
            echo $dados['local'];
            echo "\",";

            echo "\"pinos\":";
            echo $dados['pinos'];
            
            echo "},";
        }

        echo "]";
	}else{
		echo "erro";
    }

?>