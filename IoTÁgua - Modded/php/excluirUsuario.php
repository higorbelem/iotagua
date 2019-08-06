<?php
    include_once 'conexao.php';
    
    $id_usuario = $_POST['id-usuario'];

    $sql = $dbcon->query("UPDATE usuarios SET excluido = 1 WHERE id = $id_usuario");
    
    if($sql){
        echo "ok";
    }else{
        echo "erro";
    }

?>