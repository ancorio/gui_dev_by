package by.dev.gui.node;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import by.dev.gui.Application;
import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.render.Texture;
import by.dev.gui.core.render.TextureGraphicsContext;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.node.impl.Desktop;
import by.dev.gui.node.impl.Window;


public class Node {

	private Rect frame;
	public List<Node> childs;
	public Node parent = null;
	private int alpha = 255;
	private TextureGraphicsContext context;
	private boolean needsRedraw = true;
	
	public Node(Rect frame) {
		super();
		this.childs = new ArrayList<>();
		this.frame = frame;
		this.context = new TextureGraphicsContext(new Texture(), getBounds());
		
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
		setNeedsRedraw();
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
	
	public void draw() {
		if (needsRedraw) {
		    drawMe(context);
		    for (Node subnode : childs) {
		    	subnode.draw();
		    	context.drawTexture(new Point(subnode.getFrame().getLeft(), subnode.getFrame().getTop()), subnode.context.getTexture(), subnode.alpha);	
		    }
			needsRedraw = false;
		}
	}
	
	static protected Point getScreenPosition(Point point, Node node) { 
		point.x += node.frame.getLeft();
		point.y += node.frame.getTop();
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
		return point.x >= 0 && point.y >= 0 && point.x < frame.getWidth() && point.y < frame.getHeight();
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
		return new Rect(0, 0, frame.getWidth(), frame.getHeight());
	}

	public Rect getFrame() {
		return frame;
	}

	public void setFrame(Rect frame) {
		this.frame = frame;
		context.updateBounds(getBounds());
		setNeedsRedraw();
	}
	
	public void setNeedsRedraw() {
		Node node = this;
		while (node != null) {
			node.needsRedraw = true;
			node = node.parent;
		}
	}

	public TextureGraphicsContext getContext() {
		return context;
	}
	
	public Application getApplication() {
		return getDesktop().getApplication();
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
		setNeedsRedraw();
	}
	
	
}