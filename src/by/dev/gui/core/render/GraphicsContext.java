package by.dev.gui.core.render;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;

public class GraphicsContext {
	
	private VideoDevice videoDevice;
	private Point offset = new Point(0, 0);
	
	public void translate(Point point) {
		offset.x += point.x;
		offset.y += point.y;
	}
	
	public void setPixelColor(Point point, Color color) {
		videoDevice.setPixelColor(offset.x + point.x, offset.y + point.y, color.r, color.g, color.b);
	}
	
}