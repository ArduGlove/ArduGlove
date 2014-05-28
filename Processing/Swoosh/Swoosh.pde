import processing.serial.*;
import java.util.*;

Serial myPort;
Glove glove;

StarField stars;
AsteroidField asteroids;
Ship ship;

StatusView statusView;

void setup() {
  size(800, 600);
  stars = new StarField();
  asteroids = new AsteroidField();
  ship = new Ship(width/2, height - 100);

  statusView = new StatusView();
  
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[0], 38400);
  myPort.bufferUntil('\n');
  
  glove = new Glove();
}

void draw() {
  background(0);
  
  ship.move((int) mapHalfQuad(glove.ax, 16000, 20));
  
  stars.draw();
  asteroids.draw();
  ship.draw();

  statusView.draw();
  
  //text((int)frameRate, 10, 10);
  //text(glove.ax, 10, 30);
}

void serialEvent(Serial myPort) {
  String inString = myPort.readStringUntil('\n');
  glove.parseData(inString);
}

float mapHalfQuad(float value, float fromMax, float toMax) {
  value /= fromMax;
  return (Math.signum(value)*toMax*value*value + toMax*value) / 2;
}
