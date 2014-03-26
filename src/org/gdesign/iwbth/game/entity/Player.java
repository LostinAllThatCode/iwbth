package org.gdesign.iwbth.game.entity;

import static org.lwjgl.opengl.ARBTextureRectangle.GL_TEXTURE_RECTANGLE_ARB;
import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.input.ControllerManager;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.Sprite;
import org.gdesign.iwbth.game.texture.SpriteSheet;

public class Player extends Entity {	

	private static final int PLAYER_ANIMATION_IDLE = 1;
	private static final int PLAYER_ANIMATION_WALK = 2;
	private static final int PLAYER_ANIMATION_JUMP = 3;
	private static final int PLAYER_ANIMATION_FALL = 4;
	
	private int currentAnimationState = PLAYER_ANIMATION_WALK;
	private int currentFrame = 1;	
	private int jumpc = 0;
	private int facing = 1;
	
	private boolean isJumping = false;
	
	private float velY = 0;
	private float velX = 0;
	private float gravity = 0.03f;
	private float maxJumpSpeed = 12;
	private float currentJumpSpeed = 0;	

	private long lastFrame = Game.getTime();
	private int	 t = 0;
	
	public Player(int x, int y, SpriteSheet spritesheet) {
		super(x, y, spritesheet);
	}
	
	@Override
	public void move(long delta) {	
		if (!isJumping){
			if (ControllerManager.isJumpPressed()){
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
		
		if (ControllerManager.isLeftPressed()) velX += -0.1f*delta;
		if (ControllerManager.isRightPressed()) velX += 0.1f*delta;
		
		if (isJumping){
			if (!ControllerManager.isJumpPressed() && jumpc == 0) jumpc=1;
			if (ControllerManager.isJumpPressed() && jumpc == 1){
				currentJumpSpeed += 0.1f*delta;
				if (currentJumpSpeed > maxJumpSpeed*0.75f) {
					currentJumpSpeed = maxJumpSpeed*0.75f;
					velY = -currentJumpSpeed;
					jumpc++;
				}
			}

			
			velY += gravity*delta;
			currentAnimationState = PLAYER_ANIMATION_JUMP;
			}
		
		if (velY > 0) {
			currentAnimationState = PLAYER_ANIMATION_FALL;
			
			if (isGrounded()) {
				currentAnimationState = PLAYER_ANIMATION_IDLE;
				currentJumpSpeed = 0;
				velY = 0;
				jumpc = 0;
				if (!ControllerManager.isJumpPressed()) isJumping = false;
			}	
		}

		if (velX != 0){
			if (velX < -3) {
				velX = -3;
				facing = -1;
			}
			else if (velX > 3) {
				velX = 3;
				facing = 1;
			}
		}
		
		if (!ControllerManager.isLeftPressed() && !ControllerManager.isRightPressed()) {
			velX = 0;
			if (!isJumping) currentAnimationState = PLAYER_ANIMATION_IDLE; 
		} else if (ControllerManager.isJumpPressed() && isJumping && isGrounded()){
				if (velX != 0) currentAnimationState = PLAYER_ANIMATION_WALK;
		} else {
			if (!isJumping) currentAnimationState = PLAYER_ANIMATION_WALK;
		}
		
		this.rect.translate((int) velX,(int) velY);
	}
	
	private boolean  isGrounded(){
		if (this.rect.getY() >= 495) {
			this.rect.setY(495);
			return true; 
		}
		else return false;
	}
	
	@Override
	public void draw() {
		if (spritesheet != null) {
			Sprite sprite = spritesheet.getSprite(getAnimationFrame());
			
	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, sprite.getTexture());
	        glColor3f(1, 1, 1);
	        int texX  = sprite.getX();
	        int texY  = sprite.getY();
	        int texX2 = sprite.getX() + sprite.getWidth();
	        int texY2 = sprite.getY() + sprite.getHeight();
	        
	        glPushMatrix();
	        
        	if (facing == 1){
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
        	} else {
    		    glBegin(GL_QUADS);
			    	glTexCoord2f(texX2, texY);
			        glVertex2f(rect.getX(),rect.getY());
			        glTexCoord2f(texX2, texY2);
			        glVertex2f(rect.getX(), rect.getY()+sprite.getHeight());
			        glTexCoord2f(texX, texY2);
			        glVertex2f(rect.getX()+sprite.getWidth(), rect.getY()+sprite.getHeight());
			        glTexCoord2f(texX, texY);
			        glVertex2f(rect.getX()+sprite.getWidth(), rect.getY());
		        glEnd();
        	}
	 
	        glPopMatrix();
	        glBindTexture(GL_TEXTURE_RECTANGLE_ARB, 0);
		}
	}
	
	public String getCurrentAnimationState(){
		switch (currentAnimationState) {
		case 1:
			return "PLAYER_ANIMATION_IDLE";
		case 2:
			return "PLAYER_ANIMATION_WALK";
		case 3:
			return "PLAYER_ANIMATION_JUMP";
		case 4:
			return "PLAYER_ANIMATION_JUMP";
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
		return Math.floor(velX);
	}
	
	public double getVelocityY(){
		return Math.floor(velY);
	}
	
	public int getJumpCount(){
		return jumpc;
	}
	
}
