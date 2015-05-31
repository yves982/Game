package fr.cesi.ylalanne.utils.sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import fr.cesi.ylalanne.utils.ResourcesManager;

/**
 * The SoundManager.
 * <p>Note: Handles playing a single sound continuously, for the moment</p>
 */
public class SoundManager {
	private static URL soundURL;
	private static Clip clip;
	private static AudioInputStream aStream;
	
	/**
	 * Play a sound continuously.
	 *
	 * @param soundPath relative path (relative to ResourcesManager.SOUNDS_BASE) to a sound file
	 */
	public static void playContinuously(String soundPath) {
		soundURL = SoundManager.class.getResource(ResourcesManager.SOUNDS_BASE + soundPath);
		try {
			SoundManager.aStream = AudioSystem.getAudioInputStream(soundURL);
			Clip clip = AudioSystem.getClip();
			SoundManager.clip = clip;
			clip.open(aStream);
			clip.setFramePosition(0);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
	
	/**
	 * Checks if it is playing a sound.
	 *
	 * @return true if the SoundManager is playing some sound
	 */
	public static boolean isPlaying() {
		return clip != null && clip.isRunning();
	}
	
	/**
	 * Stops the currently played sound and release system resources.
	 */
	public static void stop() {
		if(clip != null && clip.isOpen() && clip.isRunning()) {
			clip.stop();
			try {
				aStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
