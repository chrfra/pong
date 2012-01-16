/**
 * @author Lyes Hassaine / LeoLoL : http://www.leolol.com 
 */

public class Quaternion {
    private final double x0, x1, x2, x3; 

    // create a new object with the given components
    public Quaternion(double x0, double x1, double x2, double x3) {
        this.x0 = x0;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
    }

    // return a string representation of the invoking object
    public String toString() {
        return x0 + " + " + x1 + "i + " + x2 + "j + " + x3 + "k";
    }

    // return the quaternion norm
    public double norm() {
        return Math.sqrt(x0*x0 + x1*x1 +x2*x2 + x3*x3);
    }

    // return the quaternion conjugate
    public Quaternion conjugate() {
        return new Quaternion(x0, -x1, -x2, -x3);
    }

    // return a new Quaternion whose value is (this + b)
    public Quaternion plus(Quaternion b) {
        Quaternion a = this;
        return new Quaternion(a.x0+b.x0, a.x1+b.x1, a.x2+b.x2, a.x3+b.x3);
    }
    
    public float[] rotate(float[] v) {
        // PBA Theorem 18.42  p'= qpq* 
        // p is quaternion [0,(x,y,z)]
        // p' is the rotatet result
        // q  is the unit quaternion representing a rotation
        // q* is the conjugated q
        Quaternion vq = new Quaternion(0.0f, v[0],v[1],v[2]);
        Quaternion rotatet = this.times(vq).times(
                this .conjugate());

        float[] vec = new float[3];
        vec[0] = (float)rotatet.getX1();
        vec[1] = (float)rotatet.getX2();
        vec[2] = (float)rotatet.getX3();
        
        return vec;
        //[(float)rotatet.getX0(),(float)rotatet.getX1(),(float)rotatet.getX2()];
    }


    // return a new Quaternion whose value is (this * b)
    public Quaternion times(Quaternion b) {
        Quaternion a = this;
        double y0 = a.x0*b.x0 - a.x1*b.x1 - a.x2*b.x2 - a.x3*b.x3;
        double y1 = a.x0*b.x1 + a.x1*b.x0 + a.x2*b.x3 - a.x3*b.x2;
        double y2 = a.x0*b.x2 - a.x1*b.x3 + a.x2*b.x0 + a.x3*b.x1;
        double y3 = a.x0*b.x3 + a.x1*b.x2 - a.x2*b.x1 + a.x3*b.x0;
        return new Quaternion(y0, y1, y2, y3);
    }

    // return a new Quaternion whose value is the inverse of this
    public Quaternion inverse() {
        double d = x0*x0 + x1*x1 + x2*x2 + x3*x3;
        return new Quaternion(x0/d, -x1/d, -x2/d, -x3/d);
    }
    
    public static Quaternion CreateFromYawPitchRoll(double yaw, double pitch, double roll)
    {
        Quaternion quaternion;
        double num9 = roll * 0.5f;
        double num6 = (double) Math.sin((double) num9);
        double num5 = (double) Math.cos((double) num9);
        double num8 = pitch * 0.5f;
        double num4 = (double) Math.sin((double) num8);
        double num3 = (double) Math.cos((double) num8);
        double num7 = yaw * 0.5f;
        double num2 = (double) Math.sin((double) num7);
        double num = (double) Math.cos((double) num7);
        double x = ((num * num4) * num5) + ((num2 * num3) * num6);
        double y = ((num2 * num3) * num5) - ((num * num4) * num6);
        double z = ((num * num3) * num6) - ((num2 * num4) * num5);
        double w = ((num * num3) * num5) + ((num2 * num4) * num6);
        quaternion = new Quaternion(x,y,z,w);
        return quaternion;
    }


    // return a / b
    public Quaternion divides(Quaternion b) {
         Quaternion a = this;
        return a.inverse().times(b);
    }
    
