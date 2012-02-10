package pong.view;

import java.awt.Font;
import java.awt.Graphics2D;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLProfile;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.awt.TextRenderer;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import pong.model.Ball;
import pong.model.Const;
import pong.model.GameItem;
import pong.model.Paddle;

public class Renderer {
	private GLU glu;

	private float bgRotation = 90;
	private Texture balltexture;
	private Texture spacetexture;
	
	
	public Renderer(GLU glu) {
		this.glu = glu;
	}
	
	/**
	 * Loads the textures from imagefiles into Texture-objects
	 */
	public void setupTextures(){
        spacetexture = loadTexture("outer_space_trip.jpg");
        balltexture = loadTexture("earth-1k.png");
	}
	
	/** 
	 * Load texture into GraphicsEngine as a String example: earth-1k.png
	 * @param String
	 * @return Textureobject ready to be applied
	 */
	private Texture loadTexture(String texture){
		
        // Load texture from resource directory, feel free to put files in there
        try {
        	InputStream stream;
        	if( (stream = getClass().getResourceAsStream("/resource/" + texture)) == null )
        	{
        		System.out.println("Texture not loaded..");
        	}
            TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
            return TextureIO.newTexture(data);
        }
        catch (IOException exc) {
            exc.printStackTrace();
            System.exit(1);
        }
		return null;
	}
	
	
	/********************************************
	 * 											*
	 * 			Drawing-methods Below			*
	 * 											*
	 ********************************************/
	
