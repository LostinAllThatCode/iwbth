package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.SpriteSheet;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class Entity {
	
	protected Rectangle rect = new Rectangle();
	protected SpriteSheet spritesheet = null;
	
	public Entity(int x, int y, int w, int h){
		rect.setBounds(x, y, w, h);
	}
	
	public Entity(int x, int y, SpriteSheet spritesheet){
		setLocation(x, y);
		this.spritesheet = spritesheet;
	}
	
	public void setLocation(int x, int y){
		this.rect.setLocation(x, y);
	}
	
	public void move(long delta){
		
	}
	
	public void draw(){
		glPushMatrix();
		
		if (Game.showTileGrid) {
			glEnable(GL_COLOR);
			
			glColor4f(1, 0, 0,.5f);
	        glBegin(GL_QUADS);
		        glVertex2f(rect.getX()-rect.getWidth()/2,  rect.getY() - rect.getHeight());
		        glVertex2f(rect.getX()-rect.getWidth()/2,  rect.getY());
		        glVertex2f(rect.getX() + rect.getWidth()/2, rect.getY());
		        glVertex2f(rect.getX() + rect.getWidth()/2, rect.getY() - rect.getHeight());
	        glEnd();
	        
	        glBegin(GL_LINE_LOOP);
		        glVertex2f(rect.getX()+rect.getWidth()/2,  rect.getY() - rect.getHeight());
		        glVertex2f(rect.getX()+rect.getWidth()/2+10,  rect.getY() - rect.getHeight()-10);
	        glEnd();      
	        
	    	Game.drawString(rect.getX()+rect.getWidth()/2+10,  rect.getY() - rect.getHeight()-25, 
	    			"["+String.valueOf(rect.getX()) +"/"+ String.valueOf(rect.getY())+"]" , Color.red);    
	        
	    	glColor3f(1, 1, 1);
	        glDisable(GL_COLOR);
		}
        glPopMatrix();
	}
	
	public Rectangle getIntersectionRectangle(){
		int eX = rect.getX()-rect.getWidth()/2;
		int eY = rect.getY()-rect.getHeight();
		return new Rectangle(eX,eY,rect.getWidth(),rect.getHeight());
	}
	
	public boolean intersects(Entity e){
		return getIntersectionRectangle().intersects(e.getIntersectionRectangle());
	}
	
	public void kill(){
		EntityManager.removeEntity(this);
	}
	
}
