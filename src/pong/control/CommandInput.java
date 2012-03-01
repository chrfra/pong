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
	//input stores the string (player name) entered by the player(s)
	private String input;
	//keeps track of the last pressed key ( used for determining which direction to rotate the menu cube )
	private int lastKey;
	
	public CommandInput(GameEngine ge) {
		this.ge = ge;
	}


	@Override
	public void keyPressed(KeyEvent arg0) {

		//store text if text input is enabled ( beginInput() has been called, but not endInput() yet  )
		if(activeInput()){
			int keyCode = arg0.getKeyCode();
			String key = KeyEvent.getKeyText(keyCode);
			// only accept one character input ( disregard ENTER string etc...)
			if(key.length() == 1){
				input = input + key;
			}
		}

		//Add ball to game
		if(arg0.getKeyCode() == KeyEvent.VK_B){
			ge.addItemToGame(new Ball(BALL_DEFAULT_XPOS, BALL_DEFAULT_YPOS, 0, BALL_RADIUS));
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

		/*
		 * handle enter (select) keypress
		 */
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			ge.select();
		}

		/*
		 * handle backspace (remove last char from input string)
		 */
		if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if(activeInput())//only chop string if user is inputting data
				input = chop(input);
		}

		/*
		 * Up,down,left,right presses
		 */

		/*
		 * RIGHT/LEFT
		 */

		//on right key press set the target menu rotation to 90 degrees to the right 
		//of the current rotation if user is not inputting text
		if(arg0.getKeyCode() == KeyEvent.VK_RIGHT){
			if(ge.getGameState() == IN_MENU && !activeInput()){
				lastKey = KEY_RIGHT;	//store what key was pressed
				ge.getMenu().rotateY(-90);
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_LEFT && !activeInput()){
			if(ge.getGameState() == IN_MENU){
				lastKey = KEY_LEFT;		//store what key was pressed
				ge.getMenu().rotateY(90);
			}
		}

		/*
		 * UP/DOWN
		 * unused
		 */
		if(arg0.getKeyCode() == KeyEvent.VK_UP && !activeInput()){
			if(ge.getGameState() == IN_MENU){
				ge.getMenu().rotateX(90);
			}
		}
		else if(arg0.getKeyCode() == KeyEvent.VK_DOWN && !activeInput()){
			if(ge.getGameState() == IN_MENU){
				ge.getMenu().rotateX(-90);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		/*
		 * escape key pauses the game, prints resume option on menu etc.
		 */
		if(arg0.getKeyCode() == KeyEvent.VK_ESCAPE){
			if(ge.getGameState()==IN_GAME){	//only set gamestate to paused if game is running and escape is pressed
				ge.setGameState(IN_MENU);
			}else if(ge.getGameState() == IN_MENU){	//pressing escape while game is paused and in the menu resumes the game
				if( ge.gameInitiated() ) // only resume game if the game (gameitems etc.) have been initiated
					ge.setGameState(IN_GAME);
			}
		}


	}
	/*
	 * enables storing of text input
	 */
	public void beginInput(){
		input = new String();
	}
	/*
	 * disables storing of text input, 
	 * the check input == null is used to determine wether the user is inputting data or not
	 */
	public void endInput(){
		input = null;
	}

	public String getInput() {
		return input;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {


	}
	/*
	 * removes the last character of the argument string
	 * @param the string to be chopped
	 */
	public static String chop(String str) {
		if (str == null) {
			return null;
		}
		int strLen = str.length();
		if (strLen < 2) {
			return "";
		}
		int lastIdx = strLen - 1;
		String ret = str.substring(0, lastIdx);
		char last = str.charAt(lastIdx);
		System.out.println("LAST: " + last);
		if (last == '\n') {
			if (ret.charAt(lastIdx - 1) == '\r') {
				System.out.println(ret.charAt(lastIdx - 1));
				return ret.substring(0, lastIdx - 1);
			}
		}
		return ret;
	}
	/*
	 * keeps track of wether the user is inputting text or not
	 * @return boolean input	returns true if input is active
	 */
	public boolean activeInput(){
		return input != null;
	}
	public int getLastKey() {
		return lastKey;
	}
}
