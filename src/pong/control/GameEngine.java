package pong.control;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.media.opengl.GLAutoDrawable;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import pong.model.*;
import pong.view.*;

public class GameEngine {
	
	private Physics physics;
	//Contains the items in the game. These items will be drawn
	private ArrayList<GameItem> items = new ArrayList<GameItem>();
	private ControlsInput mouse;
	
	Paddle paddle = new Paddle(0,0,0,1,1,1);
	public GameEngine(){
	}
	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		ge.run();
	}
	
	public void run(){
		System.out.println("Running the game...");
		physics = new Physics();
		physics.create();

//		addItemToGame(new Paddle(5,0,0, 2, 1, 2));
//		addItemToGame(new Paddle(-5,0,0, 2, 1, 2));
//		addItemToGame(new Ball(0,0,0,0.5f));
		addItemToGame(new Ball(6,0,0,0.5f));
		addItemToGame(paddle);
		GraphicsEngine ge = new GraphicsEngine(this);
		ge.setUp();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


		while(true){
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(paddle.getBody().getPosition().y+paddle.getHeight());
			physics.update();
			updatePos();
		}
	}
	
	/* USE THIS METHOD IF YOU WANT TO ADD OBJECTS TO THE GAME
	 * Paddles, balls, obstacles....
	 */
	public void addItemToGame(GameItem item){
		item.setBody(physics.addObject(item));
		item.getBody().setBullet(true);
		items.add(item);
		
//		//Add object to GameEngines HashMap
//		items.put(serialNo, item);
//		//Add object to physics simulation
//		physics.addObject(serialNo, item);
//		//Increase serialNo.
//		serialNo++;
	}
	
	/*
	 * Updates the coordinates of the objects in the scene.
	 * Reads from the physic simulation
	 */
	public void updatePos(){
		//variable i is used to identify the pad to be controlled by the user, since at this time no unique
		//identifier for each pad exists
		int i = 0;
		for(GameItem item : items){
			Body body;
			body = item.getBody();
			//move pad according to (mouse) input
			if (i == 0)
			body.setTransform(new Vec2(mouse.getXPos(),mouse.getYPos()), 0);
			item.setxPos(body.getPosition().x);
			item.setyPos(body.getPosition().y);
			//body.setLinearVelocity(new Vec2(0,0));
			i++;
		}
	}
	
	//creates mouse object, adds key and mouse listeners
	public void createListeners(GLAutoDrawable glDrawable){
		mouse = new ControlsInput();
		((Component) glDrawable).addKeyListener(mouse);
		((Component) glDrawable).addMouseMotionListener(mouse);
		((Component) glDrawable).addMouseListener(mouse);
	}
	
	public Paddle getPaddle(){
		return paddle;
	}
	
	public void exit(){
        System.exit(0);
	}
	
	public ArrayList<GameItem> getGameItems(){
		return items;
	}
}
