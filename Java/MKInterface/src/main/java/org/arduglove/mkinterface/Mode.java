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
}
