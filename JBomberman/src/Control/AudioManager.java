package Control;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * This class represents a manager for the audio of the game.
 * @author Raf&Vinz
 *
 */
public class AudioManager {
	
	private static AudioManager instance;
	private Clip clip;
	
	/**
	 * This method is used to implement Singleton pattern. It will
	 * create a instance of audio manager if it doesn't exists and then will return it.
	 * @return the single instance of AudioManager
	 */
	public static AudioManager getInstance() {
		if (instance == null)
			instance = new AudioManager();
		return instance;
	}
	
	/**
	 * Private class builder.
	 */
	private AudioManager() {

	}

	/**
	 * This method creates a clip from a music file, saves it and starts it unless an error occurs.
	 * @param filename name of the music file
	 */
	public void play(String filename) {
		clip = playAudio(filename);
		((FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-15);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	/**
	 * This method stops the clip.
	 */
	public void stop() {
		if (clip != null && clip.isActive())
			clip.stop();
	}
	
	/**
	 * This method creates a clip from a music file but doesn't save it. Then it starts the clip unless errors occur.
	 * @param filename name of the music file
	 */
	public Clip playAudio(String filename) {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filename));
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(in);
			Clip audio = AudioSystem.getClip();
			audio.open(audioIn);
			((FloatControl)audio.getControl(FloatControl.Type.MASTER_GAIN)).setValue(-10);
			audio.start();
			return audio;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		} catch (LineUnavailableException e1) {
			e1.printStackTrace();
		}
		return null;
	}

}