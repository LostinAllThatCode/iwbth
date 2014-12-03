package org.gdesign.platformer.systems;

import java.util.HashMap;

import org.gdesign.games.ecs.systems.SingleProcessSystem;
import org.gdesign.platformer.core.Constants;

import box2dLight.ConeLight;
import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.World;

public class LightSimulationSystem extends SingleProcessSystem {
	private static int LIGHTS_INDEX=0;
	
	private HashMap<Integer,Light> lightList;
	private World simWorld;
	private OrthographicCamera camera;
	private RayHandler rayHandler;
	
	
	private float delta;
	@Override
	protected void initialize() {
		camera = world.getSystem(CameraSystem.class).getCamera();
		simWorld = world.getSystem(SimulationSystem.class).getSimulationWorld();
		lightList = new HashMap<Integer,Light>();		
		delta = 0f;
		setUp();
	}
	
	public void setUp(){
		Filter f = new Filter();
		f.categoryBits = Constants.CATEGORY_LIGHT;
		f.maskBits = Constants.MASK_LIGTH;
		f.groupIndex = 1;
		
		Light.setContactFilter(f);
		
		rayHandler = new RayHandler(simWorld);
		rayHandler.setAmbientLight(1, 1, 1, .5f);
		rayHandler.setBlur(true);
		rayHandler.setBlurNum(10);
		
        for (int i = 0; i < 4; i++){
        	addLight(new ConeLight(rayHandler, 100,Color.LIGHT_GRAY, 20, (150+(i*500)) * Constants.WORLD_TO_BOX,300 * Constants.WORLD_TO_BOX,-90,30));
        }
	}

	public void addLight(Light light){
		lightList.put(++LIGHTS_INDEX, light);
	}
	
	@Override
	public void processSystem() {
		
		delta += world.getDelta();
		for (Light l : lightList.values())
		{
			if (delta >= Math.random() && l instanceof ConeLight) {
				if (l.getDistance() == 10) l.setDistance(20);
				else l.setDistance(10);
				delta = 0f;
			}

		}
		
		//TODO: Handle lights...
		rayHandler.setCombinedMatrix(camera.combined.cpy().scl(Constants.BOX_TO_WORLD));
		rayHandler.updateAndRender();
	}
	
	public int getLightCount(){
		return LIGHTS_INDEX;
	}

}
