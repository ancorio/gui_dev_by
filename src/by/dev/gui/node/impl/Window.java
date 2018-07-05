package by.dev.gui.node.impl;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.node.FillNode;

public class Window extends FillNode {
	
	final int HEADER_HEIGHT = 20;
	final int BORDER = 2;
	
	private boolean active = false;
	
	public Window(Rect frame) {
		super(frame);
		setBackgroundColor(new Color(240, 240, 120));
		addButtons();
	}
	
	public void addButtons() {
		int size = HEADER_HEIGHT - BORDER * 3;
		Button closeButton = new Button(new Rect(getFrame().getWidth() - BORDER * 2 - size, BORDER * 2, size, size));
		addSubnode(closeButton);
		closeButton.callback = new Runnable() {
			public void run() {
				removeFromParent();
			}
		};
		Button incAplhaButton = new Button(new Rect(20, 50, 40, 40));
		addSubnode(incAplhaButton);
		incAplhaButton.callback = new Runnable() {
			public void run() {
				incAplha();
			}
		};

		Button decAplhaButton = new Button(new Rect(80, 50, 40, 40));
		addSubnode(decAplhaButton);
		decAplhaButton.callback = new Runnable() {
			public void run() {
				decAplha();
			}
		};
	}

	private void incAplha() {
		alpha = Math.min(alpha + 25, 255);
	}

	private void decAplha() {
		alpha = Math.max(alpha - 25, 10);
	}

	protected void drawMe(GraphicsContext ctx) {
		super.drawMe(ctx);
		Color headerColor;
		if (active) {
			headerColor = new Color(255, 80, 80);
		} else {
			headerColor = new Color(200, 255, 170);
		}
		
		ctx.fill(getHeaderBounds(),headerColor);
		headerColor.r /= 2;
		headerColor.g /= 2;
		headerColor.b /= 2;
		
		Rect frame = getFrame();
		
		ctx.fill(new Rect(0, 0, frame.getWidth(), BORDER), headerColor);
		ctx.fill(new Rect(0, 0, BORDER, frame.getHeight()), headerColor);
		ctx.fill(new Rect(frame.getWidth() - BORDER, 0, BORDER, frame.getHeight()), headerColor);
		
		Rect cb = getContentBounds();
		
		ctx.fill(new Rect(cb.getLeft(), cb.getTop(), cb.getWidth(), BORDER), headerColor);
		ctx.fill(new Rect(cb.getLeft(), cb.getTop() + cb.getHeight() - BORDER, cb.getWidth(), BORDER), headerColor);
	}

	private Rect getHeaderBounds() {
		return new Rect(0, 0, getFrame().getWidth(), HEADER_HEIGHT);
	}
	
	private Rect getContentBounds() {
		return new Rect(0, HEADER_HEIGHT, getFrame().getWidth(), getFrame().getHeight() - HEADER_HEIGHT);
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
					Rect frame = getFrame();
					frame = new Rect(frame.getLeft() + location.x - dragPoint.x, frame.getTop() + location.y - dragPoint.y, frame.getWidth(), frame.getHeight());
					setFrame(frame);
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
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		setNeedsRedraw();
	}


}
