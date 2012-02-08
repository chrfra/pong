package pong.model;

import org.jbox2d.common.Vec2;

import pong.control.ControlsInput;

public class MoveableItem extends GameItem{

	public MoveableItem(float xPos, float yPos, float zPos, Type type) {
		super(xPos, yPos, zPos, type);
	}
	
	
	//move pad according to (mouse) input
	public void moveItem(ControlsInput mouse){
		
		body.setTransform(new Vec2(mouse.getXPos(),mouse.getYPos()), 0);
	}
}
