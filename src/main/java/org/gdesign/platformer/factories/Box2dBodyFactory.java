package org.gdesign.platformer.factories;


import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;

public class Box2dBodyFactory {
	
	private static World world;
	
	/*
	public Body createStaticLine(World world, float x1,float y1, float x2, float y2, float angle, float friction){
		
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x1 * Constants.WORLD_TO_BOX ,
							y1 * Constants.WORLD_TO_BOX);
		bodyDef.angle = (float) Math.toRadians(angle);

		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
				
		EdgeShape shape = new EdgeShape();
        shape.set(new Vector2(0,0),
        		  new Vector2((x2-x1) * Constants.WORLD_TO_BOX, (y2-y1)  * Constants.WORLD_TO_BOX));
        
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 0.1f;
	    fixtureDef.friction = friction;
	    
		b.createFixture(fixtureDef);		
		return b;
	}
	
	public static Body createKynematicBox(World world, float x, float y, float w, float h, float angle){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.KinematicBody;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX + w * Constants.WORLD_TO_BOX / 2,
							 y * Constants.WORLD_TO_BOX + h * Constants.WORLD_TO_BOX / 2);
		bodyDef.angle = (float) Math.toRadians(angle);
		bodyDef.fixedRotation = true;
		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(w*Constants.WORLD_TO_BOX/2,h*Constants.WORLD_TO_BOX/2);

		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = .82f;
	    fixtureDef.filter.categoryBits = Constants.CATEGORY_UPGRADE;
	    fixtureDef.filter.maskBits = Constants.MASK_UPGRADE;
	    
		b.createFixture(fixtureDef);		
		return b;
	}
	
	public static Body createDynamicBox(World world,float x, float y, float w, float h, float angle){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX + w * Constants.WORLD_TO_BOX / 2,
							 y * Constants.WORLD_TO_BOX + h * Constants.WORLD_TO_BOX / 2);
		bodyDef.angle = (float) Math.toRadians(angle);
		bodyDef.fixedRotation = true;
		
		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
				
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(w*Constants.WORLD_TO_BOX/2,h*Constants.WORLD_TO_BOX/2);

		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = .82f;
	    fixtureDef.friction = 0f;
	    fixtureDef.restitution = 0f;
	    fixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER;
	    fixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    
		b.createFixture(fixtureDef);		
		return b;
	}
	
	public static Body createStaticBox(World world, float x, float y, float w, float h, float angle){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX + w * Constants.WORLD_TO_BOX / 2,
							 y * Constants.WORLD_TO_BOX + h * Constants.WORLD_TO_BOX / 2);
		bodyDef.angle = (float) Math.toRadians(angle);
		bodyDef.fixedRotation = false;
		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(w*Constants.WORLD_TO_BOX/2,h*Constants.WORLD_TO_BOX/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;	
	    fixtureDef.density = 2f;
	    fixtureDef.friction = 1f;
	    fixtureDef.filter.categoryBits = Constants.CATEGORY_WORLD;
	    fixtureDef.filter.maskBits = Constants.MASK_WORLD;
	    
		b.createFixture(fixtureDef);
		
		return b;
	}

	public static Body createStaticBox(World world, float x, float y, float w, float h, float angle, float friction){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.StaticBody;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX + w * Constants.WORLD_TO_BOX / 2,
							 y * Constants.WORLD_TO_BOX + h * Constants.WORLD_TO_BOX / 2);
		bodyDef.angle = (float) Math.toRadians(angle);
		bodyDef.fixedRotation = false;
		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
		
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(w*Constants.WORLD_TO_BOX/2,h*Constants.WORLD_TO_BOX/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;		
	    fixtureDef.friction = friction;

		b.createFixture(fixtureDef);
		
		return b;
	}
	
	public static Body createDynamicCircle(World world,float x, float y, float radius){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX  * Constants.WORLD_TO_BOX / 2,
							 y * Constants.WORLD_TO_BOX  * Constants.WORLD_TO_BOX / 2);
		bodyDef.fixedRotation = true;
		
		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
				
		CircleShape shape = new CircleShape();
        shape.setRadius(radius * Constants.WORLD_TO_BOX );

		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = 1f;
	    fixtureDef.friction = 0.0f;
	    fixtureDef.restitution = 0f;
	    
		b.createFixture(fixtureDef);		
		return b;
	}
	
	public static Body createPlayerBody(World world, float x, float y, float w, float h){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX + w * Constants.WORLD_TO_BOX / 2,
							 y * Constants.WORLD_TO_BOX + h * Constants.WORLD_TO_BOX / 2);
		bodyDef.fixedRotation = true;
		
		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
				
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(w*Constants.WORLD_TO_BOX/2,h*Constants.WORLD_TO_BOX/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = .82f;
	    fixtureDef.friction = 0f;
	    fixtureDef.restitution = 0f;
	    fixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER;
	    fixtureDef.filter.maskBits = Constants.MASK_PLAYER;	  
	    b.createFixture(fixtureDef);
	    
	    shape.setAsBox(w*Constants.WORLD_TO_BOX/3,h*Constants.WORLD_TO_BOX/20,new Vector2(0,-h*Constants.WORLD_TO_BOX/2),0);	    
	    FixtureDef sensorfixtureDef = new FixtureDef();
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_FEET;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    b.createFixture(sensorfixtureDef);
	 
	    shape.setAsBox(w*Constants.WORLD_TO_BOX/3,h*Constants.WORLD_TO_BOX/20,new Vector2(0,h*Constants.WORLD_TO_BOX/2),0);	    
	    sensorfixtureDef = new FixtureDef();
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_HEAD;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    b.createFixture(sensorfixtureDef);
	    
	    shape.setAsBox(w*Constants.WORLD_TO_BOX/8,h*Constants.WORLD_TO_BOX/3,new Vector2(-w*Constants.WORLD_TO_BOX/2,0),0);	    
	    sensorfixtureDef = new FixtureDef();
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_LEFT;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    b.createFixture(sensorfixtureDef);
	    
	    shape.setAsBox(w*Constants.WORLD_TO_BOX/8,h*Constants.WORLD_TO_BOX/3,new Vector2(w*Constants.WORLD_TO_BOX/2,0),0);		    
	    sensorfixtureDef = new FixtureDef();
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_RIGHT;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    b.createFixture(sensorfixtureDef);
		return b;
	}
	
	public static Body createEnemyBody(World world, float x, float y, float w, float h){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DynamicBody;
		bodyDef.position.set(x * Constants.WORLD_TO_BOX + w * Constants.WORLD_TO_BOX / 2,
							 y * Constants.WORLD_TO_BOX + h * Constants.WORLD_TO_BOX / 2);
		bodyDef.fixedRotation = true;
		
		Body b = world.getSystem(SimulationSystem.class).getSimulationWorld().createBody(bodyDef);
				
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(w*Constants.WORLD_TO_BOX/2,h*Constants.WORLD_TO_BOX/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = .82f;
	    fixtureDef.friction = 0f;
	    fixtureDef.restitution = 0f;
	    fixtureDef.filter.categoryBits = Constants.CATEGORY_ENEMY;
	    fixtureDef.filter.maskBits = Constants.MASK_ENEMY;	  
	    b.createFixture(fixtureDef);
	    
	    return b;
	}
	*/
	
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
		if (b.getFixtureList().size != 0 ) b.getFixtureList().clear();
		b.setTransform(b.getPosition().x + width*Constants.WORLD_TO_BOX/2, b.getPosition().y + height*Constants.WORLD_TO_BOX/2, 0);
		
