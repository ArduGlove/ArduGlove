class Ship {
  int x;
  int y;
  int w = 42;
  int h = 54;
  
  PImage[] images;
  int imageSelector;
  
  Ship(int x, int y) {
    this.x = x;
    this.y = y;
    
    images = new PImage[] {
        loadImage("ship-L2.png"),
        loadImage("ship-L1.png"),
        loadImage("ship.png"),
        loadImage("ship-R1.png"),
        loadImage("ship-R2.png")
    };
    imageSelector = 2;
  }
  
  void move(int dx) {
    x += dx;
    
    if (x < 0 + w/2) x = 0 + w/2;
    if (x > width - w/2) x = width - w/2;
    
    if (dx == 0) imageSelector = 2;
    if (dx > 0) imageSelector = 3;
    if (dx > 2) imageSelector = 4;
    if (dx < 0) imageSelector = 1;
    if (dx < -2) imageSelector = 0;
  }
  
  void draw() {
    image(images[imageSelector], x - (w / 2), y - (h / 2), w, h);
  }
}
