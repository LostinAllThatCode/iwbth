package org.gdesign.iwbth.game.states;

import static org.lwjgl.opengl.GL11.*;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.input.ControllerManager;
import org.gdesign.iwbth.game.tilemap.MapManager;
import org.lwjgl.input.Keyboard;


public class GameStateRunning implements GameState {
	
	//TODO: Temporary testing environment. This will be changed dramatically.
	
	float vol = 1.0f;
	
	@Override
	public void init() {	
		EntityManager.cleanUp();
		EntityManager.init();
	}
	
	@Override
	public void handleEvents() {
		ControllerManager.pollInput();
		if (Keyboard.isKeyDown(Keyboard.KEY_F10)){
			EntityManager.getMonitor().setDebug(false);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F9)){
			EntityManager.getMonitor().setDebug(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)) AudioManager.play(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_F12)) AudioManager.stop();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_K)) EntityManager.getPlayer().kill();
	}

	@Override
	public void update() {
		MapManager.update();
		EntityManager.update();
	}
	
	@Override
	public void move(long delta) {
		MapManager.move(delta);
		EntityManager.move(delta);
	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT);
		MapManager.draw();
		EntityManager.draw();
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}
}
