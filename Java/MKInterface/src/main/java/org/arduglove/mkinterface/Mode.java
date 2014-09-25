package org.arduglove.mkinterface;

import java.awt.*;

abstract class Mode {
	Robot robot;

	Mode() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	abstract void process(SensorData data);

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

	class MousePressManager {
		int button;
		boolean pressed;
		long lastPress;

		MousePressManager(int button) {
			this.button = button;
		}

		void update(boolean shouldBePressed) {
			if (shouldBePressed && !pressed) {
				robot.mousePress(button);
				pressed = true;
				lastPress = System.currentTimeMillis();
			}
			if (!shouldBePressed && pressed) {
				robot.mouseRelease(button);
				pressed = false;
			}
		}
	}
}
