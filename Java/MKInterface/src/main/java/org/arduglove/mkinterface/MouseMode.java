package org.arduglove.mkinterface;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Mouse simulation mode
 */
public class MouseMode extends Mode {
	boolean mousePressed = false;

	int div = 50;
	double remX = 0;
	double remY = 0;

	int fullSpeed = 10;

	@Override
	void process(SensorData data) {
		data.aY *= -1;
		//data.aY -= 25;
		//data.aX -= 85;

		div = data.oneG / 5;
		int deadZone = div / 3;

		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Rectangle screen = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();

		double deltaX = mapHalfQuad(data.aX, data.oneG, fullSpeed) + remX;
		double deltaY = mapHalfQuad(data.aY, data.oneG, fullSpeed) + remX;

		remX = deltaX - (int) deltaX;
		remY = deltaY - (int) deltaY;

		mouse.translate((int) deltaX, (int) deltaY);

		if (mouse.x < 0) mouse.x = 0;
		if (mouse.y < 0) mouse.y = 0;
		if (mouse.x >= screen.width) mouse.x = screen.width - 1;
		if (mouse.y >= screen.height) mouse.y = screen.height - 1;

		robot.mouseMove(mouse.x, mouse.y);

		if (data.index && !mousePressed) {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			mousePressed = true;
		}

		if (!data.index && mousePressed) {
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			mousePressed = false;
		}
	}

	double mapHalfQuad(double value, double fromMax, double toMax) {
		value /= fromMax;
		return (Math.signum(value)*toMax*value*value + toMax*value) / 2;
	}
}
