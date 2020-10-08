#include <ESP8266WiFi.h>

const int trigPin = 2;  //D4
const int echoPin = 0;  //D3
const char* server = "garbage-monitoring-syste-f1f56.appspot.com";

// defines variables
long duration;
int distance;
int percentage;

const char *ssid =  "Manas_phone";    
const char *pass =  "12345678";

const int height=30;

const int redPin=12; //D5
const int greenPin=14; //D6

WiFiClient client;

void setup() {
pinMode(trigPin, OUTPUT); // Sets the trigPin as an Output
pinMode(echoPin, INPUT); // Sets the echoPin as an Input
pinMode(redPin, OUTPUT);
pinMode(greenPin, OUTPUT);
Serial.begin(115200); // Starts the serial communication

 Serial.println("Connecting to ");
       Serial.println(ssid);
 

       WiFi.begin(ssid, pass);
 
      while (WiFi.status() != WL_CONNECTED) 
     {
            delay(1000);
            Serial.print(".");
     }
      Serial.println("");
      Serial.println("WiFi connected");
}

void loop() {

// Clears the trigPin
digitalWrite(trigPin, LOW);
delayMicroseconds(2);

// Sets the trigPin on HIGH state for 10 micro seconds
digitalWrite(trigPin, HIGH);
delayMicroseconds(10);
digitalWrite(trigPin, LOW);

// Reads the echoPin, returns the sound wave travel time in microseconds
duration = pulseIn(echoPin, HIGH);

// Calculating the distance
distance= duration*0.034/2;
// Prints the distance on the Serial Monitor
Serial.print("Distance: ");
Serial.println(distance);
String docstr="{id:1,dist:"+String(distance)+"}";

percentage=distance*100/height;

if(percentage<30){
  Serial.println("RED");
  digitalWrite(redPin,HIGH);
  digitalWrite(greenPin,LOW);
}
else{
  Serial.println("GREEN");
  digitalWrite(redPin,LOW);
  digitalWrite(greenPin,HIGH);
}

if(WiFi.status()==WL_CONNECTED){

  if (client.connect(server,80))   
        {  
                            
          String postStr = "";
          postStr +="id=";
           postStr += "S1";
           postStr +="&dist=";
           postStr += String(distance);
           postStr +="&percentage=";
           postStr +=String(percentage);
           postStr += "\r\n\r\n\r\n";
 
          client.print("POST /distance HTTP/1.1\n");
          client.print("Host: garbage-monitoring-syste-f1f56.appspot.com\n");
          client.print("Connection: close\n");
          client.print("Content-Type: application/x-www-form-urlencoded\n");
          client.print("Content-Length: ");
          client.print(postStr.length());
          client.print("\n\n");
          client.print(postStr);
                        }
                        else{
                          Serial.println("Connection Failed");
                        }
                        
          client.stop();
}
delay(20000);
}
