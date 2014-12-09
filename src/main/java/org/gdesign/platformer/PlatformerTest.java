package org.gdesign.platformer;

import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.components.Behaviour;
import org.gdesign.platformer.components.Physics;
import org.gdesign.platformer.components.Position;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.entities.Enemy;
import org.gdesign.platformer.entities.Player;
import org.gdesign.platformer.entities.Slider;
import org.gdesign.platformer.entities.Upgrade;
import org.gdesign.platformer.managers.PlayerManager;
import org.gdesign.platformer.systems.AnimationRenderSystem;
import org.gdesign.platformer.systems.LightSimulationSystem;
import org.gdesign.platformer.systems.TextureRenderSystem;
import org.gdesign.platformer.systems.CameraSystem;
import org.gdesign.platformer.systems.DebugInfoSystem;
import org.gdesign.platformer.systems.SimulationSystem;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class PlatformerTest implements ApplicationListener, InputProcessor {
		
	private World world;
	private Box2DDebugRenderer debugRenderer;
	
	private boolean BOX2D_WORLD_DEBUG = true;
			
	public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "hello-world";
        cfg.useGL30 = false;
        cfg.width = 1024;
        cfg.height = 768;
        //cfg.x = 2000;
        cfg.resizable = false;
        cfg.vSyncEnabled = false;
        new LwjglApplication(new PlatformerTest(), cfg);
	}

	public void create() {
		Gdx.input.setInputProcessor(this);
		debugRenderer = new Box2DDebugRenderer();
		
		world = new World();
		
		world.setSystem(new CameraSystem());
		world.setSystem(new SimulationSystem(0, -16.8f));
		world.setSystem(new TextureRenderSystem());
		world.setSystem(new AnimationRenderSystem());
		world.setSystem(new LightSimulationSystem());
		world.setSystem(new DebugInfoSystem());		

		world.setManager(new PlayerManager());
		
		Entity floor = world.createEntity();
		floor.addComponent(new Position())
		.addComponent(new Physics(world,0, 0, 8000, 2,BodyType.StaticBody,Constants.CATEGORY_WORLD,Constants.MASK_WORLD,floor))
		.addToWorld();
		
		Entity wallLeft = world.createEntity();
		wallLeft.addComponent(new Position())
		.addComponent(new Physics(world,0, 0, 2, 1200,BodyType.StaticBody,Constants.CATEGORY_WORLD,Constants.MASK_WORLD,wallLeft))
		.addToWorld();

		Entity wallRight = world.createEntity();
		wallRight.addComponent(new Position())
		.addComponent(new Physics(world,1200, 0, 2, 1200,BodyType.StaticBody,Constants.CATEGORY_WORLD,Constants.MASK_WORLD,wallRight))
		.addToWorld();
		
		
		Entity testBox = world.createEntity();
		testBox.addComponent(new Position())
		.addComponent(new Physics(world,440, 150, 25, 25,BodyType.DynamicBody,Constants.CATEGORY_OBJECT,Constants.MASK_OBJECT,testBox))
		.addToWorld();
		
		
		
		//new Upgrade(world, 100, 120);
		//new Enemy(world, 500, 0, "scripts/behaviour/enemy.default.lua", "textures/entity/player/player.png");
		new Slider(world, 400, 100, "scripts/behaviour/slider.default.lua",.5f);
		new Slider(world, 800, 100, "scripts/behaviour/slider.default.lua",.7f);
		new Slider(world, 100, 300, "scripts/behaviour/slider.default.lua",.8f);
		new Slider(world, 300, 400, "scripts/behaviour/slider.default.lua",1f);
		new Slider(world, 600, 500, "scripts/behaviour/slider.default.lua",1.5f);
		world.getManager(PlayerManager.class).setPlayer(new Player(world, 470, 200));
		//world.getManager(PlayerManager.class).getPlayer().getComponent(Behaviour.class).setScript("scripts/control/testMov.lua");
		
	}
 
	public void dispose() {
		
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
		// TODO Auto-generated method stub
		
	}

	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean keyTyped(char character) {
		if (character == 'd') BOX2D_WORLD_DEBUG = (BOX2D_WORLD_DEBUG ? false : true);
		if (character == 'l') if ( world.getSystem(CameraSystem.class).isLocked() ) 
			world.getSystem(CameraSystem.class).lockCamera(false);
		else
			 world.getSystem(CameraSystem.class).lockCamera(true);
		return false;
	}

	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean scrolled(int amount) {
		world.getSystem(CameraSystem.class).setZoom(amount/10f);
		return false;
	}
	
}
