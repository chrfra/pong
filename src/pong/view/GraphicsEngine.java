package pong.view;


import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;

import pong.control.GameEngine;
import pong.model.Paddle;

import com.jogamp.opengl.util.Animator;

public class GraphicsEngine implements GLEventListener {

    private static GLU glu = new GLU();
    private static GLCanvas canvas = new GLCanvas();
	private static Frame frame = new Frame("Pong");
	
	private GameEngine ge;
	
    //animator drives display method in a loop
    private static Animator animator = new Animator(canvas);
    
    public GraphicsEngine(GameEngine ge) {
		this.ge = ge;
	}
	
    public void setUp(){
    	System.out.println("Setting up the frame...");
		//First setup of the frame
		canvas.addGLEventListener(this);
        frame.add(canvas);
        frame.setSize(800, 600);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.setVisible(true);
        //starts calling display method
        animator.start();
        canvas.requestFocus();
    }
	
	@Override
	public void display(GLAutoDrawable gLDrawable) {
		//Draw walls around playarea
		//Draw paddles, ball etc
		this.drawPaddle(gLDrawable, null);
	}

	@Override
	public void dispose(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(GLAutoDrawable glDrawable) {

        
        //Required Init-functions
        GL2 gl = glDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        
        //TODO Add listeners
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
        gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
        gl.glLoadIdentity();
	}

    public static void exit() {
        animator.stop();
        frame.dispose();
        System.exit(0);
    }
    
    public void drawPaddle(GLAutoDrawable drawable, Paddle pad){
    	GL2 gl = drawable.getGL().getGL2();
    	
    	
//    	gl.glPushMatrix();
    	gl.glClear(GL.GL_COLOR_BUFFER_BIT);
    	 gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
         gl.glLoadIdentity();
    	 
    	//TODO Draw paddle here. Use the data in the Paddle object to draw it in the right place.
         gl.glRotatef(30, 1.0f, 1.0f, 1.0f);
         gl.glTranslatef(5.0f, -5.0f, -20.0f);
    	gl.glBegin(GL2.GL_QUADS);         // Draw A Quad
    	
        gl.glColor3f(0.0f, 1.0f, 0.0f);     // Set The Color To Green
        gl.glVertex3f(1.0f, 1.0f, -1.0f);   // Top Right Of The Quad (Top)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);  // Top Left Of The Quad (Top)
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);   // Bottom Left Of The Quad (Top)
        gl.glVertex3f(1.0f, 1.0f, 1.0f);    // Bottom Right Of The Quad (Top)

        gl.glColor3f(1.0f, 0.5f, 0.0f);     // Set The Color To Orange
        gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Top Right Of The Quad (Bottom)
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Top Left Of The Quad (Bottom)
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Bottom)
        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Bottom Right Of The Quad (Bottom)

        gl.glColor3f(1.0f, 0.0f, 0.0f);     // Set The Color To Red
        gl.glVertex3f(1.0f, 1.0f, 1.0f);    // Top Right Of The Quad (Front)
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);   // Top Left Of The Quad (Front)
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Bottom Left Of The Quad (Front)
        gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Bottom Right Of The Quad (Front)

        gl.glColor3f(1.0f, 1.0f, 0.0f);     // Set The Color To Yellow
        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Bottom Left Of The Quad (Back)
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Right Of The Quad (Back)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);  // Top Right Of The Quad (Back)
        gl.glVertex3f(1.0f, 1.0f, -1.0f);   // Top Left Of The Quad (Back)

        gl.glColor3f(0.0f, 0.0f, 1.0f);     // Set The Color To Blue
        gl.glVertex3f(-1.0f, 1.0f, 1.0f);   // Top Right Of The Quad (Left)
        gl.glVertex3f(-1.0f, 1.0f, -1.0f);  // Top Left Of The Quad (Left)
        gl.glVertex3f(-1.0f, -1.0f, -1.0f); // Bottom Left Of The Quad (Left)
        gl.glVertex3f(-1.0f, -1.0f, 1.0f);  // Bottom Right Of The Quad (Left)

        gl.glColor3f(1.0f, 0.0f, 1.0f);     // Set The Color To Violet
        gl.glVertex3f(1.0f, 1.0f, -1.0f);   // Top Right Of The Quad (Right)
        gl.glVertex3f(1.0f, 1.0f, 1.0f);    // Top Left Of The Quad (Right)
        gl.glVertex3f(1.0f, -1.0f, 1.0f);   // Bottom Left Of The Quad (Right)
        gl.glVertex3f(1.0f, -1.0f, -1.0f);  // Bottom Right Of The Quad (Right)
        gl.glEnd();                         // Done Drawing The Quad
    }
}
