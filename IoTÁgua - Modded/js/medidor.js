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

      obj.forEach(function(sensor) {
        if(sensor.nome.includes("nivel")){
          var valor = parseFloat(sensor.medicao.ultimaMedicao);
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
          $('.sensores-direita').append("<li>" + sensor.nome + ": " + sensor.medicao.ultimaMedicao + "<li>");   
        }
        //$('.sensores-direita').append(sensor.nome + ": " + sensor.medicao.valor + "\n");        
      });
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