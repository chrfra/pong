package pong.model;

/* Used for constant variables */

public class Const {

	public static final int IN_MENU = 0;
	public static final int IN_GAME = 1;
	public static final int STARTUP_STATE = IN_GAME;	//starts the game in menu-mode/game-mode
	
	//input constants
	public static final float MOUSE_OFFSET = 0.0f;
	public static final float MOUSE_SENSE = 6f;
	public static final float MOUSE_DEADZONE = 0.1f;
	
	// For the screen area
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	//size of the (cubic) menu
	public static final float MENU_SIZE = 9f;
	public static final float MENU_ZPOS = 65;
			
	// Game area values
	public static final float GAME_WIDTH = 80;
	public static final float GAME_HEIGHT = 80;
	public static final float GAME_DEPTH = 2;
	
	// Default values for paddles
	public static final float DEFAULT_PADDLE_HEIGHT = 2;
	public static final float DEFAULT_PADDLE_WIDTH = 8;
	public static final float DEFAULT_PADDLE_DEPTH = 2;
	
	public static final float DEFAULT_UPADDLE_XPOS = 0;
	public static final float DEFAULT_UPADDLE_YPOS = GAME_HEIGHT/2 - DEFAULT_PADDLE_HEIGHT;
	public static final float DEFAULT_UPADDLE_ZPOS = 0;
	
	public static final float DEFAULT_DPADDLE_XPOS = 0;
	public static final float DEFAULT_DPADDLE_YPOS = -GAME_HEIGHT/2 + DEFAULT_PADDLE_HEIGHT;
	public static final float DEFAULT_DPADDLE_ZPOS = 0;
	
	// Default values for balls
	public static final float BALL_MAXSPEED = 2;
	public static final float BALL_RADIUS = 1.5f;
	
	// Default value for the main ball
	public static final float DEFAULT_BALL_POSITION_XPOS = 6;
	public static final float DEFAULT_BALL_POSITION_YPOS = 0;
	
	// Default values for Camera
	public static final float CAMERA_LOOK_AT_X = 0;
	public static final float CAMERA_LOOK_AT_Y = 0;
	public static final float CAMERA_LOOK_AT_Z = 0;
	
	public static final float CAMERA_POSITION_X = 0;
	public static final float CAMERA_POSITION_Y = 0;
	public static final float CAMERA_POSITION_Z = 100;
}
