package org.gdesign.iwbth.game.audio;

import java.io.IOException;
import java.util.ArrayList;

import org.gdesign.iwbth.game.main.Constants;
import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AudioManager {
	
	//TODO: Just a rudimentary implementation.

	public static final int SOUND_MUSIC_LOOP1 	= 0;	
	public static final int SOUND_FX_JUMP 		= 1;
	public static final int SOUND_FX_SHOT 		= 2;
	public static final int SOUND_MUSIC_DEATH	= 3;
	
	public static boolean initialized = false; 
	
	private static ArrayList<Audio> sounds = new ArrayList<>();
	private static Audio currentSound;
	
	private static float soundPitch 	= 1.0f;
	private static float soundGain 		= 0.0915000002f;
	
	public static void init(){
		if (!initialized) {	
			new Thread(new Runnable() {
				@Override
				public void run() {	
					try {
						System.out.println(Constants.getCurrentTimeStamp()+" [AUDIOMANAGER] : Initializing sounds...");
						sounds.add(AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sound/bgmusic.ogg")));
						sounds.add(AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sound/jump.ogg")));
						sounds.add(AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sound/pew2.ogg")));
						sounds.add(AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sound/dying1.ogg")));
						System.out.println(Constants.getCurrentTimeStamp()+" [AUDIOMANAGER] : Sounds loaded.");
						currentSound=sounds.get(0);
						initialized = true;
					} catch (IOException e) {
						e.printStackTrace();
					}		
				}
			}).start();
		}
	}
	
	public static void setBackgroundSound(int sound, boolean loop){
		if (initialized) {
			currentSound = sounds.get(sound);
			play(loop);
		}
	}
	
	public static void play(boolean loop){
		if (initialized) currentSound.playAsMusic(1, soundGain, loop);		
	}
	
	public static void stop(){
		if (initialized) currentSound.stop();
	}
	
	public static void setVolume(float vol){
		if (initialized) {
			currentSound.stop();
			currentSound.playAsMusic(soundPitch, vol, true);		
		}
	}
	
	public static void playFX(int sound){
		if (initialized) sounds.get(sound).playAsSoundEffect(1,1,false);
	}
	
	public static void close(){
		AL.destroy();
	}
	
	public static boolean initialized(){return initialized;}
	
}
