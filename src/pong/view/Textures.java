package pong.view;

import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textures {

	public static Texture space = load("outer_space_trip.jpg");
	public static Texture ball1 = load("balltexture1.png");
	public static Texture mainBall1 = load("balltexture3.png");
	public static Texture paddle1 = load("paddle_texture1.jpg");
	public static Texture menu = load("menu_texture1.jpg");
	public static Texture wall1 = load("wall1.jpg");
	public static Texture explosion1 = load("fire_transparent.png");
	
	
	/** 
	 * Load texture
	 * @param String
	 * @return Textureobject ready to be applied
	 */
	private static Texture load(String texture){

		try {
			InputStream stream;
			if( (stream = Object.class.getResourceAsStream("/textures/"+texture)) == null )
			{
				System.out.println("Texture not loaded..");
			}
			TextureData data = TextureIO.newTextureData(GLProfile.getDefault(), stream, false, "png");
			return TextureIO.newTexture(data);
		}
		catch (IOException exc) {
			exc.printStackTrace();
			System.exit(1);
		}
		return null;
	}
	
}
