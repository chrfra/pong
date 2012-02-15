package pong.control;

import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
//static import below enables us to write x == IN_MENU instead of x == Const.IN_MENU
import static pong.model.Const.*;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import pong.model.*;
import pong.view.*;

public class GameEngine {
	private Physics physics;
	// Contains the items in the game. These items will be drawn
	private ArrayList<GameItem> items = new ArrayList<GameItem>();
	private ControlsInput mouse;
	// references the paddle to be controlled by player 1
	private Paddle paddle;
	private Ball mainBall;
	private Player player1;
	private Player player2;
	private int gameState = IN_MENU;
	GraphicsEngine ge;
	
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
		ge = new GraphicsEngine(this);
		ge.setUp();
		
		// Show menu and let player make a choice for New Game, Quit, Highscore
		//showMenu();

		//Creates the walls that acts as goals for the player.
		Wall goal1 = new Wall(0.0f, -Const.GAME_HEIGHT/2, 0.0f, Const.GAME_WIDTH, false);  //Lower player
		Wall goal2 = new Wall(0.0f, Const.GAME_HEIGHT/2, 0.0f, Const.GAME_WIDTH, false);   //Upper player
		
		player1 = new Player("Playername1");
		paddle = new Paddle(0, Const.DEFAULT_DPADDLE_YPOS, 0, 1, 4, 1, player1);
		addItemToGame(paddle);
		
		//add player 2 to game
		player2 = new Player("Playername2");
		addItemToGame(new Paddle(0, Const.DEFAULT_UPADDLE_YPOS, 0, 1, 4, 1, player2));
		
		//add ball to game
		addItemToGame(mainBall = new Ball(6, 0, 0, 0.5f));
		
		//Adds the goals to physics simulation
		physics.addWall(goal1);
		physics.addWall(goal2);
		
		//Creates the sidewalls
		physics.addWall(new Wall(-Const.GAME_WIDTH/2, 0.0f, 0.0f, Const.GAME_HEIGHT, true)); //Left
		physics.addWall(new Wall(Const.GAME_WIDTH/2, 0.0f, 0.0f, Const.GAME_HEIGHT, true)); //Right

		// create the menu cube
		MenuCube menuCube = new MenuCube(0, 0, 90, 3, 3, 3);

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
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void ballOut(Player losingPlayer) {
		//Put gamelogic calls here
		Player winner = new Player("");
		if( losingPlayer == player1 ){
			winner = player2;
		}
		else{
			winner = player1;
		}
		
		// Increase the winners score!
		updateScore(winner);
		String url = "blip.wav";
		playSound(url);
		// Set ball to default position, ready for next round
		resetBall();
		
		// TODO Print next-round string?
	}

	public void resetBall() {
		Body ball;
		ball = mainBall.getBody();
		
		// Reverse angle
		double angle = ball.getAngle();
		angle = Math.toDegrees(angle);
		
		// set +- 60 degrees randomized 
		Random generator = new Random();
		int r = generator.nextInt(30);
		
		angle = angle-180+r;
		angle = Math.toRadians(angle);
		float newAngle = (float) angle;
		
		// Now set mainBall to default values.. ready for next round!
		ball.setTransform(new Vec2(0,0), newAngle);
		mainBall.setxPos(Const.DEFAULT_BALL_POSITION_XPOS);
		mainBall.setyPos(Const.DEFAULT_BALL_POSITION_YPOS);
		
	}

	/*	
	 * 	Increase the winning players score by 100
	 *  @param winner
	 */
	public void updateScore(Player winner) {
		int score = 0;
		score = winner.getScore();
		// Increase points with 100
		winner.setScore(score+100); 
	}
	
	public Player getPlayer1(){
		return player1; 
	}
	public Player getPlayer2(){
		return player2; 
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

	public int getGameState() {
		return gameState;
	}

	/*
	 * Play sound testing
	 * http://www.soundbyter.com/2011/04/free-sci-fi-tone-sound-effect/ blip sound source
	 * @param Takes in a filename for the sound to be played, plays it in a own thread
	 */
	public synchronized void playSound(final String url) {
		new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see comments
			public void run() {
				try {
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream("/resource/" + url));
					clip.open(inputStream);
					clip.start(); 
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
}
