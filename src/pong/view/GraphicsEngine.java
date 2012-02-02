package pong.view;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.image.BufferedImage;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.InvalidClassException;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

import pong.control.*;
import pong.model.*;

import com.jogamp.opengl.util.Animator;

public class GraphicsEngine implements GLEventListener {

	private static GLU glu = new GLU();
	private static GLCanvas canvas = new GLCanvas();
	private static Frame frame = new Frame("Pong");
	private int rotation = 0;
	private GameEngine ge;

	private ControlsInput mouse;

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
		frame.setSize(Const.SCREEN_WIDTH, Const.SCREEN_WIDTH);
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

		rotation += 1;

		//TODO Draw walls around playarea
		
		// Draw paddles, ball etc
		try {
			// IMPORTANT! PopMatrix() resets glTranslatef and glRotatef to what it was before the previous PushMatrix()
			gl.glPushMatrix();
			//this.draw3DRectangle(gl, new Paddle(50, 50, 0, 5, 10, 2));
			this.draw3DRectangle(gl, ge.getPaddle());
			gl.glPopMatrix();
			gl.glPushMatrix();
			this.draw3DRectangle(gl, new Paddle(-30, -50, 0, 3, 40, 2));
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

		// TODO Add listeners

		// add listeners
		mouse = new ControlsInput(ge);
		((Component) glDrawable).addKeyListener(mouse);
		((Component) glDrawable).addMouseMotionListener(mouse);
		((Component) glDrawable).addMouseListener(mouse);
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
		glu.gluLookAt(0.0, 0.0, 100.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	public static void exit() {
		animator.stop();
		frame.dispose();
		System.exit(0);
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

		/*Just draw a cube. No respect for xyz or dimensions
		/*		
		gl.glColor3f(0.0f, 1.0f, 0.0f); // Set The Color To Green
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
		gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Bottom Left Of The Quad (Top)
		gl.glVertex3f(1.0f, 1.0f, 1.0f); // Bottom Right Of The Quad (Top)

		gl.glColor3f(1.0f, 0.5f, 0.0f); // Set The Color To Orange
		gl.glVertex3f(1.0f, -1.0f, 1.0f); // Top Right Of The Quad (Bottom)
		gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Top Left Of The Quad (Bottom)
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Bottom)
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Bottom)

		gl.glColor3f(1.0f, 0.0f, 0.0f); // Set The Color To Red
		gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Front)
		gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Front)
		gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Front)
		gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Front)

		gl.glColor3f(1.0f, 1.0f, 0.0f); // Set The Color To Yellow
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Back)
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Back)
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Back)
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Back)

		gl.glColor3f(0.0f, 0.0f, 1.0f); // Set The Color To Blue
		gl.glVertex3f(-1.0f, 1.0f, 1.0f); // Top Right Of The Quad (Left)
		gl.glVertex3f(-1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Left)
		gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
		gl.glVertex3f(-1.0f, -1.0f, 1.0f); // Bottom Right Of The Quad (Left)

		gl.glColor3f(1.0f, 0.0f, 1.0f); // Set The Color To Violet
		gl.glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Right)
		gl.glVertex3f(1.0f, 1.0f, 1.0f); // Top Left Of The Quad (Right)
		gl.glVertex3f(1.0f, -1.0f, 1.0f); // Bottom Left Of The Quad (Right)
		gl.glVertex3f(1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Right)
		*/

		gl.glEnd(); // Done Drawing The Quad
	}
}
