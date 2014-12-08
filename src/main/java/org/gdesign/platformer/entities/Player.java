package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends Entity{
	
	public Player(World world, int x, int y) {
		super(world);
		this.addComponent(new Position(x,y));
		this.addComponent(new Behaviour("scripts/control/default.lua",this));
		this.addComponent(new Physics(world,x,y,12,45,BodyType.DynamicBody,Constants.CATEGORY_PLAYER,Constants.MASK_PLAYER,this));
		this.addComponent(new Animatable("textures/entity/player/player.png").setOffset(0, -5));
		this.addToWorld();
		System.out.println(this.getComponent(Physics.class).getBody().getFixtureList().get(0));
	}

}
