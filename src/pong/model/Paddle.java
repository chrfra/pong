package pong.model;

public class Paddle extends MoveableItem {
	
	private double height;
	private double width;
	private double depth;

	public Paddle(double xPos, double yPos, double zPos) {
		super(xPos, yPos, zPos);
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

}
