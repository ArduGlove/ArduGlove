package org.arduglove.mkinterface;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Mouse simulation mode
 */
public class MouseMode extends Mode {
	boolean mousePressed = false;

	int div = 50;
	int remX = 0;
	int remY = 0;

	@Override
	void process(SensorData data) {
		data.aX *= -1;
		data.aY *= -1;
		data.aX -= 25;
		data.aY -= 85;

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Rectangle screen = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();

		int deltaX = data.aY / div + remX / div;
		int deltaY = data.aX / div + remY / div;

		if (remX > div) remX -= div;
		if (remX < -div) remX += div;
		if (remY > div) remY -= div;
		if (remY < -div) remY += div;
		remX += data.aY % div;
		remY += data.aX % div;

		mouse.translate(deltaX, deltaY);

		if (mouse.x < 0) mouse.x = 0;
		if (mouse.y < 0) mouse.y = 0;
		if (mouse.x > screen.width) mouse.x = screen.width;
		if (mouse.y > screen.height) mouse.y = screen.height;

		if (Math.abs(data.aX) > 15 || Math.abs(data.aY) > 15) {
			robot.mouseMove(mouse.x, mouse.y);
		}

		if (data.index && !mousePressed) {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			mousePressed = true;
		}

		if (!data.index && mousePressed) {
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			mousePressed = false;
		}
	}
}
