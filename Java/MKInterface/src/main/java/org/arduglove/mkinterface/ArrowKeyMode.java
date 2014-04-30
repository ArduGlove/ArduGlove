package org.arduglove.mkinterface;

import java.awt.event.KeyEvent;

public class ArrowKeyMode extends Mode {
	KeyPressManager left = new KeyPressManager(KeyEvent.VK_LEFT);
	KeyPressManager right = new KeyPressManager(KeyEvent.VK_RIGHT);
	KeyPressManager up = new KeyPressManager(KeyEvent.VK_UP);
	KeyPressManager down = new KeyPressManager(KeyEvent.VK_DOWN);

	KeyPressManager index = new KeyPressManager(KeyEvent.VK_SPACE);

	int deadZone = 50;

	@Override
	void process(SensorData data) {
		data.aX *= -1;
		data.aY *= -1;
		data.aX -= 25;
		data.aY -= 85;

		left.update(data.aY < -1 * deadZone);
		right.update(data.aY > deadZone);
		up.update(data.aX < -1 * deadZone);
		down.update(data.aX > deadZone);

		index.update(data.index);
	}

	class KeyPressManager {
		int key;
		boolean pressed;

		KeyPressManager(int key) {
			this.key = key;
		}

		void update(boolean shouldBePressed) {
			if (shouldBePressed && !pressed) {
				robot.keyPress(key);
				pressed = true;
			}
			if (!shouldBePressed && pressed) {
				robot.keyRelease(key);
				pressed = false;
			}
		}
	}
}
