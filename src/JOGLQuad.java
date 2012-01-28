import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
//import java.awt.Robot;	//used to set mouse cursor on the same coordinates as quad
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
 
import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;
 
 
public class JOGLQuad implements GLEventListener{
	float centerX=(float)0.5;    
	float centerY=(float)0.5; 
    float deltaY=(float)0.1;
    float deltaX=(float)0.1;
    float rotateT = 0.0f;
    float rotateUp = 0.0f;
    float rotateRight = 0.0f;
    kmListener k;
 
    static GLU glu = new GLU();
 
    static GLCanvas canvas = new GLCanvas();
 
    static Frame frame = new Frame("Jogl Quad drawing");
    
    //animator drives display method in a loop
    static Animator animator = new Animator(canvas);
    
    public void display(GLAutoDrawable gLDrawable) {
    	
        final GL2 gl = gLDrawable.getGL().getGL2();
        //clear previously drawn frame
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        //remove last drawn quad 
        //(commenting this results in same effect as windows crashing, or "drawing" effect..)
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        //same effect as offset in mouselistener
        gl.glTranslatef(0f, 0f, -5.0f);
        //gl.glTranslatef(0.0f, 0.0f, -5.0f);
        
        gl.glRotatef(rotateUp, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateRight, 0.0f, 1.0f, 0.0f);
        
        
        // rotate on the three axis
        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);
        
        System.out.println("x,y:"+centerX+" , "+centerY);
        
        gl.glBegin(GL2.GL_QUADS);          
        gl.glColor3f(0.0f, 1.0f, 1.0f);   // set the color of the quad
        gl.glVertex3f(centerX-1, centerY, 0.0f);      // Top Left
        gl.glVertex3f( centerX, centerY, 0.0f);       // Top Right
        gl.glVertex3f( centerX,centerY-1, 0.0f);      // Bottom Right
        gl.glVertex3f(centerX-1,centerY-1, 0.0f);     // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
        
        GLUT glut = new GLUT();
        glut.glutWireCube(3);
/*
        gl.Begin(GL_TRIANGLES);                      // Drawing Using Triangles
        	glVertex3f( 0.0f, 1.0f, 0.0f);              // Top
        	glVertex3f(-1.0f,-1.0f, 0.0f);              // Bottom Left
        	glVertex3f( 1.0f,-1.0f, 0.0f);              // Bottom Right
        glEnd();                            // Finished Drawing The Triangle
*/
        // increasing rotation for the next iteration                                 
        //rotateT += 0.2f; 
    }

    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }
 /*Init is an initalization function which OpenGL/Jogl will call when your program starts up. 
  * Typically, you apply global settings and initalize the GL instance with your programs options.
  */
    public void init(GLAutoDrawable gLDrawable) {
        GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        //add listeners
        k = new kmListener(this);
        ((Component) gLDrawable).addKeyListener(k);
        ((Component) gLDrawable).addMouseMotionListener(k);
        ((Component) gLDrawable).addMouseListener(k);   
    }
 //change viewport size to match display window when resized by the user
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
 
    public static void main(String[] args) {
    	
        canvas.addGLEventListener(new JOGLQuad());
        frame.add(canvas);
        frame.setSize(1920, 1080);
        frame.setUndecorated(true);//enables fullscreen
        frame.setExtendedState(Frame.MAXIMIZED_BOTH);//maximizes window
        //runs exit method when closing window
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });
        frame.setVisible(true);
        //starts calling display method
        animator.start();
        canvas.requestFocus();
        //hide cursor
        frame.setCursor(frame.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));
    }
 
    public void dispose(GLAutoDrawable gLDrawable) {
        // do nothing
    }
    
    public float getCenterX() {
		return centerX;
	}

	public void setCenterX(float centerX) {
		this.centerX = centerX;
	}

	public float getCenterY() {
		return centerY;
	}

	public void setCenterY(float centerY) {
		this.centerY = centerY;
	}

	public float getRotateUp() {
		return rotateUp;
	}

	public void setRotateUp(float rotateUp) {
		this.rotateUp = rotateUp;
	}

	public float getRotateRight() {
		return rotateRight;
	}

	public void setRotateRight(float rotateRight) {
		this.rotateRight = rotateRight;
	}


	
}