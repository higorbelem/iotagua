<?php
	include_once 'conexao.php';

	$valor = $_POST['valor'];
	$id_sensor = $_POST['id-sensor'];

	try {
		// First of all, let's begin a transaction
		mysqli_autocommit($dbcon, FALSE);
		$dbcon->begin_transaction();
	
		// A set of queries; if one fails, an exception should be thrown
		$dbcon->query("INSERT INTO `medicao` (`valor`,`id-sensor`) VALUES ($valor,$id_sensor)");
		$sqlSelect = $dbcon->query("SELECT a.*, se.pinos FROM `acoes` a, `sensor` se WHERE a.`id-sensor` = se.`id` AND se.`id` = $id_sensor AND a.concluido=0");
		$dbcon->query("UPDATE `acoes` a INNER JOIN `acoes` b ON a.`id` = b.`id` AND a.`id-sensor` = $id_sensor AND a.`concluido`=0 SET a.`concluido` = 1");
	
		// If we arrive here, it means that no exception was thrown
		// i.e. no query has failed, and we can commit the transaction
		$dbcon->commit();

		if(mysqli_num_rows($sqlSelect) > 0){

			$jsonString = "[";
			while($acao = $sqlSelect->fetch_array()){
				$jsonString .= "{";
				$jsonString .= "\"id\":\"";
				$jsonString .= $acao['id'];
				$jsonString .= "\",";

				$jsonString .= "\"acao\":\"";
				$jsonString .= $acao['acao'];
				$jsonString .= "\",";

				$jsonString .= "\"idSensor\":\"";
				$jsonString .= $acao['id-sensor'];
				$jsonString .= "\",";

				$jsonString .= "\"pinos\":";
				$jsonString .= $acao['pinos'];

				$jsonString .= "},";
			}
			$jsonString = rtrim($jsonString, ',');
			$jsonString .= "]";	
			echo $jsonString;
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