package org.arduglove.mkinterface;

import java.awt.event.KeyEvent;

public class WasdKeyMode extends ArrowKeyMode {
	WasdKeyMode() {
		up = new KeyPressManager(KeyEvent.VK_W);
		left = new KeyPressManager(KeyEvent.VK_A);
		down = new KeyPressManager(KeyEvent.VK_S);
		right = new KeyPressManager(KeyEvent.VK_D);
	}
}
