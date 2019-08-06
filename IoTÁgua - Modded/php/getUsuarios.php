<?php
    include_once 'conexao.php';
    
    $id_sistema = $_POST['id-sistema'];

    $sqlSelect = $dbcon->query("SELECT * FROM usuarios WHERE `id-sistema` = $id_sistema AND `excluido` = 0");
    
    if(mysqli_num_rows($sqlSelect) > 0){

        $jsonString = "[";

        while($usuario = $sqlSelect->fetch_array()){

            $jsonString .= "{"; 

            $jsonString .= "\"id\":";
            $jsonString .= $usuario['id'];
            $jsonString .= ",";

            $jsonString .= "\"nome\":\"";
            $jsonString .= $usuario['nome'];
            $jsonString .= "\",";

            $jsonString .= "\"dataDeNascimento\":\"";
            $jsonString .= $usuario['data-de-nascimento'];
            $jsonString .= "\",";

            $jsonString .= "\"matricula\":";
            $jsonString .= $usuario['matricula'];
            $jsonString .= ",";

            $jsonString .= "\"endereco\":\"";
            $jsonString .= $usuario['endereco'];
            $jsonString .= "\",";

            $jsonString .= "\"formacao\":\"";
            $jsonString .= $usuario['formacao'];
            $jsonString .= "\",";

            $jsonString .= "\"setor\":\"";
            $jsonString .= $usuario['setor'];
            $jsonString .= "\"";

            $jsonString .= "},";

        }
        $jsonString = rtrim($jsonString, ',');  

        $jsonString .= "]";
        echo "ok,";
        echo $jsonString;
    }else{
        echo "erro";
    }
?>