<?php
    
    $bdcon = pg_connect("host=localhost port=5432 dbname=teste user=postgres password=admin");

    pg_query($bdcon, "INSERT INTO teste(nome) VALUES('nada')");
    
?>