	/**
	 * Renders a 3D ball.
	 */
	public void drawBall(GL2 gl, GameItem item) throws InvalidClassException {
		float x, y, z, r;

		// Check that item is a Paddle. Need to support all classes that have the same shape in the future.
		if (item.getType().equals("BALL")) {
			Ball Ball = (Ball) item;
			x = Ball.getxPos();
			y = Ball.getyPos();
			z = Ball.getzPos();
			r = Ball.getRadius();
		} else {
			throw new InvalidClassException("Wrong class of GameItem in draw3DRectangle(GL2 gl, GameItem item)");
		}
		
        // Enable texturing.
        gl.glEnable(GL.GL_TEXTURE_2D);
        
        // Apply earth texture
        balltexture.enable(gl);
        balltexture.bind(gl);
		
		gl.glTranslatef(x / 2f, y / 2f, z / 2f);
		// Draw Ball (possible styles: FILL, LINE, POINT).
//		gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
		gl.glColor3f(0.3f, 0.5f, 1f);
		GLUquadric ball = glu.gluNewQuadric();
		glu.gluQuadricTexture(ball, true);
		glu.gluQuadricDrawStyle(ball, GLU.GLU_FILL);
		glu.gluQuadricNormals(ball, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(ball, GLU.GLU_OUTSIDE);
		final int slices = 16;
		final int stacks = 16;
		glu.gluSphere(ball, r, slices, stacks);
		glu.gluDeleteQuadric(ball);
		
		gl.glDisable(GL.GL_TEXTURE_2D);
	}


	/**
	 * Renders a 3D rectangle.
	 * This can be used to draw a paddle or obstacle or something else.
	 */
	public void draw3DRectangle(GL2 gl, GameItem item) throws InvalidClassException {
		// xPos, yPos, zPos, width, height, depth
		float x, y, z, w, h, d;

		// Check that item is a Paddle. Need to support all classes that have the same shape in the future.
		if (item.getType().equals("PADDLE")) {
			Paddle pad = (Paddle) item;
			x = pad.getxPos();
			y = pad.getyPos();
			z = pad.getzPos();
			w = pad.getWidth();
			h = pad.getHeight();
			d = pad.getDepth();
		} else {
			throw new InvalidClassException("Wrong class of GameItem in draw3DRectangle(GL2 gl, GameItem item)");
		}
		// Move to right coordinates.
		gl.glTranslatef(x / 2f, y / 2f, z / 2f);
		
		//gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);

		gl.glBegin(GL2.GL_QUADS); // Draw A Quad
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
		gl.glVertex3f(w / 2f, h / 2f, -d / 2f); // Top Right Of The Quad (Top)
		gl.glVertex3f(-w / 2f, h / 2f, -d / 2f); // Top Left Of The Quad (Top)
		gl.glVertex3f(-w / 2f, h / 2f, d / 2f); // Bottom Left Of The Quad (Top)
		gl.glVertex3f(w / 2f, h / 2f, d / 2f); // Bottom Right Of The Quad (Top)

		gl.glColor3f(1.0f, 0.5f, 0.0f); // Set The Color To Orange
		gl.glVertex3f(w / 2f, -h / 2f, d / 2f); // Top Right Of The Quad (Bottom)
		gl.glVertex3f(-w / 2f, -h / 2f, d / 2f); // Top Left Of The Quad (Bottom)
		gl.glVertex3f(-w / 2f, -h / 2f, -d / 2f); // Bottom Left Of The Quad (Bottom)
		gl.glVertex3f(w / 2f, -h / 2f, -d / 2f); // Bottom Right Of The Quad (Bottom)

		gl.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
		gl.glVertex3f(w / 2, h / 2f, d / 2f); // Top Right Of The Quad (Front)
		gl.glVertex3f(-w / 2, h / 2f, d / 2f); // Top Left Of The Quad (Front)
		gl.glVertex3f(-w / 2, -h / 2f, d / 2f); // Bottom Left Of The Quad (Front)
		gl.glVertex3f(w / 2, -h / 2f, d / 2f); // Bottom Right Of The Quad (Front)

		gl.glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
		gl.glVertex3f(w / 2f, -h / 2f, -d / 2f); // Bottom Left Of The Quad (Back)
		gl.glVertex3f(-w / 2f, -h / 2f, -d / 2f); // Bottom Right Of The Quad (Back)
		gl.glVertex3f(-w / 2f, h / 2f, -d / 2f); // Top Right Of The Quad (Back)
		gl.glVertex3f(w / 2f, h / 2f, -d / 2f); // Top Left Of The Quad (Back)

		gl.glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
		gl.glVertex3f(-w / 2f, h / 2f, d / 2f); // Top Right Of The Quad (Left)
		gl.glVertex3f(-w / 2f, h / 2f, -d / 2f); // Top Left Of The Quad (Left)
		gl.glVertex3f(-w / 2f, -h / 2f, -d / 2f); // Bottom Left Of The Quad (Left)
		gl.glVertex3f(-w / 2f, -h / 2f, d / 2f); // Bottom Right Of The Quad (Left)

		gl.glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
		gl.glVertex3f(w / 2f, h / 2f, -d / 2f); // Top Right Of The Quad (Right)
		gl.glVertex3f(w / 2f, h / 2f, d / 2f); // Top Left Of The Quad (Right)
		gl.glVertex3f(w / 2f, -h / 2f, d / 2f); // Bottom Left Of The Quad (Right)
		gl.glVertex3f(w / 2f, -h / 2f, -d / 2f); // Bottom Right Of The Quad (Right)

		gl.glEnd(); // Done Drawing The Quad
		
		gl.glDisable(GL.GL_TEXTURE_2D);
	}
	
	/**
	 * Draws the gamearea.
	 */
	public void drawGamearea(GL2 gl){
		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
		
		//VARFÖR DIVIDERA MED 4 OCH INTE 2??????????? ARGHHHH
		gl.glVertex3f(Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, -Const.GAME_DEPTH/2); // Top Right Of The Quad (Top Wall)
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, -Const.GAME_DEPTH/2); // Top Left Of The Quad (Top Wall)
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, Const.GAME_DEPTH/2); // Bottom Left Of The Quad (Top Wall)
		gl.glVertex3f(Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, Const.GAME_DEPTH/2); // Bottom Right Of The Quad (Top Wall)
		
