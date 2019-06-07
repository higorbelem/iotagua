<?php
	include_once 'conexao.php';
	
   $cnpj = $_POST['cnpj'];
   $senha = $_POST['senha'];

	$sql1 = $dbcon -> query("SELECT ce.`id` as 'id-cedente', ce.`nome` as 'nome-cedente', ce.`uso-banco`, ce.`use-santander`, ce.`uf`, ce.`cidade`, ce.`bairro`, ce.`rua`, ce.`numero`, ce.`cep`, ce.`cnpj`, ce.`informacoes`, ce.`contato`, ce.`valor-por-metro-cubico`, ce.`esgoto`, co.`id` as 'id-conta', co.`banco`, co.`cip`, co.`conta`, co.`convenio`, co.`modalidade`, co.`agencia`  FROM `cedentes` ce INNER JOIN `contas` co ON co.`cedente-id` = ce.`id` AND ce.`cnpj` = $cnpj AND ce.`senha` = $senha ");

	if(mysqli_num_rows($sql1) > 0){
      $dados = $sql1->fetch_array();

		echo "{";

		echo "\"id\":\"";
		echo $dados['id-cedente'];
		echo "\",";

		echo "\"nome\":\"";
		echo $dados['nome-cedente'];
		echo "\",";

		echo "\"usobanco\":\"";
		echo $dados['uso-banco'];
		echo "\",";

		echo "\"useSantander\":\"";
		echo $dados['use-santander'];
		echo "\",";

		echo "\"cnpj\":\"";
		echo $dados['cnpj'];
		echo "\",";

		echo "\"informacoes\":\"";
		echo $dados['informacoes'];
      echo "\",";

      echo "\"contato\":\"";
		echo $dados['contato'];
      echo "\",";

      echo "\"uf\":\"";
		echo $dados['uf'];
      echo "\",";

      echo "\"cidade\":\"";
		echo $dados['cidade'];
      echo "\",";

      echo "\"bairro\":\"";
		echo $dados['bairro'];
      echo "\",";

      echo "\"rua\":\"";
		echo $dados['rua'];
      echo "\",";

      echo "\"numero\":\"";
		echo $dados['numero'];
      echo "\",";

      echo "\"cep\":\"";
		echo $dados['cep'];
      echo "\",";

      echo "\"valorPorMetroCubico\":\"";
		echo $dados['valor-por-metro-cubico'];
      echo "\",";
      
      echo "\"esgoto\":\"";
		echo $dados['esgoto'];
      echo "\",";

      echo "\"contas\":[";
      
      echo "{";

      echo "\"id\":\"";
      echo $dados['id-conta'];
      echo "\","; 

      echo "\"banco\":\"";
      echo $dados['banco'];
      echo "\",";

      echo "\"cip\":\"";
      echo $dados['cip'];
      echo "\",";

      echo "\"conta\":\"";
      echo $dados['conta'];
      echo "\",";

      echo "\"convenio\":\"";
      echo $dados['convenio'];
      echo "\",";

      echo "\"modalidade\":\"";
      echo $dados['modalidade'];
      echo "\",";

      echo "\"agencia\":\"";
      echo $dados['agencia'];
      echo "\",";

      echo "},";

		while($dados = $sql1->fetch_array()){
			echo "{";

         echo "\"id\":\"";
         echo $dados['id-conta'];
         echo "\","; 

         echo "\"banco\":\"";
         echo $dados['banco'];
         echo "\",";

         echo "\"cip\":\"";
         echo $dados['cip'];
         echo "\",";

         echo "\"conta\":\"";
         echo $dados['conta'];
         echo "\",";

         echo "\"convenio\":\"";
         echo $dados['convenio'];
         echo "\",";

         echo "\"modalidade\":\"";
         echo $dados['modalidade'];
         echo "\",";

         echo "\"agencia\":\"";
         echo $dados['agencia'];
         echo "\",";

			echo "},";
		}

		echo "]";

		echo "}";
	}else{
		echo "erro-login";
   }
?>