package pong.model;

import java.util.ArrayList;

public class Paddle extends MoveableItem {
	
	private float height;
	private float width;
	private float depth;
	private Player player;
	private ArrayList<GameItem> goals = new ArrayList<GameItem>();

	public Paddle(float xPos, float yPos, float zPos, float height, float width, float depth, Player player) {
		super(xPos, yPos, zPos, Type.PADDLE);
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.player = player;
	}

	public void addGoal(GameItem goal){
		goals.add(goal);
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
