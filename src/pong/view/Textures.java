package pong.view;

import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.GLProfile;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class Textures {

	public static Texture spacetexture = load("outer_space_trip.jpg");
	public static Texture ball1 = load("earth-1k.png");
	public static Texture paddle1 = load("paddle_texture1.jpg");
	public static Texture wall1 = load("washedtexture4.jpg");
	
	
	/** 
	 * Load texture
	 * @param String
	 * @return Textureobject ready to be applied
	 */
	private static Texture load(String texture){

		// Load texture from resource directory, feel free to put files in there
		try {
			InputStream stream;
//			if( (stream = getClass().getResourceAsStream("/textures/"+texture)) == null )
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
