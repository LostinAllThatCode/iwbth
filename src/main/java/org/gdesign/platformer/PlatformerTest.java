package org.gdesign.platformer;

import org.gdesign.games.ecs.World;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.factories.Box2dBodyFactory;
import org.gdesign.platformer.factories.EntityFactory;
import org.gdesign.platformer.managers.TextureManager;
import org.gdesign.platformer.systems.AnimationRenderSystem;
import org.gdesign.platformer.systems.EntityControlSystem;
import org.gdesign.platformer.systems.TextureRenderSystem;
import org.gdesign.platformer.systems.CameraSystem;
import org.gdesign.platformer.systems.DebugInfoSystem;
import org.gdesign.platformer.systems.SimulationSystem;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PlatformerTest implements ApplicationListener {
		
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	private boolean BOX2D_WORLD_DEBUG = true;
	
	private static LwjglApplication instance;
			
	public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "box2d physics platformer";
        cfg.useGL30 = false;
        cfg.width = 1024;
        cfg.height = 768;
        cfg.resizable = false;
        cfg.vSyncEnabled = true;
        instance = new LwjglApplication(new PlatformerTest(), cfg);
	}

	public void create() {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.input.setInputProcessor(new InputAdapter(){
			public boolean keyTyped(char character) {
				if (character == 'd') BOX2D_WORLD_DEBUG = (BOX2D_WORLD_DEBUG ? false : true);
				if (character == 'l') if ( world.getSystem(CameraSystem.class).isLocked() ) 
					world.getSystem(CameraSystem.class).lockCamera(false);
				else
					 world.getSystem(CameraSystem.class).lockCamera(true);
				return false;
			}

			public boolean scrolled(int amount) {
				world.getSystem(CameraSystem.class).setZoom(amount/10f);
				return false;
			}		
		});

		debugRenderer = new Box2DDebugRenderer();

		world = new World();
		
		
		
		world.setSystem(new SimulationSystem(0, -16.8f));
		world.setSystem(new CameraSystem());		
		world.setSystem(new TextureRenderSystem());
		world.setSystem(new AnimationRenderSystem());
		//world.setSystem(new LightSimulationSystem());
		world.setSystem(new EntityControlSystem());
		world.setSystem(new DebugInfoSystem());
		
		Box2dBodyFactory.addDefaultFixture(Box2dBodyFactory.createBody(BodyType.StaticBody, 0, 0, true)
				, 8000, 2, Constants.CATEGORY_WORLD, Constants.MASK_WORLD, 10).setUserData(Constants.CATEGORY_WORLD);
		
		EntityFactory.createEntityById(250, 50 , 0);
		EntityFactory.createEntityById(400, 50 , 1);
		EntityFactory.createEntityById(1200, 100, 2);	
	}
 
	public void dispose() {
		world.getManager(TextureManager.class).dispose();
		System.gc();
	}

	public void pause() {
		
	}

	public void render() {
		world.setDelta(Gdx.graphics.getDeltaTime());
		world.process();
		if (BOX2D_WORLD_DEBUG) debugRenderer.render(world.getSystem(SimulationSystem.class).getSimulationWorld(),
				world.getSystem(CameraSystem.class).getMatrix().scl(Constants.BOX_TO_WORLD));
	}

	public void resize(int arg0, int arg1) {		
	}

	public void resume() {
		
	}
	
	public static void crash(int exitcode){
		instance.getApplicationListener().dispose();
		System.exit(exitcode);
	}
}
