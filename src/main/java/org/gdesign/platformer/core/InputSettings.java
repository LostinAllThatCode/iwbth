package org.gdesign.platformer.core;

import com.badlogic.gdx.Input;

public class InputSettings {

	/**
	 * Keyboard and Controller settings
	 */
	public static enum InputType { KEYBOARD, CONTROLLER }
	
	public static InputType inputType = InputType.CONTROLLER;
	
	public static int KEY_LEFT 	= Input.Keys.LEFT;		
	public static int KEY_RIGHT = Input.Keys.RIGHT;		
	public static int KEY_UP 	= Input.Keys.UP;		
	public static int KEY_DOWN 	= Input.Keys.DOWN;		
	public static int KEY_JUMP 	= 0;//Input.Keys.SPACE;		
	public static int KEY_A 	= Input.Keys.R;			
	public static int KEY_B 	= Input.Keys.F;
	public static int KEY_X 	= Input.Keys.V;
	public static int KEY_Y 	= Input.Keys.D;	
	
	public static int CTRL_ID 	= 0;
	public static int CTRL_POV 	= 0;
	
	
}
