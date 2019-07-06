
//sensor de nivel com utrassnic
  //#include "Ultrasonic.h"
  //Ultrasonic ultrasonic(D1, D2);
  //#define ERROSENSOR 5
  //#define PROFUNDIDADEMAX 32
//sensor de nivel com utrassonic

//sensor de temperatura
  //#include "OneWire.h"
  //#include "DallasTemperature.h"
  //OneWire oneWire(pino_temperatura);
  //DallasTemperature sensors(&oneWire);
  //float Celcius=0;
//sensor de temperatura

//json
  //#include"ArduinoJson.h"
  #include "ArduinoJson-v6.11.1.h"
//json

//wifi  
  #include <ESP8266WiFiMulti.h>
  ESP8266WiFiMulti WiFiMulti;
  //#define SSID_WIFI "Higor"
  //#define PASSPHRASE_WIFI "higor1234"
  #define SSID_WIFI "BatCaverna-2.4GHz"
  #define PASSPHRASE_WIFI "kikathecat"
//wifi

//post
  #include <ESP8266HTTPClient.h>
//post

typedef struct Var{
  int idv;
  float val;
}Var;

typedef struct JsonFormat{
  char idm[4];
  char ids[4];
  char idt[16];
  char tipo[4];
  char timeStamp[8];
  char nVar[1];
  int nVarInt;
  char community[64];
  Var* vars;
}JsonFormat;

int contaPulso1, contaPulso2;
float SFA , SFA2;
JsonFormat jsonFormat;
Var* var;
bool configFinalizado = false;
StaticJsonDocument<3000> jsonDoc;

bool desserializaJson(String json){
  const int capacity = JSON_ARRAY_SIZE(20) + JSON_OBJECT_SIZE(5) + 2 * JSON_ARRAY_SIZE(1);
  
  //StaticJsonDocument<3000> jsonDoc;

  //deserializeJson(jsonDoc,json);
  DeserializationError err = deserializeJson(jsonDoc, json);

  if(err){
    Serial.print("erro: ");
    Serial.println(err.c_str());
    return false;
  }
  
  //Serial.println(jsonDoc[0]["nome"].as<char*>());
  //serializeJson(jsonDoc[0], Serial);
  //Serial.println(obj["nome"]);

  //criar um novo struct com o formato do retorno de sensores e popupar a lista de sensores

  return true;
}

String criaJson(JsonFormat* jsonFormat){
  const int bufferSize = JSON_ARRAY_SIZE(jsonFormat->nVarInt) + jsonFormat->nVarInt * JSON_OBJECT_SIZE(2) + JSON_OBJECT_SIZE(8);

  StaticJsonDocument<300> doc;

  doc["idm"] = jsonFormat->idm;
  doc["ids"] = jsonFormat->ids;
  doc["idt"] = jsonFormat->idt;
  doc["tipo"] = jsonFormat->tipo;
  doc["timestamp"] = jsonFormat->timeStamp;
  doc["nvar"] = jsonFormat->nVar;
  doc["community"] = jsonFormat->community;

  JsonArray medicoes = doc.createNestedArray("medicoes");
  int i = 0;
  for(i = 0 ; i < jsonFormat->nVarInt ; i++){
    JsonObject arrayObject = medicoes.createNestedObject();
    arrayObject["idv"] = jsonFormat->vars[i].idv;
    arrayObject["valor"] = jsonFormat->vars[i].val;
  }

  String teste = doc.as<String>();
  
  //root.printTo(Serial);
  return teste;
}

String sendHttp(JsonFormat *jsonFormat){
  String payload = "";

  String json = criaJson(jsonFormat);

  Serial.println(json);
  
  HTTPClient http;
  http.begin("192.168.0.105",8000,"/iotegrator");
  http.addHeader("Content-Type", "application/json", false, true);

  int httpCode = http.POST(json);
  if(httpCode > 0) {
    // file found at server
    //payload = http.getString();
    //Serial.printf("%d",httpCode);
    if(httpCode == HTTP_CODE_ACCEPTED) {
        payload = http.getString();
    }
  } else {
    Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
  }
  http.end();
  
  return payload;
}

void setup() {
  Serial.begin(115200);

  /*strlcpy(jsonFormat.idm, "0", 4);
  strlcpy(jsonFormat.ids, "1", 4);
  strlcpy(jsonFormat.idt, "2", 16);
  strlcpy(jsonFormat.tipo, "w", 4);
  strlcpy(jsonFormat.timeStamp, "1021902", 8);
  strlcpy(jsonFormat.nVar, "4", 1);
  jsonFormat.nVarInt = 4;
  strlcpy(jsonFormat.community, "asdasads", 64);
  
  var = (Var*) malloc(4*sizeof(Var));
  if(var == NULL){
    exit(0);
  }*/
  
  //pinMode(pinNivelTrigger, OUTPUT);
  //pinMode(pinNivelEcho, INPUT);  

  /*pinMode(pino_rele_bomba, OUTPUT_OPEN_DRAIN);
  pinMode(pino_rele_solenoid_1, OUTPUT_OPEN_DRAIN);

  digitalWrite(pino_rele_solenoid_1,  HIGH);
  
  pinMode(pino_fluxo_1,INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(pino_fluxo_1), incpulso1, RISING); 
  pinMode(pino_fluxo_2,INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(pino_fluxo_2), incpulso2, RISING); */

  //configurando wifi
  WiFiMulti.addAP(SSID_WIFI, PASSPHRASE_WIFI);
}

