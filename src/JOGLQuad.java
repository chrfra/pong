import java.awt.AWTException;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.Robot;	//used to set mouse cursor on the same coordinates as quad
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2ES1;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLLightingFunc;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.media.opengl.glu.GLU;
import javax.swing.event.MouseInputListener;
import wiiusej.*;
 
import com.jogamp.opengl.util.Animator;
 
 
public class JOGLQuad implements GLEventListener, KeyListener, MouseInputListener{
    float centerX=(float)0.5;    
    float centerY=(float)0.5; 
    float deltaY=(float)0.1;
    float deltaX=(float)0.1;
    float rotateT = 0.0f;
 
    static GLU glu = new GLU();
 
    static GLCanvas canvas = new GLCanvas();
 
    static Frame frame = new Frame("Jogl Quad drawing");
    
    //animator drives display method in a loop
    static Animator animator = new Animator(canvas);
    
    //Wiimote[] wiimote = WiiUseApiManager.getWiimotes(1, true);
    
    public void display(GLAutoDrawable gLDrawable) {
        final GL2 gl = gLDrawable.getGL().getGL2();
        //clear previously drawn frame
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        //remove last drawn quad 
        //(commenting this results in same effect as windows crashing, or "drawing" effect..)
        gl.glClear(GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -5.0f);
 
        // rotate on the three axis
        gl.glRotatef(rotateT, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(rotateT, 0.0f, 0.0f, 1.0f);

        
        // Draw A Quad
//        gl.glBegin(GL2.GL_QUADS);          
//            gl.glColor3f(0.0f, 1.0f, 1.0f);   // set the color of the quad
//            gl.glVertex3f(-1.0f, 1.0f, 0.0f);      // Top Left
//            gl.glVertex3f( 1.0f, 1.0f, 0.0f);       // Top Right
//            gl.glVertex3f( 1.0f,-1.0f, 0.0f);      // Bottom Right
//            gl.glVertex3f(-1.0f,-1.0f, 0.0f);     // Bottom Left
//            
   
        gl.glBegin(GL2.GL_QUADS);          
            gl.glColor3f(0.0f, 1.0f, 1.0f);   // set the color of the quad
            gl.glVertex3f(centerX-1, centerY, 0.0f);      // Top Left
            gl.glVertex3f( centerX, centerY, 0.0f);       // Top Right
            gl.glVertex3f( centerX,centerY-1, 0.0f);      // Bottom Right
            gl.glVertex3f(centerX-1,centerY-1, 0.0f);     // Bottom Left
        // Done Drawing The Quad
        gl.glEnd();
        
//        gl.Begin(GL_TRIANGLES);                      // Drawing Using Triangles
//            glVertex3f( 0.0f, 1.0f, 0.0f);              // Top
//            glVertex3f(-1.0f,-1.0f, 0.0f);              // Bottom Left
//            glVertex3f( 1.0f,-1.0f, 0.0f);              // Bottom Right
//        glEnd();                            // Finished Drawing The Triangle
 
        // increasing rotation for the next iteration                                 
        //rotateT += 0.2f; 
    }
 
    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
    }
 /*Init is an initalization function which OpenGL/Jogl will call when your program starts up. 
  * Typically, you apply global settings and initalize the GL instance with your programs options.
  */
    public void init(GLAutoDrawable gLDrawable) {
    	//centerMouse();
        GL2 gl = gLDrawable.getGL().getGL2();
        gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
        //add listeners
        ((Component) gLDrawable).addKeyListener(this);
        ((Component) gLDrawable).addMouseMotionListener(this);
        ((Component) gLDrawable).addMouseListener(this);   
        //wiimote[0].addWiiMoteEventListeners(new wiimoteListener());
    }
 
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
 
    public void keyPressed(KeyEvent e) {
    	//close program on escape button
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            exit();
        //steer with numpad (deprecated)
        }else if(e.getKeyCode() == KeyEvent.VK_NUMPAD8){
    		centerY+=deltaY;
    	}else if(e.getKeyCode() == KeyEvent.VK_NUMPAD2){
    		centerY-=deltaY;
    	}else if(e.getKeyCode() == KeyEvent.VK_NUMPAD4){
			centerX-=deltaX;
		}else if(e.getKeyCode() == KeyEvent.VK_NUMPAD6){
			centerX+=deltaX;
		//exit fullscreen on F12 button
		}else if (e.getKeyCode() == KeyEvent.VK_F12) {
			//frame.setUndecorated(false);
		}
    }
 
    public void keyReleased(KeyEvent e) {

    }
 
    public void keyTyped(KeyEvent e) {
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
    
    //------unused method----------
	public void centerMouse() {
		//this code works if you eg. put it in a event listener method, but moves quad with it
	//if(frame.isShowing()){
	    Point locOnScreen = frame.getLocationOnScreen();
	    System.out.println("mouse location");
	    System.out.println(locOnScreen.x + (frame.getWidth() / 2));
	    System.out.println(locOnScreen.y + (frame.getWidth() / 2));
	    int middleX = locOnScreen.x + (frame.getWidth() / 2);
	    int middleY = locOnScreen.y + (frame.getHeight() / 2);
	    try{
	      Robot rob = new Robot();
	      rob.mouseMove(middleX, middleY);
	    }catch(Exception e){System.out.println(e);}
		    //setting mouse coords
		//  }
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		//System.out.println(arg0.getXOnScreen());
		float offset =0; //used to alleviate the consequences of the cursor being too far away from the quad at startup
		centerY=-((float)arg0.getYOnScreen() / 100)+offset;
		centerX=((float)arg0.getXOnScreen() / 100)-offset-1;
		//r.delay(500);	fun effect to make game harder if using a robot object
		
		
	}
}