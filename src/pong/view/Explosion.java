package pong.view;

import java.util.Random;

import com.jogamp.opengl.util.texture.Texture;

import pong.model.Const;

public class Explosion {
	

	//The position of the origin. Only used to calculate the positions of the subexplosions
	private float[] position = new float[3];
	//Every explosion has x,y,z,radius and rotation
	private float[][] explosions = new float[Const.EXPLOSION_COUNT][5];
	private float TTL = 1000;
	private long startTime;
	private boolean ended = false;
	
	private Texture texture;
	
	public Explosion(float x, float y, float z) {
		position[0] = x;
		position[1] = y;
		position[2] = z;
		startTime = System.currentTimeMillis();
		generateRandomExplosions();
		texture = Textures.explosion1;
	}
	
	
	public void tick(){
		int i = 0;
		while(i < Const.EXPLOSION_COUNT){
			explosions[i][3] +=Const.EXPLOSION_SPREAD;
			i++;
		}
		if(System.currentTimeMillis() - startTime > TTL){
			ended=true;
		}
	}
	
	public void generateRandomExplosions(){
		Random rnd = new Random();
		for(int i = 0 ; i<Const.EXPLOSION_COUNT ; i++){
			for(int u = 0 ; u<3 ; u++){
				explosions[i][u] = position[u] + (rnd.nextFloat()*4) -2;
			}
			//Randomize radius
			explosions[i][3] = (rnd.nextFloat())+1;
			//Randomize rotation
			explosions[i][4] = rnd.nextFloat()*360;
		}
		
	}
	
	public float[][] getExplosions() {
		return explosions;
	}
	
	public boolean isEnded() {
		return ended;
	}
	public Texture getTexture() {
		return texture;
	}
	
}
