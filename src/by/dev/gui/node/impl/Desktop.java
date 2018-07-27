package by.dev.gui.node.impl;

import by.dev.gui.Application;
import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.MouseDeviceCallback;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.node.FillNode;
import by.dev.gui.node.Node;

public class Desktop extends FillNode implements MouseDeviceCallback {
	
	private Node touchFocus = null;
	private Window activeWindow = null;
	private Application application;
	
	public Desktop(int width, int height) {
		super(new Rect(0, 0, width, height));
		setBackgroundColor(new Color(150, 150, 230));
	}
	

	public void onMouseEvent(MouseEvent event) {
		Node node;
		if (touchFocus != null) {
			node = touchFocus;
		} else {
			node = nodeForMouseEvent(event);
		}
		if (node != null) {
			if (node.doMouseEvent(event)) {
				setFocus(node);
			} else {
				setFocus(null);
			}
		}
	}
	
	private void setFocus(Node node) {
		touchFocus = node;
		if (touchFocus == null) {
			return;
		}
		Window window = touchFocus.getWindow();
		if (window != null && window.parent == this) {
			childs.remove(window);
			childs.add(window);
			checkActiveWindow();
		}
	}

	public void addSubnode(Node node) {
		super.addSubnode(node);
		checkActiveWindow();
	}

	public void removeSubnode(Node node) {
		super.removeSubnode(node);
		checkActiveWindow();
	}

	private void checkActiveWindow() {
		Window window = null;
		for (Node node : childs) {
			if (Window.class.isAssignableFrom(node.getClass())) {
				window = (Window)node;
			}
		}
		if (window == null) {
			if (activeWindow != null) {
				activeWindow.setActive(false);
				activeWindow = null;
			}
		} else {
			if (window != activeWindow) {
				if (activeWindow != null) {
					activeWindow.setActive(false);
				}
				activeWindow = window;
				activeWindow.setActive(true);
			}
		}
	}


	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
	
}
