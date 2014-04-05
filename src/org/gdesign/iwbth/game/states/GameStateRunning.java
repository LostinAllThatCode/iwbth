package org.gdesign.iwbth.game.states;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.input.ControllerManager;
import org.gdesign.iwbth.game.main.Constants;
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
			Constants.DEBUG_PLAYER = false;
			EntityManager.getMonitor().setDebug(false);

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F9)){
			Constants.DEBUG_PLAYER = true;
			EntityManager.getMonitor().setDebug(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)) AudioManager.play(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_F12)) AudioManager.stop();
		
		if (Keyboard.isKeyDown(Keyboard.KEY_K)) EntityManager.getPlayer().kill();;
		
		/*
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) EntityManager.getPlayer().translate(0, -1);
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) EntityManager.getPlayer().translate(0, 1);
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) EntityManager.getPlayer().translate(-1, 0);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) EntityManager.getPlayer().translate(1, 0);
		*/
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
		glClear(GL_COLOR_BUFFER_BIT);  //| GL_DEPTH_BUFFER_BIT
		MapManager.draw();
		EntityManager.draw();
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}
}
