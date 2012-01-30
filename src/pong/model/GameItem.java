package pong.model;

public class GameItem {
	
	private float xPos;
	private float yPos;
	private float zPos;
	
	public GameItem(float xPos, float yPos, float zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}
	
	public float getxPos() {
		return xPos;
	}
	public void setxPos(float xPos) {
		this.xPos = xPos;
	}
	public float getyPos() {
		return yPos;
	}
	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	public float getzPos() {
		return zPos;
	}
	public void setzPos(float zPos) {
		this.zPos = zPos;
	}
	

}
