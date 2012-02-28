package pong.model;

import static pong.model.Const.RY_SPEED;
import static pong.model.Const.*;

import java.util.ArrayList;

public class MenuCube {
	private float xPos;
	private float yPos;
	private float zPos;
	private float height;
	private float width;
	private float rx,ry,rz,tx,ty,tz; //the cube's rotation in x,y,z in degrees, target x,y,z
	private String Player1Name = new String("Player");
	private String Player2Name = new String("AI");
	//two dimensional list holding the menu options, 
	//first list holds the lists of strings for each side of the menu
	//private ArrayList<ArrayList<String>> options;
	private String[][] options;

	/*
	 * updates the menucube's rotation angles, called every tick of the game engine
	 */
	public void tick(){
		/*
		 * Check rotation around y axis
		 */
		//Two first if:s are special cases; if cube is rotated almost -270 degrees(three steps to the right)
		//then instead of going back three steps to reach rotation = 0, simply rotate once to the right

		//cube is rotated three steps to the right of original rotation
		//=>rotate another 90 degrees to the right to reach original rotation
		if(ry < ty && ry <= -270){ 
			//"<= -270" abouve is vital, since we decrease ry below, so next time tick is run ry will be
			//-280,-290 ... until reaching 360 and is assigned the value of 0  by the setRy method ( % 360 )
			setRy(ry - RY_SPEED);
		}
		//cube is rotated three steps to the left of original rotation
		else if(ry > ty && ry >= 270){
			setRy(ry + RY_SPEED);
		}
		//if menu rotation < target rotation then rotate cube RY_SPEED degrees further
		else if(ry < ty){
			setRy(ry + RY_SPEED);
		}else if(ry > ty){
			setRy(ry - RY_SPEED);
		}


		/*
		 * Check rotation around x axis
//		 */
		//		if(rx < tx && rx >= 90){
		//			setRx(rx + RY_SPEED);
		//			//setRy(ry + 180);
		//		}
		//
		//		if(rx < tx){
		//			setRx(rx + RY_SPEED);
		//		}else if(rx > tx){
		//			setRx(rx - RY_SPEED);
		//		}
	}


	private float depth;

	public MenuCube(float xPos, float yPos, float zPos, float height, float width, float depth) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.height = height;
		this.width = width;
		this.depth = depth;
		//options = new ArrayList<ArrayList<String>>();
		options = new String[6][10]; 
		this.initCube();	// load the text for each side of the cube
		rx = ry = rz = tx = ty = tz = 0;	//the rotation of the cube is initiated as 0,0,0
	}
	/*
	 * rotates menu around Y axis (sets targetY, ty), rotation is performed in this.tick()
	 * @param degrees	number of degrees to rotate
	 */
	public void rotateY(int degrees){
		//rx == ty:don't change target y-rotation to current target rotation + 90 degrees 
		//if the cube has not fully rotated to it's target position yet!
		//-results in over/under -shoot!
		if(ry == ty)	
			setTy(ty + degrees);
	}
	/*
	 * rotates menu around X axis (UP/DOWN)
	 * @param degrees	number of degrees to rotate
	 */
	public void rotateX(int degrees){
		//see rotateY comments
		if(rx == tx){
			//(Math.abs(ry) % 360 == 0): only allow x rotation if on new game face
			//otherwise one will be able to view faces with text upside-down
			if((Math.abs(ry) % 360 == 0)){
				tx = tx + degrees;
			}
		}
	}

	/*
	 * Determines what action to perform, based on which direction the cube is facing
	 * @return	the gamestate (Const.*)to be initialized depending on what direction the cube is facing
	 */
	public int select(){
		System.out.println("ry " +ry);
		/*
		 * NOTE that if the first move the player makes is one step to the right then ry = 0-90 = -90
		 * but if the player takes three steps to the left (=0+90+90+90 = 270) we will also be facing the rightmost face
		 * This results in viewing eg. the rightmost face being identified by ry == -90 OR ry == 270
		 */
		//menu facing forward => init new and start new game
		if(ry == 0){
			return IN_GAME;
		}
		// selected resume
		else if(ry == 90 || ry == -270){
			return RESUME;
		}
		// selected to input player name
		else if(ry == -90 || ry == 270){
			return TEXT_INPUT;
		}
		else if(ry == -180){
			return TEXT_INPUT;
		}
		//no approriate option for the direction the cube is facing, return error
		return Const.ERROR;
	}
	/*
	 * DO NOT USE THIS METHOD AS IT EXPOSES THE MENUCUBE UNDERLYING DATASTRUCTURE TO THE REST OF THE PROGRAM
	 * returns all the options for a specified side of the menu
	 * @param int (number of the side for which you would like the list of options)
	 */
	public String[] getOptionsBySide(int x) {
		return options[x];
	}
	/*
	 * returns the  option for a specified side of the menu
	 * @param sideNr	the number(const.MENU_*****) of the side for which you would like the list of options)
	 * @param eltNr		The number of the option in the list to get
	 */
	public String getOption(int sideNr,int eltNr) {
		return options[sideNr][eltNr];
	}
	/*
	 * Sets the  option for a specified side of the menu
	 * @param sideNr	the number(const.MENU_*****) of the side for which you would like the list of options)
	 * @param eltNr		The number of the option in the list to get
	 * @param text		The text to be displayed at the provided location
	 */
	public void setOption(int sideNr,int eltNr, String text) {
		options[sideNr][eltNr] = text;
	}
	public String[][] getOptions() {
		return options;
	}
	/*
	 * updates the string to be printed on the menu,
	 * given the side of the menu and the string's position in the list
	 * @param sideId	side identifier (see Const.java)
	 * @param eltNr		the element's number in the list
	 * @param text		the text to be displayed
	 */
	public void updateOption(int sideId,int eltNr, String text) {
		this.options[sideId][eltNr] = text;
	}
	private void initCube() {
		options[MENU_TOP][0] = "Top";
		options[MENU_FRONT][0] = "New Game";
		options[MENU_RIGHT][0] = "Enter P1 Name: ";
		options[MENU_BACK][0] = "Enter P2 Name: ";
		options[MENU_LEFT][0] = "";
		options[MENU_BOTTOM][0] = "Top";
	}
	/*
	 * add entire menu system
	 * @param options	list of list of options, one for each side
	 */
	public void setOptions(String[][] options) {
		this.options = options;
	}

	public void setTx(float tx) {
		this.tx = tx  % 360;
	}
	public float getTx() {
		return tx;
	}

	public void setTy(float ty) {
		this.ty = ty  % 360;
	}

	public float getTy() {
		return ty;
	}

	public void setTz(float tz) {
		this.tz = tz  % 360;
	}

	public float getTz() {
		return tz;
	}

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx % 360;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry % 360;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz % 360;
	}

	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}

	public float getzPos() {
		return zPos;
	}

	public void setzPos(float zPos) {
		this.zPos = zPos;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getDepth() {
		return depth;
	}

	public void setDepth(float depth) {
		this.depth = depth;
	}
	public void setPlayer1Name(String player1Name) {
		Player1Name = player1Name;
	}
	public String getPlayer1Name() {
		return Player1Name;
	}
	public void setPlayer2Name(String player2Name) {
		Player2Name = player2Name;
	}
	public String getPlayer2Name() {
		return Player2Name;
	}
}
