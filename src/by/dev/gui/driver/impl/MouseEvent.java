package by.dev.gui.driver.impl;

import by.dev.gui.core.struct.Point;
import by.dev.gui.node.Node;

public class MouseEvent {
	
	public MouseEventType type;
	public int x;
	public int y;
	
	public MouseEvent(MouseEventType type, int x, int y) {
		super();
		this.type = type;
		this.x = x;
		this.y = y;
	}
	
	public Point locationInNode(Node node) {
		Point result = new Point(x, y);
		while (node != null) {
			result.x -= node.getFrame().getLeft();
			result.y -= node.getFrame().getTop();
			node = node.parent;
		}
		return result;
	}

}
