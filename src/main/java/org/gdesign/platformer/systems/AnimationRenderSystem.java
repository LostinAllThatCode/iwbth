package org.gdesign.platformer.systems;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.systems.EntityProcessingSystem;
import org.gdesign.platformer.components.Animatable;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationRenderSystem extends EntityProcessingSystem {
	
	private Position position;
	private SpriteBatch batch;
	private Animatable animatable;
	private OrthographicCamera camera;
	private Physics physics;
	
	private TextureRegion frame;

	@SuppressWarnings("unchecked")
	public AnimationRenderSystem() {
		setScope(Animatable.class, Position.class, Physics.class);
	}
	
	@Override
	protected void initialize() {
		batch = new SpriteBatch();
		camera = world.getSystem(CameraSystem.class).getCamera();
	}
	
	@Override
	protected void begin() {
		batch.setProjectionMatrix(camera.combined.cpy());
	}
	
	@Override
	protected void process(Entity entity) {
		try {
			animatable = entity.getComponent(Animatable.class);
			position = entity.getComponent(Position.class);
			physics = entity.getComponent(Physics.class);
			
			float x = position.xPixel + animatable.getOffset().x;
			float y = position.yPixel + animatable.getOffset().y;
			
			frame = animatable.getRegion(); 
			
			batch.begin();
			batch.draw(frame,x - frame.getRegionWidth()/2 ,y - physics.getBodyHeight()/2);
			batch.end();
			animatable.frame = null;
		} catch (Exception e) {
			System.err.println(e);
		}
	}

	
}
