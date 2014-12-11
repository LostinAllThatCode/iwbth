package org.gdesign.json;

import java.util.HashMap;
import java.util.Iterator;

import org.gdesign.platformer.components.Animatable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JSONParser {

	public static JsonReader jsonReader;
	
	static {
		jsonReader = new JsonReader();
	}
	
	public static HashMap<String, Animation> parseAnimationFromJson(Animatable animatable, String filename){
		JsonValue jsonValue = jsonReader.parse(Gdx.files.internal(filename));
		HashMap<String, Animation> animationMap = new HashMap<String, Animation>();
		Texture texture = animatable.getTexture();	
		Iterator<JsonValue> iter = jsonValue.get("animations").iterator();
		while (iter.hasNext()){
			JsonValue anim = iter.next();
			
			int frameWidth = anim.getInt("width");
			int frameHeight = anim.getInt("height");
			int frameCount = anim.getInt("frames");
					
			TextureRegion tmpRegion = new TextureRegion(texture,anim.getInt("originX"), anim.getInt("originY"), frameWidth * frameCount, frameHeight);
			TextureRegion[][] tmp = tmpRegion.split(tmpRegion.getRegionWidth()/frameCount, tmpRegion.getRegionHeight());
			TextureRegion[] region =  new TextureRegion[frameCount];
			int index = 0;
	    	for (int j = 0; j < frameCount; j++) {
	    		if (index < frameCount) region[index++] = tmp[0][j];
	    	}
			Animation animation = new Animation(anim.getFloat("speed"),region);
			if (anim.getBoolean("loop")) animation.setPlayMode(PlayMode.LOOP); else animation.setPlayMode(PlayMode.NORMAL);
			animationMap.put(anim.getString("name"), animation);
		}
		return animationMap;
	}

}
