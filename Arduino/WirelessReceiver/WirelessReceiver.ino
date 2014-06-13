#include <SPI.h>
#include "nRF24L01.h"
#include "RF24.h"

RF24 radio(9,10);

void setup() {
  pinMode(2, OUTPUT);
  pinMode(3, OUTPUT);
  digitalWrite(2, HIGH);
  digitalWrite(3, LOW);
  
  Serial.begin(38400);
  
  radio.begin();
  radio.setAutoAck(1);
  radio.openReadingPipe(1, 0xABCDABCD71LL);
  
  radio.startListening();
  radio.printDetails();
}

void loop() {
  if(!radio.available()) return;
  int16_t buffer [16];
  
  while (radio.available()) {
    radio.read(&buffer, sizeof(int16_t [16]));
  }
  
  Serial.print(' ');
  // First character can get corrupted by previous data,
  // make sure we have an extra to trim
  Serial.print(' ');
  Serial.print(buffer[0]);
  Serial.print(' ');
  Serial.print(buffer[1]);
  Serial.print(' ');
  Serial.print(buffer[2]);
  Serial.print(' ');
  Serial.print(buffer[3]);
  Serial.print(' ');
  Serial.print(buffer[4]);
  Serial.print(' ');
  Serial.print(buffer[5]);
  Serial.print(' ');
  Serial.print(buffer[6]);
  Serial.print(' ');
  Serial.print(buffer[7]);
  Serial.print(' ');
  Serial.print(buffer[8]);
  Serial.print(' ');
  Serial.print(buffer[9]);
  Serial.print(' ');
  Serial.print(buffer[10]);
  Serial.print(' ');
  Serial.print(buffer[11]);
  Serial.print(' ');
  Serial.print(buffer[12]);
  Serial.print(' ');
  Serial.print(buffer[13]);
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 0));
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 1));
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 2));
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 3));
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 4));
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 5));
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 6));
  Serial.print(' ');
  Serial.print(bitRead(buffer[14], 7));
  Serial.print(' ');
  Serial.print(buffer[15]);
  Serial.println(' ');
  
}
