void setup() {
  size(600, 600, P3D);
  //noFill();
}

void draw() {
  background(0);
  translate(width/2, height/2, 0);
  //rotateZ(-PI/2);
  rotateY(mouseX/((float) width));
  boxAt(0, 0, 110, 40, 220, 220);
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
