package by.dev.guiapp;

import by.dev.gui.core.struct.Color;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.node.impl.Button;
import by.dev.gui.node.impl.Window;

public class CustomWindow extends Window {
	
	public CustomWindow(Rect frame) {
		super(frame);
		addButtons();
	}
	

	public void addButtons() {
		int size = HEADER_HEIGHT - BORDER * 3;
		Button closeButton = new Button(new Rect(getFrame().getWidth() - BORDER * 2 - size, BORDER * 2, size, size));
		addSubnode(closeButton);
		closeButton.callback = new Runnable() {
			public void run() {
				removeFromParent();
			}
		};
		Button incAplhaButton = new Button(new Rect(20, 50, 40, 40));
		addSubnode(incAplhaButton);
		incAplhaButton.callback = new Runnable() {
			public void run() {
				incAplha();
			}
		};

		Button decAplhaButton = new Button(new Rect(80, 50, 40, 40));
		addSubnode(decAplhaButton);
		decAplhaButton.callback = new Runnable() {
			public void run() {
				decAplha();
			}
		};
	}

	private void incAplha() {
		setAlpha(Math.min(getAlpha() + 25, 255));
	}

	private void decAplha() {
		setAlpha(Math.max(getAlpha() - 25, 0));
	}
	
}
