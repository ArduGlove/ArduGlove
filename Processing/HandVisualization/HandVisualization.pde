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

void setup() {
  size(600, 600, P3D);
  
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[4], 19200);
  myPort.bufferUntil('\n');
  
  //noFill();
}

void draw() {
  background(0);
  translate(width/2, height/2, 0);
  rotateZ(-PI/2);
  
  rotateY(median(xValues));
  rotateZ(0 - median(yValues));
  
  //rotateY(mouseX/((float) width));
  boxAt(0, 0, 110, 40, 220, 220);
  boxAt(0, -140, 90, 40, 40, 60);
  boxAt(0, -140, 30, 40, 40, 60);
  rotateX(radians(-45));
  boxAt(0, -180, 0, 40, 40, 60);
  rotateX(radians(45));
  rotateY(mouseY/((float) height));
  boxAt(0, 30, -40, 40, 40, 60);
  boxAt(0, -30, -40, 40, 40, 60);
  boxAt(0, 90, -40, 40, 40, 60);
  boxAt(0, -90, -40, 40, 40, 60);
  rotateY(mouseY/((float) height));
  boxAt(0, 30, -100, 40, 40, 60);
  boxAt(0, -30, -100, 40, 40, 60);
  boxAt(0, 90, -100, 40, 40, 60);
  boxAt(0, -90, -100, 40, 40, 60);
  rotateY(mouseY/((float) height));
  boxAt(0, 30, -160, 40, 40, 60);
  boxAt(0, -30, -160, 40, 40, 60);
  boxAt(0, 90, -160, 40, 40, 60);
  boxAt(0, -90, -160, 40, 40, 60);
}

void boxAt(int x, int y, int z, int w, int h, int d) {
  translate(x, y, z);
  box(w, h, d);
  translate(-x, -y, -z);
}

void boxAt(int x, int y, int z, int size) {
  boxAt(x, y, z, size, size, size);
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
    //zButton = boolean(int(data[8]));
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
