class StatusView {
  PImage panel;
  int points = 235;

  StatusView() {
  	panel = loadImage("panel-yellow.png");
  }

  void draw() {
  	textSize(20);

    image(panel, width - 70, 50, panel.width * 2, panel.height * 2);
    fill(0);
    text("" + points, width - 50 + 2, 80 + 2);
    fill(255);
    text("" + points, width - 50, 80);

    image(panel, -22, 50, panel.width * 2, panel.height * 2);
    fill(0);
    text("" + 5, 20 + 2, 80 + 2);
    fill(255);
    text("" + 5, 20, 80);
  }
}