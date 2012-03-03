package pong.model;

import org.jbox2d.dynamics.Body;

import com.jogamp.opengl.util.texture.Texture;

public class GameItem {
	
	protected float xPos;
	protected float yPos;
	protected float zPos;
	//what type the object is, eg. ball, paddle etc. used in graphicsEngine when traversing items list and drawing them
	private Type type;
	protected Body body;
	
	//Texture
	protected Texture texture;
	
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
	
	public Texture getTexture() {
		return texture;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}
}
