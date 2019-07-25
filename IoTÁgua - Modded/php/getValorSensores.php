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
	
	/*$sql1 = $dbcon -> query("SELECT se.*,(SELECT `valor` FROM medicao WHERE `id-sensor` = se.`id` ORDER BY `id` DESC LIMIT 1) AS 'ultima-medicao' FROM sensor se WHERE se.`id-sistema` = $id_sistema");

    //SELECT se.`id` as 'id-sensor', se.`data-inicio-operacoes`,se.`local`,se.`nome`,se.`pinos`, me.`id` as 'id-medicao',me.`valor` FROM `sensor` se INNER JOIN `medicao` me ON se.`id-sistema` = $id_sistema AND me.`id-sensor` = se.`id` AND me.`id` = (SELECT MAX(`id`) FROM `medicao` WHERE se.`id-sistema` = $id_sistema AND me.`id-sensor` = se.`id`);
    //SELECT se.`id` as 'id-sensor', se.`data-inicio-operacoes`,se.`local`,se.`nome`,se.`pinos`, me.`id`, me.`valor` FROM `sensor` se, `medicao` me WHERE se.`id-sistema` = 1 AND se.`id` = me.`id-sensor` AND me.`id` = (SELECT MAX(`id`) FROM `medicao` WHERE ));

	if(mysqli_num_rows($sql1) > 0){

        $jsonString = "[";

        while($sensor = $sql1->fetch_array()){
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

            $jsonString .= "\"medicao\":{"; // abrindo medicao

            $jsonString .= "\"ultimaMedicao\":\"";
            $jsonString .= $sensor['ultima-medicao'];
            $jsonString .= "\"";

            $jsonString .= "}"; //fechando medicao

            $jsonString .= "},";
        }

        $jsonString = rtrim($jsonString, ',');

        $jsonString .= "]";

        echo $jsonString;
        //echo $dados['valor'];
	}else{
		echo "erro";
    }*/

?>