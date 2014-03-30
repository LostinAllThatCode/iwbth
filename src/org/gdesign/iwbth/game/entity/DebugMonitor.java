package org.gdesign.iwbth.game.entity;

import org.gdesign.iwbth.game.main.Game;
import org.newdawn.slick.Color;

public class DebugMonitor extends Entity{
	
	//TODO: Debug purpose only!
	
	public boolean showDebug = false;

	public DebugMonitor(int x, int y, int w, int h) {
		super(x, y, w, h);
		if (Game.showTileGrid) showDebug = true;
	}
	
	@Override
	public void draw() {
		if (showDebug) {
			Game.drawString(5, 5, "x: " 	+ EntityManager.getPlayer().getX()	+ ", "
								+ "y: "		+ EntityManager.getPlayer().getY()	+ ", "
								+ "x.vel: "	+ EntityManager.getPlayer().getVelocityX()	+ ", "
								+ "y.vel: "	+ EntityManager.getPlayer().getVelocityY()	+ ", "
								+ "j: "		+ EntityManager.getPlayer().getJumpCount()	+ ", "
								+ "s: "		+ EntityManager.getShotCount()	, Color.black);
			
			Game.drawString(5, 20, "state: " + EntityManager.getPlayer().getCurrentAnimationState()+", "
								+  "frame: " + EntityManager.getPlayer().getCurrentAnimationFrame(), Color.black);
		}
		Game.drawBigString(Game.WIDTH/2-20,50 , String.valueOf(EntityManager.getKillCounter()), Color.black);
	}
	
	public void setDebug(boolean b){
		showDebug = b;
	}
	
	public boolean isDebugEnabled(){
		return showDebug;
	}
}
