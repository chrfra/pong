package pong.model;

import java.util.ArrayList;

public class MenuCube {
	private float xPos;
	private float yPos;
	private float zPos;
	private float height;
	private float width;
	private float rx,ry,rz,tx,ty,tz; //the cube's rotation in x,y,z in degrees, target x,y,z
	private int selection = 0;
	private float rotationSpeed = 10;
	//two dimensional list holding the menu options, 
	//first list holds the lists of strings for each side of the menu
	private ArrayList<ArrayList<String>> options;
	
	
	public void tick(){
		//Do what has to be done (rotate cube and stuff)
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

	public float getRx() {
		return rx;
	}

	public void setRx(float rx) {
		this.rx = rx;
	}

	public float getRy() {
		return ry;
	}

	public void setRy(float ry) {
		this.ry = ry;
	}

	public float getRz() {
		return rz;
	}

	public void setRz(float rz) {
		this.rz = rz;
	}

	public ArrayList<ArrayList<String>> getOptions() {
		return options;
	}
	/*
	 * returns all the options for a specified side of the menu
	 * @param int (number of the side for which you would like the list of options)
	 */
	public ArrayList<String> getOptionsBySide(int x) {
		return options.get(x);
	}
	/*
	 * updates the string to be printed on the menu, given the side of the menu and the string's position in the list
	 * @param sideId	side identifier (see Const.java)
	 * @param eltNr		the element's number in the list
	 * @param text		the text to be displayed
	 */
	public void updateOption(int sideId,int eltNr, String text) {
		this.options.get(sideId).set(eltNr, text);
	}
	//add entire menu system
	public void setOptions(ArrayList<ArrayList<String>> options) {
		this.options = options;
	}

	public void setSelection(int selection) {
		this.selection = selection;
	}

	public int getSelection() {
		return selection;
	}

	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}

	public float getRotationSpeed() {
		return rotationSpeed;
	}

	public void setTx(float tx) {
		this.tx = tx;
	}

	public float getTx() {
		return tx;
	}

	public void setTy(float ty) {
		this.ty = ty;
	}

	public float getTy() {
		return ty;
	}

	public void setTz(float tz) {
		this.tz = tz;
	}

	public float getTz() {
		return tz;
	}
}
