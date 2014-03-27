package org.gdesign.iwbth.game.audio;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public class AudioManager {
	
	//TODO: Just a rudimentary implementation.

	public static final int SOUND_MUSIC_LOOP1 	= 0;	
	public static final int SOUND_FX_JUMP 		= 1;
	
	private static ArrayList<Audio> sounds = new ArrayList<>();
	private static Audio currentSound;
	
	private static float soundPitch 	= 1.0f;
	private static float soundGain 		= 0.0915000002f;
	
	public static boolean init(){
		try {
			sounds.add(AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sound/bgmusic.ogg")));
			sounds.add(AudioLoader.getAudio("OGG", ResourceLoader.getResourceAsStream("sound/jump.ogg")));
			currentSound=sounds.get(0);
			return true;
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}
	
	public static void setBackgroundSound(int sound, boolean loop){
		currentSound = sounds.get(sound);
		play(loop);
	}
	
	public static void play(boolean loop){
		currentSound.playAsMusic(1, soundGain, loop);		
	}
	
	public static void stop(){
		currentSound.stop();
	}
	
	public static void setVolume(float vol){
		currentSound.stop();
		currentSound.playAsMusic(soundPitch, vol, true);
	}
	
	public static void playFX(int sound){
		sounds.get(sound).playAsSoundEffect(1,1,false);
	}
	
	public static void close(){
		AL.destroy();
	}
	
}
