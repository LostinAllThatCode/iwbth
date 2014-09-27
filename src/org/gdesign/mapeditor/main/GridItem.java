package org.gdesign.mapeditor.main;

import org.gdesign.iwbth.game.texture.Sprite;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

public class GridItem {
	
	private int x,y,tilesize;
	private Sprite sprite;

	public GridItem(int x, int y, int tilesize, Sprite sprite){
		this.x = x;
		this.y = y;
		this.tilesize = tilesize;
		this.sprite = sprite;
	}
	
	public void update(long delta){

	}
	
	public void translate(int dx, int dy){
		this.x += dx;
		this.y += dy;
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
	        
	        glColor3f(1, 1, 1);
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
	        glDisable(GL_TEXTURE_2D);
		} else {
			glEnable(GL_COLOR);
	        glColor4f(1, 1, 1,.2f);
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

}
