package pong.model;

import pong.view.Textures;

public class Ball extends MoveableItem {
	float radius;
	float rotationX = 0;
	float rotationY = 0;
	
	public Ball(float xPos, float yPos, float zPos, float radius) {
		super(xPos, yPos, zPos, Type.BALL);
		texture = Textures.ball1;
		this.radius=radius;
	}
	
	
	
	//Sets rotation of the ball and adjusts speed
	public void tick(){
		setRotation();
		adjustSpeed();
	}
	
	/**
	 * Adjusts the ball's speed if too fast.
	 * @return
	 */
	private void adjustSpeed(){
		float speed = body.getLinearVelocity().length();
		
		if(speed > Const.BALL_MAXSPEED){
			body.setLinearDamping(10.0f);
		}else{
			body.setLinearDamping(0.0f);
		}
	}
	/**
	 * sets the rotation based on the direction of the balls 
	 */
	public void setRotation(){
		int rotationSpeed = 10;
		rotationX-=(body.getLinearVelocity().y / rotationSpeed);
		rotationY+=(body.getLinearVelocity().x / rotationSpeed);
		rotationX = rotationX%360;
		rotationY = rotationY%360;
	}
	
	public float getRadius(){
		return radius;
	}
	
	public float getRotationX() {
		return rotationX;
	}
	
	public float getRotationY() {
		return rotationY;
	}
}
