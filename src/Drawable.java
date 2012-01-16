import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;


public abstract class Drawable
{
	//public abstract void draw(GL2 gl, GLU glu);
	
	public abstract void draw(GL2 gl, GLU glu, int shaderID);
}
