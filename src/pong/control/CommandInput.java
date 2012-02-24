package pong.control;

import static pong.model.Const.*;
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
		}

		/*
		 * Up,down,left,right presses
		 */

		/*
		 * RIGHT/LEFT
		 */

		//on right key press set the target menu rotation to 90 degrees to the right 
		//of the current rotation 
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			if(ge.getGameState() == IN_MENU){
				ge.getMenu().rotateY(-90);
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_LEFT){
			if(ge.getGameState() == IN_MENU){
				ge.getMenu().rotateY(90);
			}
		}

		/*
		 * UP/DOWN
		 */
		if(arg0.getKeyCode() == KeyEvent.VK_UP){
			if(ge.getGameState() == IN_MENU){
				ge.getMenu().rotateX(90);
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_DOWN){
			if(ge.getGameState() == IN_MENU){
				ge.getMenu().rotateX(-90);
		}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//escape key pauses the game, prints resume option etc on menu
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(ge.getGameState()==IN_GAME){	//only set gamestate to paused if game is running and escape is pressed
				ge.setGameState(PAUSED);
			}else if(ge.getGameState() == PAUSED){	//pressing escape while game is paused and in the menu resumes the game
				ge.setGameState(IN_GAME);
			}
		}


	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}




}
