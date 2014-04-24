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
boolean index = false;

float volume = 100;
float bass = 100;

float yRotationStart = 0;
float xRotationStart = 0;
boolean isRotating = false;

void setup() {
  size(600, 600);
  
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[4], 19200);
  myPort.bufferUntil('\n');
  
  //noFill();
}

void draw() {
  background(0);
  
  float xValue = median(xValues);
  float yValue = median(yValues);
  
  //text(angleX, 160, 100);
  //text(angleY, 160, 120);
  //text(xValue, 160, 140);
  //text(yValue, 160, 160);
  //text((index ? "X" : ""), 200, 200);
  
  
  if (!isRotating && index) {
    yRotationStart = yValue;
    xRotationStart = xValue;
    isRotating = true;
  } else if (isRotating && !index) {
    isRotating = false;
    volume += (yValue - yRotationStart)* 100;
    bass += (xValue - xRotationStart)* 100;
  }
  
  if (isRotating) {
    //text(volume + (yValue - yRotationStart) * 100, 300, 300);
    rect(400, 400, 50, volume + (yValue - yRotationStart) * 100);
    rect(200, 400, 50, bass + (xValue - xRotationStart) * 100);
  } else {
    //text(volume, 300, 300);
    rect(400, 400, 50, volume);
    rect(200, 400, 50, bass);
  }
}


void serialEvent(Serial myPort) {
  String inString = myPort.readStringUntil('\n');
  if (inString != null && !("".equals(inString.trim()))) {
    String[] data = inString.trim().split(" ");
    if(data.length < 10) return;
    //theta = float(data[0]);
    //phi = float(data[1]);
    //accelX = int(data[2]);
    //accelY = int(data[3]);
    //accelZ = int(data[4]);
    angleX = float(data[5]);
    angleY = float(data[6]);
    angleZ = float(data[7]);
    index = boolean(int(data[8]));
    //cButton = boolean(int(data[9]));
    
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
