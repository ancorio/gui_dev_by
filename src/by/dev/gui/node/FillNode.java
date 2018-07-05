package by.dev.gui.node;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Rect;

public class FillNode extends Node {
	
	private Color backgroundColor = null;

	public FillNode(Rect frame) {
		super(frame);
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	protected void drawMe(GraphicsContext ctx) {
  		super.drawMe(ctx);
		if (backgroundColor != null) {
			ctx.fill(getBounds(), backgroundColor);
		}
	}
	
}
