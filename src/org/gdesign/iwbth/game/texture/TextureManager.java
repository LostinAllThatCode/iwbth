package org.gdesign.iwbth.game.texture;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class TextureManager {
	
	private static boolean initialized = false;
	private static ArrayList<SpriteSheet> spritesheets = new ArrayList<SpriteSheet>();
	
	public static final int WORLD 	= 0;
	public static final int PLAYER 	= 1;
	
	public static boolean init(){
		try {
			spritesheets.add(new SpriteSheet("world/spritesheet.xml"));
			spritesheets.add(new SpriteSheet("entity/player.xml"));
			return initialized = true;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return initialized = false;
	}
	
	public static SpriteSheet getSpriteSheet(int spritesheet){
		if (initialized) return spritesheets.get(spritesheet); else return null;
	}
	
	public static Sprite getSpriteFromSpriteSheet(int spritesheet, int id){
		if (initialized) return spritesheets.get(spritesheet).getSprite(id); else return null;
	}
	
	public static void cleanUp(){
		for (SpriteSheet s : spritesheets){
			s.cleanUp();
		}
	}
}
