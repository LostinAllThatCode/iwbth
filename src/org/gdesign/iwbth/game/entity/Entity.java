package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.SpriteSheet;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class Entity {
	
	protected int x,y;
	protected Rectangle rect = new Rectangle();
	protected SpriteSheet spritesheet = null;
	
	public Entity(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		rect.setBounds(x-w/2, y-h, w, h);
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
		rect.setLocation(x-rect.getWidth()/2,y-rect.getHeight());
	}
	
	public void move(long delta){
	}
	
	public void draw(){
		glPushMatrix();
		
		if (Game.showTileGrid) {
			glEnable(GL_COLOR);
			
			glColor4f(1, 0, 0,.5f);
	        glBegin(GL_QUADS);
		        glVertex2f(rect.getX(),  rect.getY());
		        glVertex2f(rect.getX(),  rect.getY() + rect.getHeight());
		        glVertex2f(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
		        glVertex2f(rect.getX() + rect.getWidth(), rect.getY());
	        glEnd();
	        
	        glBegin(GL_LINE_LOOP);
		        glVertex2f(rect.getX(),  rect.getY()+rect.getHeight());
		        glVertex2f(rect.getX()-5,  rect.getY()+rect.getHeight()+5);
	        glEnd();      
	        
	    	Game.drawString(x-50,  y+7, 
	    			"["+String.valueOf(x) +"/"+ String.valueOf(y)+"]" , Color.red);    
	        
	    	glColor3f(1, 1, 1);
	        glDisable(GL_COLOR);
		}
        glPopMatrix();
	}
	
	protected void kill(){
		EntityManager.removeEntity(this);
	}
	
	protected int getX(){
		return x;
	}

	protected int getY(){
		return y;
	}
	
}
