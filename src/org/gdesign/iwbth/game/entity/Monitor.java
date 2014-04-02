package org.gdesign.iwbth.game.entity;

import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.main.Game;
import org.newdawn.slick.Color;

public class Monitor extends Entity{
	
	//TODO: Debug purpose only!
	
	public boolean showDebug = false;

	public Monitor(int x, int y, int w, int h) {
		super(x, y, w, h);
		if (Constants.DEBUG_PLAYER) showDebug = true;
	}
	
	@Override
	public void draw() {
		if (showDebug) {
			Game.drawString(50, 5, "x: " 	+ EntityManager.getPlayer().getX()	+ ", "
								+ "y: "		+ EntityManager.getPlayer().getY()	+ ", "
								+ "x.vel: "	+ EntityManager.getPlayer().getVelocityX()	+ ", "
								+ "y.vel: "	+ EntityManager.getPlayer().getVelocityY()	+ ", "
								+ "facing: "	+ EntityManager.getPlayer().getFacing()	+ ", "
								+ "j: "		+ EntityManager.getPlayer().getJumpCount()	+ ", "
								+ "s: "		+ EntityManager.getShotCount()	, Color.black);
			
			Game.drawString(50, 20, "state: " + EntityManager.getPlayer().getCurrentAnimationState()+", "
								+  "frame: " + EntityManager.getPlayer().getCurrentAnimationFrame(), Color.black);
			
			Game.drawString(50, 35, "entity count: " + EntityManager.getEntityCount(), Color.black);
		}
	}
	
	public void setDebug(boolean b){
		showDebug = b;
	}
	
	public boolean isDebugEnabled(){
		return showDebug;
	}
}
