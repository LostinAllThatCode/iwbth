package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.components.Physics.PhysicType;
import org.gdesign.platformer.components.Renderable;

public class Upgrade extends Entity {

	public Upgrade(World world, int x, int y) {
		super(world);
		this.addComponent(new Physics(world, x, y, 32, 32, PhysicType.UPGRADE).setUserdata(this));
		this.addComponent(new Position());
		this.addComponent(new Behaviour("scripts/behaviour/upgrade.default.lua", this));
		this.addComponent(new Renderable("textures/entity/upgrade/morphMario.png"));
		this.addToWorld();
	}
	
	

}
