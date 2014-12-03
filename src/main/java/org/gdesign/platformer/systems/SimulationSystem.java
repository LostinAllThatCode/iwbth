package org.gdesign.platformer.systems;

import java.util.ArrayList;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.systems.EntityProcessingSystem;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Controller;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.entities.Enemy;
import org.gdesign.platformer.entities.Player;
import org.gdesign.platformer.entities.Upgrade;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

public class SimulationSystem extends EntityProcessingSystem implements ContactListener{
	private Physics physics;
	private Position position;
	
	private World simulation;
	private Vector2 gravity;

	private ArrayList<Body> deleteBody = new ArrayList<Body>();
	
	@SuppressWarnings("unchecked")
	public SimulationSystem(float x, float y) {
		super();
		setScope(Physics.class);
		gravity = new Vector2(x, y);
	}
	
	
	public World getSimulationWorld(){
		return this.simulation;
	}
	
	public void removeBody(Body b){
		deleteBody.add(b);
	}
	
	@Override
	public void initialize() {
		simulation = new World(gravity, true);
		simulation.setAutoClearForces(false);
        simulation.setContactListener(this);       
	}
	
	
	@Override
	protected void begin() {
		simulation.step(Gdx.graphics.getDeltaTime(), 8, 3);
	}
	
	@Override
	protected void process(Entity entity) {
		try {
			physics = entity.getComponent(Physics.class);
			position = entity.getComponent(Position.class);
			
			if (entity.hasComponent(Controller.class)) {
				entity.getComponent(Controller.class).runScript();
			}				
			
			if (entity.hasComponent(Behaviour.class)) {
				entity.getComponent(Behaviour.class).runScript();
			}	
			
			position.setPosition(physics.body.getPosition().x,physics.body.getPosition().y);
			position.setRotation(physics.body.getAngle());
		} catch (Exception e) {
			System.err.println(e + entity.toString());
		}
	}

	public void beginContact(Contact contact) {
		try {
			Fixture a = contact.getFixtureA();
			Fixture b = contact.getFixtureB();
			
			beginCollision(a, b);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void endContact(Contact contact) {
		try {
			Fixture a = contact.getFixtureA();
			Fixture b = contact.getFixtureB();
			
			endCollision(a, b);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	private void beginCollision(Fixture src, Fixture tar) {
		Entity source = (Entity) src.getBody().getUserData();
		Entity target = (Entity) tar.getBody().getUserData();
		
		if (source instanceof Enemy || source instanceof Upgrade) source.getComponent(Behaviour.class).beginCollision(target);
		if (target instanceof Enemy || target instanceof Upgrade) target.getComponent(Behaviour.class).beginCollision(source);
		
		if (source instanceof Player && tar.getFilterData().categoryBits == Constants.CATEGORY_WORLD) 
			source.getComponent(Physics.class).setSensorCollision(src.getFilterData().categoryBits, true);
		if (target instanceof Player && src.getFilterData().categoryBits == Constants.CATEGORY_WORLD) 
			target.getComponent(Physics.class).setSensorCollision(tar.getFilterData().categoryBits, true);
		
	}
	
	private void endCollision(Fixture src, Fixture tar) {
		Entity source = (Entity) src.getBody().getUserData();
		Entity target = (Entity) tar.getBody().getUserData();
		
		if (source instanceof Enemy || source instanceof Upgrade) source.getComponent(Behaviour.class).beginCollision(target);
		if (target instanceof Enemy || target instanceof Upgrade) target.getComponent(Behaviour.class).beginCollision(source);
		
		if (source instanceof Player && tar.getFilterData().categoryBits == Constants.CATEGORY_WORLD) 
			source.getComponent(Physics.class).setSensorCollision(src.getFilterData().categoryBits, false);
		if (target instanceof Player && src.getFilterData().categoryBits == Constants.CATEGORY_WORLD) 
			target.getComponent(Physics.class).setSensorCollision(tar.getFilterData().categoryBits, false);
		
		/*
		if (source instanceof Player) {
			if (target instanceof Enemy || target instanceof Upgrade) target.getComponent(Behaviour.class).beginCollision(source);
			if (tar.getFilterData().categoryBits == Constants.CATEGORY_WORLD) 
				source.getComponent(Physics.class).setSensorCollision(src.getFilterData().categoryBits, false);
		} else if (source instanceof Enemy || source instanceof Upgrade) {
			if (target instanceof Player) source.getComponent(Behaviour.class).beginCollision(target);
		} else if (src.getFilterData().categoryBits == Constants.CATEGORY_WORLD) {
			if (target instanceof Player) target.getComponent(Physics.class).setSensorCollision(tar.getFilterData().categoryBits, false);
		}
		*/
	}
	
	@Override
	public void added(Entity e) {
		super.added(e);
	}
	
	@Override
	public void removed(Entity e) {
		super.removed(e);
		simulation.destroyBody(e.getComponent(Physics.class).body);
	}

	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
	}


	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