    public float[] getRotationMatrix()
    {
    	float[] mat = new float[16];
    	
    	float xx      = (float) (x1 * x1);
    	float xy      = (float) (x1 * x2);
    	float xz      = (float) (x1 * x3);
    	float xw      = (float) (x1 * x0);
    	float yy      = (float) (x2 * x2);
    	float yz      = (float) (x2 * x3);
    	float yw      = (float) (x2 * x0);
    	float zz      = (float) (x3 * x3);
    	float zw      = (float) (x3 * x0);
        mat[0]  = 1 - 2 * ( yy + zz );
        mat[1]  =     2 * ( xy - zw );
        mat[2]  =     2 * ( xz + yw );
        mat[4]  =     2 * ( xy + zw );
        mat[5]  = 1 - 2 * ( xx + zz );
        mat[6]  =     2 * ( yz - xw );
        mat[8]  =     2 * ( xz - yw );
        mat[9]  =     2 * ( yz + xw );
        mat[10] = 1 - 2 * ( xx + yy );
        mat[3]  = mat[7] = mat[11] = mat[12] = mat[13] = mat[14] = 0;
        mat[15] = 1;
    	
    	return mat;
    	
    	
//    	float[] m = new float[16];
//    	m[0] = (float) (1.0f - 2.0f * (x1 * x1 + x2 * x2));
//    	m[1] = (float) (2.0f * (x0 * x1 - x2 * x3));
//    	m[2] = (float) (2.0f * (x2 * x0 + x1 * x3));
//    	m[3] = 0.0f;
//    	
//    	m[4] = (float) (2.0f * (x0 * x1 + x2 * x3));
//    	m[5]= (float) (1.0f - 2.0f * (x2 * x2 + x0 * x0));
//    	m[6] = (float) (2.0f * (x1 * x2 - x0 * x3));
//    	m[7] = 0.0f;
//    	
//    	m[8] = (float) (2.0f * (x2 * x0 - x1 * x3));
//    	m[9] = (float) (2.0f * (x1 * x2 + x0 * x3));
//    	m[10] = (float) (1.0f - 2.0f * (x1 * x1 + x0 * x0));
//    	m[11] = 0.0f;
//    	
//    	m[12] = 0.0f;
//    	m[13] = 0.0f;
//    	m[14] = 0.0f;
//    	m[15] = 1.0f;
//    	return m;
    }
    
    public float[] transposeMatrix(float[] mat)
    {
    	float[] tran = new float[16];
    	
    	tran[0] = mat[0];
    	tran[1] = mat[4];
    	tran[2] = mat[8];
    	tran[3] = mat[12];
    	tran[4] = mat[1];
    	tran[5] = mat[5];
    	tran[6] = mat[9];
    	tran[7] = mat[13];
    	tran[8] = mat[2];
    	tran[9] = mat[6];
    	tran[10] = mat[10];
    	tran[11] = mat[14];
    	tran[12] = mat[3];
    	tran[13] = mat[7];
    	tran[14] = mat[11];
    	tran[15] = mat[15];
    	return tran;
    }
    
    public float[] multiplyMatrix(float[] m1, float[] m2)
    {
    	if (m1.length != m2.length)
            throw new IllegalArgumentException("Vectors need to have the same length");
        float[][] m = new float[m1.length][m1.length];
        for (int i=0; i<m1.length; i++)
            for (int j=0; j<m1.length; j++)
                m[i][j] = (m1[i]*m2[j]);
        
        int id = 0;
        
        float[] newArray = new float[16];
        
        for (int j=0; j<4; j++)
        {
        	newArray[id] = m[0][j];
        	id++;
        }
        
        for (int j=0; j<4; j++)
        {
        	newArray[id] = m[1][j];
        	id++;
        }
        
        for (int j=0; j<4; j++)
        {
        	newArray[id] = m[2][j];
        	id++;
        }
        
        for (int j=0; j<4; j++)
        {
        	newArray[id] = m[3][j];
        	id++;
        }
        
        return newArray;
    }
    


    // sample client for testing
    public static void main(String[] args) {
        Quaternion a = new Quaternion(3.0, 1.0, 0.0, 0.0);
        System.out.println("a = " + a);

        Quaternion b = new Quaternion(0.0, 5.0, 1.0, -2.0);
        System.out.println("b = " + b);

        System.out.println("norm(a)  = " + a.norm());
        System.out.println("conj(a)  = " + a.conjugate());
        System.out.println("a + b    = " + a.plus(b));
        System.out.println("a * b    = " + a.times(b));
        System.out.println("b * a    = " + b.times(a));
        System.out.println("a / b    = " + a.divides(b));
        System.out.println("a^-1     = " + a.inverse());
        System.out.println("a^-1 * a = " + a.inverse().times(a));
        System.out.println("a * a^-1 = " + a.times(a.inverse()));
    }

	public double getX0()
	{
		return x0;
	}

	public double getX1()
	{
		return x1;
	}

	public double getX2()
	{
		return x2;
	}

	public double getX3()
	{
		return x3;
	}

}