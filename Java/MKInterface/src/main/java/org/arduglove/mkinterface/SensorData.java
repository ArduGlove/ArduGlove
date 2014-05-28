package org.arduglove.mkinterface;

public class SensorData {
	int aX, aY, aZ; // Accelerometer
	int gX, gY, gZ; // Gyroscope
	int cX, cY, cZ; // Compass

	int oneG;

	boolean index, middle, ring, pinky;
	boolean swipeL, swipeR;

	int indexFlex, middleFlex, ringFlex, thumbFlex;

	double angX, angY, angZ;

	int packet;
}
