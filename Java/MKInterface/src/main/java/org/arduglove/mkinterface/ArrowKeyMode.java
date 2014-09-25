package org.arduglove.mkinterface;

import java.awt.event.KeyEvent;

public class ArrowKeyMode extends Mode {
	KeyPressManager left = new KeyPressManager(KeyEvent.VK_LEFT);
	KeyPressManager right = new KeyPressManager(KeyEvent.VK_RIGHT);
	KeyPressManager up = new KeyPressManager(KeyEvent.VK_UP);
	KeyPressManager down = new KeyPressManager(KeyEvent.VK_DOWN);

	KeyPressManager index = new KeyPressManager(KeyEvent.VK_SPACE);
	KeyPressManager middle = new KeyPressManager(KeyEvent.VK_D);
	KeyPressManager ring = new KeyPressManager(KeyEvent.VK_A);
	KeyPressManager pinky = new KeyPressManager(KeyEvent.VK_W);

	int deadZone = 50;

	@Override
	void process(SensorData data) {
		deadZone = data.oneG / 5;

		left.update(data.aX < -1 * deadZone);
		right.update(data.aX > deadZone);
		up.update(data.aY < -1 * deadZone);
		down.update(data.aY > deadZone);

		index.update(data.index);
		middle.update(data.middle);
		ring.update(data.ring);
		pinky.update(data.pinky);
	}
}
