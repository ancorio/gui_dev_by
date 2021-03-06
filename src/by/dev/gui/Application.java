package by.dev.gui;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.render.SafeGraphicsContext;
import by.dev.gui.core.struct.Point;
import by.dev.gui.driver.MouseDeviceCallback;
import by.dev.gui.driver.impl.Monitor;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.driver.impl.MouseEventInvoke;
import by.dev.gui.node.impl.Desktop;

public class Application extends Thread implements MouseDeviceCallback {
	
	protected MainFrame window;
	protected Monitor monitor;
	protected Desktop desktop;
	private LinkedList <Runnable> invokeQueue = new LinkedList<>();
	
	public void start() {
		final int width = 800;
		final int height = 600;
		
		window = new MainFrame(300, 200, width, height);
		window.setVisible(true);
		monitor = new Monitor(width, height);
		desktop = new Desktop(width, height);
		desktop.setApplication(this);
		window.setMouseCallback(this);
		super.start();
	}

	public void run() {
		while (true) {
			invokes();
			draw();
			renderOnDevice();
		}
	}
	
	private void invokes() {
		while (true) {
			
			Runnable invoke = null;
			synchronized (invokeQueue) {
				if (invokeQueue.size() > 0) {
					invoke = invokeQueue.removeFirst();
				} else {
					break;
				}
			}
			if (invoke != null) {
				invoke.run();
			}
		}
	}
	
	public void invokeLater(Runnable runnable) {
		synchronized (invokeQueue) {
			invokeQueue.add(runnable);
		}
		
	}
	
	private void draw() {
		desktop.draw();
		GraphicsContext ctx = new SafeGraphicsContext(monitor);
		ctx.drawTexture(new Point(0, 0), desktop.getContext().getTexture());
	}
	
	private BufferedImage image = null;
	
	private void renderOnDevice() {
		image = monitor.getImage();
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					window.setImage(image);
				}
			});
		} catch (Exception e) {
		}
	}

	public void onMouseEvent(MouseEvent event) {
		invokeLater(new MouseEventInvoke(event, desktop));
	}
	
}
