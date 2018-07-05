package by.dev.gui.core.struct;

public class Rect { // прямоугольник
	
	public int left;
	public int top;
	public int width;
	public int height;
	
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
}