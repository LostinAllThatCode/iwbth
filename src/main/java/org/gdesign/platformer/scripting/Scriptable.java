package org.gdesign.platformer.scripting;

import org.gdesign.games.ecs.BaseComponent;
import org.gdesign.games.ecs.Entity;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.core.InputControls;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

import com.badlogic.gdx.Gdx;

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
	 * Ex.: Varargs args = call("testFunction",1,3);
	 * 		System.out.println(args.arg(1));
	 */
	public abstract void runScript();
	
	protected Scriptable init(String script){
		_G.loadfile(script).call();
		_G.set("_input", CoerceJavaToLua.coerce(InputControls.class));
		_G.set("_const", CoerceJavaToLua.coerce(Constants.class));
		_G.set("_self", CoerceJavaToLua.coerce(entity));
		_G.set("_world", CoerceJavaToLua.coerce(entity.getWorld()));
		_G.set("_gdx", CoerceJavaToLua.coerce(Gdx.class));
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
		initialized = true;
		_LUA = script;
		return this;
	}
	
	protected Varargs call(String function, LuaValue... args){
		if (initialized) {
			return _G.get(function).invoke(args);
		} else {
			System.err.println("No lua script file is attached. Use .setLuaScript()");
			return null;
		}
	}
	
	protected Varargs call(String function){
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
