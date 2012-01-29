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
		// TODO Auto-generated method stub
	}

    public static void exit() {
        animator.stop();
        frame.dispose();
        System.exit(0);
    }
    
    public void drawPaddle(GLAutoDrawable drawable, Paddle pad){
    	GL2 gl = drawable.getGL().getGL2();
    	
    	gl.glPushMatrix();
    	 
    	//TODO Draw paddle here. Use the data in the Paddle object to draw it in the right place.
    	
    	
    	
    	gl.glPopMatrix();
    }
}
