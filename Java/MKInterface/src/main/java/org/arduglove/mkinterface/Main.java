package org.arduglove.mkinterface;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import javax.swing.*;

public class Main {
	@Parameter(names = { "-p", "--port"}, description = "Port name")
	String portName;

	@Parameter(names = {"-m", "--mode"}, description = "Mode of input generation")
	String modeName = "mouse";

	@Parameter(names = {"-h", "--help"}, help = true, hidden = true)
	boolean help;

	public static void main(String[] args) {
		Main main = new Main();
		JCommander jc = new JCommander(main, args);
		if (main.help) {
			jc.setProgramName("arduglove");
			jc.usage();
			System.exit(0);
		}
		main.handler();
}

	private void handler() {
		if (portName == null) {
			String[] choices = SerialPortList.getPortNames();
			if (choices.length == 0) {
				JOptionPane.showMessageDialog(null, "No ports found");
				System.exit(0);
			}
			if (choices.length == 1) {
				portName = choices[0];
			} else {
				portName = (String) JOptionPane.showInputDialog(null,
						"Choose port", "Choose port", JOptionPane.QUESTION_MESSAGE, null, choices, choices[0]);
			}
			if (portName == null) {
				System.exit(0);
			}
		}

		SerialPort port = new SerialPort(portName);

		Mode mode = null;
		switch (modeName) {
			case "mouse": mode = new MouseMode(port); break;
		}
		if (mode == null) {
			JOptionPane.showMessageDialog(null, "Invalid mode switch");
			System.exit(0);
		}

		try {
			port.openPort();
			port.setParams(19200, 8, 1, 0);
			port.addEventListener(mode, SerialPort.MASK_RXCHAR);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
}