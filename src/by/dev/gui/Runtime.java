package by.dev.gui;

import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.SwingUtilities;

import by.dev.gui.core.render.GraphicsContext;
import by.dev.gui.core.render.SafeGraphicsContext;
import by.dev.gui.driver.MouseDeviceCallback;
import by.dev.gui.driver.impl.Monitor;
import by.dev.gui.driver.impl.MouseEvent;
import by.dev.gui.driver.impl.MouseEventInvoke;
import by.dev.gui.node.impl.Desktop;

public class Runtime extends Thread implements MouseDeviceCallback {
	
	private MainFrame window;
	private Monitor monitor;
	private Desktop desktop;
	private LinkedList <Runnable> invokeQueue = new LinkedList<>();
	
	public void start() {
		final int width = 1024;
		final int height = 768;
		
		window = new MainFrame(300, 200, width, height);
		window.setVisible(true);
		monitor = new Monitor(width, height);
		desktop = new Desktop(width, height);
		window.setMouseCallback(this);
		super.start();
	}

	public void run() {
		while (true) {
			invokes();
			draw();
			renderOnMonitor();
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
	
	private void invokeLater(Runnable runnable) {
		synchronized (invokeQueue) {
			invokeQueue.add(runnable);
		}
		
	}
	
	private void draw() {
		GraphicsContext ctx = new SafeGraphicsContext(monitor);
		desktop.draw(ctx);
	}
	
	private void renderOnMonitor() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					BufferedImage image = monitor.getImage();
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
