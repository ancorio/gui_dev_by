package by.dev.gui.core.render;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Point;
import by.dev.gui.core.struct.Rect;

public class TextureGraphicsContext extends SafeGraphicsContext {

	private Rect bounds;
	
	public TextureGraphicsContext(Texture videoDevice, Rect bounds) {
		super(videoDevice);
		this.bounds = bounds;
		allocTexture();
	}
	
	public Texture getTexture() {
		return (Texture)videoDevice;
	}

	public void updateBounds(Rect bounds) {
		if (this.bounds.equals(bounds)) {
			return;
		}
		this.bounds = bounds;
		allocTexture();
	}
	
	private void allocTexture() {
		getTexture().setBounds(bounds);
	}
	

	public void drawTexture(Point leftTop, Texture texture, int alpha) {
		Point current = new Point(0, 0);
		Color colorA = new Color(0, 0, 0);
		Color colorB = new Color(0, 0, 0);
		for (int x = 0; x < texture.getBounds().getWidth(); x++) {
			for (int y = 0; y < texture.getBounds().getHeight(); y++) {
				current.x = leftTop.x + x;
				current.y = leftTop.y + y;
				texture.getRGB(x, y, colorA);
				getTexture().getRGB(current.x, current.y, colorB);
				colorB.r = colorB.r + (colorA.r - colorB.r) * alpha / 255;
				colorB.g = colorB.g + (colorA.g - colorB.g) * alpha / 255;
				colorB.b = colorB.b + (colorA.b - colorB.b) * alpha / 255;
				setPixelColor(current, colorB);
			}
		}
	}
	
}
