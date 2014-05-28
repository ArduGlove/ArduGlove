
int targetNumber = 0;
int targetCount = 10;
int targetX;
int targetY;
int targetRadius = 50;

int startTime;
int targetsHit = 0;
int timeTaken;

void setup() {
  size(800, 600);
  
  ellipseMode(RADIUS);
  
  textSize(30);
  textAlign(CENTER);
}

void draw() {
  background(0);
  if (targetNumber == 0) {
    text("Click screen to begin", width/2, height/2);
  } else if (targetNumber <= targetCount) {
    ellipse(targetX, targetY, targetRadius, targetRadius);
  }
  if (targetNumber > targetCount) {
    text(targetsHit + "/" + targetCount
        + " " + (timeTaken / 100) / 10.0,
        width/2, height/2);
  }
}

void mousePressed() {
  if (targetNumber == 0) {
    startTime = millis();
  }
  if (targetNumber <= targetCount) {
    // Check if click hit target
    if (dist(targetX, targetY, mouseX, mouseY) < targetRadius) {
      targetsHit++;
    }
    // Make new target
    targetX = int(random(targetRadius, width - targetRadius));
    targetY = int(random(targetRadius, height - targetRadius));
    targetNumber++;
    
    if (targetNumber > targetCount) timeTaken = millis() - startTime;
  }
}
