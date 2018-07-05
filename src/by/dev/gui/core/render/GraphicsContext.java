package by.dev.gui.core.render;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.VideoDevice;

public class GraphicsContext {
	
	protected VideoDevice videoDevice;
	protected Point offset = new Point(0, 0);
	
	public GraphicsContext(VideoDevice videoDevice) {
		super();
		this.videoDevice = videoDevice;
	}

	public void translate(Point point) {
		offset.x += point.x;
		offset.y += point.y;
	}
	
	public void setPixelColor(Point point, Color color) {
		videoDevice.setPixelColor(offset.x + point.x, offset.y + point.y, color.r, color.g, color.b);
	}
	
	public void fill(Rect rect, Color color) {
		Point rightBottom = new Point(rect.left + rect.width, rect.top + rect.height);
		Point current = new Point(0, 0);
		//System.out.println("R: " + rect.left + " " + rect.top + " " + rect.width + " " + rect.height);
		for (current.x = rect.left; current.x < rightBottom.x; current.x++) {
			for (current.y = rect.top; current.y < rightBottom.y; current.y++) {
				setPixelColor(current, color);
			}
		}
	}
	
}