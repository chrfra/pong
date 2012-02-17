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
	private Paddle paddle1;
	private Paddle paddle2;
	private Ball mainBall;
	private Player player1;
	private Player player2;
	//gameState tells graphics engine whether to render in menu-mode or game-mode, start in menu-mode
	private int gameState = STARTUP_STATE;
	GraphicsEngine ge;
	MenuCube menu;	//the menu
	private boolean resetGame = false; 
	public GameEngine() {
	}

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		ge.initGame();
	}

	public void initGame(){
		System.out.println("Initializing the game...");
		physics = new Physics();
		//Create the world
		physics.create(this);
		ge = new GraphicsEngine(this);
		ge.setUp();

		//Creates the walls that acts as goals for the player.
		Wall goal1 = new Wall(0.0f, -Const.GAME_HEIGHT/2, 0.0f, Const.GAME_WIDTH, false);  //Lower player
		Wall goal2 = new Wall(0.0f, Const.GAME_HEIGHT/2, 0.0f, Const.GAME_WIDTH, false);   //Upper player
		
		//add player 1 to game
		paddle1 = new Paddle(0, Const.DEFAULT_DPADDLE_YPOS, 0, Const.DEFAULT_PADDLE_HEIGHT, Const.DEFAULT_PADDLE_WIDTH, Const.DEFAULT_PADDLE_DEPTH);
		player1 = new Player("Playername1", paddle1);
		player1.addGoal(goal1);
		addItemToGame(paddle1);
		
		//add player 2 to game
		paddle2 = new Paddle(0, Const.DEFAULT_UPADDLE_YPOS, 0, Const.DEFAULT_PADDLE_HEIGHT, Const.DEFAULT_PADDLE_WIDTH, Const.DEFAULT_PADDLE_DEPTH);
		player2 = new Player("Playername2", paddle2);
		player2.addGoal(goal2);
		addItemToGame(paddle2);

		//create the menu cube
		menu = new MenuCube(0,0,MENU_ZPOS,MENU_SIZE,MENU_SIZE,MENU_SIZE);

		//add ball to game
		addItemToGame(mainBall = new Ball(6, 0, 0, Const.BALL_RADIUS));
		
		//Adds the goals to physics simulation
		physics.addWall(goal1);
		physics.addWall(goal2);
		
		//Creates the sidewalls
		physics.addWall(new Wall(-Const.GAME_WIDTH/2, 0.0f, 0.0f, Const.GAME_HEIGHT, true)); //Left
		physics.addWall(new Wall(Const.GAME_WIDTH/2, 0.0f, 0.0f, Const.GAME_HEIGHT, true)); //Right
		
		//Run the game.
		this.run();
	}
	public void run() {
		System.out.println("Running the game...");
		try {
		//run game, draw score, zoom etc. if starting/resuming the game
		if(gameState == IN_GAME){

			// Delay to start the game after the window is drawn.
			Thread.sleep(2000);
			
			Camera.smoothZoom(90);
			System.out.println(Camera.getPosition()[2]);
			while (true) {
				Thread.sleep(1);
				
				//Checks if the balls have ludicrous speed.
				checkBallSpeed();
				physics.update();
				updatePos();
				if(resetGame == true){
					resetBall();
				}
			}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void ballOut(Player losingPlayer) {
		//Put gamelogic calls here
		Player winner = new Player("", null);
		if( losingPlayer == player1 ){
			winner = player2;
		}
		else{
			winner = player1;
		}
		
		// Increase the winners score!
		updateScore(winner);
		// Set ball to default position, ready for next round
		resetGame = true;
		
		// TODO Print next-round string?
	}

	public void resetBall() {
		Body ball;
		ball = mainBall.getBody();
		
		Random generator = new Random();
		float r = generator.nextFloat();
		
		Vec2 vec = new Vec2(ball.getLinearVelocity());
		
		// Now set mainBall to default values.. ready for next round!
		ball.setTransform(new Vec2(0,0), 0);
		mainBall.setxPos(Const.DEFAULT_BALL_POSITION_XPOS);
		mainBall.setyPos(Const.DEFAULT_BALL_POSITION_YPOS);
		float x = vec.x;
		float y = vec.y;
		x = x*-1+r;
		y = y*-1;
		vec.set(x, y);
		
		ball.setLinearVelocity(vec);

		resetGame = false;
		
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
		mouse = new ControlsInput(paddle1);
		((Component) glDrawable).addKeyListener(mouse);
		((Component) glDrawable).addMouseMotionListener(mouse);
		((Component) glDrawable).addMouseListener(mouse);
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

	public void exit() {
		System.exit(0);
	}

	public ArrayList<GameItem> getGameItems() {
		return items;
	}

	public int getGameState() {
		return gameState;
	}
	
	public Player getPlayer1(){
		return player1; 
	}
	public Player getPlayer2(){
		return player2; 
	}

	public MenuCube getMenu(){
		return menu; 
	}


}
