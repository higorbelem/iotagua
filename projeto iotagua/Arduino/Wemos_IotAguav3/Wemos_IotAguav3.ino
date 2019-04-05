/**
 * BasicHTTPClientPost.ino
 * 
 * See http://posttestserver.com/ for the HTTP server details.
 */


//sensor ultra
#include "Ultrasonic.h"
#define pino_trigger D4 //amarelo
#define pino_echo D5  //branco
Ultrasonic ultrasonic(pino_trigger, pino_echo);
#define ERROSENSOR 5
#define PROFUNDIDADEMAX 32

//wifi
#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>

//json
#include"ArduinoJson.h"

#include <ESP8266HTTPClient.h>
#define USE_SERIAL Serial

//reles
#define RELE_BOMBA D5 //pinMode(RELE_BOMBA, OUTPUT_OPEN_DRAIN); digitalWrite(RELE_BOMBA,  LOW);
#define RELE_SOLENOIDE_MEIO D4   //pinMode(RELE_SOLENOIDE_MEIO, OUTPUT_OPEN_DRAIN); digitalWrite(RELE_SOLENOIDE_MEIO,  LOW);

ESP8266WiFiMulti WiFiMulti;

float vazao1,vazao2;
float media1,media2;
int contaPulso1,contaPulso2;
int i,j;

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

String criaJson(JsonFormat* jsonFormat){
  const size_t bufferSize = JSON_ARRAY_SIZE(jsonFormat.nVarInt) + jsonFormat.nVarInt * JSON_OBJECT_SIZE(2) + JSON_OBJECT_SIZE(8);

  DynamicJsonBuffer jsonBuffer(bufferSize);
  JsonObject& root = jsonBuffer.createObject();
  root["idm"] = jsonFormat.idm;
  root["ids"] = jsonFormat.ids;
  root["idt"] = jsonFormat.idt;
  root["tipo"] = jsonFormat.tipo;
  root["timestamp"] = jsonFormat.timeStamp;
  root["nvar"] = jsonFormat.nVar;
  root["community"] = jsonFormat.community;

  JsonArray& medicoes = root.createNestedArray("medicoes");
  int i = 0;
  for(i = 0 ; i < nVarInt ; i++){
    JsonObject& arrayObject = medicoes.createNestedObject();
    arrayObject["idv"] = jsonFormat.vars[i].idv;
    arrayObject["valor"] = jsonFormat.vars[i].val;
  }

  String teste;
  
  //root.printTo(Serial);
  root.printTo(teste);
  return teste;
}

void setup() {

//A função deve retornar a string...
    USE_SERIAL.begin(115200);

    pinMode(D6,INPUT);
    pinMode(D7,INPUT);

    attachInterrupt(D6,incPulso1,RISING);
    attachInterrupt(D7,incPulso2,RISING);
    
    /*for(uint8_t t = 4; t > 0; t--) {
        USE_SERIAL.printf("[SETUP] WAIT %d...\n", t);
        USE_SERIAL.flush();
        delay(1000);
    }
    WiFiMulti.addAP("merda", "merdinha");
*/
    //WiFiMulti.addAP("PROF_UESC", "ProFUesC20@10!&(");
}

void loop() {
    // wait for WiFi connection
    //if((WiFiMulti.run() == WL_CONNECTED)) {

        contaPulso1 = 0;
        contaPulso2 = 0;
        sei();
        delay(1000);
        cli();

        vazao1 = contaPulso1 / 5.5;
        media1 = media1 + vazao1;
        vazao2 = contaPulso2 / 5.5;
        media2 = media2 + vazao2;

        Serial.print("Fluxo em ml(1): ");
        Serial.println(media1);
        Serial.print("Fluxo em ml(2): ");
        Serial.println(media2);

       /* USE_SERIAL.print(media1);
        USE_SERIAL.print("\n");
        USE_SERIAL.print(media2);
        USE_SERIAL.print("\n");
        USE_SERIAL.print("\n");*/
        
        /*Var *var = (Var*) malloc(2*sizeof(Var));
        if(var == NULL){
          exit(0);
        }
        strcpy(var[0].idv, "1");
        var[0].val = media1;
        strcpy(var[1].idv, "2");
        var[1].val = media2;
        
        String json = criaJson("0","2","0","w","timesta","2", 2,"community",var);
        free(var);
        //USE_SERIAL.print(json+"\n");
        HTTPClient http;

        USE_SERIAL.print("[HTTP] begin...\n");
        http.begin("192.168.71.127",8000,"/iotegrator");
        http.addHeader("Content-Type", "application/json", false, true);
        
        USE_SERIAL.print("[HTTP] POST...\n");
        // start connection and send HTTP header
        int httpCode = http.POST(json); // Argumentos: 

        // httpCode will be negative on error
        if(httpCode > 0) {
            // HTTP header has been send and Server response header has been handled
            USE_SERIAL.printf("[HTTP] POST... code: %d\n", httpCode);

            // file found at server
            if(httpCode == HTTP_CODE_OK) {
                String payload = http.getString();
                USE_SERIAL.println(payload);
            }
        } else {
            USE_SERIAL.printf("[HTTP] POST... failed, error: %s\n", http.errorToString(httpCode).c_str());
        }

        http.end();*/
    //}

    delay(5000);
}

float GetSNA(){
  float cmMsec;
  long microsec = ultrasonic.timing();
  cmMsec = ultrasonic.convert(microsec, Ultrasonic::CM) - ERROSENSOR;

  float percent = 100 - (cmMsec/PROFUNDIDADEMAX*100);

  return percent;
}

void LigaDesigaBomba(bool ligar){
  
}

void incPulso1 ()
{ 
    contaPulso1++; //Incrementa a variável de contagem dos pulsos
}

void incPulso2 ()
{ 
    contaPulso2++; //Incrementa a variável de contagem dos pulsos
}
