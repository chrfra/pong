import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.media.opengl.GL2;

/**
 * @author Lyes Hassaine / LeoLoL : http://www.leolol.com 
 */

public class ShaderControl
{
	private int     vertexShaderProgram;
	private int     fragmentShaderProgram;
	private int     shaderprogram;
	public String[] vsrc;
	public String[] fsrc;
	public String[] gsrc;

	public void init( GL2 gl )
	{
		try
		{
			attachShaders(gl);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public String[] loadShaderSrc( String name )
	{
		StringBuilder sb = new StringBuilder();
		try
		{
			InputStream is = getClass().getResourceAsStream(name);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String line;
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
				sb.append('\n');
			}
			is.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("Shader is " + sb.toString());
		return new String[]
		{ sb.toString() };
	}

	private void attachShaders( GL2 gl ) throws Exception
	{
		vertexShaderProgram = gl.glCreateShader(GL2.GL_VERTEX_SHADER);
		fragmentShaderProgram = gl.glCreateShader(GL2.GL_FRAGMENT_SHADER);
		
		gl.glShaderSource(vertexShaderProgram, 1, vsrc, null, 0);
		gl.glCompileShader(vertexShaderProgram);

		gl.glShaderSource(fragmentShaderProgram, 1, fsrc, null, 0);
		gl.glCompileShader(fragmentShaderProgram);
		
		shaderprogram = gl.glCreateProgram();
		//
		gl.glAttachShader(shaderprogram, vertexShaderProgram);
		gl.glAttachShader(shaderprogram, fragmentShaderProgram);
		
		gl.glLinkProgram(shaderprogram);
		gl.glValidateProgram(shaderprogram);
		IntBuffer intBuffer = IntBuffer.allocate(1);
		gl.glGetProgramiv(shaderprogram, GL2.GL_LINK_STATUS, intBuffer);
		if (intBuffer.get(0) != 1)
		{
			gl.glGetProgramiv(shaderprogram, GL2.GL_INFO_LOG_LENGTH, intBuffer);
			int size = intBuffer.get(0);
			System.err.println("Program link error: ");
			if (size > 0)
			{
				ByteBuffer byteBuffer = ByteBuffer.allocate(size);
				gl.glGetProgramInfoLog(shaderprogram, size, intBuffer, byteBuffer);
				for (byte b : byteBuffer.array())
				{
					System.err.print((char) b);
				}
			}
			else
			{
				System.out.println("Unknown");
			}
			System.exit(1);
		}
	}
	
                public int useShader( GL2 gl )
	{
		gl.glUseProgram(shaderprogram);
		return shaderprogram;
	}

	public void dontUseShader( GL2 gl )
	{
		gl.glUseProgram(0);
	}

	public int getShaderprogram()
	{
		return shaderprogram;
	}

	public void setShaderprogram(int shaderprogram)
	{
		this.shaderprogram = shaderprogram;
	}
	
	
}