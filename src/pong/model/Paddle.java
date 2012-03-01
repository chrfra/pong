package pong.model;

import org.jbox2d.common.Vec2;

import pong.view.Textures;


public class Paddle extends MoveableItem {
	
	private float height;
	private float width;
	private float depth;


	public Paddle(float xPos, float yPos, float zPos, float height, float width, float depth) {
		super(xPos, yPos, zPos, Type.PADDLE);
		this.height = height;
		this.width = width;
		this.depth = depth;
		
		texture = Textures.paddle1;
	}
	
	public void adjustYPos(float targetY, boolean isBottom){
		//The force applied depends on the distance between targetY and current Y. Works like a rubber band.
		//More force is applied if the paddle moves to the wall that is closest.
		Float elasticityUp;
		Float elasticityDown;
		
		if(isBottom){
			elasticityUp = new Float(Math.pow(Math.abs(targetY - yPos), 2));
			elasticityDown = new Float(Math.pow(Math.abs(targetY - yPos), 4));
		}else{
			elasticityUp = new Float(Math.pow(Math.abs(targetY - yPos), 4));
			elasticityDown = new Float(Math.pow(Math.abs(targetY - yPos), 2));
		}

		//Gives the paddle some room to move freely in
		int deadZone = 2;
		
		
		if(yPos > targetY + deadZone){
//			body.setLinearVelocity(body.getLinearVelocity().sub(new Vec2(0, elasticity*1)));
			body.applyForce(body.getLinearVelocity().sub(new Vec2(0, elasticityUp*10000)), new Vec2(0,0));
		}else if(yPos < targetY - deadZone) {
//			body.setLinearVelocity(body.getLinearVelocity().add(new Vec2(0, elasticity*1)));
			body.applyForce(body.getLinearVelocity().add(new Vec2(0, elasticityDown*10000)), new Vec2(0,0));
		}
		
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
