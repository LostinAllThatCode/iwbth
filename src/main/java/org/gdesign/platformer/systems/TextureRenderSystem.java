package org.gdesign.platformer.systems;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.systems.EntityProcessingSystem;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.components.Renderable;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class TextureRenderSystem extends EntityProcessingSystem {
	
	private Position position;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Renderable render;

	@SuppressWarnings("unchecked")
	public TextureRenderSystem() {
		setScope(Renderable.class, Position.class);
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
			render = entity.getComponent(Renderable.class);
			position = entity.getComponent(Position.class);

			float x = position.xPixel + render.getOffset().x;
			float y = position.yPixel + render.getOffset().y;

			batch.begin();
			batch.draw(render.getRegion(),x - render.getRegion().getRegionWidth()/2 ,y - render.getRegion().getRegionHeight()/2,x,y,
					render.getRegion().getRegionWidth(),render.getRegion().getRegionHeight(),1,1,position.rDeg);
			batch.end();
			
			
		} catch (Exception e) {
			System.err.println(e);
		}
	}
	
}
