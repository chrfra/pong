package pong.control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


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

		if(arg0.getKeyCode() == KeyEvent.VK_A){
			
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
