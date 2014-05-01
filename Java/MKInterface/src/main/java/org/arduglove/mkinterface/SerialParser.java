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
		port.setParams(19200, 8, 1, 0);
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

						if (dataStrings.length != 10) continue;
						SensorData data = new SensorData();

						data.aX = Integer.parseInt(dataStrings[2]);
						data.aY = Integer.parseInt(dataStrings[3]);
						data.aZ = Integer.parseInt(dataStrings[4]);

						data.index = Integer.parseInt(dataStrings[8]) == 1;
						data.middleFlex = Integer.parseInt(dataStrings[9]);
						mode.process(data);
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
