package by.dev.gui.driver.impl;

import by.dev.gui.node.impl.Desktop;

public class MouseEventInvoke implements Runnable {

	public MouseEvent event;
	public Desktop desktop;

	public MouseEventInvoke(MouseEvent event, Desktop desktop) {
		super();
		this.event = event;
		this.desktop = desktop;
	}
	
	
	public void run() {
		desktop.onMouseEvent(event);
	}
}
