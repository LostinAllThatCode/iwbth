package org.gdesign.platformer.components;

import org.gdesign.games.ecs.BaseComponent;
import org.gdesign.games.ecs.World;
import org.gdesign.platformer.Box2dBodyFactory;
import org.gdesign.platformer.core.Constants;

import com.badlogic.gdx.physics.box2d.Body;

public class Physics extends BaseComponent{

	public boolean _feet,_head,_left,_right;
	public Body body;
	private int w,h,cat;
	
	public Physics(World world, int x, int y, int width, int height, int category){
		cat = category;
		
		w = width;
		h = height;
		switch (category) {
			case Constants.CATEGORY_PLAYER:
				body = Box2dBodyFactory.createPlayerBody(world, x, y, width, height);
				break;
			case Constants.CATEGORY_ENEMY:
				body = Box2dBodyFactory.createEnemyBody(world, x, y, width, height);
				break;
			case Constants.CATEGORY_UPGRADE:
				body = Box2dBodyFactory.createKynematicBox(world, x, y, width, height,0);
				break;
			case Constants.CATEGORY_WORLD:
				body = Box2dBodyFactory.createStaticBox(world, x, y, width, height,0);
				break;
			default:
				break;
		}
		body.setUserData(this);
	}
	
	public Physics setUserdata(Object o){
		this.body.setUserData(o);
		return this;
	}
	
	public void setSensorCollision(int category, boolean collision){
		switch (category) {
			case Constants.CATEGORY_PLAYER_FEET:
				_feet = collision;
				break;
			case Constants.CATEGORY_PLAYER_HEAD:
				_head = collision;
				break;
			case Constants.CATEGORY_PLAYER_LEFT:
				_left = collision;
				break;
			case Constants.CATEGORY_PLAYER_RIGHT:
				_right = collision;
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

}
