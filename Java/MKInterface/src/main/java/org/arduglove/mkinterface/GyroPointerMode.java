package org.arduglove.mkinterface;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * "GyroMouse" simulation mode, using the rotation from gyroscope data to determine mouse cursor movement.
 */
public class GyroPointerMode extends Mode {
    boolean mousePressed = false;

    Rectangle screen = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();

    int gyroLow = 0;
    int gyroHigh = -16000;
    int gyroSensitivityX = screen.width/10;  // Higher value gives more sensitivity
    int gyroSensitivityY = screen.height/10;
    int gyroThresholdX = 600;                // Values lower than the threshold are ignored
    int gyroThresholdY = 800;

    @Override
    void process(SensorData data) {

        // Calculate new X and Y position based on gyro data
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        int distanceX = 0;
        int distanceY = 0;
        if ( Math.abs(data.gZ) > gyroThresholdX ) distanceX = map(data.gZ, gyroLow, gyroHigh, 0, gyroSensitivityX);
        if ( Math.abs(data.gX) > gyroThresholdY ) distanceY = map(data.gX, gyroLow, -gyroHigh, 0, gyroSensitivityY);
        int posX = mouse.x + distanceX;
        int posY = mouse.y + distanceY;

        // Don't allow mouse to go outside screen bounds
        if ( posX > screen.width ) posX = screen.width-1;
        if ( posY > screen.height ) posY = screen.height-1;
        if ( posX < 0 ) posX = 0;
        if ( posY < 0 ) posY = 0;

        robot.mouseMove(posX, posY);

        if (data.index && !mousePressed) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            mousePressed = true;
        }
        if (!data.index && mousePressed) {
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            mousePressed = false;
        }
    }

    /**
     * Re-maps a number from one range to another. That is, a value of fromLow would get mapped to toLow,
     * a value of fromHigh to toHigh, values in-between to values in-between, etc. Inspired by the Processing method.
     * @param value     the number to map
     * @param fromLow   the lower bound of the value's current range
     * @param fromHigh  the upper bound of the value's current range
     * @param toLow     the lower bound of the value's target range
     * @param toHigh    the upper bound of the value's target range
     * @return          The mapped value. Will always be within toLow/toHigh bounds
     */
    private int map(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
        return toLow + (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow);
    }
}
