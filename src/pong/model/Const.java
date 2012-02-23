package pong.model;

/* Used for constant variables */

public class Const {
	//gamestates, used in gameengine and graphicsengine game loops to determine what to draw/process
	public static final int IN_MENU = 0;
	public static final int IN_GAME = 1;
	public static final int GAME_ENDED = 3;
	public static final int PAUSED = 4;
	public static final int STARTUP_STATE = 0;	//starts the game in menu-mode/game-mode
	
	//input constants
	public static final float MOUSE_OFFSET = 0.0f;
	public static final float MOUSE_SENSE = 40f;
	public static final float MOUSE_DEADZONE = 0.1f;
	
	// For the screen area
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	/*
	 * Menu options
	 */
	//size of the (cubic) menu
	public static final float MENU_SIZE = 9f;
	public static final float MENU_ZPOS = 98;
	
	//identifiers for the different sides of the cube, used logically to identify the sides from eachother when calling updateOption()
	public static final int MENU_TOP = 0;
	public static final int MENU_FRONT = 1;
	public static final int MENU_RIGHT = 2;
	public static final int MENU_BACK = 3;
	public static final int MENU_LEFT = 4;
	public static final int MENU_BOTTOM= 5;

	public static final float RY_SPEED = 1f; //initial rotationspeed around the y-axis MUST BE DIVISIBLE BY 360!
			
	// Game area values
	public static final float GAME_WIDTH = 80;
	public static final float GAME_HEIGHT = 80;
	public static final float GAME_DEPTH = 5;
	
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
	public static final float BALL_MAXSPEED = 15;
	public static final float BALL_RADIUS = 1.5f;
	
	// Default value for the main ball
	public static final float BALL_DEFAULT_XPOS = 0;
	public static final float BALL_DEFAULT_YPOS = 0;
	
	// Default values for Camera
	public static final float CAMERA_LOOK_AT_X = 0;
	public static final float CAMERA_LOOK_AT_Y = 0;
	public static final float CAMERA_LOOK_AT_Z = 0;
	
	public static final float CAMERA_POSITION_X = 0;
	public static final float CAMERA_POSITION_Y = 0;
	public static final float CAMERA_POSITION_Z = 100;
	
	// Default values for Players
	public static final int DEFAULT_STARTING_SCORE = 0;
	public static final int DEFAULT_AMOUNT_PLAYER_LIVES = 3;
	
	//sets how fast the game logic is to be updated, when this framerate is exceeded, the game(logic) thread sleeps
	public static double TARGET_FRAMERATE = 60;
	
	//rendering constants
	public static final int VSYNC = 1; //0 = off 1 = on
}
