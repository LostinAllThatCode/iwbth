package org.gdesign.iwbth.game.states;

public interface GameState {
		
	void init();
	void handleEvents();
	void update();
	void move(long delta);
	void draw();
	void cleanUp();

}
