package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.texture.Sprite;
import org.gdesign.iwbth.game.texture.SpriteSheet;

public class PlayerShot extends Entity {
	
	private static int instanceCounter=1;
	
	private final int SPRITE_ID = 14;
	private float speed = 0.6f;	
	private boolean hasCollided;
	private String collidedWith;
	private int id = 0;

	public PlayerShot(int x, int y, SpriteSheet spritesheet) {
		super(x, y, 5,5);
		this.id = instanceCounter++;
		this.spritesheet = spritesheet;
	}

	public void setSpawn(int x, int y, int dir){
		this.facing = dir;
		this.hasCollided = false;
		this.collidedWith = "none";
		setLocation(x, y);
	}
	
	@Override
	public void draw() {
		super.draw();
		glPushMatrix();
		
		Sprite sprite = spritesheet.getSprite(SPRITE_ID);
		
        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sprite.getTexture());
        glColor3f(1, 1, 1);
        int texX  = sprite.getX();
        int texY  = sprite.getY();
        int texX2 = sprite.getX() + sprite.getWidth();
        int texY2 = sprite.getY() + sprite.getHeight();
        
       
        glEnable(GL_TEXTURE_RECTANGLE_ARB);
	    glBegin(GL_QUADS);
	        glTexCoord2f(texX, texY);
	        glVertex2f(x-sprite.getWidth()/2, y-sprite.getHeight());
	        glTexCoord2f(texX, texY2);
	        glVertex2f(x-sprite.getWidth()/2, y);
	        glTexCoord2f(texX2, texY2);
	        glVertex2f(x+sprite.getWidth()/2, y);
	        glTexCoord2f(texX2, texY);
	        glVertex2f(x+sprite.getWidth()/2, y-sprite.getHeight());
        glEnd();
        glDisable(GL_TEXTURE_RECTANGLE_ARB);
    	glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
        
    	glPopMatrix();
	}
	
	@Override
	public void move(long delta) {
		if (this.rect.getX() >= Constants.GAME_WIDTH || this.rect.getX() <= 0) {
			hasCollided = true;
			EntityManager.remove(this);
			this.collidedWith = "world";
		}
		if (!hasCollided) {
			this.x += (speed*delta*facing);
			setLocation(x, y);
		}
	}
	
	public void checkCollsion(Entity e){
		if (rect.intersects(e.rect)) {
			hasCollided = true;
			EntityManager.remove(this);
			EntityManager.remove(e);
			this.collidedWith = e.getClass().getSimpleName();
		}
	}
	
	@Override
	public String toString() {
		return "["+id+"|x:"+x+"|y:"+y +  ((collidedWith.compareTo("none") == 0) ? "]" : "|collision:"+collidedWith);
	}
}
