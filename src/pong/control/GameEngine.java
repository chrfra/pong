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
	//references the paddle to be controlled by player 1
	Paddle paddle;
	
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
		GraphicsEngine ge = new GraphicsEngine(this);
		ge.setUp();
		
		Player player1 = new Player("Playername1");
		paddle = new Paddle(0,Const.DEFAULT_DPADDLE_YPOS,0,1,4,1,player1);
		addItemToGame(paddle);
		Player player2 = new Player("Playername2");
		addItemToGame(new Paddle(0,Const.DEFAULT_UPADDLE_YPOS,0,1,4,1,player2));
		addItemToGame(new Ball(6,0,0,0.5f));
		
		// Delay to start the game after the window is drawn.
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Camera.smoothZoom(60);
		System.out.println(Camera.getPosition()[2]);
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
		for(GameItem item : items){
			Body body;
			body = item.getBody();
			item.setxPos(body.getPosition().x);
			item.setyPos(body.getPosition().y);
		}
	}
	
	//creates mouse object, adds key and mouse listeners
	public void createListeners(GLAutoDrawable glDrawable){
		//create mouse listener and connect it to the moveableItem to be controlled
		mouse = new ControlsInput(paddle);
		((Component) glDrawable).addKeyListener(mouse);
		((Component) glDrawable).addMouseMotionListener(mouse);
		((Component) glDrawable).addMouseListener(mouse);
	}
	
	public void exit(){
        System.exit(0);
	}
	
	public ArrayList<GameItem> getGameItems(){
		return items;
	}
}
