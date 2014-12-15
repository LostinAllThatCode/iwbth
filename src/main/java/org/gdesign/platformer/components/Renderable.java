package org.gdesign.platformer.components;

import org.gdesign.games.ecs.BaseComponent;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Renderable extends BaseComponent {
	
	protected Texture texture;
	protected TextureRegion region;
	protected Vector2 offset;
	
	public Renderable(Texture t) {
		texture = t;
		region = new TextureRegion(texture);
		offset = new Vector2(0,0);
	}
	
	public Renderable(Texture t, int x, int y, int width, int height) {
		this(t);
		setRegion(x, y, width, height);
	}
	
	public Renderable setRegion(int x, int y, int width, int height){
		region.setRegion(x, y, width, height);
		return this;
	}
	
	public Renderable setOffset(float x, float y){
		offset.x = x;
		offset.y = y;
		return this;
	}
	
	public Vector2 getOffset(){
		return offset;
	}
	
	public Texture getTexture(){
		return texture;
	}
	
	public TextureRegion getRegion(){
		return region;
	}
	
	@Override
	public String toString() {
		return "w="+texture.getWidth()+",h="+texture.getHeight();
	}

}
