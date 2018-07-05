package by.dev.gui.core.render;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.VideoDevice;

public class GraphicsContext {
	
	protected VideoDevice videoDevice;
	
	public GraphicsContext(VideoDevice videoDevice) {
		super();
		this.videoDevice = videoDevice;
	}

	
	public void setPixelColor(Point point, Color color) {
		videoDevice.setPixelColor(point.x, point.y, color.r, color.g, color.b);
	}
	
	public void fill(Rect rect, Color color) {
		Point rightBottom = new Point(rect.getLeft() + rect.getWidth(), rect.getTop() + rect.getHeight());
		Point current = new Point(0, 0);
		for (current.x = rect.getLeft(); current.x < rightBottom.x; current.x++) {
			for (current.y = rect.getTop(); current.y < rightBottom.y; current.y++) {
				setPixelColor(current, color);
			}
		}
	}
	
	public void drawTexture(Point leftTop, Texture texture) {
		Point current = new Point(0, 0);
		Color color = new Color(0, 0, 0);
		for (int x = 0; x < texture.getBounds().getWidth(); x++) {
			for (int y = 0; y < texture.getBounds().getHeight(); y++) {
				current.x = leftTop.x + x;
				current.y = leftTop.y + y;
				texture.getRGB(x, y, color);
				setPixelColor(current, color);
			}
		}
	}
	
}