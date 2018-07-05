package by.dev.gui.node.impl;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.MouseDeviceCallback;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.node.FillNode;
import by.dev.gui.node.Node;

public class Desktop extends FillNode implements MouseDeviceCallback {
	
	private Node touchFocus = null;
	
	public Desktop(int width, int height) {
		super(new Rect(0, 0, width, height));
		setBackgroundColor(new Color(150, 150, 230));
		Button button = new Button(new Rect(20, 20, 40, 40));
		addSubnode(button);
		button.callback = new Runnable() {

			public void run() {
				createWindow();
			}
		};
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
		}
	}
	
	private void createWindow() {
		Window window = new Window(new Rect(80 + childs.size() * 30, 40 + childs.size() * 30, 300, 200));
		addSubnode(window);
	}
	

}
