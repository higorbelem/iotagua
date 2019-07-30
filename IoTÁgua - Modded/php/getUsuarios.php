<?php
    include_once 'conexao.php';
    
    $id_sistema = $_POST['id-sistema'];

    try {
		mysqli_autocommit($dbcon, FALSE);
		$dbcon->begin_transaction();
	
        $sqlSelect = $dbcon->query("SELECT se.* FROM sensor se WHERE se.`id-sistema` = $id_sistema");
        
        $jsonString = "[";

        if(mysqli_num_rows($sqlSelect) > 0){
            while($sensor = $sqlSelect->fetch_array()){

                $jsonString .= "{";

                $jsonString .= "\"id\":\"";
                $jsonString .= $sensor['id'];
                $jsonString .= "\",";
    
                $jsonString .= "\"dataInicioOperacoes\":\"";
                $jsonString .= $sensor['data-inicio-operacoes'];
                $jsonString .= "\",";
    
                $jsonString .= "\"local\":\"";
                $jsonString .= $sensor['local'];
                $jsonString .= "\",";
    
                $jsonString .= "\"nome\":\"";
                $jsonString .= $sensor['nome'];
                $jsonString .= "\",";
    
                $jsonString .= "\"pinos\":";
                $jsonString .= $sensor['pinos'];
                $jsonString .= ",";
    
                $jsonString .= "\"medicoes\":["; // abrindo medicao

                $sqlSelectMedicoes = $dbcon->query("SELECT `valor` FROM medicao WHERE `id-sensor` = $sensor[id] ORDER BY `id` DESC LIMIT 20");
                if(mysqli_num_rows($sqlSelectMedicoes) > 0){
                    while($valor = $sqlSelectMedicoes->fetch_array()){
                        $jsonString .= $valor['valor'];
                        $jsonString .= ",";
                    }
                    $jsonString = rtrim($jsonString, ',');
                }

                $jsonString .= "]"; // fechando medicao

                $jsonString .= "},";

            }
            $jsonString = rtrim($jsonString, ',');

            $jsonString .= "]";

            echo $jsonString;
        }

		$dbcon->commit();

		if(mysqli_num_rows($sqlSelect) > 0){

		}else{
			echo "ok";
		}
	} catch (Exception $e) {
		// An exception has been thrown
		// We must rollback the transaction
		echo "erro";
		$dbcon->rollback();
	}

?>