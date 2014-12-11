package org.gdesign.platformer.scripting;

import org.gdesign.games.ecs.BaseComponent;
import org.gdesign.games.ecs.Entity;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.core.InputSettings;
import org.gdesign.platformer.core.InputSettings.InputType;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;

public abstract class Scriptable extends BaseComponent{
	private boolean initialized=false;
	
	private Globals _G;
	private String _LUA = "";
	private Entity entity;
	
	public Scriptable(String script, Entity e) {
		_G = JsePlatform.standardGlobals();
		entity = e;
		setScript(script);
	}
	
	/**
	 * Function to implement for scripting purpose. Should call any lua function in your script file.
	 * Lua script file function:
	 * 
	 * 		function testFunction(a,b)
	 * 			return a + b
	 * 		end
	 * 
	 * Ex.: Varargs args = jcall("testFunction",1,3);
	 * 		System.out.println(args.arg(1));
	 */
	public abstract void runScript();
	
	protected Scriptable init(String script){
		_G.loadfile(script).call();
		_G.set("INPUT", CoerceJavaToLua.coerce(InputSettings.class));
		_G.set("CONST", CoerceJavaToLua.coerce(Constants.class));
		_G.set("GDX", CoerceJavaToLua.coerce(Gdx.class));
		_G.set("JOINT", CoerceJavaToLua.coerce(JointType.class));
		_G.set("jmath",CoerceJavaToLua.coerce(Math.class));
		_G.set("_self", CoerceJavaToLua.coerce(entity));
		_G.set("_world", CoerceJavaToLua.coerce(entity.getWorld()));
		_G.set("component",new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue arg0) {
				try {
					return CoerceJavaToLua.coerce(Class.forName("org.gdesign.platformer.components."+arg0));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		});
		_G.set("system",new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue arg0) {
				try {
					return CoerceJavaToLua.coerce(Class.forName("org.gdesign.platformer.systems."+arg0));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		});
		_G.set("manager",new OneArgFunction() {
			@Override
			public LuaValue call(LuaValue arg0) {
				try {
					return CoerceJavaToLua.coerce(Class.forName("org.gdesign.platformer.managers."+arg0));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					return null;
				}
			}
		});
		_G.set("isKeyDown",new OneArgFunction() {		
			@Override
			public LuaValue call(LuaValue arg0) {
				int keycode = arg0.toint();
				boolean retVal = false;
				if (InputSettings.inputType == InputType.KEYBOARD){
					if (Gdx.input.isKeyPressed(keycode)) retVal = true;
				} else if (InputSettings.inputType == InputType.CONTROLLER){
					if (Controllers.getControllers().size == 0) System.err.println("No controller with id " + InputSettings.CTRL_ID +" active");
					else if (keycode == InputSettings.KEY_UP) retVal = Controllers.getControllers().get(InputSettings.CTRL_ID).getPov(InputSettings.CTRL_POV).equals(PovDirection.north);
					else if (keycode == InputSettings.KEY_LEFT) retVal = Controllers.getControllers().get(InputSettings.CTRL_ID).getPov(InputSettings.CTRL_POV).equals(PovDirection.west);
					else if (keycode == InputSettings.KEY_RIGHT) retVal = Controllers.getControllers().get(InputSettings.CTRL_ID).getPov(InputSettings.CTRL_POV).equals(PovDirection.east);
					else if (keycode == InputSettings.KEY_DOWN) retVal = Controllers.getControllers().get(InputSettings.CTRL_ID).getPov(InputSettings.CTRL_POV).equals(PovDirection.south);
					else if (Controllers.getControllers().get(InputSettings.CTRL_ID).getButton(keycode)) retVal = true;
				}
				return LuaValue.valueOf(retVal);
			}
		});
		initialized = true;
		_LUA = script;
		return this;
	}
	
	public Varargs jcall(String function, Object... args){
		if (initialized) {
			LuaValue[] values = new LuaValue[args.length];
			int i=0;
			for (Object o : args){
				values[i++] = CoerceJavaToLua.coerce(o);
			}
			return _G.get(function).invoke(values);
		} else {
			System.err.println("No lua script file is attached. Use .setLuaScript()");
			return null;
		}
	}	
	
	public Varargs call(String function, LuaValue... args){
		if (initialized) {
			return _G.get(function).invoke(args);
		} else {
			System.err.println("No lua script file is attached. Use .setLuaScript()");
			return null;
		}
	}
	
	public Varargs call(String function){
		return call(function,LuaValue.NILS);
	}
	
	public void setScript(String lua){
		_LUA = lua;
		init(_LUA);
	}
	
	public void reload(){
		init(_LUA);
	}
	
	@Override
	public String toString() {
		return _LUA;
	}
}
