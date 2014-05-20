package org.arduglove.mkinterface;

import java.awt.*;
import java.awt.event.InputEvent;
import java.util.Arrays;

/**
 * Mouse simulation mode, using the position of the glove to determine cursor position,
 * unlike MouseMode, which uses the position of the glove to determine cursor direction.
 */
public class PointerMode extends Mode {
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

    @Override
    void process(SensorData data) {
        // Update array of the last measured values
        if (++xValuesIndex >= xValues.length) xValuesIndex = 0;
        if (++yValuesIndex >= yValues.length) yValuesIndex = 0;
        xValues[xValuesIndex] = data.aY;
        yValues[yValuesIndex] = data.aX;

        // Get median value of last measured values
        int[] sortedX = xValues.clone();
        Arrays.sort(sortedX);
        int[] sortedY = yValues.clone();
        Arrays.sort(sortedY);
        int medianX = sortedX[sortedX.length/2];
        int medianY = sortedY[sortedY.length/2];

        // Make sure newly calculated median value is above jitter threshold
        if ( Math.abs(medianX-lastMedianX) < threshold ) medianX = lastMedianX;
        if ( Math.abs(medianY-lastMedianY) < threshold ) medianY = lastMedianY;
        lastMedianX = medianX;
        lastMedianY = medianY;

        // X value from sensor corresponds to Y value on screen, and vice versa
        int posX = map(medianX, leftmost, rightmost, 0, screen.width-1);
        int posY = map(medianY, lowest, highest, screen.height-1, 0);

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
        // Ensure value is not lower than fromLow bounds
        if ( (fromLow < fromHigh && value < fromLow) || (fromLow > fromHigh && value > fromLow) ) return toLow;
        // Ensure value is not higher than fromHigh bounds
        else if ( (fromHigh > fromLow && value > fromHigh) || (fromHigh < fromLow && value < fromHigh) ) return toHigh;
        // Calculate re-mapped number
        else return toLow + (value - fromLow) * (toHigh - toLow) / (fromHigh - fromLow);
    }

}
