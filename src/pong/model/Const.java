package pong.model;

/* Used for constant variables */

public class Const {
	//input constants
	public static final float MOUSE_OFFSET = 165f;
	public static final float MOUSE_SENSE = 2.5f;
	// For the screen area
	public static final int SCREEN_WIDTH = 800;
	public static final int SCREEN_HEIGHT = 600;
	
	// Game area values
	public static final float GAME_WIDTH = 30;
	public static final float GAME_HEIGHT = 20;
	public static final float GAME_DEPTH = 2;
	
	// Default values for paddles
	public static final float DEFAULT_PADDLE_HEIGHT = 20;
	public static final float DEFAULT_PADDLE_WIDTH = 100;
	public static final float DEFAULT_PADDLE_DEPTH = 20;
	
	public static final float DEFAULT_UPADDLE_XPOS = 0;
	public static final float DEFAULT_UPADDLE_YPOS = GAME_HEIGHT/2 - 2;
	public static final float DEFAULT_UPADDLE_ZPOS = 0;
	
	public static final float DEFAULT_DPADDLE_XPOS = 0;
	public static final float DEFAULT_DPADDLE_YPOS = -GAME_HEIGHT/2 + 2;
	public static final float DEFAULT_DPADDLE_ZPOS = 0;
	
	// Default values for Camera
	public static final float CAMERA_LOOK_AT_X = 0;
	public static final float CAMERA_LOOK_AT_Y = 0;
	public static final float CAMERA_LOOK_AT_Z = 0;
	
	public static final float CAMERA_POSITION_X = 0;
	public static final float CAMERA_POSITION_Y = 0;
	public static final float CAMERA_POSITION_Z = 50;
}
