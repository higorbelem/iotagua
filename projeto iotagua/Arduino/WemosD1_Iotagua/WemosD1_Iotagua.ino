#include <ESP8266WiFi.h>

float SFA, SFA2, Litros = 0, Litros2 = 0, Mililitros = 0, Mililitros2 = 0, media = 0, media2 = 0; //Variável para armazenar o valor em L/min
int atual, SNA, contaPulso, contaPulso2, Min = 0, Min2 = 0 , i = 0, i2 = 0; //Variável para a quantidade de pulsos

String apiKey = "HFEOKU9I2OETJURD";             // Poner el API KEY del canal de Thingspeak entre las comillas
/*const char* ssid = "PROF_UESC";                    // Poner el nombre de la red WiFi entre las comillas
const char* password = "ProFUesC20@10!&(";*/             // Poner la contraseña de la red WiFi entre las comillas
const char* server = "dweet.io";
const char* server2 = "http:/foodapp.ga";

const char* ssid = "BatCaverna-2.4GHz";                    // Poner el nombre de la red WiFi entre las comillas
const char* password = "kikathecat";

/*const char* ssid = "Blue";                    // Poner el nombre de la red WiFi entre las comillas
const char* password = "ewmg1726";*/
 
String rele; // salvara a string "ON" quando relé estiver ligado e "Off" quando estiver desligado. 
int porta_rele = D8; // porta digital do Relé

WiFiClient client;

boolean ligado = true;

 
void setup(){
  Serial.begin(115200);  
    //pinMode(D7, OUTPUT_OPEN_DRAIN);
    pinMode(porta_rele, OUTPUT); 
    Serial.begin(115200);                             // Poner monitor serie a 115200 baudios
    delay(10);


    attachInterrupt(D3, incpulso, RISING); 
    attachInterrupt(D7, incpulso2, RISING); 

     
    WiFi.begin(ssid, password);
     
    Serial.print("\n\nConectando a WiFi ");
    Serial.println(ssid);
     
    WiFi.begin(ssid, password);
     
    while (WiFi.status() != WL_CONNECTED){ // imprime "...." até criar um conexão com a rede wireless
      delay(500);
      Serial.print(".");
    }
    
    Serial.println("");
    Serial.print("Conexion satisfactoria a WiFi ");
    Serial.println(ssid);
 
}
 
void loop() {    

    /*SNA = analogRead(A0); // lê o valor analógico do SNA
  
    if(SNA > 920 ){
        //rele = "off";
        //digitalWrite(porta_rele,  LOW); // Desliga
    }else{
        //rele = "On";
        //digitalWrite(porta_rele, HIGH); // Liga
    }
      // Salva na variavél atual, qual é o nivel lido no SNA
         if(SNA <= 147) atual = 1;
          if(SNA >= 148 && SNA <= 150) atual = 2;  // Valores obtidos através de teste
           if(SNA >= 151 && SNA <= 153) atual = 3;
            if(SNA >= 154 && SNA <= 159) atual = 4;
             if(SNA >= 160 && SNA <= 9999) atual = 5;
              if(SNA >= 9999 && SNA <= 9999) atual = 6;
               if(SNA >= 9999 && SNA <= 9999) atual = 7;

    Serial.println("SNA: " + String(SNA) + " | " + String(atual));
    */
    contaPulso = 0;
    contaPulso2 = 0;
    
    sei();        //Habilita interrupção
    delay (1000); //Aguarda 1 segundo
    cli();        //Desabilita interrupção


  //------------------------------------------------------------------------------------ Trecho do código responsavel por fazer o calculo do gasto da água  SFA 1 -----------------------------------------------
     SFA  = contaPulso / 5.5; //Converte para L/min
     media += SFA; //Soma a vazão para o calculo da media
     i++;
     Serial.println(String(SFA) + " L/min " + String(Min) + " : " + String(i) + " s " ); //Escreve no display o valor da vazão  
     Mililitros = SFA / 60;
     Litros += Mililitros;
     Serial.println(String(Litros) + " L " );
      
     // Neste conjunto de linhas fizemos a média das leituras obtidas a cada 1 minuto
     if (i == 60){
     Min++;
       
     media = media / 60 * Min ; //faz a média
     Serial.print("Media por minuto = "  + String(media) + " L/min "); //Imprime a frase Media por minuto =
      
      i = 0; //Zera a variável i para uma nova contagem  
     }  

 //------------------------------------------------------------------------------------ Trecho do código responsavel por fazer o calculo do gasto da água  SFA 2 -----------------------------------------------
     SFA2  = contaPulso2 / 5.5; //Converte para L/min
     media2 += SFA2; //Soma a vazão para o calculo da media
     i++;
     Serial.println(String(SFA2) + " L/min " + String(Min2) + " : " + String(i2) + " s " ); //Escreve no display o valor da vazão  
     Mililitros2 = SFA2 / 60;
     Litros2 += Mililitros2;
     Serial.println(String(Litros2) + " L " );
      
     // Neste conjunto de linhas fizemos a média das leituras obtidas a cada 1 minuto
     if (i2 == 60){
     Min2++;
       
     media2 = media2 / 60 * Min2 ; //faz a média
     Serial.print("Media por minuto = "  + String(media2) + " L/min "); //Imprime a frase Media por minuto =
      
      i2 = 0; //Zera a variável i para uma nova contagem  
     }

      
// -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------               
      

    
   // SFA = contaPulso / 5.5; //Converte para L/min
   // SFA2 = contaPulso2 / 5.5; //Converte para L/min

     
    /*
    if (client.connect(server,80)) {

       /*  client.print(String("POST /dweet/for/iotagua?SensorFluxo1=")  + String(SFA) + "&SensorFluxo2=" + String(SFA2) + "&SensorNivel=" + String(atual) + "&Rele=" + String(rele) +  " HTTP/1.1\r\n" + "Host: " + server + "\r\n" + "Connection: close\r\n\r\n");
      
        Serial.println("###############################");

        Serial.println("Sensor Level: " + String(SNA));
        Serial.println("Relé " + String(rele));
        Serial.print("Vazão 1: ");
        Serial.print(SFA); //Imprime na serial o valor da vazão
        Serial.println(" L/min"); //Imprime L/min

        Serial.print("Vazão 2: ");
        Serial.print(SFA2); //Imprime na serial o valor da vazão
        Serial.println(" L/min"); //Imprime L/min

        Serial.println("Enviando dados a Dweet.io");
    }
    */
  
    /*if (client.connect(server2,21)) {

        client.print(String("POST /login/registrar.php?nome=merda&email=gmail&senha=1234")  +  " HTTP/1.1\r\n" + "Host: " + server2 + "\r\n" + "Connection: close\r\n\r\n");
    }
    
    client.stop();*/
 
    //Serial.println("Esperando 5 segundos");
    delay(500);
}


void incpulso ()
{ 
    contaPulso++; //Incrementa a variável de contagem dos pulsos
}

void incpulso2 ()
{ 
    contaPulso2++; //Incrementa a variável de contagem dos pulsos
} 
