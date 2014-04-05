package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.texture.Sprite;
import org.gdesign.iwbth.game.texture.SpriteSheet;
import org.gdesign.iwbth.game.tilemap.MapManager;
import org.gdesign.iwbth.game.tilemap.Tile;

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
		if (!hasCollided){
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
	     
	    	glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
	    	glDisable(GL_TEXTURE_RECTANGLE_ARB);
	    	glPopMatrix();
		}
	}
	
	@Override
	public void move(long delta) {
		velX = speed*delta*facing;
		checkMapCollision();
		if (!hasCollided) {
			this.x += velX;
			setLocation(x, y);
		}
	}
	
	@Override
	public void checkMapCollision() {
		Tile t;
		if (this.rect.getX() >= Constants.GAME_WIDTH || this.rect.getX() <= 0) {
			hasCollided = true;
			EntityManager.remove(this);
			this.collidedWith = "world";
			return;
		}
		t = MapManager.getTileConnectedToEntity(this, MapManager.LEFT);
		if (t != null)
			if (t.getId() > 0 && t.getId() < 20 && velX < 0){
				if (t.intersects(this)) {
					hasCollided = true;
					EntityManager.remove(this);
					this.collidedWith = t.getClass().getSimpleName();
					return;
				}
			}
		t = MapManager.getTileConnectedToEntity(this, MapManager.RIGHT);
		if (t != null)
		if (t.getId() > 0 && t.getId() < 20 && velX > 0) {
			if (t.intersects(this)) {
				hasCollided = true;
				EntityManager.remove(this);
				this.collidedWith = t.getClass().getSimpleName();
				return;
			}
		}
	}
	
	
	public void checkEntityCollsion(Entity e){
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
