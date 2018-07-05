package by.dev.gui.core.render;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;
import by.dev.gui.driver.VideoDevice;

public class SafeGraphicsContext extends GraphicsContext {

	public SafeGraphicsContext(VideoDevice videoDevice) {
		super(videoDevice);
	}
	
	
	public void setPixelColor(Point point, Color color) {
		int x = offset.x + point.x;
		int y = offset.y + point.y;
		if (videoDevice.getBounds().contains(new Point(x, y))) {
			super.setPixelColor(point, color);
		}
	}
}
