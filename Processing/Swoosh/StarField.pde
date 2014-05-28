class StarField {
  Star[] stars;
  
  StarField() {
    stars = new Star[50];
    for(int i = 0; i < stars.length; i++) {
      stars[i] = new Star(
          (int) random(width),
          (int) random(height),
          (int) random(5, 15));
    }
  }
  
  void draw() {
    stroke(255);

    for(int i = 0; i < stars.length; i++) {
      Star s = stars[i];
      line(s.x, s.y, s.x, s.y + s.speed);
      s.y += s.speed;
      if(s.y > height) {
        stars[i] = new Star((int) random(width), 0, (int) random(5, 15));
      }
    }
  }
  
  class Star {
    int x;
    int y;
    int speed;
    
    Star(int x, int y, int speed) {
      this.x = x;
      this.y = y;
      this.speed = speed;
    }
  }
}
