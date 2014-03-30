package org.gdesign.iwbth.game.states;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

import org.gdesign.iwbth.game.entity.EntityManager;
import org.gdesign.iwbth.game.main.Game;
import org.lwjgl.input.Keyboard;
import org.newdawn.slick.Color;


public class GameStatePause implements GameState {

	@Override
	public void init() {
	}

	@Override
	public void handleEvents() {
		if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
			Game.GSM.setState(0);
		}

	}

	@Override
	public void update(long delta) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Game.drawBigString(Game.WIDTH/2-100, 100, "KILLS: "+EntityManager.getKillCounter(), Color.orange);
		Game.drawBigString(Game.WIDTH/2-200, Game.HEIGHT-200, "ENTER TO RESTART", Color.blue);
		
	}

	@Override
	public void cleanUp() {
		// TODO Auto-generated method stub
	}

}
