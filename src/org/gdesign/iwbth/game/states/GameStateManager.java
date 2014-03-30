package org.gdesign.iwbth.game.states;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

public class GameStateManager {
	
	public static final int GAMESTATE_INTRO 		= 0;
	public static final int GAMESTATE_MENU 			= 1;
	public static final int GAMESTATE_RUNNING 		= 2;
	public static final int GAMESTATE_PAUSED 		= 3;
	public static final int GAMESTATE_OUTRO 		= 4;

	private ArrayList<GameState> gamestates = new ArrayList<GameState>();
	
	private GameState currentstate = null;
	
	public GameStateManager(){
		gamestates.add(new GameStateRunning());
		gamestates.add(new GameStatePause());
		setState(0);
	}
	
	public void setState(int state){
		this.currentstate = gamestates.get(state);
		this.currentstate.init();
	}
	
	public void handleEvents(){
		this.currentstate.handleEvents();
	}
	
	public void update(long delta){	
		this.currentstate.update(delta);	
	}
	
	public void draw(){
		this.currentstate.draw();
	}
	
	public void cleanUp(){
		Display.destroy();
	}
	
}
