package by.dev.gui.core.struct;

public class Rect {
	
	private int left;
	private int top;
	private int width;
	private int height;
	
	public Rect(int left, int top, int width, int height) {
		this.left = left;
		this.top = top;
		this.width = width;
		this.height = height;
	}
	
	public Rect(Rect rect) {
		this.left = rect.left;
		this.top = rect.top;
		this.width = rect.width;
		this.height = rect.height;
	}
	
	public boolean contains(Point point) {
		return left <= point.x && left + width > point.x && top <= point.y && top + height > point.y; 
	}

	public int getLeft() {
		return left;
	}
	
	public int getTop() {
		return top;
	}

	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean equals(Object texture) {
		Rect r = (Rect)texture;
		return r.left == this.left && r.top == this.top && r.width == this.width && r.height == this.height;
	}
	
}