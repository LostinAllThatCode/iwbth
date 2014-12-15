package org.gdesign.platformer.systems;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.systems.EntityProcessingSystem;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.factories.EntityFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;

public class EntityControlSystem extends EntityProcessingSystem {
	
	private OrthographicCamera camera;
	private float zone = 100;
	
	@SuppressWarnings("unchecked")
	public EntityControlSystem() {
		setScope(Position.class);
	}
	
	@Override
	protected void initialize() {
		camera = world.getSystem(CameraSystem.class).getCamera();
		EntityFactory.initialize(world);
	}

	@Override
	protected void process(Entity entity) {
		Position position = entity.getComponent(Position.class);
		Vector3 cam = camera.position;
		
		if (entity.isEnabled()){
			if ( position.xPixel < (cam.x - camera.viewportWidth/2 - zone) || position.xPixel > (cam.x + camera.viewportWidth/2 + zone) ||
					position.yPixel < (cam.y - camera.viewportHeight/2 - zone) || position.yPixel > (cam.y + camera.viewportHeight/2 + zone)) {
				entity.disable();
				Gdx.app.debug(EntityControlSystem.class.toString(), "(-) " + entity);
			}
		} else {
			if ( position.xPixel > (cam.x - camera.viewportWidth/2 - zone) && position.xPixel < (cam.x + camera.viewportWidth/2 + zone) &&
					position.yPixel > (cam.y - camera.viewportHeight/2 - zone) && position.yPixel < (cam.y + camera.viewportHeight/2 + zone)) {
				entity.enable();
				Gdx.app.debug(EntityControlSystem.class.toString(), "(+) " + entity);
			}
		}
		
	}
	
	@Override
	public void changed(Entity e) {
	}

}
