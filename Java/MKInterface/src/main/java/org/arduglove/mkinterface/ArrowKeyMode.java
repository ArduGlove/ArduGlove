package org.arduglove.mkinterface;

import java.awt.event.KeyEvent;

public class ArrowKeyMode extends Mode {
	KeyPressManager left = new KeyPressManager(KeyEvent.VK_LEFT);
	KeyPressManager right = new KeyPressManager(KeyEvent.VK_RIGHT);
	KeyPressManager up = new KeyPressManager(KeyEvent.VK_UP);
	KeyPressManager down = new KeyPressManager(KeyEvent.VK_DOWN);

	KeyPressManager index = new KeyPressManager(KeyEvent.VK_SPACE);

	KeyPressManager middleFlex = new KeyPressManager(KeyEvent.VK_D);

	int deadZone = 50;

	@Override
	void process(SensorData data) {
		data.aY *= -1;
		//data.aX -= 25;
		//data.aY -= 85;

		left.update(data.aX < -1 * deadZone);
		right.update(data.aX > deadZone);
		up.update(data.aY < -1 * deadZone);
		down.update(data.aY > deadZone);

		index.update(data.index);

		middleFlex.update(data.middleFlex < 400);
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
