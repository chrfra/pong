package pong.control;

import static pong.model.Const.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import pong.model.Ball;
import pong.view.Camera;


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
		if(arg0.getKeyCode() == KeyEvent.VK_1){
			Camera.setMode(CAM_STATIC);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_2){
			Camera.setMode(CAM_FOLLOW_BALLS);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_3){
			Camera.setMode(CAM_LOOKAT_BALLS);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_4){
			Camera.setMode(CAM_PADDLE1);
		}
		if(arg0.getKeyCode() == KeyEvent.VK_5){
			Camera.setMode(CAM_PADDLE2);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		//escape key pauses the game, prints resume option etc on menu
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(ge.getGameState()==IN_GAME){	//only set gamestate to paused if game is running and escape is pressed
				ge.setGameState(PAUSED);
			}else if(ge.getGameState() == IN_MENU){	//pressing escape in the menu resumes the game
				ge.setGameState(IN_GAME);
			}
		}

		/*
		 * Up,down,left,right presses
		 */
		
		/*
		 * RIGHT/LEFT
		 */
		//on right key press set the target menu rotation to 90 degrees to the right of the current rotation 
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			if(ge.getGameState() == IN_MENU && ge.getMenu().getRx() < 90){
				//don't decrease target y-rotation to current target rotation + 90 degrees if the cube has not fully rotated to it's target position yet!-results in over/under -shoot!
				if(ge.getMenu().getRy() == ge.getMenu().getTy())	
					ge.getMenu().setTy((ge.getMenu().getTy() + 90));
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_LEFT){
			if(ge.getGameState() == IN_MENU && ge.getMenu().getRx() < 90){
				//don't decrease target y-rotation to current target rotation - 90 degrees if the cube has not fully rotated to it's target position yet!-results in over/under -shoot!
				if(ge.getMenu().getRy() == ge.getMenu().getTy())	{
					ge.getMenu().setTy(ge.getMenu().getTy() - 90);
				}
			}
		}
		
		/*
		 * UP/DOWN
		 */
		if(arg0.getKeyCode() == KeyEvent.VK_UP){
			if(ge.getGameState() == IN_MENU){
				if(ge.getMenu().getRx() == ge.getMenu().getTx())	
					ge.getMenu().setTx((ge.getMenu().getTx() + 90));
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_DOWN){
			if(ge.getGameState() == IN_MENU){
				if(ge.getMenu().getRx() == ge.getMenu().getTx())	{
					ge.getMenu().setTx(ge.getMenu().getTx() - 90);
				}
			}
		}
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}




}
