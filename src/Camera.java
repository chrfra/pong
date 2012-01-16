import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;



public class Camera
{
	float[] position = new float[3];
	float[] viewDir = new float[3];
	float[] upVector = new float[3];
	float[] rightVector = new float[3];
	
	float[] initviewDir = new float[3];
	float[] initupVector = new float[3];
	float[] initrightVector = new float[3];
	
	float rotatedX;
	float rotatedY;
	float rotatedZ;
	
	float zoomDistance;
	
	

	
	public float getF3dVectorLength( float x, float y, float z)
	{
		return (float)(Math.sqrt((x*x)+(y*y)+(z*z)));
	}
	
	public float[] normalize3dVector( float[] v)
	{
		float[] res = new float[3];
		float l = getF3dVectorLength(v[0],v[1],v[2]);
		if (l == 0.0f) 
		{
			res[0] = 0.0f;
			res[1] = 0.0f;
			res[2] = 0.0f;
			return res;
		}
		res[0] = v[0] / l;
		res[1] = v[1] / l;
		res[2] = v[2] / l;
		return res;
	}
	
	public void rotateByQuaternion( Quaternion q )
	{
		viewDir = q.rotate(initviewDir);
		upVector = q.rotate(initupVector);
		rightVector = q.rotate(initrightVector);
	}
	
	public float[] operatorPlus (float[] v, float[] u)
	{
		float[] res = new float[3];
		res[0] = v[0]+u[0];
		res[1] = v[1]+u[1];
		res[2] = v[2]+u[2];
		return res;
	}
	
	public float[] operatorMinus (float[] v, float[] u)
	{
		float[] res = new float[3];
		res[0] = v[0]-u[0];
		res[1] = v[1]-u[1];
		res[2] = v[2]-u[2];
		return res;
	}
	
	
	public float[] operatorMultiply (float[] v, float r)
	{
		float[] res = new float[3];
		res[0] = v[0]*r;
		res[1] = v[1]*r;
		res[2] = v[2]*r;
		return res;
	}
	
	public float operatorMultiply (float[] v, float[] u)	//dot product
	{
		return v[0]*u[0]+v[1]*u[1]+v[2]*u[2];
	}
	
	public float[] crossProduct (float[] u, float[] v)
	{
		float[] resVector = new float[3];
		resVector[0] = u[1]*v[2] - u[2]*v[1];
		resVector[1] = u[2]*v[0] - u[0]*v[2];
		resVector[2] = u[0]*v[1] - u[1]*v[0];
		return resVector;
	}
	
	
	
	
	
	
	//***************************************************************************************//
	
	public Camera()
	{
		//Init with standard OGL values:
		position[0] = 0.0f;
		position[1] = 0.0f;
		position[2] = 0.0f;
		
		initviewDir[0] = 1.0f;
		initviewDir[1] = 0.0f;
		initviewDir[2] = 0.0f;
		
		initrightVector[0] = 0.0f;
		initrightVector[1] = 0.0f;
		initrightVector[2] = 1.0f;
		
		initupVector[0] = 0.0f;
		initupVector[1] = 1.0f;
		initupVector[2] = 0.0f;
		
		zoomDistance = -10.0f;
		
//		viewDir[0] = 0.0f;
//		viewDir[1] = 0.0f;
//		viewDir[2] = 1.0f;
//		
//		rightVector[0] = 0.0f;
//		rightVector[1] = 0.0f;
//		rightVector[2] = 1.0f;
//		
//		upVector[0] = 0.0f;
//		upVector[1] = -1.0f;
//		upVector[2] = 0.0f;
	
		//Only to be sure:
		rotatedX = rotatedY = rotatedZ = 0.0f;
	}
	
	public void move (float[] Direction)
	{
		position = operatorPlus(position , Direction);
	}
	
	public void rotateX (float angle)
	{
		rotatedX += angle;
		
		//Rotate viewdir around the right vector:
		viewDir = normalize3dVector(operatorPlus(operatorMultiply(viewDir,(float)Math.cos(angle*Math.PI/180.0))
									, operatorMultiply(upVector,(float)Math.sin(angle*Math.PI/180.0))));
	
		//now compute the new UpVector (by cross product)
		upVector = operatorMultiply(crossProduct(viewDir, rightVector),-1.0f);
	
		
	}
	
	public void rotateY (float angle)
	{
		rotatedY += angle;
		
		//Rotate viewdir around the up vector:
		viewDir = normalize3dVector(operatorPlus(operatorMultiply(viewDir,(float)Math.cos(angle*Math.PI/180))
									, operatorMultiply(rightVector,(float)Math.sin(angle*Math.PI/180))));
	
		//now compute the new RightVector (by cross product)
		rightVector = crossProduct(viewDir, upVector);
	}
	
	public void rotateZ (float angle)
	{
		rotatedZ += angle;
		
		//Rotate viewdir around the right vector:
		rightVector = normalize3dVector(operatorPlus(operatorMultiply(rightVector,(float)Math.cos(angle*Math.PI/180))
				, operatorMultiply(upVector,(float)Math.sin(angle*Math.PI/180))));
	
		//now compute the new UpVector (by cross product)
		upVector = operatorMultiply(crossProduct(viewDir, rightVector),-1);
	}
	
	public void render( GL2 gl, GLU glu )
	{
		
//		gl.glMatrixMode(GL2.GL_PROJECTION);
//		gl.glLoadIdentity();
//		glu.gluPerspective(50.0, 1.0, 3.0, 20.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		//The point at which the camera looks:
		float[] viewPoint = operatorPlus(position, viewDir);
	
		//as we know the up vector, we can easily use gluLookAt:
		glu.gluLookAt(	position[0] ,position[1] ,position[2] ,
					viewPoint[0] ,viewPoint[1] ,viewPoint[2] ,
					upVector[0] ,upVector[1] ,upVector[2] );
	
	}
	
	public void moveForward( float distance )
	{
		position =  operatorPlus(position, operatorMultiply(viewDir, distance));
	}
	
	public void strafeRight ( float distance )
	{
		position = operatorPlus(position, operatorMultiply(rightVector, distance));
	}
	
	public void moveUpward( float distance )
	{
		position = operatorPlus(position, operatorMultiply(upVector, distance));
	}

	public float[] getPosition()
	{
		return position;
	}

	public void setXPos(double xPos)
	{
		position[0] = (float) xPos;	
	}

	public void setYPos(double yPos)
	{
		position[1] = (float) yPos;
	}

	public void setZPos(double zPos)
	{
		position[2] = (float) zPos;
	}

	public float getZoomDistance()
	{
		return zoomDistance;
	}

	public void zoomOut()
	{
		zoomDistance += 0.5f;
	}

	public void zoomIn()
	{
		zoomDistance -= 0.5f;
	}
}