package org.gdesign.iwbth.game.states;

public interface GameState {
	
	void init();
	void handleEvents();
	void update(long delta);
	void draw();
	void cleanUp();

}
