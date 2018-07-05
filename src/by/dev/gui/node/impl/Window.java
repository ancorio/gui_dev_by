package by.dev.gui.node.impl;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.node.FillNode;

public class Window extends FillNode {
	
	final int HEADER_HEIGHT = 30;
	final int BORDER = 2;
	
	public Window() {
		super();
		setBackgroundColor(new Color(240, 240, 120));
		addButtons();
	}

	public Window(Rect frame) {
		super(frame);
		setBackgroundColor(new Color(240, 240, 120));
		addButtons();
	}
	
	public void addButtons() {
		int size = HEADER_HEIGHT - BORDER * 3;
		Button closeButton = new Button(new Rect(frame.width - BORDER * 2 - size, BORDER * 2, size, size));
		addSubnode(closeButton);
		closeButton.callback = new Runnable() {
			
			public void run() {
				removeFromParent();
			}
		};
	}

	protected void drawMe(GraphicsContext ctx) {
		super.drawMe(ctx);
		Color headerColor;
		if (parent.childs.indexOf(this) == parent.childs.size() - 1) {
			headerColor = new Color(240, 255, 200);
		} else {
			headerColor = new Color(200, 255, 170);
		}
		
		ctx.fill(getHeaderBounds(),headerColor);
		headerColor.r /= 2;
		headerColor.g /= 2;
		headerColor.b /= 2;
		
		ctx.fill(new Rect(0, 0, frame.width, BORDER), headerColor);
		ctx.fill(new Rect(0, 0, BORDER, frame.height), headerColor);
		ctx.fill(new Rect(frame.width - BORDER, 0, BORDER, frame.height), headerColor);
		
		Rect cb = getContentBounds();
		
		ctx.fill(new Rect(cb.left, cb.top, cb.width, BORDER), headerColor);
		ctx.fill(new Rect(cb.left, cb.top + cb.height - BORDER, cb.width, BORDER), headerColor);
	}

	private Rect getHeaderBounds() {
		return new Rect(0, 0, frame.width, HEADER_HEIGHT);
	}
	
	private Rect getContentBounds() {
		return new Rect(0, HEADER_HEIGHT, frame.width, frame.height - HEADER_HEIGHT);
	}
	
	private Point dragPoint = null;
	private boolean dragging = false;
	
	public boolean doMouseEvent(MouseEvent event) {
		switch (event.type) {
			case PRESS: {
				Point location = event.locationInNode(this);
				if (getHeaderBounds().contains(location)) {
					dragging = true;
					dragPoint = event.locationInNode(this.getDesktop());
				}
				return getBounds().contains(location);
			}
			case DRAG: {
				if (dragging) {
					Point location = event.locationInNode(this.getDesktop());
					frame.left += location.x - dragPoint.x;
					frame.top +=  location.y - dragPoint.y;
					dragPoint = location;
				}
				return true;
			}
			case RELEASE: {
				dragging = false;
				return false;
			}
		}
		return false;
	}

}
