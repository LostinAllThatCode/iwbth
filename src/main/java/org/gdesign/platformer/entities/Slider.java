package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Slider extends Entity {

	public Slider(World world, int x, int y, String behaviour) {
		super(world);
		this.addComponent(new Position(x,y));
		this.addComponent(new Behaviour(behaviour,this));
		this.addComponent(new Physics(world,x,y,100,20,BodyType.KinematicBody,Constants.CATEGORY_OBJECT, Constants.MASK_OBJECT,this));
		this.addToWorld();
	}

}
