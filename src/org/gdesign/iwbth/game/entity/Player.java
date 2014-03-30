package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.input.ControllerManager;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.Sprite;
import org.gdesign.iwbth.game.texture.SpriteSheet;

public class Player extends Entity {	

	//TODO: Cleaning up variables. Optimizing jump detection. Collision detection not implemented yet. Just rudimentary.
	
	private final int PLAYER_ANIMATION_IDLE = 1;
	private final int PLAYER_ANIMATION_WALK = 2;
	private final int PLAYER_ANIMATION_JUMP = 3;
	private final int PLAYER_ANIMATION_FALL = 4;
	
	private int currentAnimationState = PLAYER_ANIMATION_WALK;
	private int currentFrame = 1;	
	private int jumpc = 0;
	private int facing = 1;
	
	private boolean isJumping = false;
	private boolean canShoot = true;
	
	private float velY = 0, velX = 0, gravity = 0.03f, maxJumpSpeed = 10,currentJumpSpeed = 0;	

	private long lastFrame = Game.getTime();
	private long lastShot  = Game.getTime();
	private int	 t = 0;
	
	public Player(int x, int y, SpriteSheet spritesheet) {
		super(x,y,8,17);
		this.spritesheet = spritesheet;
	}
	
	@Override
	public void move(long delta) {
		if (ControllerManager.isShootPressed()) shoot(); else canShoot = true;	
		
		if (!isGrounded()) {
			velY += gravity*delta;
			if (velY > 0) currentAnimationState = PLAYER_ANIMATION_FALL;
			if (velY < 0) currentAnimationState = PLAYER_ANIMATION_JUMP;
		} else {
			velY = 0;
			currentAnimationState = PLAYER_ANIMATION_IDLE;
		}
		
		if (!isJumping){
			if (ControllerManager.isJumpPressed()){
				if (currentJumpSpeed == 0) AudioManager.playFX(AudioManager.SOUND_FX_JUMP);
				currentJumpSpeed += 0.1f*delta;
				if (currentJumpSpeed > maxJumpSpeed) {
					currentJumpSpeed = maxJumpSpeed;
					velY = -currentJumpSpeed;
					isJumping = true;
				}
			}
			if (!ControllerManager.isJumpPressed() && currentJumpSpeed != 0){
				if (currentJumpSpeed > maxJumpSpeed) currentJumpSpeed = maxJumpSpeed;
				velY = -currentJumpSpeed;
				isJumping = true;
			}
		}
		
		if (ControllerManager.isLeftPressed()) velX = (float) Math.ceil(-0.2f*delta);
		if (ControllerManager.isRightPressed()) velX = (float) Math.floor(0.2f*delta);
		
		if (isJumping){
			if (!ControllerManager.isJumpPressed() && jumpc == 0) jumpc=1;
			if (ControllerManager.isJumpPressed() && jumpc == 1){
				currentJumpSpeed += 0.1f*delta;
				if (currentJumpSpeed > maxJumpSpeed*0.75f) {
					currentJumpSpeed = maxJumpSpeed*0.75f;
					velY = -currentJumpSpeed;
					jumpc++;
					AudioManager.playFX(AudioManager.SOUND_FX_JUMP);
				}
			}
		}

		if (!ControllerManager.isLeftPressed() && !ControllerManager.isRightPressed() 
				|| ControllerManager.isLeftPressed() && ControllerManager.isRightPressed()) {
			velX = 0;
			if (!isJumping) currentAnimationState = PLAYER_ANIMATION_IDLE; 
		} else if (ControllerManager.isJumpPressed() && isJumping && isGrounded()){
				if (velX != 0) currentAnimationState = PLAYER_ANIMATION_WALK;
		} else {
			facing = (int) Math.signum(velX);
			if (!isJumping) currentAnimationState = PLAYER_ANIMATION_WALK;
		}
		
		this.x += velX;
		this.y += velY;
		
		if (isGrounded() && jumpc > 0) {		
			currentJumpSpeed = 0;			
			velY = 0;
			jumpc = 0;
			if (!ControllerManager.isJumpPressed()) isJumping = false;					
		}
		
		setLocation(x, y);
	}
	
	private boolean  isGrounded(){
		if (y >= 520) {
			this.setLocation(x, 520);
			return true; 
		}
		else return false;
	}
	
	@Override
	public void draw() {
		
		if (spritesheet != null) {
			glPushMatrix();
			Sprite sprite = spritesheet.getSprite(getAnimationFrame());
			
			glEnable(GL_TEXTURE_RECTANGLE_ARB);
	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sprite.getTexture());
	        glColor3f(1, 1, 1);
	        
	        int texX  = sprite.getX();
	        int texY  = sprite.getY();
	        int texX2 = sprite.getX() + sprite.getWidth();
	        int texY2 = sprite.getY() + sprite.getHeight();
        
        	if (facing == 1){
			    glBegin(GL_QUADS);
			        glTexCoord2f(texX, texY);
			        glVertex2f(x-sprite.getWidth()/2,y-sprite.getHeight());
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
			    	glVertex2f(x-sprite.getWidth()/2,y-sprite.getHeight());
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
	
	public String getCurrentAnimationState(){
		switch (currentAnimationState) {
		case 1:
			return "idle";
		case 2:
			return "walk";
		case 3:
			return "jump";
		case 4:
			return "fall";
		default:
			return "NOT DEFINED YET";
		}
	}
	
	public int getCurrentAnimationFrame(){
		return this.currentFrame;
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

	public double getVelocityX(){
		return velX;
	}
	
	public double getVelocityY(){
		return velY;
	}
	
	public int getJumpCount(){
		return jumpc;
	}
	
	private void shoot(){
		long time = Game.getTime();
		if (time - lastShot >= 150 && canShoot) {
			lastShot = time;
			canShoot = false;
			EntityManager.addShot(x, y-this.rect.getHeight()/2, facing);
			AudioManager.playFX(AudioManager.SOUND_FX_SHOT);
		}
	}
	
}
