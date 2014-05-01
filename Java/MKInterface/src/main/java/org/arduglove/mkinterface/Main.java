package org.arduglove.mkinterface;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
	@Parameter(names = { "-p", "--port"}, description = "Port name")
	String portName;

	@Parameter(names = {"-m", "--mode"}, description = "Mode of input generation. Available modes: mouse, arrow, wasd")
	String modeName = "mouse";

	@Parameter(names = {"-h", "--help"}, help = true, hidden = true)
	boolean help;

	SerialParser parser;

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
		if (isMac()) createMacGUI();

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

		Mode mode = getMode(modeName);
		if (mode == null) {
			JOptionPane.showMessageDialog(null, "Invalid mode switch");
			System.exit(0);
		}

		try {
			parser = new SerialParser(port, mode);
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}

	private static Mode getMode(String name) {
		switch (name.toLowerCase()) {
			case "mouse": return new MouseMode();
			case "arrow": return new ArrowKeyMode();
			case "wasd": return new WasdKeyMode();
		}
		return null;
	}

	private void createMacGUI() {
		System.setProperty("apple.awt.UIElement", "true");
		SystemTray tray = SystemTray.getSystemTray();

		Image image = Toolkit.getDefaultToolkit().getImage("/Users/erik/Documents/Java/misc/trayicon.png");
		PopupMenu popup = new PopupMenu();
		Menu modes = new Menu("Modes");

		String[] modeStrings = {"Mouse", "Arrow", "WASD"};

		for (final String s : modeStrings) {
			MenuItem m = new MenuItem(s);
			m.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					parser.setMode(getMode(s));
				}
			});
			modes.add(m);
		}

		popup.add(modes);
		MenuItem quit = new MenuItem("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		popup.add(quit);
		TrayIcon icon = new TrayIcon(image, "ArduGlove", popup);

		try {
			tray.add(icon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}

	private static boolean isMac() {
		return System.getProperty("os.name").startsWith("Mac");
	}
}