package org.gdesign.platformer.scripting;

import org.gdesign.games.ecs.BaseComponent;
import org.gdesign.games.ecs.Entity;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public abstract class Scriptable extends BaseComponent{
	private boolean initialized=false;
	
	private Globals _G;
	private String _LUA = "";
	
	public Scriptable(String script, Entity e) {
		_G = JsePlatform.standardGlobals();
		setLuaScript(script, e);
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
	
	public Scriptable setLuaScript(String script, Entity e){
		_G.loadfile(script).call();
		_G.get("init").call(CoerceJavaToLua.coerce(e));
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
	
	@Override
	public String toString() {
		return _LUA;
	}
}
