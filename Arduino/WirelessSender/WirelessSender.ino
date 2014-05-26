#include <SPI.h>
#include "nRF24L01.h"
#include "RF24.h"

#include "Wire.h"
#include "I2Cdev.h"
#include "MPU6050.h"

RF24 radio(9,10);

MPU6050 accelgyro;

int16_t ax, ay, az;
int16_t gx, gy, gz;
int16_t mx, my, mz;

int16_t flexa, flexb, flexc, flexd, flexe;

int16_t buttons = 0;

int16_t packet = 0;

unsigned long starttime = 0;
unsigned long stoptime = 0;

int16_t time = 0;

void setup() {
  radio.begin();
  radio.setAutoAck(1);
  
  radio.openWritingPipe(0xABCDABCD71LL);
  
  Wire.begin();
  accelgyro.initialize();
  
  pinMode(2, INPUT_PULLUP);
  pinMode(3, INPUT_PULLUP);
  pinMode(4, INPUT_PULLUP);
  pinMode(5, INPUT_PULLUP);
  pinMode(6, INPUT_PULLUP);
  pinMode(7, INPUT_PULLUP);
  pinMode(8, INPUT_PULLUP);
  pinMode(A6, INPUT_PULLUP);
}

void loop() {
  //accelgyro.getMotion9(&ax, &ay, &az, &gx, &gy, &gz, &mx, &my, &mz);
  accelgyro.getMotion6(&ax, &ay, &az, &gx, &gy, &gz);
  
  flexa = analogRead(A0);
  flexb = analogRead(A1);
  flexc = analogRead(A2);
  flexd = analogRead(A3);
  flexe = analogRead(A7);
  
  bitWrite(buttons, 0, digitalRead(2));
  bitWrite(buttons, 1, digitalRead(3));
  bitWrite(buttons, 2, digitalRead(4));
  bitWrite(buttons, 3, digitalRead(5));
  bitWrite(buttons, 4, digitalRead(6));
  bitWrite(buttons, 5, digitalRead(7));
  bitWrite(buttons, 6, digitalRead(8));
  bitWrite(buttons, 7, digitalRead(A6));
  
  packet++;
  packet = packet % 1000;
  
  
  int16_t buffer [] = {
      ax, ay, az,
      gx, gy, gz,
      mx, my, mz,
      flexa, flexb, flexc,
      flexd, flexe,
      buttons,
      packet};
  
  
  
  radio.write(&buffer, sizeof(int16_t [16]));
  
  //delay(10);
}