		gl.glVertex3f(Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT/4f, -Const.GAME_DEPTH/2); // Top Right Of The Quad (Bottom Wall)
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT/4f, -Const.GAME_DEPTH/2); // Top Left Of The Quad (Bottom Wall)
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT/4f, Const.GAME_DEPTH/2); // Bottom Left Of The Quad (Bottom Wall)
		gl.glVertex3f(Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT/4f, Const.GAME_DEPTH/2); // Bottom Right Of The Quad (Bottom Wall)
		
		
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, Const.GAME_DEPTH/2); // Top Right Of The Quad (Left Wall)
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, -Const.GAME_DEPTH/2); // Top Left Of The Quad (Left Wall)
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT / 4f, -Const.GAME_DEPTH/2); // Bottom Left Of The Quad (Left Wall)
		gl.glVertex3f(-Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT / 4f, Const.GAME_DEPTH/2); // Bottom Right Of The Quad (Left Wall)
		
		gl.glVertex3f(Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, -Const.GAME_DEPTH/2); // Top Right Of The Quad (Right Wall)
		gl.glVertex3f(Const.GAME_WIDTH / 4f, Const.GAME_HEIGHT / 4f, Const.GAME_DEPTH/2); // Top Left Of The Quad (Right Wall)
		gl.glVertex3f(Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT / 4f, Const.GAME_DEPTH/2); // Bottom Left Of The Quad (Right Wall)
		gl.glVertex3f(Const.GAME_WIDTH / 4f, -Const.GAME_HEIGHT / 4f, -Const.GAME_DEPTH/2); // Bottom Right Of The Quad (Right Wall)
		
		gl.glEnd();
	}
	
	/**
	 * Draws the sphere around the game.
	 */
	public void drawBackground(GL2 gl){
		
		gl.glTranslatef(0,0,0);
		gl.glRotatef(bgRotation, 1.0f, 1.0f, 0.0f);
		bgRotation+=0.01;
		
		// Set radius of background sphere
		int r = 100;
		
        // Enable texturing.
        gl.glEnable(GL.GL_TEXTURE_2D);
        
        // Apply space texture
        spacetexture.enable(gl);
        spacetexture.bind(gl);
        
		// Draw Ball (possible styles: FILL, LINE, POINT).
		//gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
		gl.glColor3f(0.3f, 0.5f, 1f);
		GLUquadric bkg = glu.gluNewQuadric();
		glu.gluQuadricTexture(bkg, true);
		glu.gluQuadricDrawStyle(bkg, GLU.GLU_FILL);
		glu.gluQuadricNormals(bkg, GLU.GLU_FLAT);
		glu.gluQuadricOrientation(bkg, GLU.GLU_OUTSIDE);
		final int slices = 16;
		final int stacks = 16;
		glu.gluSphere(bkg, r, slices, stacks);
		glu.gluDeleteQuadric(bkg);
		
		gl.glDisable(GL.GL_TEXTURE_2D);
			
	}
	/**
	 * Draws a string in 2D
	 */
	public void renderStrokeString(GL2 gl, int font, String string) {
		GLUT glut = new GLUT();
		// Center Our Text On The Screen
		float width = glut.glutStrokeLength(font, string);
		gl.glTranslatef(-width / 2f, 200, -700);
		// Render The Text
		glut.glutStrokeString(font, string);
	}
	
	
	/**
	 * Draws a string in 2D, is much nicer and can be customized in the init() method to set font and size
	 * The text is draw depending on the position it gets from the call (x, y)
	 */
	public void renderText(GLAutoDrawable drawable, TextRenderer textrenderer, int x, int y, String text){

		textrenderer.beginRendering(drawable.getHeight(),drawable.getWidth());
		
		// optionally set the color
		textrenderer.setColor(1.0f, 1.0f, 0.0f, 0.8f);
		textrenderer.draw(text, x, y);
		// ... more draw commands, color changes, etc.
		textrenderer.endRendering();
	}
}
