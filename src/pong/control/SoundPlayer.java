package pong.control;
import java.net.URL;

import javax.media.Format;
import javax.media.GainControl;
import javax.media.Manager;
import javax.media.Player;
import javax.media.PlugInManager;
import javax.media.format.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundPlayer {
	static Player player;
	
	
	/**
	 * @param String url
	 * Takes a string which is the name of the sound to be played, plays MP3 files (and others..)
	 */
	public static void playMP3(String url) {
		Format input1 = new AudioFormat(AudioFormat.MPEGLAYER3);
		Format input2 = new AudioFormat(AudioFormat.MPEG);
		Format output = new AudioFormat(AudioFormat.LINEAR);
		PlugInManager.addPlugIn(
			"com.sun.media.codec.audio.mp3.JavaDecoder",
			new Format[]{input1, input2},
			new Format[]{output},
			PlugInManager.CODEC
		);
		
		
		URL defaultImage = SoundPlayer.class.getResource("/resource/" + url);
		try{
			player = Manager.createRealizedPlayer(defaultImage);
			player.realize();
			GainControl gc = player.getGainControl();
			player.start();
			
			// Set to max volume only for win
			if(url.equals("win.mp3") == true){
				gc.setLevel(0.8f);
			}
			else{
				gc.setLevel(0.1f);
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public static void stopMp3(){
		player.stop();
		//player.close();
	}
	
	/**
	 * @param String url
	 * Takes in a filename for the sound to be played, plays it in a own thread
	 * plays WAV-files.
	 * 
	 */
	public static synchronized void playWav(final String url) {
		new Thread(new Runnable() { // the wrapper thread is unnecessary, unless it blocks on the Clip finishing, see
					// comments
					public void run() {
						try {
							Clip clip = AudioSystem.getClip();
							AudioInputStream inputStream = AudioSystem
									.getAudioInputStream(getClass()
											.getResourceAsStream(
													"/resource/" + url));
							clip.open(inputStream);
							clip.start();
						} catch (Exception e) {
							System.err.println(e.getMessage());
						}
					}
				}).start();
	}
}