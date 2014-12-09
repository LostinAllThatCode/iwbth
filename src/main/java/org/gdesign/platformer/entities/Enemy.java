package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Enemy extends Entity {

	public Enemy(World world, int x, int y, String behaviour, String texture) {
		super(world);
		this.addComponent(new Position(x,y));
		this.addComponent(new Behaviour(behaviour,this));
		this.addComponent(new Physics(world,x,y,12,52,BodyType.DynamicBody,Constants.CATEGORY_ENEMY, Constants.MASK_ENEMY,this));
		this.addComponent(new Animatable(texture));
		this.addToWorld();
	}

}
