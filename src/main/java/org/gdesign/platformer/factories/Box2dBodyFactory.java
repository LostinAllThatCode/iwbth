package org.gdesign.platformer.factories;


import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Box2dBodyFactory {
	
	private static World world;
	
	public static void initialize(World simulationWorld) {
		world = simulationWorld;
	}
	
	public static Body createBody(BodyType type, float x, float y, boolean fixedRotation){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = type;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX,
							 y * Constants.WORLD_TO_BOX);
		bodyDef.fixedRotation = true;
		
		Body b = world.createBody(bodyDef);
		
		return b;
	}
	
	public static void addDefaultFixture(Body b, float width, float height, int category, int mask, float friction){
		b.setTransform(b.getPosition().x + width*Constants.WORLD_TO_BOX/2, b.getPosition().y + height*Constants.WORLD_TO_BOX/2, 0);
		
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(width*Constants.WORLD_TO_BOX/2,height*Constants.WORLD_TO_BOX/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = .3f;
	    fixtureDef.friction = friction;
	    fixtureDef.restitution = 0f;
	    fixtureDef.filter.categoryBits = (short) category;
	    fixtureDef.filter.maskBits = (short) mask;	 
	    
	    b.createFixture(fixtureDef);
	    shape.dispose();
	}
	
	public static void addPlayerFixtureAndSensors(Body b, float width, float height, int category, int mask){
		addDefaultFixture(b, width, height, category, mask, 0);
		b.setSleepingAllowed(false);
		b.setBullet(true);
		
		CircleShape shape = new CircleShape();
		FixtureDef sensorfixtureDef = new FixtureDef();
		
		shape.setRadius(width/2 * Constants.WORLD_TO_BOX);
		shape.setPosition(new Vector2(0,-height*Constants.WORLD_TO_BOX/2 * .95f));
		sensorfixtureDef.shape = shape;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_FEET;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
		b.createFixture(sensorfixtureDef);
		
	}
	
	public static void removeBody(Body b){
		world.destroyBody(b);
	}


}
