package pong.control;

import pong.model.*;
import pong.view.*;

public class GameEngine {

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		ge.run();
	}
	
	public void run(){
		System.out.println("Running the game...");
		GraphicsEngine ge = new GraphicsEngine(this);
		ge.setUp();
		
		// Set up paddle
		Paddle paddle = new Paddle(0, 0, 0);
		paddle.setDepth(30);
		paddle.setHeight(10);
		paddle.setDepth(10);
		
	}
	
}
