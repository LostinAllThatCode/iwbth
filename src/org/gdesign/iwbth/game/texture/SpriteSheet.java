package org.gdesign.iwbth.game.texture;


import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;

import org.lwjgl.opengl.GL11;
import org.xml.sax.SAXException;

public class SpriteSheet {
	private int texture;
	private ArrayList<Sprite> sprites = new ArrayList<>();
	
	public SpriteSheet(String filename) throws ParserConfigurationException, SAXException, IOException{
		TextureLoader.getSpriteSheetFromXML(filename, this);
	}
	
	public Sprite getSprite(int id){
		for (Sprite s : sprites){
			if (s.getId() == id) return s;
		}
		return null;
	}
	
	public int getSpriteCount(){
		return this.sprites.size();
	}
	
	public boolean addSprite(Sprite s){
		return sprites.add(s);
	}
	
	public void setTexture(int texture){
		this.texture = texture;
	}
	
	public int getTexture(){
		return this.texture;
	}
	
	@Override
	public String toString() {
		String s = "";
		for (Sprite sprite : sprites){
			s += sprite.toString();
		}
		return s;
	}
	
	public void cleanUp() {
		System.out.println("delete texture : "+texture);
		GL11.glDeleteTextures(texture);
	}
}
