//pinos
  #define pino_rele_solenoid_1 D1
  #define pino_rele_bomba D2
  #define pino_temperatura D3
  #define pino_trigger_nivel D4 //amarelo
  #define pino_echo_nivel D5  //branco
  #define pino_fluxo_1 D6
  #define pino_fluxo_2 D7
//pinos

//sensor de nivel com utrassnic
  #include "Ultrasonic.h"
  Ultrasonic ultrasonic(pino_trigger_nivel, pino_echo_nivel);
  #define ERROSENSOR 5
  #define PROFUNDIDADEMAX 32
//sensor de nivel com utrassonic

//sensor de temperatura
  #include "OneWire.h"
  #include "DallasTemperature.h"
  OneWire oneWire(pino_temperatura);
  DallasTemperature sensors(&oneWire);
  float Celcius=0;
//sensor de temperatura

//json
  #include"ArduinoJson.h"
//json

//wifi  
  #include <ESP8266WiFiMulti.h>
  ESP8266WiFiMulti WiFiMulti;
  #define SSID_WIFI "Higor"
  #define PASSPHRASE_WIFI "higor1234"
//wifi

//post
  #include <ESP8266HTTPClient.h>
//post

typedef struct Var{
  char idv[64];
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

String criaJson(JsonFormat* jsonFormat){
  const size_t bufferSize = JSON_ARRAY_SIZE(jsonFormat->nVarInt) + jsonFormat->nVarInt * JSON_OBJECT_SIZE(2) + JSON_OBJECT_SIZE(8);

  DynamicJsonBuffer jsonBuffer(bufferSize);
  JsonObject& root = jsonBuffer.createObject();
  root["idm"] = jsonFormat->idm;
  root["ids"] = jsonFormat->ids;
  root["idt"] = jsonFormat->idt;
  root["tipo"] = jsonFormat->tipo;
  root["timestamp"] = jsonFormat->timeStamp;
  root["nvar"] = jsonFormat->nVar;
  root["community"] = jsonFormat->community;

  JsonArray& medicoes = root.createNestedArray("medicoes");
  int i = 0;
  for(i = 0 ; i < jsonFormat->nVarInt ; i++){
    JsonObject& arrayObject = medicoes.createNestedObject();
    arrayObject["idv"] = jsonFormat->vars[i].idv;
    arrayObject["valor"] = jsonFormat->vars[i].val;
  }

  String teste;
  
  //root.printTo(Serial);
  root.printTo(teste);
  return teste;
}

void setup() {
  Serial.begin(115200);

  strlcpy(jsonFormat.idm, "0", 4);
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
  }

  pinMode(pino_rele_bomba, OUTPUT_OPEN_DRAIN);
  pinMode(pino_rele_solenoid_1, OUTPUT_OPEN_DRAIN);

  digitalWrite(pino_rele_solenoid_1,  HIGH);
  
  pinMode(pino_fluxo_1,INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(pino_fluxo_1), incpulso1, RISING); 
  pinMode(pino_fluxo_2,INPUT_PULLUP);
  attachInterrupt(digitalPinToInterrupt(pino_fluxo_2), incpulso2, RISING); 

  //configurando wifi
  WiFiMulti.addAP(SSID_WIFI, PASSPHRASE_WIFI);
}

void loop() {
  if((WiFiMulti.run() == WL_CONNECTED)) {
    //sensor de nivel com utrassonic
    float cmMsec = ultrasonic.convert(ultrasonic.timing(), Ultrasonic::CM) - ERROSENSOR;
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
    http.begin("192.168.71.127",8000,"/iotegrator");
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
  delay (1000);
}

void incpulso1 ()
{ 
    contaPulso1++; //Incrementa a variável de contagem dos pulsos
}

void incpulso2 ()
{ 
    contaPulso2++; //Incrementa a variável de contagem dos pulsos
}