void loop() {
  
  if((WiFiMulti.run() == WL_CONNECTED)) {

    if(configFinalizado == false){
      strlcpy(jsonFormat.idm, "1", 4);
      strlcpy(jsonFormat.ids, "1", 4);
      strlcpy(jsonFormat.idt, "1", 16);
      strlcpy(jsonFormat.tipo, "c", 4);
      strlcpy(jsonFormat.timeStamp, "1021902", 8);
      strlcpy(jsonFormat.nVar, "4", 1);
      jsonFormat.nVarInt = 0;
      strlcpy(jsonFormat.community, "asdasads", 64);
      
      /*var = (Var*) malloc(4*sizeof(Var));
      if(var == NULL){
        exit(0);
      }*/
      String res = sendHttp(&jsonFormat);

      if(res != ""){
        configFinalizado = true;

        Serial.println(res);

        if(desserializaJson(res)){
          //Serial.println(jsonDoc[0]["nome"].as<char*>());

          int i = 0;
          for(i = 0 ; i < jsonDoc.size() ; i++){
            //Serial.println(jsonDoc[i]["nome"].as<char*>());
            String nome = jsonDoc[i]["nome"].as<char*>();
            
            if(nome.indexOf("nivel") >= 0){
              //Serial.println(jsonDoc[i]["pinos"][0]["pino1"].as<char*>());
              
              int pTrigger = jsonDoc[i]["pinos"][0]["trigger"].as<uint8_t>();
              int pEcho = jsonDoc[i]["pinos"][1]["echo"].as<uint8_t>();
              
              pinMode(pTrigger, OUTPUT);
              pinMode(pEcho, INPUT); 
            }
          }
        }
          
         //ultrasonic(pino_trigger_nivel, pino_echo_nivel)
      }else{
        Serial.println(res);
      }
    }else{

      strlcpy(jsonFormat.idm, "1", 4);
      strlcpy(jsonFormat.ids, "1", 4);
      strlcpy(jsonFormat.idt, "1", 16);
      strlcpy(jsonFormat.tipo, "w", 4);
      strlcpy(jsonFormat.timeStamp, "1021902", 8);
      strlcpy(jsonFormat.nVar, "1", 1);
      jsonFormat.nVarInt = jsonDoc.size();
      strlcpy(jsonFormat.community, "asdasads", 64);

      var = (Var*) malloc(jsonDoc.size()*sizeof(Var));
      if(var == NULL){
        exit(0);
      }

      int i = 0;
      for(i = 0 ; i < jsonDoc.size() ; i++){
        String nome = jsonDoc[i]["nome"].as<char*>();
            
        if(nome.indexOf("nivel") >= 0){
          long duration, distance;

          int pTrigger = jsonDoc[i]["pinos"][0]["trigger"].as<uint8_t>();
          int pEcho = jsonDoc[i]["pinos"][1]["echo"].as<uint8_t>();
      
          digitalWrite(pTrigger, LOW);
          delayMicroseconds(2);
          digitalWrite(pTrigger, HIGH);
          delayMicroseconds(10);
          digitalWrite(pTrigger, LOW);
          duration = pulseIn(pEcho, HIGH);
          //Calculate the distance (in cm) based on the speed of sound.
          distance = duration/58.2;
          
          var[i].idv = jsonDoc[i]["id"].as<int>();
          var[i].val = distance;
        }
        
      }
      
      jsonFormat.vars = var;
      
      String res = sendHttp(&jsonFormat);

      if(res != ""){
          
      }

      free(var);
      
      delay (2000);
      
      //sensor de nivel com utrassonic
      /*float cmMsec = ultrasonic.convert(ultrasonic.timing(), Ultrasonic::CM) - ERROSENSOR;
      float percent = 100 - (cmMsec/PROFUNDIDADEMAX*100);
  
      if(percent < 50){
        digitalWrite(pino_rele_bomba,  LOW);
      }else if(percent > 85){
        digitalWrite(pino_rele_bomba,  HIGH);
      }
      strcpy(var[0].idv, "SNA");
      var[0].val = percent;
      //sensor de nivel com utrassonic
    
      //sensor de fluxo
      contaPulso1 = 0;
      contaPulso2 = 0;
    
      sei();        //Habilita interrupção
      delay (1000); //Aguarda 1 segundo
      cli();   
    
      SFA  = contaPulso1 / 5.5; //Converte para L/min
      SFA2  = contaPulso2 / 5.5; //Converte para L/min
    
      strcpy(var[1].idv, "SFA1");
      var[1].val = SFA;
      strcpy(var[2].idv, "SFA2");
      var[2].val = SFA2;
      //sensor de fluxo
    
      //sensor de temperatura
      sensors.requestTemperatures(); 
      Celcius=sensors.getTempCByIndex(0);
    
      strcpy(var[3].idv, "temperatura");
      var[3].val = Celcius;
      //sensor de temperatura
    
      jsonFormat.vars = var;
      String json = criaJson(&jsonFormat);
  
      Serial.println(json);
    
      HTTPClient http;
      http.begin("192.168.43.78",8000,"/iotegrator");
      http.addHeader("Content-Type", "application/json", false, true);
    
      int httpCode = http.POST(json);
      if(httpCode > 0) {
        // file found at server
        if(httpCode == HTTP_CODE_OK) {
            String payload = http.getString();
            Serial.println(payload);
        }
      } else {
        Serial.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
      }
      http.end();
      //Serial.println(json);
    }else{
      Serial.println("Tentando se conectar ao WI-FI");
      WiFiMulti.addAP(SSID_WIFI, PASSPHRASE_WIFI);
    }
    delay (1000);*/
  }
}
}

void incpulso1 ()
{ 
    contaPulso1++; //Incrementa a variável de contagem dos pulsos
}

void incpulso2 ()
{ 
    contaPulso2++; //Incrementa a variável de contagem dos pulsos
}
