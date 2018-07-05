package by.dev.gui.driver.impl;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import by.dev.gui.core.render.Texture;
import by.dev.gui.core.struct.Rect;

public class Monitor extends Texture {
	
	
	public Monitor(int width, int height) {
		super();
		setBounds(new Rect(0, 0, width, height));
	}

	public BufferedImage getImage() {

		BufferedImage image = new BufferedImage(getBounds().getWidth(), getBounds().getHeight(), BufferedImage.TYPE_INT_RGB);

	    WritableRaster raster = (WritableRaster) image.getData();
	    raster.setPixels(0, 0, getBounds().getWidth(), getBounds().getHeight(), bitmap);
	    image.setData(raster);
	    image.flush();
	    
		return image;
	}

}
