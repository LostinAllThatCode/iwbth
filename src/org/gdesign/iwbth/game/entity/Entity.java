package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.SpriteSheet;
import org.gdesign.iwbth.game.tilemap.MapManager;
import org.gdesign.iwbth.game.tilemap.Tile;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class Entity {
	
	protected int x,y,w,h,facing;
	protected float velX,velY;
	protected boolean isGrounded = false;
	protected Rectangle rect = new Rectangle();
	protected SpriteSheet spritesheet = null;
	
	public Entity(int x, int y, int w, int h){
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.facing = 1;
		rect.setBounds(x-w/2, y-h, w, h);
	}
	
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
		rect.setLocation(x-rect.getWidth()/2,y-rect.getHeight());
	}
	
	public void checkMapCollision(){	
		Tile t;
		t = MapManager.getTileConnectedToEntity(this, MapManager.LEFT);
		if (t != null)
			if (t.getId() > 0 && t.getId() < 20 && velX < 0){
				if (t.intersects(this)) velX = 0;
			}
		t = MapManager.getTileConnectedToEntity(this, MapManager.RIGHT);
		if (t != null)
			if (t.getId() > 0 && t.getId() < 20 && velX > 0) {
				if (t.intersects(this)) velX = 0;
			} 
		t = MapManager.getTileConnectedToEntity(this, MapManager.UP);
		if (t != null)
			if (t.getId() > 0 && t.getId() < 20 && velY < 0) {
				if (t.intersects(this)) velY *= -.15f;
			}
		t = MapManager.getTileConnectedToEntity(this, MapManager.DOWN);
		if (t != null)
			if (t.getId() > 0 && t.getId() < 20 && velY > 0) {
				if (t.intersects(this)){
					isGrounded = true;
					velY = t.getY()-this.getY();
				} 
			} else if (t.getId() == 0){
					isGrounded = false;
			}
			
		setLocation((int) (x+velX),(int) (y+velY));
	}
	
	public void move(long delta){}
	
	public void draw(){
		glPushMatrix();	
		if (Constants.DEBUG_HITBOX) {
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
	
	public void kill(){
		EntityManager.remove(this);
	}
	
	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return w;
	}

	public int getHeight(){
		return h;
	}
	
	public int getFacing(){
		return facing;
	}
	
	public boolean intersects(Rectangle r){
		return r.intersects(rect);
	}
	
	@Override
	public String toString() {
		return "["+getClass().getSimpleName()+":"+hashCode()+"|x:"+x+"|y:"+y+"]";
	}
	
}
