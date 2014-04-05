package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.Sprite;
import org.gdesign.iwbth.game.texture.SpriteSheet;
import org.gdesign.iwbth.game.tilemap.MapManager;

public class EnemyMe extends Entity {
	
	//TODO: Temporary testing enemy class
	
	private final int PLAYER_ANIMATION_IDLE = 1;
	private final int PLAYER_ANIMATION_WALK = 2;
	private final int PLAYER_ANIMATION_JUMP = 3;
	private final int PLAYER_ANIMATION_FALL = 4;
	
	private int currentAnimationState = PLAYER_ANIMATION_IDLE;
	private int currentFrame = 1;	
	
	private float velY = 0, velX = 0, gravity = 0.01f;
	//maxJumpSpeed = 10,currentJumpSpeed = 0;	
	
	private int facing=-1;
	
	private long lastFrame = Game.getTime();
	//private long lastShot  = Game.getTime();
	
	private int	 t = 0;

	public EnemyMe(int x, int y, SpriteSheet spritesheet) {
		super(x,y,8,16);
		this.spritesheet = spritesheet;
		currentAnimationState = PLAYER_ANIMATION_WALK;
	}	
	
	public void setRandomFacing(int number){
		if (number % 3 == 0) facing = 1; else facing = -1;
	}
	
	@Override
	public void move(long delta) {
		if (!isGrounded) {
			velY += gravity*delta;
			if (velY > 0) currentAnimationState = PLAYER_ANIMATION_FALL;
			if (velY < 0) currentAnimationState = PLAYER_ANIMATION_JUMP;
		} else {
			velY = 0;
			velX = .1f*delta*facing;
			currentAnimationState = PLAYER_ANIMATION_WALK;					
		}		
		checkMapCollision();
	}

	@Override
	public void checkMapCollision() {
		if (MapManager.getTileConnectedToEntity(this, MapManager.LEFT).getId() > 0 && velX < 0){
			if (MapManager.getTileConnectedToEntity(this, MapManager.LEFT).intersects(this)) facing = 1;
		}
		if (MapManager.getTileConnectedToEntity(this, MapManager.RIGHT).getId() > 0 && velX > 0) {
			if (MapManager.getTileConnectedToEntity(this, MapManager.RIGHT).intersects(this)) facing = -1;
		}
		if (MapManager.getTileConnectedToEntity(this, MapManager.UP).getId() > 0 && velY < 0) {
			if (MapManager.getTileConnectedToEntity(this, MapManager.UP).intersects(this)) velY *= -.15f;
		}
		if (MapManager.getTileConnectedToEntity(this, MapManager.DOWN).getId() > 0 && velY > 0) {
			isGrounded = true;
			velY = MapManager.getTileConnectedToEntity(this, MapManager.DOWN).getY()-this.getY();
		} else isGrounded = false;
			
		if (x > Constants.GAME_WIDTH || x < 0 || y > Constants.GAME_HEIGHT || y < 0) {
			kill();
		}
		setLocation((int) (x+velX),(int) (y+velY));
	}
	
	@Override
	public void draw() {		
		if (spritesheet != null) {
			glPushMatrix();
			Sprite sprite = spritesheet.getSprite(getAnimationFrame());
			
			glEnable(GL_TEXTURE_RECTANGLE_ARB);
	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sprite.getTexture());
	        glColor3f(0, 0, 0);
	        
	        int texX  = sprite.getX();
	        int texY  = sprite.getY();
	        int texX2 = sprite.getX() + sprite.getWidth();
	        int texY2 = sprite.getY() + sprite.getHeight();
	        
        	if (facing == 1){
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
        	} else {
    		    glBegin(GL_QUADS);
			    	glTexCoord2f(texX2, texY);
			    	glVertex2f(x-sprite.getWidth()/2, y-sprite.getHeight());
			        glTexCoord2f(texX2, texY2);
			        glVertex2f(x-sprite.getWidth()/2, y);
			        glTexCoord2f(texX, texY2);
			        glVertex2f(x+sprite.getWidth()/2, y);
			        glTexCoord2f(texX, texY);
			        glVertex2f(x+sprite.getWidth()/2, y-sprite.getHeight());
		        glEnd();
        	}
        	glDisable(GL_TEXTURE_2D);
        	glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
        	glPopMatrix();
		}
		super.draw();
	}

	private int getAnimationFrame(){
		long time = Game.getTime();
		t += (time - lastFrame);
		lastFrame = time;
		switch (currentAnimationState) {
			case PLAYER_ANIMATION_IDLE:
				if (t >= 150) {
					if (currentFrame >= 1 && currentFrame < 4) currentFrame++; else currentFrame = 1;
					lastFrame = time;
					t = 0;
				}
				break;
			case PLAYER_ANIMATION_WALK:
				if (t >= 50) {
					if (currentFrame >= 5 && currentFrame < 9) currentFrame++; else currentFrame = 5;
					lastFrame = time;
					t = 0;
				}
				break;
			case PLAYER_ANIMATION_JUMP:
				if (t >= 100) {
					if (currentFrame >= 10 && currentFrame < 11) currentFrame++; else currentFrame = 10;
					lastFrame = time;
					t = 0;
				}
				break;
			case PLAYER_ANIMATION_FALL:
				if (t >= 100) {
					if (currentFrame >= 12 && currentFrame < 13) currentFrame++; else currentFrame = 12;
					lastFrame = time;
					t = 0;
				}
				break;	
			default:
				return currentFrame;
		}
		return currentFrame;
	}
	
}
