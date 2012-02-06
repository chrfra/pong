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
	//Contains the items in the game. These items will be drawn
	private ArrayList<GameItem> items = new ArrayList<GameItem>();

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
		
		for(GameItem item : items){
			Body body;
			body = item.getBody();
			item.setxPos(body.getPosition().x);
			item.setyPos(body.getPosition().y);
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
		return items;
	}
}
