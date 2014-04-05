package org.gdesign.iwbth.game.states;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.main.Constants;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.tilemap.MapManager;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;


public class GameStatePause implements GameState {

	@Override
	public void init() {
	}

	@Override
	public void handleEvents() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			GameStateManager.setState(0);
			AudioManager.stop();
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)){
			AudioManager.play(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F12)){
			AudioManager.stop();
		}
	}

	@Override
	public void update() {
	}
	
	@Override
	public void move(long delta) {
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		MapManager.draw();
		EntityManager.draw();
		Game.drawf2String(Constants.GAME_WIDTH/2-150, Constants.GAME_HEIGHT/2-50, "LOL YOU DIED", Color.white);
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}

}
