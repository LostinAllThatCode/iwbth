package org.gdesign.iwbth.game.tilemap;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.entity.Entity;
import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.Sprite;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class Tile {
	
	private int x,y,tilesize,id;
	private Rectangle crect;
	private Sprite sprite;
	public boolean highlight=false;

	public Tile(int x, int y, int tilesize, Sprite sprite){
		this.x = x;
		this.y = y;
		this.tilesize = tilesize;
		this.sprite = sprite;
		this.crect = new Rectangle(x,y,tilesize,tilesize);
		if (sprite != null) this.id = sprite.getId();
	}
	
	public void update(){
		highlight=false;
	}
	
	public void move(long delta) {
		// TODO Auto-generated method stub
		
	}
	
	public void draw(){
		glPushMatrix();
		if (sprite != null) {
			
			glEnable(GL_TEXTURE_RECTANGLE_ARB);
	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sprite.getTexture());
	       
	        int tx  = sprite.getX();
	        int ty  = sprite.getY();
	        int tx2 = sprite.getX() + sprite.getWidth();
	        int ty2 = sprite.getY() + sprite.getHeight();
	        
	        glColor3f(1,1,1);
	        
	        glBegin(GL_QUADS);
		        glTexCoord2f(tx, ty);
		        glVertex2f(x, y);
		        glTexCoord2f(tx, ty2);
		        glVertex2f(x, y+tilesize);
		        glTexCoord2f(tx2, ty2);
		        glVertex2f(x+tilesize, y+tilesize);
		        glTexCoord2f(tx2, ty);
		        glVertex2f(x+tilesize, y);
	        glEnd();

	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
	        glDisable(GL_TEXTURE_RECTANGLE_ARB);
		} else {
			glEnable(GL_COLOR);
			glColor3f(.7f,.85f,1);
		    glBegin(GL_QUADS);
		        glVertex2f(x, y);
		        glVertex2f(x, y+tilesize);
		        glVertex2f(x+tilesize, y+tilesize);
		        glVertex2f(x+tilesize, y);
	        glEnd();
	        glColor3f(1,1,1);
	        glDisable(GL_COLOR);
		}
		
		if (Constants.DEBUG_GRID){
			glEnable(GL_COLOR);
	        glColor3f(1, 1, 1);
	        glBegin(GL_LINE_LOOP);
	        	glVertex2f(x, y);
		        glVertex2f(x, y+tilesize);
		        glVertex2f(x+tilesize, y+tilesize);
		        glVertex2f(x+tilesize, y);
	        glEnd();
	        glDisable(GL_COLOR);
		}
		
		if (highlight) Game.drawString(x+tilesize/2, y+tilesize/2, "C", Color.red);
		
		glPopMatrix();
	}
	
	public void translate(int dx, int dy){
		this.x += dx;
		this.y += dy;
	}
	
	public boolean intersects(Entity e){
		if (sprite != null){
			return (e.intersects(crect));
		} else return false;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getId(){ return id;}

}
