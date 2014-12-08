package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.components.Renderable;
import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Upgrade extends Entity {

	public Upgrade(World world, int x, int y) {
		super(world);
		this.addComponent(new Physics(world, x, y, 32, 32,BodyType.KinematicBody, Constants.CATEGORY_UPGRADE, Constants.MASK_UPGRADE,this));
		this.addComponent(new Position());
		this.addComponent(new Behaviour("scripts/behaviour/upgrade.default.lua", this));
		this.addComponent(new Renderable("textures/entity/upgrade/morphMario.png"));
		this.addToWorld();
	}
	
	

}
