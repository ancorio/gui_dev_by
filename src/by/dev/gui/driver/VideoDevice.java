package by.dev.gui.driver;

import by.dev.gui.core.struct.Rect;

public interface VideoDevice {
	
	public Rect getBounds();
	public void setPixelColor(int x, int y, int r, int g, int b);
	
}