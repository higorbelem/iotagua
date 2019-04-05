#include"ArduinoJson.h"

void setup() {

  Serial.begin(115200);
  
  const int nada = JSON_ARRAY_SIZE(5) + JSON_OBJECT_SIZE(4);
  StaticJsonBuffer<nada> jsonBuffer;

  JsonObject& root = jsonBuffer.createObject();
  root["sensor"] = "gps";
  root["time"] = 1351824120;

  JsonArray& data = root.createNestedArray("data");
  data.add(48.756080);
  data.add(2.302038);

  root.printTo(Serial);
}

void loop() {

}
