package org.gdesign.platformer.systems;

import org.gdesign.games.ecs.systems.SingleProcessSystem;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.managers.PlayerManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class CameraSystem extends SingleProcessSystem {
	
	private OrthographicCamera camera;
	private boolean lockCamera;
	
	public OrthographicCamera getCamera(){
		return camera;
	}
	
	public void lockCamera(boolean lock){
		lockCamera = lock; 
	}
	
	public boolean isLocked(){
		return lockCamera;
	}
	
	public void setZoom(float zoom) {
		if (camera.zoom + zoom <= 0.1f || camera.zoom +zoom >= 2f) return;
		camera.zoom += zoom;
	}

	@Override
	protected void initialize() {
		camera = new OrthographicCamera();
		camera.setToOrtho(false);
	}
	

	@Override
	protected void begin() {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
	}
	
	@Override
	public void processSystem() {
		if (!isLocked()) {
			Position position = world.getManager(PlayerManager.class).getPlayer().getComponent(Position.class);	

			float lerp = 0.25f;
			Vector3 camerapos = this.getCamera().position;
			camerapos.x += ((position.x * Constants.BOX_TO_WORLD) - camerapos.x) * lerp;
			camerapos.y += (((position.y * Constants.BOX_TO_WORLD) - camerapos.y) * lerp);			
		}
	}
	
	@Override
	protected void end() {
		camera.update();
	}
	
	public Matrix4 getMatrix(){
		return camera.combined.cpy();
	}

}
