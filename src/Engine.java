import java.awt.Component;
import java.awt.Frame;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

import com.jogamp.opengl.util.Animator;
import com.jogamp.opengl.util.gl2.GLUT;

/**
 * The class responsible for the main 3D rendering aspects and game logic
 */
public class Engine implements GLEventListener, KeyListener
{
	// Length of the labeled axes
	private static final int FLOOR_LEN = 600;

	// The shader manager/control class, each shader has a new ShaderControl object
	private ShaderControl shader;

	// Keyboard input
	private boolean[] keys;

	// The camera class which allows us to view the scene
	private Camera camera;
	private boolean mouseMode = true;
	
	// A trackball so we can look around the model
	private Trackball track;
	
	// A player with an OBJModel to move around the scene
	private Player player;

	// JOGL and Frame stuff
	private static GLU glu = new GLU();
	private static GLUT glut = new GLUT();
	private static GLCanvas canvas;

	private static Frame frame = new Frame("Jogl Shader Lesson 1 - Shader Manager");
	private static Animator animator;

	// The display loop, which is called each frame
	public void display(GLAutoDrawable gLDrawable)
	{
		final GL2 gl = gLDrawable.getGL().getGL2();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
		
		// Background colour
		gl.glClearColor(0.9f, 0.9f, 0.9f, 0.0f);
		
		// Check keyboard input
		keyboardChecks();

		// Render the scene
		renderScene(gLDrawable);
	}

	public void renderScene(GLAutoDrawable gLDrawable)
	{
		GL2 gl = gLDrawable.getGL().getGL2();
		
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);
		
