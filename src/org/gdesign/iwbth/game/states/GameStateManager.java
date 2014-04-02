package org.gdesign.iwbth.game.states;

import java.util.ArrayList;

import org.lwjgl.opengl.Display;

public class GameStateManager {
	
	public static final int GAMESTATE_INIT	 		= 0;
	public static final int GAMESTATE_INTRO 		= 1;
	public static final int GAMESTATE_MENU 			= 2;
	public static final int GAMESTATE_RUNNING 		= 3;
	public static final int GAMESTATE_PAUSED 		= 4;
	public static final int GAMESTATE_OUTRO 		= 5;

	private static ArrayList<GameState> gamestates = new ArrayList<GameState>();
	
	private static GameState currentstate = null;	
	
	public static void init(){
		gamestates.add(new GameStateInit());
		gamestates.add(new GameStateIntro());
		gamestates.add(new GameStateMenu());
		gamestates.add(new GameStateRunning());
		gamestates.add(new GameStatePause());
		gamestates.add(new GameStateOutro());
		setState(0);
	}
	
	public static void setState(int state){
		currentstate = gamestates.get(state);
		currentstate.init();
	}
	
	public static void handleEvents(){
		currentstate.handleEvents();
	}
	
	public static void update(){	
		currentstate.update();	
	}
	
	public static void move(long delta){
		currentstate.move(delta);
	}
	
	public static void draw(){
		currentstate.draw();
	}
	
	public static void cleanUp(){
		Display.destroy();
	}
	
}
