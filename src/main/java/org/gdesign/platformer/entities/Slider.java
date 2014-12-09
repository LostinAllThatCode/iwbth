package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;
import org.luaj.vm2.LuaValue;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Slider extends Entity {

	public Slider(World world, int x, int y, String behaviour, float timeToStart) {
		super(world);
		this.addComponent(new Position(x,y));
		this.addComponent(new Behaviour(behaviour,this));
		this.addComponent(new Physics(world,x,y,100,20,BodyType.KinematicBody,Constants.CATEGORY_WORLD, Constants.MASK_WORLD,this));
		this.addToWorld();
		
		this.getComponent(Behaviour.class).call("init",LuaValue.valueOf(timeToStart));
	}

}
