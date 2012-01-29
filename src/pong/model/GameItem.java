package pong.model;

public class GameItem {
	
	private double xPos;
	private double yPos;
	private double zPos;
	
	public GameItem(double xPos, double yPos, double zPos) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
	}
	
	public double getxPos() {
		return xPos;
	}
	public void setxPos(double xPos) {
		this.xPos = xPos;
	}
	public double getyPos() {
		return yPos;
	}
	public void setyPos(double yPos) {
		this.yPos = yPos;
	}
	public double getzPos() {
		return zPos;
	}
	public void setzPos(double zPos) {
		this.zPos = zPos;
	}
	

}
