import processing.serial.*;

import java.util.*;

float[] yValues = new float[10];
int yValuesPointer = 0;
float[] xValues = new float[10];
int xValuesPointer = 0;

Serial myPort;

boolean cButton = false;
boolean zButton = false;
float theta = 0;
float phi = 0;
int accelX = 0;
int accelY = 0;
int accelZ = 0;
float angleX = 0;
float angleY = 0;
float angleZ = 0;

void setup() {
  size(800, 600, P3D);
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[4], 19200);
  myPort.bufferUntil('\n');
  textAlign(RIGHT);
}

void draw() {
  background(0);
  
  text(accelX, 100, 100);
  text(accelY, 100, 120);
  text(accelZ, 100, 140);
  
  text(angleX, 160, 100);
  text(angleY, 160, 120);
  text(median(xValues), 160, 140);
  text(median(yValues), 160, 160);
  
  text(theta, 220, 100);
  text(phi, 220, 120);
  
  translate(400, 300);
  rotateX(median(xValues));
  //rotateX(angleX);
  rotateZ(0 - median(yValues));
  //rotateZ(0 - angleY);
  //rotateY(radians(angleZ));
  box(200, 50, 200);
}

void serialEvent(Serial myPort) {
  String inString = myPort.readStringUntil('\n');
  if (inString != null && !("".equals(inString.trim()))) {
    String[] data = inString.trim().split(" ");
    if(data.length < 10) return;
    theta = float(data[0]);
    phi = float(data[1]);
    accelX = int(data[2]);
    accelY = int(data[3]);
    accelZ = int(data[4]);
    angleX = float(data[5]);
    angleY = float(data[6]) + 0.5;
    angleZ = float(data[7]);
    zButton = boolean(int(data[8]));
    cButton = boolean(int(data[9]));
    
    if (++yValuesPointer > yValues.length-1) yValuesPointer = 0;
    yValues[yValuesPointer] = angleY;
    if (++xValuesPointer > xValues.length-1) xValuesPointer = 0;
    xValues[xValuesPointer] = angleX;
  }
}

float median(float[] arr) {
  float[] sorted = arr.clone();
  Arrays.sort(sorted);
  return sorted[sorted.length/2];
}
