package org.gdesign.platformer.managers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.gdesign.games.ecs.BaseManager;
import org.gdesign.games.ecs.Entity;
import org.gdesign.platformer.PlatformerTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;


public class TextureManager extends BaseManager {
	
	String mapping = "./target/classes/";
	String location;
	LinkedHashMap<String,Texture> textures;
	
	public TextureManager(String textureLocation) {
		textures = new LinkedHashMap<String,Texture>();
		location = mapping+textureLocation;
		
		if (preloadTextures()) {
			for (String texturename : textures.keySet()){
				Gdx.app.debug(TextureManager.class.toString(), "+ " + texturename + "@[" + textures.get(texturename) + "]");
			}
		}
		else {
			Gdx.app.error(TextureManager.class.toString(), "Error preloading textures.");
			PlatformerTest.crash(1);
		}
		Gdx.app.log(TextureManager.class.toString(), "Textures/Spritesheets loaded.");
	}
	
	public Texture getTexture(String location){
		return textures.get(location);
	}
	
	public String getLocation(Texture t){
		if (textures.containsValue(t)){
			for (Entry<String, Texture> en : textures.entrySet()){
				if (en.getValue().equals(t)) return en.getKey();
			}
		}
		return "";
	}

	public void dispose(){
		for (Texture texture : textures.values()){
			texture.dispose();
		}
		Gdx.app.log(TextureManager.class.toString(), "Textures/Spritesheets removed.");
	}

	private boolean preloadTextures(){
		Gdx.app.debug(TextureManager.class.toString(), "Looking for textures...");
		if ( !Gdx.files.internal(location).exists() ) return false;
		ArrayList<FileHandle> files = new ArrayList<FileHandle>(); 
		flist(Gdx.files.internal(location),files);
		Gdx.app.debug(TextureManager.class.toString(), "Preloading " + files.size() + " textures...");
		if (files.size() == 0) return false;
		for (FileHandle f : files){
			String texturePath = f.path().substring(mapping.length(), f.path().length());
			Texture texture = new Texture(Gdx.files.internal(texturePath));
			textures.put(texturePath,texture);			
		}
		return true;
	}
	
	private void flist(FileHandle f, ArrayList<FileHandle> fileList){
		if (!f.isDirectory() && f.name().contains(".png")) fileList.add(f);
		else {
			for (FileHandle fileH : f.list()){
				flist(fileH, fileList);
			}
		}
	}
	
	@Override
	public void added(Entity e) {}

	@Override
	public void changed(Entity e) {}

	@Override
	public void removed(Entity e) {}

	@Override
	public void enabled(Entity e) {}

	@Override
	public void disabled(Entity e) {}

}