		PolygonShape shape = new PolygonShape();
        shape.setAsBox(width*Constants.WORLD_TO_BOX/2,height*Constants.WORLD_TO_BOX/2);
		FixtureDef fixtureDef = new FixtureDef();
	    fixtureDef.shape = shape;
	    fixtureDef.density = .82f;
	    fixtureDef.friction = friction;
	    fixtureDef.restitution = 0f;
	    fixtureDef.filter.categoryBits = (short) category;
	    fixtureDef.filter.maskBits = (short) mask;	  
	    
	    b.createFixture(fixtureDef);	    
	}
	
	public static void addPlayerFixtureAndSensors(Body b, float width, float height, int category, int mask){
		addDefaultFixture(b, width, height, category, mask, 1);
		
		PolygonShape shape = new PolygonShape();
		FixtureDef sensorfixtureDef = new FixtureDef();
		 
	    shape.setAsBox(width*Constants.WORLD_TO_BOX/2 - width*Constants.WORLD_TO_BOX/8 ,.025f,new Vector2(0,-height*Constants.WORLD_TO_BOX/2-.025f),0);	       
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_FEET;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    
	    b.createFixture(sensorfixtureDef);
	    
	    shape.setAsBox(width*Constants.WORLD_TO_BOX/2 - width*Constants.WORLD_TO_BOX/8 ,.025f,new Vector2(0,height*Constants.WORLD_TO_BOX/2+.025f),0);	 	    
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_HEAD;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    b.createFixture(sensorfixtureDef);

	    shape.setAsBox(.025f,height*Constants.WORLD_TO_BOX/2 - height*Constants.WORLD_TO_BOX/8,new Vector2(-width*Constants.WORLD_TO_BOX/2 - .025f,0),0);	    
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_LEFT;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    b.createFixture(sensorfixtureDef);

	    shape.setAsBox(.025f,height*Constants.WORLD_TO_BOX/2 - height*Constants.WORLD_TO_BOX/8,new Vector2(width*Constants.WORLD_TO_BOX/2 + .025f,0),0);	    
	    sensorfixtureDef.shape = shape;
	    sensorfixtureDef.isSensor = true;
	    sensorfixtureDef.filter.categoryBits = Constants.CATEGORY_PLAYER_RIGHT;
	    sensorfixtureDef.filter.maskBits = Constants.MASK_PLAYER;
	    b.createFixture(sensorfixtureDef);
	    
	}
	
	public static void removeBody(Body b){
		world.destroyBody(b);
	}

	public static void initialize(World simulationWorld) {
		world = simulationWorld;
	}
}