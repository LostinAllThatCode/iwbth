package org.gdesign.iwbth.game.entity;

import org.gdesign.iwbth.game.input.ControllerManager;
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
	}
	
	public void setPlayerObject(Player e){
		player = (Player) e;
	}

	
	@Override
	public void draw() {
		if (showDebug) {
			Game.drawString(5, 25, "x				: "+player.rect.getX(), Color.white);
			Game.drawString(5, 35, "y				: "+player.rect.getY(), Color.white);
			Game.drawString(5, 45, "velX			: "+player.getVelocityX(), Color.white);
			Game.drawString(5, 55, "velY			: "+player.getVelocityY(), Color.white);
			Game.drawString(5, 65, "jump			: "+player.getJumpCount(), Color.white);
			Game.drawString(5, 75, "curretFrame		: "+player.getCurrentAnimationFrame(), Color.white);
			Game.drawString(5, 85, "curretAnim		: "+player.getCurrentAnimationState(), Color.white);
			
			
			Game.drawString(Game.WIDTH-100, 5, ControllerManager.getControllerId()+":CTRL_ID", Color.white);
			Game.drawString(Game.WIDTH-100, 15,ControllerManager.isUpPressed()+":UP", Color.white);
			Game.drawString(Game.WIDTH-100, 25,ControllerManager.isLeftPressed()+":LEFT", Color.white);
			Game.drawString(Game.WIDTH-100, 35,ControllerManager.isDownPressed()+":DOWN", Color.white);
			Game.drawString(Game.WIDTH-100, 45,ControllerManager.isRightPressed()+":RIGHT", Color.white);
			Game.drawString(Game.WIDTH-100, 55,ControllerManager.isShootPressed()+":SHOOT", Color.white);
			Game.drawString(Game.WIDTH-100, 65,ControllerManager.isJumpPressed()+":JUMP", Color.white);	
		}
	}
	
	public void setDebug(boolean b){
		showDebug = b;
	}
	
	public boolean isDebugEnabled(){
		return showDebug;
	}
}
