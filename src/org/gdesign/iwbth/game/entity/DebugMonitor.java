package org.gdesign.iwbth.game.entity;

import org.gdesign.iwbth.game.main.Game;
import org.newdawn.slick.Color;

public class DebugMonitor extends Entity{
	
	//TODO: Debug purpose only!
	
	public boolean showDebug = false;
	public String debugDelta,debugFPS;
	public Player player;

	public DebugMonitor(int x, int y, int w, int h) {
		super(x, y, w, h);
		debugDelta = "";
		debugFPS = "";
		if (Game.showTileGrid) showDebug = true;
	}
	
	public void setPlayerObject(Player e){
		player = (Player) e;
	}

	
	@Override
	public void draw() {
		if (showDebug) {
			Game.drawString(5, 5, "x: " + player.rect.getX()+", y: "+ player.rect.getY()+", "
									+"x.vel: "+player.getVelocityX()+", y.vel: "+ player.getVelocityY()+", "
									+"j: "+player.getJumpCount()+", s: "+EntityManager.getShotCount(), Color.black);
			Game.drawString(5, 20, "state: "+player.getCurrentAnimationState()+", "
									+"frame: " +player.getCurrentAnimationFrame(), Color.black);
			
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
