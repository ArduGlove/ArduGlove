import processing.serial.*;
import java.util.*;

Serial myPort;

int ax = 0;
int ay = 0;
int az = 0;
int gx = 0;
int gy = 0;
int gz = 0;
int mx = 0;
int my = 0;
int mz = 0;
int flexa = 0;
int flexb = 0;
int flexc = 0;
int flexd = 0;
int flexe = 0;

boolean buttona = false;
boolean buttonb = false;
boolean buttonc = false;
boolean buttond = false;
boolean buttone = false;
boolean buttonf = false;
boolean buttong = false;
boolean buttonh = false;

int packet = 0;

void setup() {
  size(600, 600, P3D);
  
  println(Serial.list());
  myPort = new Serial(this, Serial.list()[6], 38400);
  myPort.bufferUntil('\n');
}

void draw() {
  background(0);

  text(ax, 30, 30);
  text(ay, 30, 50);
  text(az, 30, 70);
  text(gx, 30, 110);
  text(gy, 30, 130);
  text(gz, 30, 150);
  text(mx, 30, 190);
  text(my, 30, 210);
  text(mz, 30, 230);
  
  text(flexa, 100, 30);
  text(flexb, 100, 50);
  text(flexc, 100, 70);
  text(flexd, 100, 90);
  text(flexe, 100, 110);
  
  text(int(buttona), 170, 30);
  text(int(buttonb), 170, 50);
  text(int(buttonc), 170, 70);
  text(int(buttond), 170, 90);
  text(int(buttone), 170, 110);
  text(int(buttonf), 170, 130);
  text(int(buttong), 170, 150);
  text(int(buttonh), 170, 170);
  
  text(packet, 170, 210);
  
  translate(width/2, height/2, 0);
  
  rotateX(ay / 10000.0);
  rotateZ(ax / 10000.0);
  
  box(250, 50, 300);
  
  translate(-100, 0, -200);
  
  // index finger
  rotateX(0 - ((flexc - 680) / 600.0));
  if (buttong) fill(255, 0, 0);
  box(50, 50, 100);
  fill(255);
  rotateX(0 - ((flexc - 680) / 600.0));
  translate(0, 0, -100);
  if (buttonf) fill(255, 0, 0);
  box(50, 50, 100);
  fill(255);
  rotateX(0 - ((flexc - 680) / 600.0));
  translate(0, 0, -100);
  if (buttone) fill(255, 0, 0);
  box(50, 50, 100);
  fill(255);
  // reset
  translate(0, 0, 100);
  rotateX(((flexc - 680) / 600.0));
  translate(0, 0, 100);
  rotateX(((flexc - 680) / 600.0));
  rotateX(((flexc - 680) / 600.0));
  
  translate(67, 0, 0);  
  
  // middle-finger
  rotateX(0 - ((flexb - 680) / 600.0));
  box(50, 50, 100);
  rotateX(0 - ((flexb - 680) / 600.0));
  translate(0, 0, -100);
  box(50, 50, 100);
  rotateX(0 - ((flexb - 680) / 600.0));
  translate(0, 0, -100);
  if (buttond) fill(255, 0, 0);
  box(50, 50, 100);
  fill(255);
  // reset
  translate(0, 0, 100);
  rotateX(((flexb - 680) / 600.0));
  translate(0, 0, 100);
  rotateX(((flexb - 680) / 600.0));
  rotateX(((flexb - 680) / 600.0));
  
  translate(66, 0, 0);
  
  // ring-finger
  rotateX(0 - ((flexa - 680) / 600.0));
  box(50, 50, 100);
  rotateX(0 - ((flexa - 680) / 600.0));
  translate(0, 0, -100);
  box(50, 50, 100);
  rotateX(0 - ((flexa - 680) / 600.0));
  translate(0, 0, -100);
  if (buttonc) fill(255, 0, 0);
  box(50, 50, 100);
  fill(255);
  // reset
  translate(0, 0, 100);
  rotateX(((flexa - 680) / 600.0));
  translate(0, 0, 100);
  rotateX(((flexa - 680) / 600.0));
  rotateX(((flexa - 680) / 600.0));
  
  translate(67, 0, 0);
  
  // pinky
  rotateX(0 - ((flexa - 680) / 600.0));
  box(50, 50, 100);
  rotateX(0 - ((flexa - 680) / 600.0));
  translate(0, 0, -100);
  box(50, 50, 100);
  rotateX(0 - ((flexa - 680) / 600.0));
  translate(0, 0, -100);
  if (buttonb) fill(255, 0, 0);
  box(50, 50, 100);
  fill(255);
  // reset
  translate(0, 0, 100);
  rotateX(((flexa - 680) / 600.0));
  translate(0, 0, 100);
  rotateX(((flexa - 680) / 600.0));
  rotateX(((flexa - 680) / 600.0));
  
  translate(-250, 0, 200);
  
  // thumb
  rotateX(0.5);
  rotateY(0.5 + ((flexd - 680) / 600.0) / 2);
  box(50, 50, 100);
  rotateY(((flexd - 680) / 600.0) / 2);
  translate(0, 0, -100);
  box(50, 50, 100);
  rotateY(((flexd - 680) / 600.0) / 2);
  translate(0, 0, -60);
  box(50, 50, 60);
}

void serialEvent(Serial myPort) {
  String inString = myPort.readStringUntil('\n');
  if (inString != null && !("".equals(inString.trim()))) {
    String[] data = inString.trim().split(" ");
    if(data.length != 23) return;
    ax = int(data[0]);
    ay = int(data[1]);
    az = int(data[2]);
    gx = int(data[3]);
    gy = int(data[4]);
    gz = int(data[5]);
    mx = int(data[6]);
    my = int(data[7]);
    mz = int(data[8]);
    
    flexa = int(data[9]);
    flexb = int(data[10]);
    flexc = int(data[11]);
    flexd = int(data[12]);
    flexe = int(data[13]);
    
    buttona = int(data[14]) == 0;
    buttonb = int(data[15]) == 0;
    buttonc = int(data[16]) == 0;
    buttond = int(data[17]) == 0;
    buttone = int(data[18]) == 0;
    buttonf = int(data[19]) == 0;
    buttong = int(data[20]) == 0;
    buttonh = int(data[21]) == 0;
    
    packet = int(data[22]);
  }
}
