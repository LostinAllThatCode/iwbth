package org.gdesign.platformer.components;

import org.gdesign.games.ecs.BaseComponent;
import org.gdesign.platformer.core.Constants;

public class Position extends BaseComponent {
	
	// Real physics world position in meters
	public float x, y, px, py, r, pr;
	public float xPixel, yPixel, pxPixel, pyPixel, rDeg, prDeg;

	public Position(){
		setPosition(0, 0);
	}
	
	public Position(float x, float y){
		setPosition(x, y);
	}
	
	public Position setRotation(float rad){
		this.pr = this.r;
		this.r = rad;
		this.rDeg = (float) Math.toDegrees(this.r);
		this.prDeg = (float) Math.toDegrees(this.pr);
		return this;		
	}
	
	public Position setPosition(float x, float y){
		this.px = x; 	
		this.py = y;
		this.x = x;	
		this.y = y;
		this.xPixel = this.x * Constants.BOX_TO_WORLD;
		this.yPixel = this.y * Constants.BOX_TO_WORLD;
		this.pxPixel = this.px * Constants.BOX_TO_WORLD;
		this.pyPixel = this.py * Constants.BOX_TO_WORLD;
		return this;
	}
}
