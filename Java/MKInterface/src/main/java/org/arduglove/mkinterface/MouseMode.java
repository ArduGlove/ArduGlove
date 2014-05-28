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
		data.aY *= -1;
		//data.aY -= 25;
		//data.aX -= 85;

		div = data.oneG / 5;
		int deadZone = div / 3;

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Rectangle screen = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();

		int deltaX = data.aX / div + remX / div;
		int deltaY = data.aY / div + remY / div;

		if (remX > div) remX -= div;
		if (remX < -div) remX += div;
		if (remY > div) remY -= div;
		if (remY < -div) remY += div;
		remX += data.aX % div;
		remY += data.aY % div;

		mouse.translate(deltaX, deltaY);

		if (mouse.x < 0) mouse.x = 0;
		if (mouse.y < 0) mouse.y = 0;
		if (mouse.x >= screen.width) mouse.x = screen.width - 1;
		if (mouse.y >= screen.height) mouse.y = screen.height - 1;

		if (Math.abs(data.aX) > deadZone || Math.abs(data.aY) > deadZone) {
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
