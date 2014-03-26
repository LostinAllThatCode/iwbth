package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.texture.Sprite;
import org.gdesign.iwbth.game.texture.SpriteSheet;
import org.lwjgl.util.Rectangle;

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
		if (spritesheet == null){
			glColor3f(1, 0, 0);
	        glBegin(GL_QUADS);
		        glVertex2f(rect.getX(),  rect.getY());
		        glVertex2f(rect.getX(),  rect.getY() + rect.getHeight());
		        glVertex2f(rect.getX() + rect.getWidth(), rect.getY() + rect.getHeight());
		        glVertex2f(rect.getX() + rect.getWidth(), rect.getY());
	        glEnd();
		} else {
			Sprite sprite = spritesheet.getSprite(1);
			
	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sprite.getTexture());
	        glColor3f(1, 1, 1);
	        
	        int texX  = sprite.getX();
	        int texY  = sprite.getY();
	        int texX2 = sprite.getX() + sprite.getWidth();
	        int texY2 = sprite.getY() + sprite.getHeight();

	        glBegin(GL_QUADS);
		        glTexCoord2f(texX, texY);
		        glVertex2f(rect.getX(),rect.getY());
		        glTexCoord2f(texX, texY2);
		        glVertex2f(rect.getX(), rect.getY()+sprite.getHeight());
		        glTexCoord2f(texX2, texY2);
		        glVertex2f(rect.getX()+sprite.getWidth(), rect.getY()+sprite.getHeight());
		        glTexCoord2f(texX2, texY);
		        glVertex2f(rect.getX()+sprite.getWidth(), rect.getY());
	        glEnd();

	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
		}
	}
	
}
