package org.arduglove.mkinterface;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Mouse simulation mode, using the position of the glove to determine cursor position,
 * unlike MouseMode, which uses the position of the glove to determine cursor direction.
 */
public class GyroPointerMode extends Mode {
    int lowest = 100;     // Sensor value of lowest (Y) position of cursor
    int highest = -100;   // Sensor value of highest (Y) position of cursor
    int leftmost = -50;   // Sensor value of leftmost (X) position of cursor
    int rightmost = -250; // Sensor value of rightmost (X) position of cursor

    int threshold = 2;    // Anti jitter threshold. Higher gives less jitter, but is less precise

    boolean mousePressed = false;
    int[] xValues = new int[21];
    int[] yValues = new int[21];
    int xValuesIndex = 0;
    int yValuesIndex = 0;
    int lastMedianX = 0;
    int lastMedianY = 0;

    Rectangle screen = MouseInfo.getPointerInfo().getDevice().getDefaultConfiguration().getBounds();

    int gyroLow = 0;
    int gyroHigh = -16000;
    int gyroSensitivityX = screen.width/10;  // higher value gives more sensitivity
    int gyroSensitivityY = screen.height/10;
    int gyroThresholdX = 600;
    int gyroThresholdY = 800;

    @Override
    void process(SensorData data) {
        lowest = data.oneG / 2;
        highest = data.oneG / -2;
        leftmost = data.oneG * -1;
        rightmost = data.oneG / 2;

        System.out.println("X:"+data.gX+"\tY:"+data.gY+"\tZ:" + data.gZ);

        // Calculate new X position based on gyro data
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        int distanceX = 0;
        int distanceY = 0;
        if ( Math.abs(data.gZ) > gyroThresholdX ) distanceX = map(data.gZ, gyroLow, gyroHigh, 0, gyroSensitivityX);
        if ( Math.abs(data.gX) > gyroThresholdY ) distanceY = map(data.gX, gyroLow, -gyroHigh, 0, gyroSensitivityY);
        int posX = mouse.x + distanceX;
        int posY = mouse.y + distanceY;
        if ( posX > screen.width ) posX = screen.width-1;
        if ( posY > screen.height ) posY = screen.height-1;

        if ( posX < 0 ) posX = 0;
        if ( posY < 0 ) posY = 0;

        //int posY = mapWithinBounds(medianY, lowest, highest, screen.height-1, 0);

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
    private int mapWithinBounds(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
        // Ensure value is not lower than fromLow bounds
        if ( (fromLow < fromHigh && value < fromLow) || (fromLow > fromHigh && value > fromLow) ) return toLow;
            // Ensure value is not higher than fromHigh bounds
        else if ( (fromHigh > fromLow && value > fromHigh) || (fromHigh < fromLow && value < fromHigh) ) return toHigh;
            // Calculate re-mapped number
        else return toLow + (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow);
    }

    /**
     *
     */
    private int map(int value, int fromLow, int fromHigh, int toLow, int toHigh) {
        return toLow + (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow);
    }
}
