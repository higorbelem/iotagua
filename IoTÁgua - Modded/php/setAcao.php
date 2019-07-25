<?php
    include_once 'conexao.php';
    
    $id_sistema = $_POST['id-sistema'];
    $id_sensor = $_POST['id-sensor'];
    $acao = $_POST['acao'];
	
	$sql1 = $dbcon -> query("INSERT INTO acoes(acao, `id-sensor`, `id-sistema`) VALUES ($acao, $id_sensor, $id_sistema)");

    //SELECT se.`id` as 'id-sensor', se.`data-inicio-operacoes`,se.`local`,se.`nome`,se.`pinos`, me.`id` as 'id-medicao',me.`valor` FROM `sensor` se INNER JOIN `medicao` me ON se.`id-sistema` = $id_sistema AND me.`id-sensor` = se.`id` AND me.`id` = (SELECT MAX(`id`) FROM `medicao` WHERE se.`id-sistema` = $id_sistema AND me.`id-sensor` = se.`id`);
    //SELECT se.`id` as 'id-sensor', se.`data-inicio-operacoes`,se.`local`,se.`nome`,se.`pinos`, me.`id`, me.`valor` FROM `sensor` se, `medicao` me WHERE se.`id-sistema` = 1 AND se.`id` = me.`id-sensor` AND me.`id` = (SELECT MAX(`id`) FROM `medicao` WHERE ));

	if($sql1){
        echo "ok";  
	}else{
		echo "erro";
    }

?>