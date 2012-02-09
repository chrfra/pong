package pong.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.image.BufferedImage;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.util.ArrayList;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import jogamp.opengl.glu.GLUquadricImpl;

import pong.control.*;
import pong.model.*;

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class GraphicsEngine implements GLEventListener {

	private static GLU glu = new GLU();
	private static GLCanvas canvas = new GLCanvas();
	private static Frame frame = new Frame("Pong");
	private int rotation = 0;
	private GameEngine ge;
	private GLUT glut = new GLUT();
	private Texture balltexture;
	private Texture spacetexture;

	// animator drives display method in a loop
	private static Animator animator = new Animator(canvas);

	public GraphicsEngine(GameEngine ge) {
		this.ge = ge;
	}
	

	public void setUp() {
		System.out.println("Setting up the frame...");
		// First setup of the frame
		canvas.addGLEventListener(this);
		frame.add(canvas);
		frame.setSize(Const.SCREEN_WIDTH, Const.SCREEN_HEIGHT);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});
		frame.setVisible(true);
		// starts calling display method
		animator.start();
		canvas.requestFocus();
	}

	@Override
	public void display(GLAutoDrawable gLDrawable) {
		GL2 gl = gLDrawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		//Items to be drawn
		ArrayList<GameItem> items = ge.getGameItems();
		
		rotation += 1;
		// IMPORTANT! PopMatrix() resets glTranslatef and glRotatef to what it was before the previous PushMatrix()
		gl.glPushMatrix();
		this.drawGamearea(gl);
		gl.glPopMatrix();
		
		gl.glPushMatrix();
		this.drawBackground(gl);
		gl.glPopMatrix();

		// Draw paddles, ball etc
		try {
			for(GameItem item : items){
				gl.glPushMatrix();
				if(item.getType().equals("PADDLE")){
					this.draw3DRectangle(gl, item);
				}else if(item.getType().equals("BALL")){
					this.drawBall(gl, item);
				}
				gl.glPopMatrix();
			}
			gl.glPushMatrix();
			// Render a string on screen
			renderStrokeString(gl, GLUT.STROKE_MONO_ROMAN, "Hej"); 
			gl.glPopMatrix();
			

		} catch (InvalidClassException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(GLAutoDrawable glDrawable) {

		// Required Init-functions
		GL2 gl = glDrawable.getGL().getGL2();
		gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		
		
		// Fix lights
		
        // Prepare light parameters.
        float SHINE_ALL_DIRECTIONS = 1;
        float[] lightPos = {20, 0, 0, SHINE_ALL_DIRECTIONS};
        float[] lightColorAmbient = {0.2f, 0.2f, 0.2f, 1f};
        float[] lightColorSpecular = {0.8f, 0.8f, 0.8f, 1f};

        // Set light parameters.
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
        gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

        // Enable lighting in GL.
        gl.glEnable(GL2.GL_LIGHT1);
        gl.glEnable(GL2.GL_LIGHTING);

        // Set material properties.
        float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_SPECULAR, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);
        
        // Set textures
        spacetexture = loadTexture("outer_space_trip_08_by_brujo.jpg");
        balltexture = loadTexture("earth-1k.png");
		
		// add listeners for keyboard and mouse input
        ge.createListeners(glDrawable);

	}

	@Override
	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
		GL2 gl = gLDrawable.getGL().getGL2();
		if (height <= 0) {
			height = 1;
		}

		float h = (float) width / (float) height;
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();

		glu.gluPerspective(50.0f, h, 1.0, 1000.0);
		// Set camera to look at Origo from 20 units away.
		glu.gluLookAt(0.0, 0.0, 15.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public static void exit() {
		animator.stop();
		frame.dispose();
		System.exit(0);
	}
	/*
	 * Renders a Ball.
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
		gl.glRotatef(rotation, 1.0f, 1.0f, 1.0f);
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
	/*
	 * Renders a 3D rectangle.
	 * This can be used to draw a paddle or obstacle or something else.
	 */
	public void draw3DRectangle(GL2 gl, GameItem item) throws InvalidClassException {
		// xPos, yPos, zPos, width, height, depth
		float x, y, z, w, h, d;

		// Check that item is a Paddle. Need to support all classes that have the same shape in the future.
		if (item instanceof Paddle) {
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
		System.out.println("Graphics: " +y);
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

	/* Draws walls on top and in the bottom
	 * 
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

	public void renderStrokeString(GL2 gl, int font, String string) {
		// Center Our Text On The Screen
		float width = glut.glutStrokeLength(font, string);
		gl.glTranslatef(-width / 2f, 200, -700);
		// Render The Text
		glut.glutStrokeString(font, string);
	}
	
	/* Load texture into GraphicsEngine as a String example: earth-1k.png
	 * @param String
	 * @return Textureobject ready to be applied
	 */
	public Texture loadTexture(String texture){
		
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
	
	public void drawBackground(GL2 gl){
	
		gl.glTranslatef(1,0,0);
		gl.glRotatef((float)rotation/2, 0.5f, 0.0f, 1.0f);
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

}
