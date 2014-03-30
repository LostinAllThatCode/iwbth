package org.gdesign.iwbth.game.states;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.input.ControllerManager;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.tilemap.TileMap;
import org.lwjgl.input.Keyboard;


public class GameStateRunning implements GameState {
	
	//TODO: Temporary testing environment. This will be changed dramatically.
	
	private TileMap currentMap;
	
	float vol = 1.0f;
	
	@Override
	public void init() {
		if (currentMap == null) {
			currentMap = new TileMap("sad");
		}
		
		EntityManager.cleanUp();
		EntityManager.init();
	}
	
	@Override
	public void handleEvents() {
		ControllerManager.pollInput();
		if (Keyboard.isKeyDown(Keyboard.KEY_F10)){
			EntityManager.getMonitor().setDebug(false);
			Game.showTileGrid = false;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F9)){
			EntityManager.getMonitor().setDebug(true);
			Game.showTileGrid = true;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)) AudioManager.play(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_F12)) AudioManager.stop();
	}

	@Override
	public void update(long delta) {
		currentMap.update(delta);
		EntityManager.update(delta);
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		currentMap.draw();
		EntityManager.draw();
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}
}
