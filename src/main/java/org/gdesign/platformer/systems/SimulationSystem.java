package org.gdesign.platformer.systems;

import java.util.ArrayList;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.systems.EntityProcessingSystem;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Controller;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.entities.Player;

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
		simulation.step(Gdx.graphics.getDeltaTime(), 6, 3);
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
			
			if (a.getBody().getUserData() instanceof Player) beginCollision(a, b);
			if (b.getBody().getUserData() instanceof Player) beginCollision(b, a);

		} catch (Exception e) {
			System.err.println(e);
		}
	}

	public void endContact(Contact contact) {
		try {
			Fixture a = contact.getFixtureA();
			Fixture b = contact.getFixtureB();
			
			if (a.getBody().getUserData() instanceof Player) endCollision(a, b);
			if (b.getBody().getUserData() instanceof Player) endCollision(b, a);
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
	private void beginCollision(Fixture src, Fixture tar) {
		Entity source = (Entity) src.getBody().getUserData();
		Entity target = (Entity) tar.getBody().getUserData();
		
		source.getComponent(Physics.class).setSensorCollision(src.getFilterData().categoryBits,true);
		
		switch (src.getFilterData().categoryBits) {
			case Constants.CATEGORY_PLAYER:
					switch (tar.getFilterData().categoryBits) {
						case Constants.CATEGORY_WORLD:
							break;
						case Constants.CATEGORY_UPGRADE:
							target.getComponent(Behaviour.class).handleCollision(source);
							break;
						default:
							break;
						}
				break;
				
			default:
				break;
			}
	}
	
	private void endCollision(Fixture src, Fixture tar) {
		Entity source = (Entity) src.getBody().getUserData();
		Entity target = (Entity) tar.getBody().getUserData();
		
		source.getComponent(Physics.class).setSensorCollision(src.getFilterData().categoryBits,false);
		
		switch (src.getFilterData().categoryBits) {
			case Constants.CATEGORY_PLAYER:
					switch (tar.getFilterData().categoryBits) {
						case Constants.CATEGORY_WORLD:
							break;
						case Constants.CATEGORY_UPGRADE:
							target.getComponent(Behaviour.class).handleCollision(source);
							break;
						default:
							break;
						}
				break;
				
			default:
				break;
			}
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
