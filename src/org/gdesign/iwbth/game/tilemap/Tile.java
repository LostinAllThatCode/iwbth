package org.gdesign.iwbth.game.tilemap;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.Sprite;

public class Tile {
	
	private int x,y,tilesize;
	private Sprite sprite;

	public Tile(int x, int y, int tilesize, Sprite sprite){
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
		if (sprite != null) {
	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sprite.getTexture());
	
	        int tx  = sprite.getX();
	        int ty  = sprite.getY();
	        int tx2 = sprite.getX() + sprite.getWidth();
	        int ty2 = sprite.getY() + sprite.getHeight();
	
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
		}
		if (Game.showTileGrid){
	        glColor3f(1, 1, 1);
	        glBegin(GL_LINE_LOOP);
	        	glVertex2f(x, y);
		        glVertex2f(x, y+tilesize);
		        glVertex2f(x+tilesize, y+tilesize);
		        glVertex2f(x+tilesize, y);
	        glEnd();
		}

	}
}