		gl.glClearColor(0.9f, 0.9f, 0.9f, 0.0f);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);

		// Render according to camera viewpoint
		camera.render(gl, glu);


		
		// Draw some axes
		labelAxes(gLDrawable);
		
		// Draw a teapot
		drawTeapot(gLDrawable);

		gl.glPushMatrix();
		gl.glTranslated(player.getXPos(), player.getYPos(), player.getZPos());

		/*
		 * Euler method = gimble lock, yuck! gl.glRotated(-player.getYaw() * (180
		 * / Math.PI), 0.0f, 1.0f, 0.0f); // the order of yaw and pitch is
		 * important! gl.glRotated(player.getPitch() * (180 / Math.PI), 1.0f,
		 * 0.0f, 0.0f); gl.glRotated(player.getRoll() * (180 / Math.PI), 0.0f,
		 * 0.0f, 1.0f);
		 */

		Quaternion qx = new Quaternion(Math.cos(player.getRollChange() / 2.0),
				Math.sin(player.getRollChange() / 2.0), 0.0, 0.0);

		Quaternion qy = new Quaternion(Math.cos(player.getYawChange() / 2.0),
				0.0, Math.sin(player.getYawChange() / 2.0), 0.0);

		Quaternion qz = new Quaternion(Math.cos(player.getPitchChange() / 2.0),
				0.0, 0.0, Math.sin(player.getPitchChange() / 2.0));

		Quaternion qxy = qz.times(qy);
		Quaternion qxyz = qx.times(qxy);

		player.setQ(player.getQ().times(qxyz));

		float[] rotMatrix = player.getQ().getRotationMatrix();
		float[] tranRotMatrix = player.getQ().transposeMatrix(rotMatrix);

		gl.glMultMatrixf(tranRotMatrix, 0);

		if (player.isSelected())
		{
			camera.rotateByQuaternion(player.getQ());
		}

		player.rotateByQuaternion(player.getQ());

		player.draw(gl, glu, shader.getShaderprogram());
		gl.glPopMatrix();


		camera.setXPos(player.getXPos());
		camera.setYPos(player.getYPos());
		camera.setZPos(player.getZPos());
		
		if (mouseMode)
		{
			camera.rotateByQuaternion(track.getQuaternion());
		}
		
		camera.moveForward(camera.getZoomDistance());

		player.moveForward((float) player.getSpeed());

	}

	// This method adds a light to the scene with ambient, specular and diffuse properties
	private void addLight(GLAutoDrawable gLDrawable)
	{
		GL2 gl = gLDrawable.getGL().getGL2();

		float Al[] = { 0.0f, 0.0f, 0.0f, 1.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, Al, 0);

		float Dl[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, Dl, 0);

		float Sl[] = { 1.0f, 1.0f, 1.0f, 1.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_SPECULAR, Sl, 0);

		float Am[] = { 0.3f, 0.3f, 0.3f, 1.0f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, Am, 0);

		float Dm[] = { 0.9f, 0.9f, 0.9f, 1.0f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, Dm, 0);

		float Sm[] = { 0.6f, 0.6f, 0.6f, 1.0f };
		gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_SPECULAR, Sm, 0);

		float f = 140.0f;
		gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, f);

		float lightPos[] = { 1.0f, 1.0f, 1.0f, 0.0f };
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, lightPos, 0);
	}

	public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged,
			boolean deviceChanged)
	{
	}

	public void init(GLAutoDrawable gLDrawable)
	{
		GL2 gl = gLDrawable.getGL().getGL2();
		

		gl.glShadeModel(GLLightingFunc.GL_SMOOTH);

		// enable polygon antialiasing
		gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
		gl.glEnable(GL2.GL_BLEND);
		gl.glEnable(GL2.GL_POLYGON_SMOOTH);
		gl.glDisable(GL2.GL_DEPTH_TEST);
		gl.glHint(GL2.GL_POLYGON_SMOOTH_HINT, GL2.GL_NICEST);

		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

		gl.glShadeModel(GLLightingFunc.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL2.GL_DEPTH_TEST);

		// Set the screen to refresh at 60fps
		gl.setSwapInterval(0);
		
		// Add a light to our scene
		addLight(gLDrawable);

		// Shader stuff
		shader = new ShaderControl();
		shader.fsrc = shader.loadShaderSrc("basicFragShader.glsl"); // fragment shader GLSL Code
		shader.vsrc = shader.loadShaderSrc("basicVertShader.glsl"); // vertex shader GLSL Code
		shader.init(gl);

		// Boolean array for keyboard input
		keys = new boolean[256];	

		
		// Load in a model for the player
		OBJModel model1 = new OBJModel(gl, "res/ann.obj",
				"res/ship_hull12_COLOR.png", "res/ship_hull12_NRM.png", "res/ship_hull12_DISP.png");

		// Assign the loaded model to the player
		player = new Player("Bob", model1, 0, 0, 0);
		
		camera = new Camera();
		camera.moveForward(10.0f);
		
		// Initialise the trackball
		track = new Trackball(player, camera);
		track.listen(canvas);
		
		player.select();

		gl.glDepthFunc(GL2.GL_LEQUAL);
		gl.glHint(GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);
		((Component) gLDrawable).addKeyListener(this);
	}

	public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width,
			int height)
	{
		GL2 gl = gLDrawable.getGL().getGL2();
		if (height <= 0)
		{
			height = 1;
		}
		float h = (float) width / (float) height;
		gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h, 1.0, 1000.0);
		gl.glMatrixMode(GLMatrixFunc.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void keyPressed(KeyEvent key)
	{
		if (key.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			exit();
		} else
		{
			try
			{
				char i = key.getKeyChar();
				keys[(int) i] = true;
			} catch (Exception e)
			{
			}
			;
		}

	}

	@Override
	public void keyReleased(KeyEvent key)
	{
		try
		{
			char i = key.getKeyChar();
			keys[(int) i] = false;
		} catch (Exception e)
		{
		}
		;
	}

	public void keyboardChecks()
	{
		player.setPitchChange(0);
		player.setYawChange(0);
		player.setRollChange(0);

		if (keys['w']) // Move forwards
		{
			player.accelerate(0.0001f);
		}

		if (keys['s']) // Move backwards
		{
			player.accelerate(-0.0001f);
		}

		if (keys['m']) // Pitch up
		{
			player.setPitchChange((float) (player.getPitchChange() + 0.5f * Math.PI / 180));
		}

		if (keys['n']) // Pitch down
		{
			player.setPitchChange((float) (player.getPitchChange() - 0.5f * Math.PI / 180));
		}

		if (keys['a']) // Strafe left
		{
			player.strafeRight(-0.01f);
		}

		if (keys['d']) // Strafe right
		{
			player.strafeRight(0.01f);
		}

		if (keys['e']) // Pitch up
		{
			player.setYawChange((float) (player.getYawChange() - 0.5f * Math.PI / 180));
		}

		if (keys['q']) // Pitch down
		{
			player.setYawChange((float) (player.getYawChange() + 0.5f * Math.PI / 180));
		}

		if (keys['z'])
		{
			player.setRollChange((float) (player.getRollChange() - 0.5f * Math.PI / 180));
		}

		if (keys['x'])
		{
			player.setRollChange((float) (player.getRollChange() + 0.5f * Math.PI / 180));
		}

		if (keys['y'])
		{
			float[] vector = new float[3];
			vector[0] = 10.0f;
			vector[1] = 10.0f;
			vector[2] = 10.0f;
			camera.move(vector);
		}

	}


	public void keyTyped(KeyEvent e)
	{
	}

	public static void exit()
	{
		animator.stop();
		frame.dispose();
		System.exit(0);
	}

	public static void main(String[] args)
	{
		//create a profile, in this case OpenGL 3.1 or later
		GLProfile profile = GLProfile.get(GLProfile.GL2);

		//configure context
		GLCapabilities capabilities = new GLCapabilities(profile);
		capabilities.setNumSamples(4); // enable anti aliasing - just as a example
		capabilities.setSampleBuffers(true);
		capabilities.setDoubleBuffered(true);

		//initialize a GLDrawable of your choice
		canvas = new GLCanvas(capabilities);

		//register GLEventListener	      
		canvas.addGLEventListener(new Engine());

		animator = new Animator(canvas);

		//canvas.addGLEventListener(new Graphics());
		frame.add(canvas);
		
		// --Uncomment this section to make the demo full screen, just insert the correct resolution-- //
		//	frame.setSize(1920, 1080);
		//	frame.setUndecorated(true);
		//	frame.setExtendedState(Frame.MAXIMIZED_BOTH);
		// ------------------------------------------------------------------------------------------- //
		
		frame.setSize(600, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	    canvas.requestFocus();
		frame.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				exit();
			}
		});
		frame.setVisible(true);
		animator.start();
		canvas.requestFocus();
	}

	public void dispose(GLAutoDrawable gLDrawable)
	{
		// do nothing
	}

	private void labelAxes(GLAutoDrawable gLDrawable)
	{
		GL2 gl = gLDrawable.getGL().getGL2();

		gl.glDisable(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_DEPTH_TEST);

		gl.glColor4f(1.0f, 1.0f, 1.0f, 1.0f); // white
		gl.glLineWidth(2.0f); // thicken the line
		for (int i = -FLOOR_LEN / 2; i <= FLOOR_LEN / 2; i++)
			drawAxisText(gLDrawable, "" + i, (float) i, 0.0f, 0.0f); // along
																		// x-axis

		gl.glColor4f(0.0f, 0.0f, 1.0f, 1.0f); // blue
		for (int i = -FLOOR_LEN / 2; i <= FLOOR_LEN / 2; i++)
			drawAxisText(gLDrawable, "" + i, 0.0f, 0.0f, (float) i); // along
																		// z-axis
		gl.glLineWidth(1.0f); // reset line width

		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_LIGHTING);
	}

	private void drawAxisText(GLAutoDrawable gLDrawable, String txt, float x,
			float y, float z)
	{

		GL2 gl = gLDrawable.getGL().getGL2();
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_DEPTH_TEST);

		gl.glPushMatrix();
		gl.glTranslatef(x, y, z); // position the text
		gl.glScalef(0.0015f, 0.0015f, 0.0015f); // reduce rendering size
		// center text on the x-axis
		float width = glut.glutStrokeLength(GLUT.STROKE_MONO_ROMAN, txt);
		gl.glTranslatef(-width / 2.0f, 0, 0);
		// render the text using a stroke font
		for (int i = 0; i < txt.length(); i++)
		{
			char ch = txt.charAt(i);
			glut.glutStrokeCharacter(GLUT.STROKE_MONO_ROMAN, ch);
		}
		gl.glPopMatrix(); // restore model view

		gl.glEnable(GL2.GL_DEPTH_TEST);
		gl.glEnable(GL2.GL_LIGHTING);
	}
 
    private static void drawTeapot(GLAutoDrawable drawable)
    {
        GL2 gl = drawable.getGL().getGL2();

        gl.glPushAttrib(GL2.GL_TRANSFORM_BIT);
        {
            gl.glMatrixMode(GL2.GL_MODELVIEW);
            gl.glPushMatrix();
            {
                gl.glTranslated(0.0, 0, 0);
                glut.glutSolidTeapot(0.25, true);
            }
            gl.glPopMatrix();
        }
        gl.glPopAttrib();
    }
}