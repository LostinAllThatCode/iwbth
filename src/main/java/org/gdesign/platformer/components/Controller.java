package org.gdesign.platformer.components;

import org.gdesign.games.ecs.Entity;
import org.gdesign.platformer.scripting.Scriptable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controllers;

public class Controller extends Scriptable {
	/**
	 * Keyboard and Controller settings
	 */
	public static int KEY_LEFT 	= Input.Keys.LEFT;		public static int CTRL_ID 	= 0;	
	public static int KEY_RIGHT = Input.Keys.RIGHT;		public static int CTRL_POV 	= 0;
	public static int KEY_UP 	= Input.Keys.UP;		public static int CTRL_A 	= 0;
	public static int KEY_DOWN 	= Input.Keys.DOWN;		public static int CTRL_B 	= 1;	
	public static int KEY_JUMP 	= Input.Keys.SPACE;		public static int CTRL_X 	= 2;	
	public static int KEY_A 	= Input.Keys.R;			public static int CTRL_Y 	= 3;
	public static int KEY_B 	= Input.Keys.F;
	public static int KEY_X 	= Input.Keys.V;
	public static int KEY_Y 	= Input.Keys.D;	
	
	public Controller(String script, Entity e) {
		super(script, e);
	}
	
	public void runScript(){
		call("doControls");
	}
	
	public boolean isKeyDown(int keycode){
		if (Gdx.input.isKeyPressed(keycode)) return true;
		return false;
	}
	
	public boolean isKeyUp(int keycode){
		if (Gdx.input.isKeyPressed(keycode)) return false;
		return true;
	}
	
	public boolean isButtonPressed(int button){
		if (Controllers.getControllers().size == 0) return false;
		if (Controllers.getControllers().get(CTRL_ID).getButton(button)) return true;
		return false;
	}
	
	public boolean isPoVDown(String PoVdirection){
		if (Controllers.getControllers().size == 0) return false;
		if (Controllers.getControllers().get(CTRL_ID).getPov(CTRL_POV).toString().equals(PoVdirection)) return true;
		return false;
	}
	
	public String getPoV(){
		if (Controllers.getControllers().size == 0) return null;
		return Controllers.getControllers().get(CTRL_ID).getPov(0).toString();
	}
	
}
