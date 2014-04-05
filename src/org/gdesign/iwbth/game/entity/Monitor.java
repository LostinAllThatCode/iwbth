package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.GL11.*;

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
			glPushMatrix();
			glEnable(GL_COLOR);
			
			glColor4f(0, 0, 0,.8f);
	        glBegin(GL_QUADS);
		        glVertex2f(x ,  y);
		        glVertex2f(x ,  y + Constants.GAME_HEIGHT/6);
		        glVertex2f(x + Constants.GAME_WIDTH, y + Constants.GAME_HEIGHT/6);
		        glVertex2f(x + Constants.GAME_WIDTH, y);
	        glEnd();
	        
	    	glColor3f(1, 1, 1);
	        glDisable(GL_COLOR);
			
			Game.drawString(x+5, y+5, "x: " 	+ EntityManager.getPlayer().getX()	+ ", "
								+ "y: "		+ EntityManager.getPlayer().getY()	+ ", "
								+ "x.vel: "	+ EntityManager.getPlayer().getVelocityX()	+ ", "
								+ "y.vel: "	+ EntityManager.getPlayer().getVelocityY()	+ ", "
								+ "facing: "	+ EntityManager.getPlayer().getFacing()	+ ", "
								+ "j: "		+ EntityManager.getPlayer().getJumpCount()	+ ", "
								+ "s: "		+ EntityManager.getShotCount()	, Color.white);
			
			Game.drawString(x+5, y+20, "state: " + EntityManager.getPlayer().getCurrentAnimationState()+", "
								+  "frame: " + EntityManager.getPlayer().getCurrentAnimationFrame(), Color.white);
			
			Game.drawString(x+5, y+35, "entity count: " + EntityManager.getEntityCount(), Color.white);
		}
	}
	
	public void setDebug(boolean b){
		showDebug = b;
	}
	
	public boolean isDebugEnabled(){
		return showDebug;
	}
}
