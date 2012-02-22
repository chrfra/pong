package pong.model;

public class Wall extends GameItem {
	
	private float depth = Const.GAME_DEPTH;
	
	private float length;
	
	//Is the wall standing or lying down.
	private boolean isStanding;
	
	
	public Wall(float xPos, float yPos, float zPos, float length, boolean isStanding) {
		super(xPos, yPos, zPos, Type.WALL);
		this.length = length;
		this.isStanding = isStanding;
	}


	public float getLength() {
		return length;
	}


	public boolean isStanding() {
		return isStanding;
	}


	public float getDepth() {
		return depth;
	}


	public void setDepth(float depth) {
		this.depth = depth;
	}



}
