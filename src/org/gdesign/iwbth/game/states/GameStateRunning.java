package org.gdesign.iwbth.game.states;

import org.gdesign.iwbth.game.audio.AudioManager;
import org.gdesign.iwbth.game.entity.DebugMonitor;
import org.gdesign.iwbth.game.entity.Entity;
import org.gdesign.iwbth.game.entity.Player;
import org.gdesign.iwbth.game.input.ControllerManager;
import org.gdesign.iwbth.game.main.Game;
import org.gdesign.iwbth.game.texture.TextureManager;
import org.gdesign.iwbth.game.tilemap.TileMap;
import org.lwjgl.input.Keyboard;


public class GameStateRunning implements GameState {
	
	private Entity temp;
	private DebugMonitor mon;
	
	private GameStateManager gsm;
	private TileMap currentMap;
	
	float vol = 1.0f;
	
	public GameStateRunning(GameStateManager gsm){
		this.gsm = gsm;
		this.init();
	}
	
	@Override
	public void init() {
		currentMap = new TileMap("sad");
		mon = new DebugMonitor(0, 0, Game.WIDTH, 150);
		temp = new Player(150, 495, TextureManager.getSpriteSheet(TextureManager.PLAYER));
		mon.setPlayerObject(temp);
	}
	
	@Override
	public void handleEvents() {
		ControllerManager.pollInput();
		if (Keyboard.isKeyDown(Keyboard.KEY_F10)){
			mon.setDebug(false);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F9)){
			mon.setDebug(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F11)) AudioManager.play(true);
		if (Keyboard.isKeyDown(Keyboard.KEY_F12)) AudioManager.stop();
	}

	@Override
	public void update(long delta) {
		currentMap.update(delta);
		temp.move(delta);
		mon.move(delta);
	}

	@Override
	public void draw() {
		currentMap.draw();
		mon.draw();
		temp.draw();
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}
}
