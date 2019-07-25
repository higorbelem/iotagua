
//sensor de nivel com utrassnic
  //#include "Ultrasonic.h"
  //Ultrasonic ultrasonic(D1, D2);
  #define ERROSENSOR 5
  #define PROFUNDIDADEMAX 34
//sensor de nivel com utrassonic

//sensor de temperatura
  #include "OneWire.h"
  #include "DallasTemperature.h"
  OneWire oneWire(-1);
  DallasTemperature sensors(&oneWire);
//sensor de temperatura

//json
  //#include"ArduinoJson.h"
  #include "ArduinoJson-v6.11.1.h"
//json

//wifi  
  #include <ESP8266WiFiMulti.h>
  ESP8266WiFiMulti WiFiMulti;
  #define SSID_WIFI "Higor"
  #define PASSPHRASE_WIFI "higor1234"
  //#define SSID_WIFI "BatCaverna-2.4GHz"
  //#define PASSPHRASE_WIFI "kikathecat"
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

int teste = 0;
int contaPulso1,contaPulso2;
float SFA;
JsonFormat jsonFormat;
Var* var;
bool configFinalizado = false;
StaticJsonDocument<3000> jsonDoc;
StaticJsonDocument<3000> acoesDocs;

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

  StaticJsonDocument<500> doc;

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

    //Serial.printf("id: %d, valor: %f\n", jsonFormat->vars[i].idv, jsonFormat->vars[i].val);
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
  http.begin("192.168.43.78",8000,"/iotegrator");
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

int convertPin(String pin){
  if(pin.equals("D0")){
    return 16;
  }else if(pin.equals("D1")){
    return 5;
  }else if(pin.equals("D2")){
    return 4;
  }else if(pin.equals("D3")){
    return 0;
  }else if(pin.equals("D4")){
    return 2;
  }else if(pin.equals("D5")){
    return 14;
  }else if(pin.equals("D6")){
    return 12;
  }else if(pin.equals("D7")){
    return 13;
  }else if(pin.equals("D8")){
    return 15;
  }else{
    return -1;
  }
}

void setup() {
  Serial.begin(115200);

  cli();   

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
              
              uint8_t pTrigger = convertPin(jsonDoc[i]["pinos"][0]["trigger"].as<char*>());
              uint8_t pEcho = convertPin(jsonDoc[i]["pinos"][1]["echo"].as<char*>());
              
              pinMode(pTrigger, OUTPUT);
              pinMode(pEcho, INPUT); 
            }else if(nome.indexOf("fluxo") >= 0){

              uint8_t pino_fluxo = convertPin(jsonDoc[i]["pinos"][0]["fluxo"].as<char*>());
                            
              pinMode(pino_fluxo,INPUT_PULLUP);
              if(i == 4){
                attachInterrupt(digitalPinToInterrupt(pino_fluxo), incpulso1, RISING); 
              }else{
                attachInterrupt(digitalPinToInterrupt(pino_fluxo), incpulso2, RISING); 
              }
            }else if(nome.indexOf("temperatura") >= 0){

              uint8_t pino_temperatura = convertPin(jsonDoc[i]["pinos"][0]["temperatura"].as<char*>());
                            
              oneWire = OneWire(pino_temperatura);
              DallasTemperature sensors(&oneWire);
            }else if(nome.indexOf("bomba") >= 0 || nome.indexOf("solenoide") >= 0){
              uint8_t pino_rele = convertPin(jsonDoc[i]["pinos"][0]["rele"].as<char*>());
              pinMode(pino_rele, OUTPUT_OPEN_DRAIN);
              digitalWrite(pino_rele,  HIGH);  
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
          long duration;
          float distance;

          uint8_t pTrigger = convertPin(jsonDoc[i]["pinos"][0]["trigger"].as<char*>());
          uint8_t pEcho = convertPin(jsonDoc[i]["pinos"][1]["echo"].as<char*>());
          
          //Serial.printf("trigger: (%d, %s) , echo: (%d, %s)\n", pTrigger, jsonDoc[i]["pinos"][0]["trigger"].as<char*>(), pEcho, jsonDoc[i]["pinos"][1]["echo"].as<char*>());
          
          digitalWrite(pTrigger, LOW);
          delayMicroseconds(2);
          digitalWrite(pTrigger, HIGH);
          delayMicroseconds(10);
          digitalWrite(pTrigger, LOW);
          duration = pulseIn(pEcho, HIGH);
          //Calculate the distance (in cm) based on the speed of sound.
          distance = (duration/58.2) - ERROSENSOR;

          float percent = 100 - (distance/(PROFUNDIDADEMAX - ERROSENSOR)*100);

          if(percent > 100) percent = 100;
          if(percent < 0) percent = 0;
          
          var[i].idv = jsonDoc[i]["id"].as<int>();
          var[i].val = percent;
          
          //Serial.printf("2trigger: %d , echo: %d\n", pTrigger, pEcho);
          //Serial.printf("Id: %d , valor: %d\n", jsonDoc[i]["id"].as<int>(), percent);
        }else if(nome.indexOf("fluxo") >= 0){
          //Serial.printf("quant: %d", jsonFormat.nVarInt);
          //Serial.printf("Id: %d\n", jsonDoc[i]["id"].as<int>());
          contaPulso1 = 0;
          contaPulso2 = 0;

          teste = i;
          sei();        //Habilita interrupção
          delay (1000); //Aguarda 1 segundo
          cli();   

          if(teste == 4){
            SFA  = contaPulso1 / 5.5;
          }else if(teste == 5){
              SFA = contaPulso2 / 5.5;
            }
          //SFA  = contaPulso1 / 5.5; //Converte para L/min

          //Serial.printf("SFA: %f",SFA);
          
          var[i].idv = jsonDoc[i]["id"].as<int>();
          var[i].val = SFA; 
               
        }else if(nome.indexOf("temperatura") >= 0){
          float Celcius=0;
          sensors.requestTemperatures(); 
          Celcius=sensors.getTempCByIndex(0);
        
          var[i].idv = jsonDoc[i]["id"].as<int>();
          var[i].val = Celcius; 
        }else if(nome.indexOf("bomba") >= 0){
          var[i].idv = jsonDoc[i]["id"].as<int>();
          var[i].val = -1;   
        }else if(nome.indexOf("solenoide") >= 0){
          var[i].idv = jsonDoc[i]["id"].as<int>();
          var[i].val = -1; 
        }
        
      }
      
      jsonFormat.vars = var;
      
      String res = sendHttp(&jsonFormat);

      if(res != ""){
        Serial.println(res);
        DeserializationError err = deserializeJson(acoesDocs, res);

        if(err){
          Serial.print("erro: ");
          Serial.println(err.c_str());
        }else{
          int i = 0;
          int j = 0;
          for(i = 0 ; i < acoesDocs.size() ; i++){
            for(j = 0 ; j < acoesDocs[i].size() ; j++){
              int acao = acoesDocs[i][j]["acao"].as<int>();
              uint8_t pinRele = convertPin(acoesDocs[i][j]["pinos"][0]["rele"].as<char*>());
              Serial.println(acao);
              Serial.println(pinRele);
              if(acao == 0){
                digitalWrite(pinRele,  HIGH);     
              }else if(acao == 1){
                digitalWrite(pinRele,  LOW);     
              }
            }
          }
        }
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
  }else{
    Serial.println("Não conseguiu conectar ao wifi, tentando novamente...");
    delay(1000);
  }
}

void incpulso1 ()
{ 
  contaPulso1++;
}

void incpulso2 ()
{ 
  contaPulso2++;
}
