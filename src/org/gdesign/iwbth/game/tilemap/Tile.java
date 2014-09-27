package org.gdesign.iwbth.game.tilemap;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.entity.Entity;
import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.texture.Sprite;
import org.lwjgl.util.Rectangle;

public class Tile {
	
	private int x,y,tilesize,id,dX,dY,dir;
	private Rectangle crect;
	private Sprite sprite;
	private boolean solid,moveable;

	public Tile(int x, int y, int tilesize, Sprite sprite){
		this.x = x;
		this.y = y;
		this.dX = 0;
		this.dY = 0;
		this.dir = 1;
		this.tilesize = tilesize;
		this.sprite = sprite;
		this.crect = new Rectangle(x,y,tilesize,tilesize);
		if (sprite != null) {
			this.id = sprite.getId();
			if (this.id > 0 && this.id <= 20) {
				solid = true;
				moveable = false;
			}
			if (this.id > 60 && this.id <= 80) {
				solid = true;
				moveable = true;
			}
		} else {
			solid = false;
			moveable = false;
		}
	}
	
	public void update(){
	}
	
	public void move(long delta) {
		if (isMoveable()) {
			if (dX < -50) dir =  1;
			if (dX > 50) dir = -1;
			int speed = (int) (delta * .13f * dir);
			dX += speed;
			translate(speed, 0);
		}
		
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
		
		glPopMatrix();
	}
	
	public void translate(int dx, int dy){
		this.x += dx;
		this.y += dy;
		this.crect.translate(dx, dy);
	}
	
	public boolean intersects(Entity e){
		if (sprite != null){
			return (e.intersects(crect));
		} else return false;
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	public boolean isMoveable(){
		return moveable;
	}
	
	public int getX() { return x; }
	public int getY() { return y; }
	public int getId(){ return id;}

}
