package org.arduglove.mkinterface;

import jssc.SerialPort;

/**
 * Mouse simulation mode
 */
public class MouseMode extends Mode {
	public MouseMode(SerialPort port) {
		super(port);
	}

	@Override
	void process(SensorData data) {
		System.out.printf("\r %6d %6d %6d   ", data.aX, data.aY, data.aZ);
	}
}
