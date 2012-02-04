package pong.control;

import pong.model.*;
import pong.view.*;

public class GameEngine {
	
	private Paddle paddle;

	public GameEngine(){
		paddle = new Paddle(0,0,0, 0, 0, 0);
	}
	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		ge.run();
	}
	
	public void run(){
		System.out.println("Running the game...");
		
		
		Physics physics = new Physics();
		physics.create();
		physics.addBall();
		while(true){
			System.out.println("Updating physics");
			physics.update();
		}
		
		
		// Set up paddle
//		paddle.setxPos(0);
//		paddle.setyPos(0);
//		paddle.setzPos(0);
//		
//		paddle.setDepth(30);
//		paddle.setHeight(10);
//		paddle.setWidth(10);
//		
//		GraphicsEngine ge = new GraphicsEngine(this);
//		ge.setUp();
	
	}
	
	public void moveX(float m){
		System.out.println("XPos: " + m);
		paddle.setxPos(m);
		
	}
	
	public void moveY(float m){
		System.out.println("YPos: " + m);
		paddle.setyPos(m);
	}
	
	public Paddle getPaddle(){
		return paddle;
	}
	
	public void exit(){
        System.exit(0);
	}
	
}
