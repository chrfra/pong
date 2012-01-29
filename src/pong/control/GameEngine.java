package pong.control;

import pong.view.GraphicsEngine;

public class GameEngine {

	public static void main(String[] args) {
		GameEngine ge = new GameEngine();
		ge.run();
	}
	
	public void run(){
		System.out.println("Running the game...");
		GraphicsEngine ge = new GraphicsEngine(this);
		ge.setUp();
	}
	
}
