package org.gdesign.iwbth.game.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Controller;
import org.lwjgl.input.Controllers;

public class ControllerManager {
	
	//TODO: Just a rudimentary implementation.
	
	protected static Controller controller;
	
	protected static int ACTIVE_CONTROLLER = 0;
	
	public static String KEY_DOWN 		= "unbound";
	public static String KEY_UP 		= "unbound";
	public static String KEY_LEFT 		= "unbound";
	public static String KEY_RIGHT 		= "unbound";
	public static String KEY_JUMP 		= "unbound";
	public static String KEY_SHOOT 		= "unbound";
	
	protected static boolean isKeyJumpPressed 	= false;
	protected static boolean isKeyShootPressed 	= false;
	protected static boolean isKeyDownPressed	= false;
	protected static boolean isKeyUpPressed 	= false;	
	protected static boolean isKeyLeftPressed	= false;
	protected static boolean isKeyRightPressed 	= false;
			
	public static void pollInput(){
		
		isKeyLeftPressed = false;
		isKeyRightPressed = false;
		isKeyUpPressed = false;
		isKeyDownPressed = false;
		isKeyJumpPressed = false;
		isKeyShootPressed = false;
		
		if (controller != null) {
			controller.poll();
			if (controller.getPovX() == -1.0f) isKeyLeftPressed =  true;
			if (controller.getPovX() == 1.0f) isKeyRightPressed =  true; 
			if (controller.getPovY() == -1.0f) isKeyUpPressed =  true;
			if (controller.getPovY() == 1.0f) isKeyDownPressed =  true;
			for (int i=0; i<controller.getButtonCount()-1;i++){
				if (controller.getButtonName(i).equals(KEY_JUMP)) {
					if (controller.isButtonPressed(i)) isKeyJumpPressed =  true;
				}
				if (controller.getButtonName(i).equals(KEY_SHOOT)) {
					if (controller.isButtonPressed(i)) isKeyShootPressed =  true;
				};
			}		
		} 			
		if (Keyboard.isCreated()){
			Keyboard.poll();
			if (Keyboard.isKeyDown(Keyboard.KEY_UP)) 	isKeyUpPressed = true; 	
			if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) 	isKeyLeftPressed = true; 
			if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) 	isKeyDownPressed = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) isKeyRightPressed = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) isKeyJumpPressed = true;
			if (Keyboard.isKeyDown(Keyboard.KEY_R)) 	isKeyShootPressed = true;
		}
	}
	
	public static void bindKeys() throws LWJGLException{
		if (!Controllers.isCreated()) Controllers.create();
		
		System.out.print("Press up button now... ");
		KEY_UP = waitForButtonEventFromAnySource();
		System.out.print("Press left button now... ");
		KEY_LEFT = waitForButtonEventFromAnySource();
		System.out.print("Press down button now... ");
		KEY_DOWN = waitForButtonEventFromAnySource();
		System.out.print("Press right button now... ");
		KEY_RIGHT = waitForButtonEventFromAnySource();
		System.out.print("Press jump button now... ");
		KEY_JUMP = waitForButtonEventFromAnySource();
		System.out.print("Press shoot button now... ");
		KEY_SHOOT = waitForButtonEventFromAnySource();		
	}
	
	public static void listControllers() throws LWJGLException{
		if (!Controllers.isCreated()) Controllers.create();
		for (int i=0;i<Controllers.getControllerCount();i++){
			Controller c = Controllers.getController(i);
			System.out.println(c.getName());
		}
	}
	
	private static String waitForButtonEventFromAnySource(){
		String buttonName = "unbound";
		while (buttonName.equals("unbound")) {
			for (int i=0;i<Controllers.getControllerCount();i++){
				Controller c = Controllers.getController(i);
				c.poll();
				if (c.getPovX() == -1.0f) buttonName 	=  "DPAD_LEFT";
				if (c.getPovX() ==  1.0f) buttonName 	=  "DPAD_RIGHT";
				if (c.getPovY() == -1.0f) buttonName 	=  "DPAD_UP";
				if (c.getPovY() ==  1.0f) buttonName 	=  "DPAD_DOWN";
				for (int j=0; j<c.getButtonCount()-1;j++){
					if (c.isButtonPressed(j)) buttonName = c.getButtonName(j);
				}
				if (!buttonName.equals("unbound")) {
					controller = c;
					break;
				}
			}
		}
		System.out.println(buttonName+"("+controller.getName()+")");
		waitForReset();
		return buttonName;
	}
	
	public static void waitForReset(){
		boolean allKeysReleased=false;
		while (!allKeysReleased) {
			allKeysReleased = true;
			
			for (int i=0;i<Controllers.getControllerCount();i++){
				Controller c = Controllers.getController(i);
				c.poll();
				if (c.getPovX() != 0.0f) allKeysReleased = false; 
				if (c.getPovY() != 0.0f) allKeysReleased = false; 
				for (int j=0; j<c.getButtonCount()-1;j++){
					if (c.isButtonPressed(j)) allKeysReleased = false; 
				}
			}
		}
	}

	public static boolean isKeyDown(int key){
		if (Keyboard.isKeyDown(key)) return true;
		return false;
	}
	
	public static boolean isJumpPressed(){
		return isKeyJumpPressed;
	}
	
	public static boolean isShootPressed(){
		return isKeyShootPressed;
	}
	
	public static boolean isUpPressed(){
		return isKeyUpPressed;
	}
	
	public static boolean isDownPressed(){
		return isKeyDownPressed;
	}
	
	public static boolean isLeftPressed(){
		return isKeyLeftPressed;
	}
	
	public static boolean isRightPressed(){
		return isKeyRightPressed;
	}

	public static int getControllerId(){
		if (controller == null) return -1;
		return controller.getIndex();
	}
}
