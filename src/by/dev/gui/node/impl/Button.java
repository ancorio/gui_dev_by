package by.dev.gui.node.impl;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.node.Node;

public class Button extends Node {
	
	public Runnable callback;

	public Button(Rect frame) {
		super(frame);
	}

	public Color defaultColor = new Color(255, 255, 255);
	public Color activeColor = new Color(220, 220, 220);
	
	private boolean active = false;
	

	protected void drawMe(GraphicsContext ctx) {
  		super.drawMe(ctx);
  		if (active) {
  			if (activeColor != null) {
  				ctx.fill(getBounds(), activeColor);
  			}
  		} else {
  			if (defaultColor != null) {
  				ctx.fill(getBounds(), defaultColor);
  			}
  		}
	}
	
	public boolean doMouseEvent(MouseEvent event) {
		switch (event.type) {
			case PRESS: {
				if (getBounds().contains(event.locationInNode(this))) {
					active = true;
					setNeedsRedraw();
					return true;
				} else {
					return false;
				}
			}
			case DRAG: {
				if (active) {
					if (getBounds().contains(event.locationInNode(this))) {
						return true;
					}
				}
				active = false;
				setNeedsRedraw();
				return false;
			}
			case RELEASE: {
				if (active) {
					if (getBounds().contains(event.locationInNode(this))) {
						if (callback != null) {
							getRuntime().invokeLater(callback);
						}
					}
					active = false;
					setNeedsRedraw();
				}
				return false;
			}
		}
		return false;
	}

}
