package org.gdesign.platformer.components;

import org.gdesign.games.ecs.Entity;
import org.gdesign.platformer.scripting.Scriptable;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class Behaviour extends Scriptable {
		

	public Behaviour(String script, Entity e) {
		super(script, e);
	}

	@Override
	public void runScript() {
		call("doBehaviour");
	}
	
	public void handleCollision(Entity target){
		call("handleCollision",CoerceJavaToLua.coerce(target));
	}

}
