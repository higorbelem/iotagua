function getNivel(){
  $.ajax({
    method: 'post',
    //url: 'php/getNivel.php',
    url: 'php/getValorSensores.php',
    data: {
      "id-sistema": "1"
    },
    success: function(data) {
      var obj = JSON.parse(data);

      $('.sensores-direita').empty();
      $('.sensores-esquerda').empty();

      obj.forEach(function(sensor) {
        if(sensor.nome.includes("nivel")){
          var valor = parseFloat(sensor.medicoes[0]);
          if (valor == 0) {
            $('.circle-inner, .gauge-copy').css({
              'transform' : 'rotate(-45deg)' 
            });
            $('.gauge-copy').css({
              'transform' : 'translate(-50%, -50%) rotate(0deg)'
            });
            $('.percentage').text('0 %');
          } else if(valor >= 0 && valor <= 100) {
            var dVal = valor;
            var newVal = dVal * 1.8 - 45;
            $('.circle-inner, .gauge-copy').css({
              'transform' : 'rotate(' + newVal + 'deg)' 
            });
            $('.gauge-copy').css({
              'transform' : 'translate(-50%, -50%) rotate(' + dVal * 1.8 + 'deg)'
            });
            $('.percentage').text(dVal + ' %');
          } else {
            $('.percentage').text('Invalid input value');
          }
        }else{
          if(sensor.nome.includes("bomba") || sensor.nome.includes("solenoide")){
            $('.sensores-esquerda').append("<li>" + sensor.nome + "<button class=\""+ sensor.nome + "-ligar" + "\">Ligar</button>" + "<button class=\""+ sensor.nome + "-desligar" + "\">Desligar</button>" + "<li>");   
            $('.' + sensor.nome + "-ligar").on('click',function(){
              setAcao(sensor.id, 1);
            });
            $('.' + sensor.nome + "-desligar").on('click',function(){
              setAcao(sensor.id, 0);
            });
          }else{
            $('.sensores-direita').append("<li>" + sensor.nome + "<li>");   
            $('.sensores-direita').append("<canvas id=\"chart" + "-" + sensor.nome + "\" width=\"200\" height=\"200\"></canvas>");
            var ctx = document.getElementById("chart-" + sensor.nome).getContext('2d');
            generateChart(ctx,sensor.medicoes,sensor.medicoes);
          }
        }
        //$('.sensores-direita').append(sensor.nome + ": " + sensor.medicao.valor + "\n");        
      });
    }
  });
}

function setAcao(idSensor, acao){
  $.ajax({
    method: 'post',
    //url: 'php/getNivel.php',
    url: 'php/setAcao.php',
    data: {
      "id-sistema": "1",
      "id-sensor": idSensor,
      "acao": acao,
    },
    success: function(data) {
      if(data.includes("ok")){
        alert("Comando enviado com sucesso.");
      }else{          
        alert("Ocorreu algum erro.");
      }
    }
  });
}

function generateChart(ctx,medicoes,medicoesLabel){
  var myChart = new Chart(ctx, {
      type: 'line',
      data: {
          labels: medicoesLabel,
          datasets: [{
              label: 'Medições',
              data: medicoes,
              backgroundColor:'rgba(255, 99, 132, 0.2)',
              borderColor:'rgba(255, 99, 132, 1)',
              borderWidth: 1
          }]
      },
      options: {
          scales: {
              yAxes: [{
                  ticks: {
                      beginAtZero: true
                  }
              }],
              xAxes: [
                {
                    ticks: {
                        display: false
                    }
                }
            ]
          },
          animation:{
            duration : 0
          }
      }
  });
}

$(function(){
  
  window.setInterval(function(){
    getNivel();
  }, 1000);

  getNivel();
  $('.refresh').on('click',function(){
    getNivel();
  });
});

/*$(function(){
  $('.text-box').keyup(function(){
    if ($('.text-box').val() == '') {
      $('.circle-inner, .gauge-copy').css({
        'transform' : 'rotate(-45deg)' 
      });
      $('.gauge-copy').css({
        'transform' : 'translate(-50%, -50%) rotate(0deg)'
      });
      $('.percentage').text('0 %');
    } else if($('.text-box').val() >= 0 && $('.text-box').val() <= 100) {
      var dVal = $(this).val();
      var newVal = dVal * 1.8 - 45;
      $('.circle-inner, .gauge-copy').css({
        'transform' : 'rotate(' + newVal + 'deg)' 
      });
      $('.gauge-copy').css({
        'transform' : 'translate(-50%, -50%) rotate(' + dVal * 1.8 + 'deg)'
      });
      $('.percentage').text(dVal + ' %');
    } else {
      $('.percentage').text('Invalid input value');
    }
  });
});

$('#jquery').on('click',function(){

    $.ajax({
        
        url : 'php/functions.php',
        type : "POST",
        data     : {sensor: 'sensor2'},
        success  : function ( data ){
          
          // $.each(data.sensor,function(key,index,value){

            // $('#sensor').find("li").append(' <li>Sensor de Fluxo 1: </span> <span >0 ml/s</li>');

          // });

           $('#temp').text( 'ºC');
           $('#ph').text( '15');
        },

        error    : function (){
            alert('eu3');
        }
          
    }); 


});*/