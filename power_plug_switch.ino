#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <WiFiManager.h>
#include <ESP8266mDNS.h>;


#define relay_1 14 
String room_light = "0";

ESP8266WebServer server; //server variable
void setup() {
  initializePin(); //call function
  Serial.begin(74880);
  delay(500);

  //Connect to network
  WiFiManager wifiManager;
  wifiManager.autoConnect("ESP8266","password");

  Serial.println("Connected.");

  if (!MDNS.begin("esp8266"))   {  Serial.println("Error setting up MDNS responder!");  }
      else                          {  Serial.println("mDNS responder started");  }
      
serverSection();
server.begin();
Serial.println("Server started");
}

void loop() {
  server.handleClient();
}

void initializePin(){
  
  pinMode(relay_1, OUTPUT);
  digitalWrite(relay_1, LOW);
}

void serverSection(){
  server.on("/", []() {
    server.send(200, "text/html", "<!DOCTYPE html><html><meta charset='UTF-8'><head></head><body><h2>Power_Plug</h2><h3><a href='/room_light'>Switch</a></body></html>");
  });

  server.on("/room_light", room_light_state);

  server.on("/status", all_state);
  
  server.begin();
}

void room_light_state(){
  if(room_light == "0"){
    room_light = "1";
    digitalWrite(relay_1, HIGH);
    server.send(200, "text/html", room_light);
  }else{
    room_light = "0";
    digitalWrite(relay_1, LOW);
    server.send(200, "text/html", room_light);
  }
}
void all_state(){
 
   server.send(200, "text/html", "{'rl':'"+room_light+"'}");

}
