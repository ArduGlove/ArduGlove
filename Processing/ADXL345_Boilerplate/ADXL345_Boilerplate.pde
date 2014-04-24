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

void setup() {
  size(600, 600);
  
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[4], 19200);
  myPort.bufferUntil('\n');
  
  //noFill();
}

void draw() {
  background(0);
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
