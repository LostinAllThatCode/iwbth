package org.gdesign.platformer.components;

import org.gdesign.games.ecs.BaseComponent;
import org.gdesign.games.ecs.Entity;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.core.Constants;
import org.gdesign.platformer.factories.Box2dBodyFactory;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Physics extends BaseComponent{

	public boolean SENSOR_FEET,SENSOR_HEAD,SENSOR_LEFT,SENSOR_RIGHT;	

	public Body body;
	private int w,h,cat;
	
	public Physics(World world, int x, int y, int width, int height, BodyType type, int category, int mask, Entity parent){
		switch (category) {
			case Constants.CATEGORY_PLAYER:
				body = Box2dBodyFactory.createBody(type, x, y, true);
				Box2dBodyFactory.addPlayerFixtureAndSensors(body, width, height, category, mask);
				break;
			case Constants.CATEGORY_ENEMY:
				body = Box2dBodyFactory.createBody(type, x, y, true);
				Box2dBodyFactory.addDefaultFixture(body, width, height, category, mask, 0);
				break;
			case Constants.CATEGORY_UPGRADE:
				body = Box2dBodyFactory.createBody(type, x, y, true);
				Box2dBodyFactory.addDefaultFixture(body, width, height, category, mask, 0);
				break;
			case Constants.CATEGORY_OBJECT:
				body = Box2dBodyFactory.createBody(type, x, y, true);
				Box2dBodyFactory.addDefaultFixture(body, width, height, category, mask, .05f);
				break;
			case Constants.CATEGORY_WORLD:
				body = Box2dBodyFactory.createBody(type, x, y, true);
				Box2dBodyFactory.addDefaultFixture(body, width, height, category, mask,10);
				break;
			default:
				break;
		}
		cat = category;
		
		w = width;
		h = height;
		body.setUserData(parent);
	}
	
	public void setSensorCollision(int category, boolean collision){
		switch (category) {
			case Constants.CATEGORY_PLAYER_FEET:
				
				SENSOR_FEET = collision;
				break;
			case Constants.CATEGORY_PLAYER_HEAD:
				SENSOR_HEAD = collision;
				break;
			case Constants.CATEGORY_PLAYER_LEFT:
				SENSOR_LEFT = collision;
				break;
			case Constants.CATEGORY_PLAYER_RIGHT:
				SENSOR_RIGHT = collision;
				break;
			default:
				break;
		}
	}
	
	public int getBodyWidth(){
		return w;
	}
	
	public int getBodyHeight(){
		return h;
	}
	
	public int getCategory(){
		return cat;
	}

	public Body getBody() {
		return body;
	}
	
	@Override
	public String toString() {
		return "x="+body.getPosition().x+",y="+body.getPosition().y+",vx="+body.getLinearVelocity().x+",vy="+body.getLinearVelocity().y;
	}

}
