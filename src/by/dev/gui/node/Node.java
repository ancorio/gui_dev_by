package by.dev.gui.node;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.node.impl.Desktop;
import by.dev.gui.node.impl.Window;


public class Node {

	public Rect frame;
	public List<Node> childs;
	public Node parent = null;
	
	public Node() {
		this(new Rect(0, 0, 0, 0));
	}
	
	public Node(Rect frame) {
		super();
		this.childs = new ArrayList<>();
		this.frame = new Rect(frame);
	}
	
	public void addSubnode(Node node) {
		if (node == null) {
			throw new RuntimeException();
		}
		if (node.parent == this) {
			return;
		}
		if (node == this) {
			throw new RuntimeException();
		}
		if (node.parent != null) {
			node.parent.removeSubnode(node);
		}
		childs.add(node);
		node.parent = this;
	}
	
	public void removeSubnode(Node node) {
		if (node == null) {
			throw new RuntimeException();
		}
		if (node.parent != this) {
			throw new RuntimeException();
		}
		childs.remove(node);
		node.parent = null;
	}
	
	public void removeFromParent() {
		parent.removeSubnode(this);
	}
	
	protected void drawMe(GraphicsContext ctx) {
	}
	
	public void draw(GraphicsContext ctx) {
		ctx.translate(new Point(frame.left, frame.top));
	    drawMe(ctx);
	    for (Node subnode : childs) {
	    	subnode.draw(ctx);
	    }
		ctx.translate(new Point(-frame.left, -frame.top));
	}
	
	static protected Point getScreenPosition(Point point, Node node) { 
		point.x += node.frame.left;
		point.y += node.frame.top;
		if (node.parent == null) {
			return point;
		} else {
			return node.parent.getScreenPosition(point);
		}
	}
	
	public Point getScreenPosition(Point point) {
		return Node.getScreenPosition(new Point(point), this);
	}
	
	public boolean isInBounds(Point point) {
		return point.x >= 0 && point.y >= 0 && point.x < frame.width && point.y < frame.height;
	}

	public Node nodeForMouseEvent(MouseEvent event) {
		Point location = event.locationInNode(this);
		if (isInBounds(location)) {
			ListIterator<Node> li = childs.listIterator(childs.size());

			while (li.hasPrevious()) {
				Node child = li.previous();
				Node result = child.nodeForMouseEvent(event);
				if (result != null) {
					return result;
				}
			}
			return this;
		} else {
			return null;
		}
	}
	
	public boolean doMouseEvent(MouseEvent event) {
		return false;
	}

	public Desktop getDesktop() {
		Node node = this;
		while (node != null && !(Desktop.class.isAssignableFrom(node.getClass()))) {
			node = node.parent;
		}
		return (Desktop) node;
	}

	public Window getWindow() {
		Node node = this;
		while (node != null && !(Window.class.isAssignableFrom(node.getClass()))) {
			node = node.parent;
		}
		return (Window) node;
	}
	
	public Rect getBounds() {
		return new Rect(0, 0, frame.width, frame.height);
	}

}