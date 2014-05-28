import java.util.*;

class AsteroidField {
  LinkedList<Asteroid> asteroids;
  PImage[] images;
  
  AsteroidField() {
    asteroids = new LinkedList<Asteroid>();
    images = new PImage[] {
        loadImage("asteroid1.png"),
        loadImage("asteroid2.png"),
        loadImage("asteroid3.png"),
        loadImage("asteroid4.png"),
        loadImage("asteroid5.png"),
        loadImage("asteroid6.png"),
        loadImage("asteroid7.png")
    };

    for (int i = 0; i < 10; i++) {
      asteroids.add(new Asteroid(
          int(random(width)),
          int(random(height)),
          images[int(random(images.length))]));
    }
  }
  
  void draw() {
    for (Asteroid a : asteroids) {
      a.y += 3;
      if (a.y > height + a.h) {
        a.y = 0 - a.h;
        a.x = int(random(width));
      }
      a.draw();
    }
  }
  
  class Asteroid {
    int x;
    int y;
    int w;
    int h;
    PImage image;

    Asteroid(int x, int y, PImage image) {
      this.x = x;
      this.y = y;
      this.image = image;
      this.w = image.width * 2;
      this.h = image.height * 2;
    }

    void draw() {
      image(image, x - (w / 2), y - (h / 2), w, h);
    }
  }
}
