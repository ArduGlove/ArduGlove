package org.arduglove.mkinterface;

import jssc.*;

import java.util.Arrays;

public class SerialParser implements SerialPortEventListener{
	private final SerialPort port;
	private Mode mode;
	private boolean enabled = true;

	byte[] buffer = new byte[32768];
	int bufferPointer = 0;

	public SerialParser(SerialPort port, Mode mode) throws SerialPortException {
		this.port = port;
		this.mode = mode;

		port.openPort();
		port.setParams(38400, 8, 1, 0);
		port.addEventListener(this, SerialPort.MASK_RXCHAR);
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (!enabled) return;

		if (event.getEventType() == SerialPortEvent.RXCHAR) {
			try {
				while (port.getInputBufferBytesCount() > 0) {
					byte[] read = port.readBytes(1);
					buffer[bufferPointer++] = read[0];

					if (buffer[bufferPointer-1] == (byte) '\n') {
						byte[] slice = Arrays.copyOfRange(buffer, 0, bufferPointer - 1);
						String[] dataStrings = new String(slice).trim().split(" ");
						bufferPointer = 0;

						// Cabled simple glove
						if (dataStrings.length == 10) {
							SensorData data = new SensorData();
							data.oneG = 250;

							data.aY = Integer.parseInt(dataStrings[2]);
							data.aX = Integer.parseInt(dataStrings[3]) * -1;
							data.aZ = Integer.parseInt(dataStrings[4]);

							data.index = Integer.parseInt(dataStrings[8]) == 1;
							data.middleFlex = Integer.parseInt(dataStrings[9]);
							mode.process(data);
						}

						// Wireless full-featured glove
						if (dataStrings.length == 23) {
							SensorData data = new SensorData();
							data.oneG = 16000;

							data.aX = Integer.parseInt(dataStrings[0]);
							data.aY = Integer.parseInt(dataStrings[1]);
							data.aZ = Integer.parseInt(dataStrings[2]);

							data.gX = Integer.parseInt(dataStrings[3]);
							data.gY = Integer.parseInt(dataStrings[4]);
							data.gZ = Integer.parseInt(dataStrings[5]);

							data.cX = Integer.parseInt(dataStrings[6]);
							data.cY = Integer.parseInt(dataStrings[7]);
							data.cZ = Integer.parseInt(dataStrings[8]);

							data.ringFlex = Integer.parseInt(dataStrings[9]);
							data.middleFlex = Integer.parseInt(dataStrings[10]);
							data.indexFlex = Integer.parseInt(dataStrings[11]);
							data.thumbFlex = Integer.parseInt(dataStrings[12]);

							//data.indexFlex = Integer.parseInt(dataStrings[13]);

							//data.pinky = Integer.parseInt(dataStrings[14]) == 0;
							data.pinky = Integer.parseInt(dataStrings[15]) == 0;
							data.ring = Integer.parseInt(dataStrings[16]) == 0;
							data.middle = Integer.parseInt(dataStrings[17]) == 0;
							data.index = Integer.parseInt(dataStrings[18]) == 0;
							data.swipeL = Integer.parseInt(dataStrings[19]) == 0;
							data.swipeR = Integer.parseInt(dataStrings[20]) == 0;
							//data.swipeR = Integer.parseInt(dataStrings[21]) == 0;

							data.packet = Integer.parseInt(dataStrings[22]);
							mode.process(data);
						}
					}
				}
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
