package by.dev.gui.node;

import java.util.List;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;


public class Node {

	public Rect frame;
	public List<Node> childs;
	public Node parent = null;
	
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
	
}