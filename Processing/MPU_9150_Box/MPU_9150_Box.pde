import processing.serial.*;

Serial myPort;

float angleX = 0;
float angleY = 0;
float angleZ = 0;

void setup() {
  size(800, 600, P3D);
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[4], 115200);
  myPort.bufferUntil('\n');
  textAlign(RIGHT);
}

void draw() {
  background(0);
  
  text(angleX, 160, 100);
  text(angleY, 160, 120);
  text(angleZ, 160, 140);
  
  translate(400, 300);
  rotateX(angleX);
  rotateZ(angleY);
  //rotateY(0 - angleZ);
  box(200, 50, 200);
}

void serialEvent(Serial myPort) {
  String inString = myPort.readStringUntil('\n');
  if (inString != null && !("".equals(inString.trim()))) {
    String[] data = inString.trim().split(" ");
    if(data.length < 6) return;
    angleX = radians(float(data[1]));
    angleY = radians(float(data[3]));
    angleZ = radians(float(data[5]));
  }
}
