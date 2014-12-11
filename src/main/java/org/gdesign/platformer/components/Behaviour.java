package org.gdesign.platformer.components;

import org.gdesign.games.ecs.Entity;
import org.gdesign.platformer.scripting.Scriptable;

public class Behaviour extends Scriptable {

	public Behaviour(Entity e, String script) {
		super(script, e);
	}
	
	public Behaviour(Entity e, String script, Object... obj){
		super(script, e);
		jcall("init",obj);
	}

	@Override
	public void runScript() {
		jcall("doBehaviour");
	}
	
	public void beginCollision(Entity target){
		jcall("beginCollision",target);
	}
	
	public void endCollision(Entity target){
		jcall("endCollision",target);
	}

}
