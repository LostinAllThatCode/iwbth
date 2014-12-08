package org.gdesign.platformer.systems;

import java.util.ArrayList;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.systems.EntityProcessingSystem;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.entities.Enemy;
import org.gdesign.platformer.entities.Player;
import org.gdesign.platformer.entities.Slider;
import org.gdesign.platformer.entities.Upgrade;
import org.gdesign.platformer.factories.Box2dBodyFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

public class SimulationSystem extends EntityProcessingSystem implements ContactListener {
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
        Box2dBodyFactory.initialize(simulation);
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
			
			if (entity.hasComponent(Behaviour.class)) {
				entity.getComponent(Behaviour.class).runScript();
			}	
			
			position.setPosition(physics.body.getPosition().x,physics.body.getPosition().y);
			position.setRotation(physics.body.getAngle());
		} catch (Exception e) {
			System.err.println(e + entity.toString());
		}
	}
	
	@Override
	protected void end() {
	
	}
	
	private void beginCollision(Fixture src, Fixture tar) {
		Entity source = (Entity) src.getBody().getUserData();
		Entity target = (Entity) tar.getBody().getUserData();

		if (src.getFilterData().categoryBits == Constants.CATEGORY_PLAYER_FEET) {
			source.getComponent(Physics.class).setSensorCollision(Constants.CATEGORY_PLAYER_FEET, true);
		}
		if (tar.getFilterData().categoryBits == Constants.CATEGORY_PLAYER_FEET) {
			target.getComponent(Physics.class).setSensorCollision(Constants.CATEGORY_PLAYER_FEET, true);
		}
		
		if (source instanceof Enemy || source instanceof Upgrade || source instanceof Slider) source.getComponent(Behaviour.class).beginCollision(target);
		else if (target instanceof Enemy || target instanceof Upgrade || target instanceof Slider) target.getComponent(Behaviour.class).beginCollision(source);
	
	}
	
	private void endCollision(Fixture src, Fixture tar) {
		Entity source = (Entity) src.getBody().getUserData();
		Entity target = (Entity) tar.getBody().getUserData();
		
		if (src.isSensor() || tar.isSensor()){
			if (src.getFilterData().categoryBits == Constants.CATEGORY_PLAYER_FEET) {
				source.getComponent(Physics.class).setSensorCollision(Constants.CATEGORY_PLAYER_FEET, false);
			}
			if (tar.getFilterData().categoryBits == Constants.CATEGORY_PLAYER_FEET) {
				target.getComponent(Physics.class).setSensorCollision(Constants.CATEGORY_PLAYER_FEET, false);
			}
		}
		
		if (source instanceof Enemy || source instanceof Upgrade || source instanceof Slider) source.getComponent(Behaviour.class).endCollision(target);
		else if (target instanceof Enemy || target instanceof Upgrade || target instanceof Slider) target.getComponent(Behaviour.class).endCollision(source);
	
	}
	
	@Override
	public void removed(Entity e) {
		super.removed(e);
		Box2dBodyFactory.removeBody(e.getComponent(Physics.class).getBody());
	}


	public void beginContact(Contact contact) {
		beginCollision(contact.getFixtureA(), contact.getFixtureB());
	}


	public void endContact(Contact contact) {
		endCollision(contact.getFixtureA(), contact.getFixtureB());
	}


	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}


	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}

}
