<?php
    include_once 'conexao.php';
    
    $id_sistema = $_POST['id-sistema'];
	
	$sql1 = $dbcon -> query("SELECT *,(SELECT `volume-reservatorio` FROM sistema WHERE `id` = $id_sistema) AS 'volume-reservatorio' FROM `sensor` WHERE `id-sistema` = $id_sistema");

	if(mysqli_num_rows($sql1) > 0){
        $jsonString = "";

        $jsonString .= "[";
        while($dados = $sql1->fetch_array()){
            $jsonString .= "{";

            $jsonString .= "\"id\":\"";
            $jsonString .= $dados['id'];
            $jsonString .= "\",";

            $jsonString .= "\"nome\":\"";
            $jsonString .= $dados['nome'];
            $jsonString .= "\",";

            $jsonString .= "\"dataInicioOperacoes\":\"";
            $jsonString .= $dados['data-inicio-operacoes'];
            $jsonString .= "\",";

            $jsonString .= "\"local\":\"";
            $jsonString .= $dados['local'];
            $jsonString .= "\",";

            $jsonString .= "\"pinos\":";
            $jsonString .= $dados['pinos'];
            $jsonString .= ",";

            $jsonString .= "\"volumeReservatorio\":";
            $jsonString .= $dados['volume-reservatorio'];

            $jsonString .= "},";
        }

        $jsonString = rtrim($jsonString, ',');

        $jsonString .= "]";

        echo $jsonString;
	}else{
		echo "erro";
    }

?>