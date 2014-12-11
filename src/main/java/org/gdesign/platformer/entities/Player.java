package org.gdesign.platformer.entities;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player extends Entity{
	
	public Player(World world, int x, int y, Texture texture, String json) {
		super(world);
		this.addComponent(new Position(x,y));
		this.addComponent(new Behaviour(this,"scripts/behaviour/player.default.lua"));
		this.addComponent(new Physics(world,x,y,12,45,BodyType.DynamicBody,Constants.CATEGORY_PLAYER,Constants.MASK_PLAYER,this));
		this.addComponent(new Animatable(texture, json).setOffset(0, -5));
		this.addToWorld();
	}

}
