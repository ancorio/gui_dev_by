package by.dev.gui.core.render;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;
import by.dev.gui.driver.VideoDevice;

public class SafeGraphicsContext extends GraphicsContext {

	public SafeGraphicsContext(VideoDevice videoDevice) {
		super(videoDevice);
	}
	
	public void setPixelColor(Point point, Color color) {
		if (videoDevice.getBounds().contains(point)) {
			super.setPixelColor(point, color);
		}
	}
}
