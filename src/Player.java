import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class Player extends Drawable
{
	private boolean selected;

	private float speed;
	private float maxSpeed;
	private float afterBurnerSpeed;

	private String name;
	private OBJModel model;
	private Camera camera;

	private float pitchChange;
	private float yawChange;
	private float rollChange;

	private float[] position = new float[3];
	private float[] viewDir = new float[3];
	private float[] upVector = new float[3];
	private float[] rightVector = new float[3];

	private float[] initviewDir = new float[3];
	private float[] initupVector = new float[3];
	private float[] initrightVector = new float[3];

	private Quaternion q;
	private Quaternion q2;

	public Player(String name, OBJModel model, float xPos, float yPos,
			float zPos)
	{
		this.setName(name);
		this.model = model;

		q = Quaternion.CreateFromYawPitchRoll(0, 0, 0);
		q2 = Quaternion.CreateFromYawPitchRoll(0, 0, 0);

		maxSpeed = 0.04f;

		pitchChange = 0;
		yawChange = 0;
		rollChange = 0;

		position[0] = xPos;
		position[1] = yPos;
		position[2] = zPos;

		initviewDir[0] = 1.0f;
		initviewDir[1] = 0.0f;
		initviewDir[2] = 0.0f;

		initrightVector[0] = 0.0f;
		initrightVector[1] = 0.0f;
		initrightVector[2] = 1.0f;

		initupVector[0] = 0.0f;
		initupVector[1] = 1.0f;
		initupVector[2] = 0.0f;

		selected = false;
	}

	public OBJModel getModel()
	{
		return model;
	}

	public float getF3dVectorLength(float x, float y, float z)
	{
		return (float) (Math.sqrt((x * x) + (y * y) + (z * z)));
	}

	public float[] normalize3dVector(float[] v)
	{
		float[] res = new float[3];
		float l = getF3dVectorLength(v[0], v[1], v[2]);
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

	public void rotateByQuaternion(Quaternion q)
	{
		viewDir = q.rotate(initviewDir);
		rightVector = q.rotate(initrightVector);
		upVector = q.rotate(initupVector);

	}

	public float[] operatorPlus(float[] v, float[] u)
	{
		float[] res = new float[3];
		res[0] = v[0] + u[0];
		res[1] = v[1] + u[1];
		res[2] = v[2] + u[2];
		return res;
	}

	public float[] operatorMinus(float[] v, float[] u)
	{
		float[] res = new float[3];
		res[0] = v[0] - u[0];
		res[1] = v[1] - u[1];
		res[2] = v[2] - u[2];
		return res;
	}

	public float[] operatorMultiply(float[] v, float r)
	{
		float[] res = new float[3];
		res[0] = v[0] * r;
		res[1] = v[1] * r;
		res[2] = v[2] * r;
		return res;
	}

	public float operatorMultiply(float[] v, float[] u) //dot product
	{
		return v[0] * u[0] + v[1] * u[1] + v[2] * u[2];
	}

	public float[] crossProduct(float[] u, float[] v)
	{
		float[] resVector = new float[3];
		resVector[0] = u[1] * v[2] - u[2] * v[1];
		resVector[1] = u[2] * v[0] - u[0] * v[2];
		resVector[2] = u[0] * v[1] - u[1] * v[0];
		return resVector;
	}

	//***************************************************************************************//

	public void move(float[] Direction)
	{
		position = operatorPlus(position, Direction);
	}

	public void rotateX(float angle)
	{
		//Rotate viewdir around the right vector:
		viewDir = normalize3dVector(operatorPlus(
				operatorMultiply(viewDir,
						(float) Math.cos(angle * Math.PI / 180.0)),
				operatorMultiply(upVector,
						(float) Math.sin(angle * Math.PI / 180.0))));

		//now compute the new UpVector (by cross product)
		upVector = operatorMultiply(crossProduct(viewDir, rightVector), -1.0f);

	}

	public void rotateY(float angle)
	{
		//Rotate viewdir around the up vector:
		viewDir = normalize3dVector(operatorPlus(
				operatorMultiply(viewDir,
						(float) Math.cos(angle * Math.PI / 180)),
				operatorMultiply(rightVector,
						(float) Math.sin(angle * Math.PI / 180))));

		//now compute the new RightVector (by cross product)
		rightVector = crossProduct(viewDir, upVector);
	}

	public void rotateZ(float angle)
	{
		//Rotate viewdir around the right vector:
		rightVector = normalize3dVector(operatorPlus(
				operatorMultiply(rightVector,
						(float) Math.cos(angle * Math.PI / 180)),
				operatorMultiply(upVector,
						(float) Math.sin(-angle * Math.PI / 180))));

		//now compute the new UpVector (by cross product)
		upVector = operatorMultiply(crossProduct(viewDir, rightVector), -1);
	}

	public void render(GL2 gl, GLU glu)
	{

		//The point at which the camera looks:
		float[] viewPoint = operatorPlus(position, viewDir);

		//as we know the up vector, we can easily use gluLookAt:
		glu.gluLookAt(position[0], position[1], position[2], viewPoint[0],
				viewPoint[1], viewPoint[2], upVector[0], upVector[1],
				upVector[2]);

	}

	public void moveForward(float distance)
	{

		position = operatorPlus(position, operatorMultiply(viewDir, distance));
	}

	public void strafeRight(float distance)
	{
		position = operatorPlus(position,
				operatorMultiply(rightVector, distance));
	}

	public void moveUpward(float distance)
	{
		position = operatorPlus(position, operatorMultiply(upVector, distance));
	}

	public float[] getPosition()
	{
		return position;
	}

	public void setXPos(float xPos)
	{
		position[0] = (float) xPos;
	}

	public void setYPos(float yPos)
	{
		position[1] = (float) yPos;
	}

	public void setZPos(float zPos)
	{
		position[2] = (float) zPos;
	}

	public void shoot()
	{
		// TODO Auto-generated method stub

	}

	public void accelerate(float amount)
	{
		speed += amount;

		if (speed > maxSpeed)
		{
			speed = maxSpeed;
		}

	}

	/* ------- Get and Set commands--------- */

	public Camera getCamera()
	{
		return camera;
	}

	public float getXPos()
	{
		return position[0];
	}

	public float getYPos()
	{
		return position[1];
	}

	public float getZPos()
	{
		return position[2];
	}

	public float getSpeed()
	{
		return speed;
	}

	public void setSpeed(float speed)
	{
		this.speed = speed;
	}

	public float getMaxSpeed()
	{
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed)
	{
		this.maxSpeed = maxSpeed;
	}

	public float getAfterBurnerSpeed()
	{
		return afterBurnerSpeed;
	}

	public void setAfterBurnerSpeed(float afterBurnerSpeed)
	{
		this.afterBurnerSpeed = afterBurnerSpeed;
	}

	public void setCamera(Camera camera)
	{
		this.camera = camera;
	}

//	public void draw(GL2 gl, GLU glu)
//	{
//		model.drawModel(gl);
//		//glut.glutSolidTeapot(2.0);
//	}

	public float getPitchChange()
	{
		return pitchChange;
	}

	public void setPitchChange(float pitchChange)
	{
		this.pitchChange = pitchChange;
	}

	public float getYawChange()
	{
		return yawChange;
	}

	public void setYawChange(float yawChange)
	{
		this.yawChange = yawChange;
	}

	public float getRollChange()
	{
		return rollChange;
	}

	public void setRollChange(float rollChange)
	{
		this.rollChange = rollChange;
	}

	public void select()
	{
		selected = true;
	}

	public boolean isSelected()
	{
		return selected;
	}

	public Quaternion getQ()
	{
		return q;
	}

	public void setQ(Quaternion q)
	{
		this.q = q;
	}

	public Quaternion getQ2()
	{
		return q2;
	}

	public void setQ2(Quaternion q2)
	{
		this.q2 = q2;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public void draw(GL2 gl, GLU glu, int shaderID)
	{
		model.drawModel(gl, shaderID);
	}

	/* --------------------------- */

}
