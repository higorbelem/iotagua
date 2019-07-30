<?php 
    include_once 'conexao.php';

    $nome = $_POST['nome'];
    $dataDeNascimento = $_POST['data-de-nascimento'];
    $matricula = $_POST['matricula'];
    $endereco = $_POST['endereco'];
    $formacao = $_POST['formacao'];
    $setor = $_POST['setor'];
    $idSistema = $_POST['id-sistema'];

    $sqlResult = $dbcon->query("INSERT INTO `usuarios` (`nome`,`data-de-nascimento`,`matricula`,`endereco`,`formacao`,`setor`,`id-sistema`) VALUES ('$nome','$dataDeNascimento',$matricula,'$endereco','$formacao','$setor',$idSistema)");

    if($sqlResult){
        echo "ok";
    }else{
        echo "erro";
    }

?>