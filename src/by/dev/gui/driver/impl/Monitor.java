package by.dev.gui.driver.impl;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import by.dev.gui.core.struct.Rect;
import by.dev.gui.driver.VideoDevice;

public class Monitor implements VideoDevice {

	private int[] bitmap;
	private Rect bounds;
	
	public Monitor(int width, int height) {
		super();
		this.bounds = new Rect(0, 0, width, height);
		this.bitmap = new int[3 * width * height];
	}
	
	public void setPixelColor(int x, int y, int r, int g, int b) {
		int startIndex = (x + y * bounds.width) * 3;
		bitmap[startIndex + 0] = r;
		bitmap[startIndex + 1] = g;
		bitmap[startIndex + 2] = b;
	}
	
	public BufferedImage getImage() {
		
		BufferedImage image = new BufferedImage(bounds.width, bounds.height, BufferedImage.TYPE_INT_RGB);

	    WritableRaster raster = (WritableRaster) image.getData();
	    raster.setPixels(0, 0, bounds.width, bounds.height, bitmap);
	    image.setData(raster);
	    image.flush();

		return image;
	}

	public Rect getBounds() {
		return bounds;
	}

}
