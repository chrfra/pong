package pong.model;

import static pong.model.Const.RY_SPEED;

import java.util.ArrayList;

public class MenuCube {
	private float xPos;
	private float yPos;
	private float zPos;
	private float height;
	private float width;
	private float rx,ry,rz,tx,ty,tz; //the cube's rotation in x,y,z in degrees, target x,y,z
	private int selection = 0;
	//two dimensional list holding the menu options, 
	//first list holds the lists of strings for each side of the menu
	private ArrayList<ArrayList<String>> options;

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

		else if(rx < tx){
			setRx(rx + RY_SPEED);
		}else if(rx > tx){
			setRx(rx - RY_SPEED);
		}
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

	private float depth;

	public MenuCube(float xPos, float yPos, float zPos, float height, float width, float depth) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.zPos = zPos;
		this.height = height;
		this.width = width;
		this.depth = depth;
		options = new ArrayList<ArrayList<String>>();
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
			//viewing right face
			else if((Math.abs(ry) % 360 == 90)){
				System.out.println("right flip");
			}
			//System.out.println("(Math.abs(ry) % 360) är :" + (Math.abs(ry) % 360));
		}
	}

	/*
	 * returns all the options for a specified side of the menu
	 * @param int (number of the side for which you would like the list of options)
	 */
	public ArrayList<String> getOptionsBySide(int x) {
		return options.get(x);
	}
	public ArrayList<ArrayList<String>> getOptions() {
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
		this.options.get(sideId).set(eltNr, text);
	}
	/*
	 * add entire menu system
	 * @param options	list of list of options, one for each side
	 */
	public void setOptions(ArrayList<ArrayList<String>> options) {
		this.options = options;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}

	public int getSelection() {
		return selection;
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
}
