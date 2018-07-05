package by.dev.gui;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

import by.dev.gui.driver.MouseDevice;
import by.dev.gui.driver.MouseDeviceCallback;
import by.dev.gui.driver.impl.MouseEventType;

public class MainFrame extends JFrame implements MouseListener, MouseMotionListener, MouseDevice {
	
	private Image image = null;
	private MouseDeviceCallback mouseCallback = null;
	
	public MainFrame(int x, int y, int width, int height) {
		super("Dev.by GUI");
		
		setBounds(50, 50, width, height);
		setUndecorated(true);
		setLayout(new BorderLayout());
		
		addMouseListener(this);
		addMouseMotionListener(this);
	}
	
	void setImage(Image image) {
		this.image = image;
		repaint();
	}
	
	public void paint(Graphics g) {
        g.drawImage(image, 0, 0, null);
    }

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		if (mouseCallback != null) {
			mouseCallback.onMouseEvent(new by.dev.gui.driver.impl.MouseEvent(MouseEventType.PRESS, e.getX(), e.getY()));;
		}
	}

	public void mouseReleased(MouseEvent e) {
		if (mouseCallback != null) {
			mouseCallback.onMouseEvent(new by.dev.gui.driver.impl.MouseEvent(MouseEventType.RELEASE, e.getX(), e.getY()));;
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		if (mouseCallback != null) {
			mouseCallback.onMouseEvent(new by.dev.gui.driver.impl.MouseEvent(MouseEventType.DRAG, e.getX(), e.getY()));;
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void setMouseCallback(MouseDeviceCallback callback) {
		this.mouseCallback = callback;
	}

}
