package by.dev.guiapp;

import by.dev.gui.Application;
import by.dev.gui.core.struct.Rect;
import by.dev.gui.node.impl.Button;
import by.dev.gui.node.impl.Window;

public class CustomApplication extends Application {

	public static void main(String[] args) {
		Application application = new CustomApplication();
		application.start();
	}
	
	public void start() {
		super.start();
		Button button = new Button(new Rect(20, 20, 40, 40));
		desktop.addSubnode(button);
		button.callback = new Runnable() {

			public void run() {
				createWindow();
			}
		};
	}
	
	
	private void createWindow() {
		Window window = new CustomWindow(new Rect(80 + desktop.childs.size() * 30, 40 + desktop.childs.size() * 30, 200, 160));
		desktop.addSubnode(window);
	}

}
