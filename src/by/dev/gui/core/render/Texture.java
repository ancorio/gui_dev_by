package by.dev.gui.core.render;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.VideoDevice;

public class Texture implements VideoDevice {
	
	private Rect bounds;
	protected int[] bitmap;
	
	public Texture() {
		super();
	}

	private void alloc() {
		bitmap = new int[3 * bounds.getWidth() * bounds.getHeight()];
	}
	
	public void setBounds(Rect bounds) {
		this.bounds = bounds;
		alloc();
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setPixelColor(int x, int y, int r, int g, int b) {
		int startIndex = (x + y * bounds.getWidth()) * 3;
		bitmap[startIndex + 0] = r;
		bitmap[startIndex + 1] = g;
		bitmap[startIndex + 2] = b;
	}

	public void getRGB(int x, int y, Color color) {
		try {
			int startIndex = (x + y * bounds.getWidth()) * 3;
			color.r = bitmap[startIndex + 0];
			color.g = bitmap[startIndex + 1];
			color.b = bitmap[startIndex + 2];
		} catch (Exception e) {
		}
	}
	
	

}
