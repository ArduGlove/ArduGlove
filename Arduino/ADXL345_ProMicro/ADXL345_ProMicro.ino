#include <Wire.h>
#include <ADXL345.h>


ADXL345 adxl;

void setup() {
  Serial.begin(9600);
  adxl.powerOn();
  
  pinMode(10, INPUT_PULLUP);
}

void loop() {
  int ax,ay,az;  
  adxl.readAccel(&ax, &ay, &az);
  
  double xAngle = atan( ax / (sqrt(square(ay) + square(az))));
  double yAngle = atan( ay / (sqrt(square(ax) + square(az))));
  double zAngle = atan( sqrt(square(ax) + square(ay)) / az);
  
  double r = sqrt(square(ax) + square(ay) + square(az));
  double theta = acos(az / r);
  double phi = atan2(ay, ax);
  
  if (az < 0) {
    //xAngle = 3.14159 - xAngle;
    yAngle = 3.14159 - yAngle;
  }

  Serial.print(theta);
  Serial.print(' ');
  Serial.print(phi);
  Serial.print(' ');
  Serial.print(ax);
  Serial.print(' ');
  Serial.print(ay);
  Serial.print(' ');
  Serial.print(az);
  Serial.print(' ');
  Serial.print(xAngle);
  Serial.print(' ');
  Serial.print(yAngle);
  Serial.print(' ');
  Serial.print(zAngle);
  Serial.print(' ');
  Serial.print(!digitalRead(10));
  Serial.print(' ');
  Serial.println(analogRead(A9));
  
  delay(2);
 
}
