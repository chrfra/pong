package pong.model;

public class Paddle extends MoveableItem {
	
	private float height;
	private float width;
	private float depth;

	public Paddle(float xPos, float yPos, float zPos) {
		super(xPos, yPos, zPos);
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}

}
