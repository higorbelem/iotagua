$(function(){

});

function valida(){
    var nome = document.forms["formUsuario"]["nome"].value;
    var dataDeNascimento = document.forms["formUsuario"]["dataDeNascimento"].value;
    var matricula = document.forms["formUsuario"]["matricula"].value;
    var endereco = document.forms["formUsuario"]["endereco"].value;
    var formacao = document.forms["formUsuario"]["formacao"].value;
    var setor = document.forms["formUsuario"]["setor"].value;
    
    campoVazio = false;

    if(nome == ""){
        campoVazio = true;
    }
    if(dataDeNascimento == ""){
        campoVazio = true;
    }
    if(matricula == ""){
        campoVazio = true;
    }
    if(endereco == ""){
        campoVazio = true;
    }
    if(formacao == ""){
        campoVazio = true;
    }
    if(setor == ""){
        campoVazio = true;
    }

    if(campoVazio){
        alert("Algum campo está vazio.");
    }else{
        sendPost(nome,dataDeNascimento,matricula,endereco,formacao,setor,1);
    }
    
    return false
}

function getUsuarios(idSistema){
    $.ajax({
        method: 'post',
        //url: 'php/getNivel.php',
        url: 'php/getUsuarios.php',
        data: {
            "id-sistema" : idSistema
        },
        success: function(data) {
            
        }
    });
}

function sendPost(nome,dataDeNascimento,matricula,endereco,formacao,setor,idSistema){
    $.ajax({
        method: 'post',
        //url: 'php/getNivel.php',
        url: 'php/cadastraUsuario.php',
        data: {
            "nome":nome,
            "data-de-nascimento":dataDeNascimento,
            "matricula":matricula,
            "endereco":endereco,
            "formacao":formacao,
            "setor":setor,
            "id-sistema":idSistema
        },
        success: function(data) {
            if(data === "ok"){
                alert("Cliente adicionado.");
                var modal = document.getElementById("myModal");
                modal.style.display = "none";
            }else{
                alert("Houve algum erro.");
            }
        }
    });
}