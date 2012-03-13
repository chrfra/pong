package pong.control;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URI;
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

		// Check if running from JAR or Eclipse
		File check = new File(SoundPlayer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String checkJar = check.toURI().toString();

		URL newurl = null;
		if(checkJar.endsWith(".jar")){
			// Inside JAR
			// Get path to JAR.
			File jarpath = new File(SoundPlayer.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			URI jarURI = jarpath.toURI();

			try {
				String newpath = jarURI.toString();
				newpath = newpath.substring(0, newpath.length()-12);
				// 12 means pong_fat.jar, remove this to make compatible for JARs to run sound
				newurl = new URL(newpath+"/sound/"+url);

			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else{
			// Running in eclipse
			newurl = SoundPlayer.class.getResource("/sound/" + url);
		}


		try{
			player = Manager.createRealizedPlayer(newurl);
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
											"/sound/" + url));
					clip.open(inputStream);
					clip.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
}