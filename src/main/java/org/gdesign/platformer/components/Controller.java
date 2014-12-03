package org.gdesign.platformer.components;

import org.gdesign.games.ecs.Entity;
import org.gdesign.platformer.core.InputControls;
import org.gdesign.platformer.scripting.Scriptable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;

public class Controller extends Scriptable {
	
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
		if (Controllers.getControllers().get(InputControls.CTRL_ID).getButton(button)) return true;
		return false;
	}
	
	public boolean isPoVDown(String PoVdirection){
		if (Controllers.getControllers().size == 0) return false;
		if (Controllers.getControllers().get(InputControls.CTRL_ID).getPov(InputControls.CTRL_POV).toString().equals(PoVdirection)) return true;
		return false;
	}
	
	public String getPoV(){
		if (Controllers.getControllers().size == 0) return null;
		return Controllers.getControllers().get(InputControls.CTRL_ID).getPov(0).toString();
	}
	
}
