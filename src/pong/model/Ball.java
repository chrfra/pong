package pong.model;

public class Ball extends MoveableItem {
	float radius;
	public Ball(float xPos, float yPos, float zPos, float radius) {
		super(xPos, yPos, zPos);
		this.radius=radius;
	}
	public float getRadius(){
		return radius;
	}
}
