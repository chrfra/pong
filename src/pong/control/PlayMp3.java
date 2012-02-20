package pong.control;
import java.net.URL;
import javax.media.Format;
import javax.media.GainControl;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;

public class PlayMp3 {
	static Player player;
	public static void playNow(String url) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		
		
		URL defaultImage = PlayMp3.class.getResource("/resource/" + url);
		try{
			player = Manager.createRealizedPlayer(defaultImage);
			player.realize();
			GainControl gc = player.getGainControl();
			player.start();
			// Set to max volume
			gc.setLevel(0.8f);

		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void stopMp3(){
		player.stop();
		//player.close();
	}
}