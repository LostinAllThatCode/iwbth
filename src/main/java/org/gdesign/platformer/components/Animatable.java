package org.gdesign.platformer.components;


import java.util.HashMap;

import org.gdesign.json.JSONParser;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Animatable extends Renderable {
	
	private HashMap<String, Animation> animations;
	
	public float stateTime;
	public boolean flipX,flipY;
	
	public String currentAnimationString;
	public Animation currentAnimation;
	public TextureRegion frame;
	
	public Animatable(Texture t, String json) {
		super(t);
		animations = new HashMap<String, Animation>();
		if (json != null) {
			animations = JSONParser.parseAnimationFromJson(this,json);
			if (!animations.isEmpty()){
				currentAnimationString = animations.entrySet().iterator().next().getKey();
				currentAnimation = animations.get(currentAnimationString);
			}
		}

		stateTime = 0f;
		flipX = false;
		flipY = false;
	}
	
	public Animatable flip(boolean x, boolean y){
		flipX = x;
		flipY = y;
		return this;
	}
	
	public Animation getAnimation(String name){
		return animations.get(name);
	}
	
	public Animatable setCurrentAnimation(String name){
		if (!currentAnimationString.equals(name.toUpperCase())) {
			stateTime = 0f;
			currentAnimation = animations.get(name);
			currentAnimationString = name;
		}
		return this;
	}
	
	public Animatable setCurrentAnimation(String name, boolean reset){
		if (reset) stateTime = 0f;
		return setCurrentAnimation(name);
	}

	public Animatable setCurrentAnimation(String name, boolean reset, boolean flipXaxis, boolean flipYaxis){	
		flip(flipXaxis,flipYaxis);
		return setCurrentAnimation(name,reset);
	}
	
	public boolean isFinished(){
		return currentAnimation.isAnimationFinished(stateTime);
	}

	public String getCurrentAnimation(){
		return currentAnimationString;
	}
	
	@Override
	public TextureRegion getRegion() {
		if (frame == null) frame = getKeyFrame();
		return frame;
	}
	
	private TextureRegion getKeyFrame(){
		stateTime += Gdx.graphics.getDeltaTime();
		frame = currentAnimation.getKeyFrame(stateTime);
		if (flipX) {
			if (!frame.isFlipX()) frame.flip(true, false);
		} else {
			if (frame.isFlipX()) frame.flip(true, false);
		}
		if (flipY) {
			if (!frame.isFlipY()) frame.flip(false, true);
		} else {
			if (frame.isFlipY())  frame.flip(false, true);
		}
		return frame;
	}
	
	
}
