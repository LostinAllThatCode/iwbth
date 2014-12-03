package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Controller;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;

public class Player extends Entity{
	
	public Player(World world, int x, int y) {
		super(world);
		this.addComponent(new Position(x,y));
		this.addComponent(new Controller("scripts/control/default.lua",this));
		this.addComponent(new Physics(world,x,y,12,52,Constants.CATEGORY_PLAYER).setUserdata(this));
		this.addComponent(new Animatable("textures/entity/player/player.png"));
		this.addToWorld();
	}

}
