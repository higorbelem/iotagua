$(function () {
    getUsuarios(1)
});

function valida() {
    var nome = document.forms["formUsuario"]["nome"].value;
    var dataDeNascimento = document.forms["formUsuario"]["dataDeNascimento"].value;
    var matricula = document.forms["formUsuario"]["matricula"].value;
    var endereco = document.forms["formUsuario"]["endereco"].value;
    var formacao = document.forms["formUsuario"]["formacao"].value;
    var setor = document.forms["formUsuario"]["setor"].value;

    campoVazio = false;

    if (nome == "") {
        campoVazio = true;
    }
    if (dataDeNascimento == "") {
        campoVazio = true;
    }
    if (matricula == "") {
        campoVazio = true;
    }
    if (endereco == "") {
        campoVazio = true;
    }
    if (formacao == "") {
        campoVazio = true;
    }
    if (setor == "") {
        campoVazio = true;
    }

    if (campoVazio) {
        alert("Algum campo está vazio.");
    } else {
        sendPost(nome, dataDeNascimento, matricula, endereco, formacao, setor, 1);
    }

    return false
}

function getUsuarios(idSistema) {
    $.ajax({
        method: 'post',
        //url: 'php/getNivel.php',
        url: 'php/getUsuarios.php',
        data: {
            "id-sistema": idSistema
        },
        success: function (data) {
            result = data.split(",", 1)

            if (result == "ok") {
                var obj = JSON.parse(data.substr(3))

                $('.listSensores').empty();

                obj.forEach(function (usuario) {
                    $('.listSensores').append("<div id=\"usuarioBtn\" class=\"usuarioBtn" + usuario.id + "\">" + usuario.nome + "<button id=\"excluirBtn\" class=\"btn" + usuario.id + "\">excluir</button> </div>")
                    $('.usuarioBtn' + usuario.id).on('click', function () {
                        $('.spanNome').text(usuario.nome)
                        $('.spanNascimento').text(usuario.dataDeNascimento)
                        $('.spanMatricula').text(usuario.matricula)
                        $('.spanEndereco').text(usuario.endereco)
                        $('.spanFormacao').text(usuario.formacao)
                        $('.spanSetor').text(usuario.setor)

                        var modal = document.getElementById("viewModal");
                        modal.style.display = "block";
                    });
                    $('.btn' + usuario.id).on('click', function () {
                        excluirUsuario(usuario.id)
                    });
                })
            }

        }
    });
}

function excluirUsuario(idUsuario) {
    $.ajax({
        method: 'post',
        //url: 'php/getNivel.php',
        url: 'php/excluirUsuario.php',
        data: {
            "id-usuario": idUsuario
        },
        success: function (data) {
            if (data === "ok") {
                alert("Cliente excluido.");
                getUsuarios(1)
            } else {
                alert("Houve algum erro.");
            }
        }
    });
}

function sendPost(nome, dataDeNascimento, matricula, endereco, formacao, setor, idSistema) {
    $.ajax({
        method: 'post',
        //url: 'php/getNivel.php',
        url: 'php/cadastraUsuario.php',
        data: {
            "nome": nome,
            "data-de-nascimento": dataDeNascimento,
            "matricula": matricula,
            "endereco": endereco,
            "formacao": formacao,
            "setor": setor,
            "id-sistema": idSistema
        },
        success: function (data) {
            if (data === "ok") {
                alert("Cliente adicionado.");
                var modal = document.getElementById("myModal");
                modal.style.display = "none";
            } else {
                alert("Houve algum erro.");
            }
        }
    });
}