package org.gdesign.iwbth.game.states;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.TextureManager;
import org.gdesign.iwbth.game.tilemap.MapManager;
import org.newdawn.slick.Color;

public class GameStateInit implements GameState {

	@Override
	public void init() {
		AudioManager.init();
		MapManager.init();
	}

	@Override
	public void handleEvents() {
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
		Game.drawBigString(15, 15, "LOADING DATA...", Color.orange);
		Game.drawBigString(200, 15, "PLEASE WAIT", Color.magenta);
		if (MapManager.initialized() && TextureManager.initialized() && AudioManager.initialized)
			GameStateManager.setState(GameStateManager.GAMESTATE_RUNNING);
	}

	@Override
	public void cleanUp() {
		
	}

}
