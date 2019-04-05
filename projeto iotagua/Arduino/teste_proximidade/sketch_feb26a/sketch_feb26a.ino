
#include "Ultrasonic.h"
 
#define pino_trigger D4 //amarelo
#define pino_echo D5  //branco
 
Ultrasonic ultrasonic(pino_trigger, pino_echo);

#define ERROSENSOR 5
#define PROFUNDIDADEMAX 32
 
void setup()
{
  Serial.begin(9600);
  Serial.println("Lendo dados do sensor...");
}
 
void loop()
{
  float cmMsec;
  long microsec = ultrasonic.timing();
  cmMsec = ultrasonic.convert(microsec, Ultrasonic::CM) - ERROSENSOR;

  float percent = 100 - (cmMsec/PROFUNDIDADEMAX*100);

  Serial.print("Distancia em cm: ");
  Serial.println(cmMsec);
  Serial.print("Distancia porc: ");
  Serial.println(percent);
  delay(1000);
}
