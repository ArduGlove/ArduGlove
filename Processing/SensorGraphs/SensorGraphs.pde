import processing.serial.*;
import java.util.*;

Serial myPort;

float[] yValues = new float[10];
int yValuesPointer = 0;
float[] xValues = new float[10];
int xValuesPointer = 0;

float angleX = 0;
float angleY = 0;
float angleZ = 0;

float accelX;
float accelY;
float accelZ;

boolean index = false;

float volume = 100;
float bass = 100;

float yRotationStart = 0;
float xRotationStart = 0;
boolean isRotating = false;

float multiplier = 0.5;
int x = 0;
int middle = 400;

float lastX = middle;
float lastY = middle;
float lastZ = middle;
int lastMiddleFlex = middle;
int middleFlex = middle;

void setup() {
  size(1000, middle*2);
  
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[0], 19200);
  myPort.bufferUntil('\n');
  
  //noFill();
  background(0);
}

void draw() {
  if ( index || x == 0 ) {
    x = 0;
    background(0);
    
    stroke(100);
    for (int i = -20; i < 20; i++) {
      fill(255);
      text(i*100, 5, middle+i*100*multiplier-3);
      strokeWeight(i==0 ? 3 : 1);
      line(0, middle+i*100*multiplier, displayWidth, middle+i*100*multiplier);
      text((i+4)*100, 970, (i+4)*100*multiplier-3);
    }
  }
  
  float xValue = median(xValues);
  float yValue = median(yValues);
  
  fill(40);
  rect(45, 0, 100, 150);
  fill(255,255,255);
  
  text(angleX, 50, 20);
  text(angleY, 50, 40);
  
  fill(255,0,0);
  text(accelX, 50, 60);
  fill(0,255,0);
  text(accelY, 50, 80);
  fill(50,50,255);
  text(accelZ, 50, 100);
  fill(255,255,0);
  text(middleFlex, 50, 120);
  
  stroke(255, 0, 0);
  line(x-1, middle+lastX*multiplier, x, middle+accelX*multiplier);
  stroke(0, 255, 0);
  line(x-1, middle+lastY*multiplier, x, middle+accelY*multiplier);
  stroke(50, 50, 255);
  line(x-1, middle+lastZ*multiplier, x, middle+accelZ*multiplier);
  stroke(255, 255, 0);
  line(x-1, lastMiddleFlex*multiplier, x, middleFlex*multiplier);
  
  lastX = accelX;
  lastY = accelY;
  lastZ = accelZ;
  lastMiddleFlex = middleFlex;
  x++;
  stroke(0);
}


void serialEvent(Serial myPort) {
  String inString = myPort.readStringUntil('\n');
  if (inString != null && !("".equals(inString.trim()))) {
    String[] data = inString.trim().split(" ");
    if(data.length < 10) return;
    //theta = float(data[0]);
    //phi = float(data[1]);
    accelX = int(data[2]);
    accelY = int(data[3]);
    accelZ = int(data[4]);
    angleX = float(data[5]);
    angleY = float(data[6]);
    angleZ = float(data[7]);
    index = boolean(int(data[8]));
    middleFlex = int(data[9]);
    
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
