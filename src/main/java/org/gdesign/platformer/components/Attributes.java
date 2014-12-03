package org.gdesign.platformer.components;

import org.gdesign.games.ecs.BaseComponent;

public class Attributes extends BaseComponent {

	public static enum EntityType { DEFAULT, WORLD, PLAYER, ENEMY};
	
	public EntityType type;

	public Attributes(EntityType type){
		this.type = type;
	}
	
}
