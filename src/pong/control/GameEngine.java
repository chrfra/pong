package pong.control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.jbox2d.dynamics.Body;

import pong.model.*;
import pong.view.*;

public class GameEngine {
	
	private Physics physics;
	//HashMap for GameItems. Needs to be synchronized?
	private HashMap<Integer, GameItem> items = new HashMap<Integer, GameItem>();
	//Used as an identifier for an object in the game.
	Integer serialNo = new Integer(0);

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

		addItemToGame(new Paddle(150,10,0, 10, 10, 2));
		addItemToGame(new Paddle(150,50,0, 10, 10, 2));
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
			physics.update();
			updatePos();
		}
	}
	
	/* USE THIS METHOD IF YOU WANT TO ADD OBJECTS TO THE GAME
	 * Paddles, balls, obstacles....
	 */
	public void addItemToGame(GameItem item){
		//Add object to GameEngines HashMap
		items.put(serialNo, item);
		//Add object to physics simulation
		physics.addObject(serialNo, item);
		//Increase serialNo.
		serialNo++;
	}
	
	/*
	 * Updates the coordinates of the objects in the scene.
	 * Reads from the physic simulation
	 */
	public void updatePos(){
		
		
		HashSet<Map.Entry<Integer, Body>> physSet = physics.getObjects();
//		HashSet<Map.Entry<Integer, GameItem>> engineSet = new HashSet<Map.Entry<Integer, GameItem>>(items.entrySet());
		for(Map.Entry<Integer, Body> bodyEntry : physSet){
			
			//Gets the item in GameEngines list corresponding to body's key
			GameItem item = items.get(bodyEntry.getKey());
			Body body = bodyEntry.getValue();
			item.setxPos(body.getPosition().x);
			item.setyPos(body.getPosition().y);
			items.put(bodyEntry.getKey(), item);
			
		}
	}
	
	public void moveX(float m){
//		System.out.println("XPos: " + m);
		paddle.setxPos(m);
		
	}
	
	public void moveY(float m){
//		System.out.println("YPos: " + m);
		paddle.setyPos(m);
	}
	
	public Paddle getPaddle(){
		return paddle;
	}
	
	public void exit(){
        System.exit(0);
	}
	
	public ArrayList<GameItem> getGameItems(){
		return new ArrayList<GameItem>(items.values());
	}
}
