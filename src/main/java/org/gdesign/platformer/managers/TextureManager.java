package org.gdesign.platformer.managers;

import java.util.ArrayList;
import java.util.HashMap;
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
	HashMap<String,Texture> textures;
	
	public TextureManager(String textureLocation) {
		textures = new HashMap<String,Texture>();
		location = mapping+textureLocation;
		if (preloadTextures()) {
			for (String texturename : textures.keySet()){
				System.out.println(texturename + " preloaded as " + textures.get(texturename));
			}
		}
		else PlatformerTest.crash(1, "Error while preloading textures...");
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

	public String getAnimationData(String textureLocation){
		return textureLocation.substring(0, textureLocation.length()-3)+"json";
	}
	
	public String getAnimationData(Texture t){
		String animData = getLocation(t);
		return animData.substring(0, animData.length()-3)+"json";
	}

	public void dispose(){
		System.out.println("TextureManager cleanup.");
		for (Texture texture : textures.values()){
			texture.dispose();
		}
	}

	private boolean preloadTextures(){
		if ( !Gdx.files.internal(location).exists() ) return false;
		ArrayList<FileHandle> files = new ArrayList<FileHandle>(); 
		flist(Gdx.files.internal(location),files);
		if (files.size() == 0) return false;
		for (FileHandle f : files){
			String texturePath = f.path().substring(mapping.length(), f.path().length());
			textures.put(texturePath, new Texture(Gdx.files.internal(texturePath)));
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
	public void added(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changed(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removed(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void enabled(Entity e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void disabled(Entity e) {
		// TODO Auto-generated method stub

	}

}
