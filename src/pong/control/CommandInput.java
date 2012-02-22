package pong.control;

import static pong.model.Const.BALL_DEFAULT_XPOS;
import static pong.model.Const.BALL_DEFAULT_YPOS;
import static pong.model.Const.BALL_RADIUS;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pong.model.Ball;


/**
 * Listens for general commands that control the application. Compared to MouseInput and MotionInput this isn't connected to a player or paddle.
 * e.g. escape to pause, 1-3 to change cameramode. 
 * 
 * @author sajohan
 *
 */
public class CommandInput implements KeyListener {

	GameEngine ge;
	
	public CommandInput(GameEngine ge) {
		this.ge = ge;
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {

		//Add ball to game
		if(arg0.getKeyCode() == KeyEvent.VK_B){
			ge.addItemToGame(new Ball(BALL_DEFAULT_XPOS, BALL_DEFAULT_YPOS, 0, BALL_RADIUS));
			System.out.println("Adding ball...");
		}else if(arg0.getKeyCode() == KeyEvent.VK_V){
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
	
}
