package org.gdesign.iwbth.game.texture;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.gdesign.iwbth.game.main.Functions;
import org.xml.sax.SAXException;

public class TextureManager {
	
	private static boolean initialized = false;
	
	private static ArrayList<SpriteSheet> spritesheets = new ArrayList<SpriteSheet>();
	
	public static final int WORLD 	= 0;
	public static final int PLAYER 	= 1;
	
	public static void init(){
		if (!initialized) {	
			try {
				System.out.println(Functions.getTimeStamp()+" [TEXTUREMANAGER] : Initializing textures...");
				spritesheets.add(new SpriteSheet("world/spritesheet.xml"));
				spritesheets.add(new SpriteSheet("entity/player.xml"));
				System.out.println(Functions.getTimeStamp()+" [TEXTUREMANAGER] : Textures loaded																																							.");
				initialized = true;
			} catch (ParserConfigurationException | SAXException | IOException e) {
				System.err.println(Functions.getTimeStamp()+" [TEXTUREMANAGER] : "+e.getMessage());	
			}
		}
	}
	
	public static SpriteSheet getSpriteSheet(int spritesheet){
		if (initialized) return spritesheets.get(spritesheet); else return null;
	}
	
	public static Sprite getSpriteFromSpriteSheet(int spritesheet, int id){
		if (initialized) return spritesheets.get(spritesheet).getSprite(id); else return null;
	}
	
	public static void cleanUp(){
		if (initialized){
			for (SpriteSheet s : spritesheets){
				s.cleanUp();
			}
		}
	}
	
	public static boolean initialized(){return initialized;}
}
