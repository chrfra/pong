package pong.model;

import org.jbox2d.dynamics.Body;

public class GameItem {
	
	private float xPos;
	private float yPos;
	private float zPos;
	private Type type;
	protected Body body;
	
	public GameItem(float xPos, float yPos, float zPos, Type type) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.type = type;
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

	public String getType() {
		return type.name();
	}

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}
}
