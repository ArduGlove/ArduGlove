package org.arduglove.mkinterface;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

import java.util.Arrays;

abstract class Mode implements SerialPortEventListener{
	private SerialPort port;

	byte[] buffer = new byte[32768];
	int bufferPointer = 0;

	public Mode(SerialPort port) {
		this.port = port;
	}

	abstract void process(SensorData data);

	@Override
	public void serialEvent(SerialPortEvent event) {
		if (event.getEventType() == SerialPortEvent.RXCHAR) {
			try {
				while (port.getInputBufferBytesCount() > 0) {
					byte[] read = port.readBytes(1);
					buffer[bufferPointer++] = read[0];

					if (buffer[bufferPointer-1] == (byte) '\n') {
						byte[] slice = Arrays.copyOfRange(buffer, 0, bufferPointer-1);
						String[] dataStrings = new String(slice).split(" ");
						bufferPointer = 0;

						if (dataStrings.length < 10) continue;
						SensorData data = new SensorData();
						data.aX = Integer.parseInt(dataStrings[2]);
						data.aY = Integer.parseInt(dataStrings[3]);
						data.aZ = Integer.parseInt(dataStrings[4]);
						process(data);
					}
				}
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
}
