package pong.control;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import pong.model.*;
import pong.view.*;

public class GameEngine {
	private int state = 0;
	private Physics physics;
	// Contains the items in the game. These items will be drawn
	private ArrayList<GameItem> items = new ArrayList<GameItem>();
	private ControlsInput mouse;
	// references the paddle to be controlled by player 1
	private Paddle paddle;
	private Ball mainBall;
	private Player player1;
	private Player player2;

	public GameEngine() {
	}

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		ge.run();
	}

	public void run() {
		System.out.println("Running the game...");
		physics = new Physics();
		//Create the world
		physics.create();
		
		GraphicsEngine ge = new GraphicsEngine(this);
		ge.setUp();
		
		// Show menu and let player make a choice for New Game, Quit, Highscore
		//showMenu();

		//Creates the walls that acts as goals for the player.
		Wall goal1 = new Wall(0.0f, -Const.GAME_HEIGHT/2, 0.0f, Const.GAME_WIDTH, false);  //Lower player
		Wall goal2 = new Wall(0.0f, Const.GAME_HEIGHT/2, 0.0f, Const.GAME_WIDTH, false);   //Upper player
		
		player1 = new Player("Playername1");
		paddle = new Paddle(0, Const.DEFAULT_DPADDLE_YPOS, 0, 1, 4, 1, player1);
		addItemToGame(paddle);
		player2 = new Player("Playername2");
		addItemToGame(new Paddle(0, Const.DEFAULT_UPADDLE_YPOS, 0, 1, 4, 1, player2));
		addItemToGame(mainBall = new Ball(6, 0, 0, 0.5f));
		
		//Adds the goals to physics simulation
		physics.addWall(goal1);
		physics.addWall(goal2);
		
		//Creates the sidewalls
		physics.addWall(new Wall(-Const.GAME_WIDTH/2, 0.0f, 0.0f, Const.GAME_HEIGHT, true)); //Left
		physics.addWall(new Wall(Const.GAME_WIDTH/2, 0.0f, 0.0f, Const.GAME_HEIGHT, true)); //Right


		try {
			// Delay to start the game after the window is drawn.
			Thread.sleep(2000);
			
			Camera.smoothZoom(15);
			System.out.println(Camera.getPosition()[2]);
			while (true) {
				Thread.sleep(1);
				
				//Checks if the balls have ludicrous speed.
				checkBallSpeed();
				physics.update();
				updatePos();
				
				//Put gamelogic calls here
				if(ballOut() ){
					
					updateScore();
					resetBall();
				}
				
				
				
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean ballOut() {
		// TODO Auto-generated method stub
		return false;
	}

	private void resetBall() {
		Body ball;
		ball = mainBall.getBody();
		
		// Reverse angle
		double angle = ball.getAngle();
		angle = Math.toDegrees(angle);
		angle = angle*-1;
		angle = Math.toRadians(angle);
		float newAngle = (float) angle;
		
		// Now set mainBall to default values.. ready for next round!
		ball.setTransform(new Vec2(0,0), newAngle);
		mainBall.setxPos(Const.DEFAULT_BALL_POSITION_XPOS);
		mainBall.setyPos(Const.DEFAULT_BALL_POSITION_YPOS);
		
	}

	private void updateScore() {
		// TODO Auto-generated method stub
		
	}

	/* USE THIS METHOD IF YOU WANT TO ADD OBJECTS TO THE GAME
	 * Paddles, balls, obstacles....
	 */
	public void addItemToGame(GameItem item) {
		item.setBody(physics.addObject(item));
		item.getBody().setBullet(true);
		items.add(item);
	}

	/*
	 * Updates the coordinates of the objects in the scene.
	 * Reads from the physic simulation
	 */
	public void updatePos() {
		// variable i is used to identify the pad to be controlled by the user, since at this time no unique
		// identifier for each pad exists
		for (GameItem item : items) {
			Body body;
			body = item.getBody();
			item.setxPos(body.getPosition().x);
			item.setyPos(body.getPosition().y);
		}
	}
	/**
	 * Checks a ball's speed. If it's over maximum: set lineardamping to something. If not: set lineardamping to zero.
	 * 
	 */
	public void checkBallSpeed(){
		
		for(GameItem item : items){
			if(item.getType().equals("BALL")){
				Body body = item.getBody();
				float speed = body.getLinearVelocity().length();
	
				if(speed > Const.BALL_MAXSPEED){
					body.setLinearDamping(0.8f);
				}else{
					body.setLinearDamping(0.0f);
				}
				
			}
		}
		
	}

	// creates mouse object, adds key and mouse listeners
	public void createListeners(GLAutoDrawable glDrawable) {
		// create mouse listener and connect it to the moveableItem to be controlled
		mouse = new ControlsInput(paddle);
		((Component) glDrawable).addKeyListener(mouse);
		((Component) glDrawable).addMouseMotionListener(mouse);
		((Component) glDrawable).addMouseListener(mouse);
		System.out.println(glDrawable.getWidth());
	}

	public void exit() {
		System.exit(0);
	}

	public ArrayList<GameItem> getGameItems() {
		return items;
	}
}
