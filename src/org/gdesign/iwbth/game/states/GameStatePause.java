package org.gdesign.iwbth.game.states;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.main.Game;
import org.newdawn.slick.Color;


public class GameStatePause implements GameState {
	
	public GameStatePause(){
		this.init();
	}
	
	@Override
	public void init() {
	}

	@Override
	public void handleEvents() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Game.drawBigString(Game.WIDTH/2-300, 300, "DEAD SOOOO DEAD", Color.blue);
		Game.drawBigString(Game.WIDTH/2-300, 100, "KILLS: "+EntityManager.getKillCounter(), Color.orange);
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub

	}

}
