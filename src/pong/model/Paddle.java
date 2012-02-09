package pong.model;

public class Paddle extends MoveableItem {
	
	private float height;
	private float width;
	private float depth;
	private Player player;

	public Paddle(float xPos, float yPos, float zPos, float height, float width, float depth, Player player) {
		super(xPos, yPos, zPos, Type.PADDLE);
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.player = player;
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
