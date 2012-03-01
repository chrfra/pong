package pong.model;

import pong.view.Textures;

public class Ball extends MoveableItem {
	float radius;
	public Ball(float xPos, float yPos, float zPos, float radius) {
		super(xPos, yPos, zPos, Type.BALL);
		texture = Textures.ball1;
		this.radius=radius;
	}
	
	/**
	 * Adjusts the ball's speed if too fast.
	 * @return
	 */
	public void adjustSpeed(){
		float speed = body.getLinearVelocity().length();
		
		if(speed > Const.BALL_MAXSPEED){
			body.setLinearDamping(10.0f);
		}else{
			body.setLinearDamping(0.0f);
		}
	}
	
	public float getRadius(){
		return radius;
	}

}
