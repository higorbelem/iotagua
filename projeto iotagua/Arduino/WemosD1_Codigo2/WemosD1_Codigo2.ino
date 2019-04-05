#include <ESP8266WiFi.h>
#include <ArduinoHttpClient.h>
const char* ssid = "Higor";
const char* password = "higor1234";
//char serverAddress[] = "foodapp.ga"; // server address

int port = 21;
const char* host = "http://foodapp.ga";

WiFiClient wifi;
HttpClient client = HttpClient(wifi, host, port);
int status = WL_IDLE_STATUS;


String response;
int statusCode = 0;

void setup() {
Serial.begin(19200);
delay(10);

// We start by connecting to a WiFi network

Serial.println();
Serial.println();
Serial.print("Connecting to ");
Serial.println(ssid);


/* Explicitly set the ESP8266 to be a WiFi-client, otherwise, it by default,
would try to act as both a client and an access-point and could cause
network-issues with your other WiFi-devices on your WiFi-network. */
WiFi.mode(WIFI_STA);
WiFi.begin(ssid, password);
Serial.println("Signal Strenght : " + String(WiFi.RSSI()) + " dBm");

while (WiFi.status() != WL_CONNECTED) {
delay(500);
Serial.print(".");
}

Serial.println("");
Serial.println("WiFi connected");
Serial.println("IP address: ");
Serial.println(WiFi.localIP());
}

int value = 0;

void loop() {

  Serial.println("making POST request");
  String contentType = "application/x-www-form-urlencoded";
  String postData = "";
  client.post("/login/testeIc.php", contentType, postData);
  // read the status code and body of the response
  statusCode = client.responseStatusCode();
  response = client.responseBody();
  
  Serial.print("Status code: ");
  Serial.println(statusCode);
  Serial.print("Response: ");
  Serial.println(response);
  
  
  Serial.println("Wait five seconds");
  delay(5000);

}


