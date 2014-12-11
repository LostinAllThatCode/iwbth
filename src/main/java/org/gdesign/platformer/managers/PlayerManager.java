package org.gdesign.platformer.managers;

import org.gdesign.games.ecs.BaseManager;
import org.gdesign.games.ecs.Entity;

public class PlayerManager extends BaseManager {
	
	Entity playerEntity;
	
	public PlayerManager setPlayer(Entity e){
		playerEntity = e;
		return this;
	}
	
	public Entity getPlayer(){
		return playerEntity;
	}
	
	@Override
	public void added(Entity e) {}

	@Override
	public void changed(Entity e) {}

	@Override
	public void removed(Entity e) {
		if (e.equals(playerEntity)) playerEntity = null;
	}

	@Override
	public void enabled(Entity e) {}

	@Override
	public void disabled(Entity e) {}

}
